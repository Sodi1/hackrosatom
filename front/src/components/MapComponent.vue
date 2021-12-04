<template>
    <div>
        <div v-if="kind == 'main'" class="d-flex justify-center mb-6 pt-3">
            <div style="width: 50%">
                <v-autocomplete
                    :search-input.sync="search"
                    v-model="selectedPlant"
                    clearable
                    solo
                    cache-items
                    item-text="fullName"
                    return-object
                    :filter="filterObject"
                    :items="plants"
                >
                    <template v-slot:selection="data">
                        {{ data.item.fullName }}
                    </template>
                    <template v-slot:item="data">
                        <template>
                            <v-list-item-content>
                                {{ data.selected }}
                                <v-list-item-title>{{
                                    data.item.isLeak ? "🔴" : "" + data.item.fullName
                                }}</v-list-item-title>
                                <v-list-item-subtitle
                                    v-html="data.item.factAddress"
                                ></v-list-item-subtitle>
                            </v-list-item-content>
                        </template>
                    </template>
                </v-autocomplete>
            </div>
            <div class="pl-6">
                <v-select
                    v-model="selectedFilter"
                    style="width: 100%; height: 46px"
                    solo
                    item-value="value"
                    item-text="name"
                    :items="filters"
                    @input="loadPoints"
                    multiple
                    label="Фильтр по типу"
                ></v-select>
            </div>
        </div>
        <l-map :zoom="zoom" :center="center" ref="map" v-bind:style="mapStyle">
            <l-tile-layer :url="url"></l-tile-layer>
            <v-marker-cluster ref="clusterRef">
                <l-geo-json
                    ref="plants"
                    :geojson="plantsGeojson"
                    :options="plantsOptions"
                    :options-style="styleFunction"
                ></l-geo-json>
            </v-marker-cluster>
            <l-geo-json
                ref="devices"
                :geojson="deviseGeojson"
                :options="deviseOptions"
                :options-style="styleFunction"
            ></l-geo-json>
            <v-marker-cluster ref="oilClusterRef">
                <l-geo-json
                    ref="oilGroove"
                    :geojson="oilGrooveGeojson"
                    :options="oilGrooveOptions"
                ></l-geo-json>
            </v-marker-cluster>
            <l-geo-json
                ref="pipelines"
                :geojson="pipelineGeojson"
                :options="pipelineOptions"
                :options-style="pipelineStyleFunction"
            ></l-geo-json>
            <l-geo-json
                ref="issues"
                :geojson="issuesGeojson"
                :options="issuesOptions"
                :options-style="issuesStyleFunction"
            ></l-geo-json>
            <l-geo-json ref="issues" :geojson="citiesGeojson" :options="citiesOptions"></l-geo-json>
        </l-map>
    </div>
</template>
<script>
import { LMap, LTileLayer, LGeoJson, LMarker, LIcon } from "vue2-leaflet";
import { Icon } from "leaflet";
import { IssueService } from "@/services/IssueService";
import Vue2LeafletMarkerCluster from "vue2-leaflet-markercluster";

const appUrl =
    process.env.NODE_ENV === "production" ? "https://oil.kovalev.team" : "http://localhost:8080";

delete Icon.Default.prototype._getIconUrl;
Icon.Default.mergeOptions({
    iconRetinaUrl: require("leaflet/dist/images/marker-icon-2x.png"),
    iconUrl: require("leaflet/dist/images/marker-icon.png"),
    shadowUrl: require("leaflet/dist/images/marker-shadow.png")
});
export default {
    components: {
        LMap,
        LTileLayer,
        LGeoJson,
        LMarker,
        LIcon,
        "v-marker-cluster": Vue2LeafletMarkerCluster
    },
    name: "MapComponent",
    props: {
        kind: String,
        points: Array
    },
    data() {
        return {
            url: "https://tile.openstreetmap.org/{z}/{x}/{y}.png",
            zoom: 4,
            center: [65.7558, 80.6173],
            plantsGeojson: {
                features: []
            },
            deviseGeojson: {
                features: []
            },
            pipelineGeojson: {
                features: []
            },
            oilGrooveGeojson: {
                features: []
            },
            issuesGeojson: {
                features: []
            },
            citiesGeojson: {
                features: []
            },
            pipelineOptions: {},
            plantsOptions: {},
            deviseOptions: {},
            citiesOptions: {},
            issuesOptions: {},
            oilGrooveOptions: {},
            maxCount: 0,
            markers: [],
            regions: [],
            statistics: [],
            plants: [],
            issues: [],
            issuesMarkers: [],
            selectedPlant: null,
            search: null,
            map: null,
            deviseMarkers: [],
            subscriptionId: null,
            selectedFilter: ["NPZ", "NPS", "MINING"],
            filters: [
                {
                    name: "НПС",
                    value: "NPS"
                },
                {
                    name: "НПЗ",
                    value: "NPZ"
                },
                {
                    name: "Добыча",
                    value: "MINING"
                }
            ]
        };
    },

    async mounted() {
        this.map = this.$refs.map.mapObject;
        this.loadPoints();
        this.loadDeviseMarkers();
        this.subscriptionId = IssueService.subscribeToIssuesUpdates((data) =>
            this.loadIssues(data)
        );
        if (this.kind == "devise") {
            this.map.fitBounds(
                this.deviseMarkers
                    .map((dm) => {
                        return dm.geometry.coordinates;
                    })
                    .concat(this.markers.map((dm) => dm.geometry.coordinates))
            );
        }

        this.pipelineGeojson.features = require("@/assets/geojsons/map-objects.json").features;
        this.oilGrooveGeojson.features = require("@/assets/geojsons/oilGrove.json").features;
        console.log(this.pipelineGeojson.features);
        this.plantsOptions = {
            onEachFeature: this.onEachFeature,
            pointToLayer: this.pointToLayer,
            limitMarkersCount: 1,
            limitMarkersCountGlobally: true,
            coordsToLatLng: (coords) => {
                return [coords[0], coords[1]];
            }
        };
        this.deviseOptions = {
            onEachFeature: this.deviseOnEachFeature,
            pointToLayer: this.devisePointToLayer,
            limitMarkersCount: 1,
            limitMarkersCountGlobally: true,
            coordsToLatLng: (coords) => {
                return [coords[0], coords[1]];
            }
        };
        this.pipelineOptions = {
            onEachFeature: this.pipelineOnEachFeature,
            pointToLayer: this.pipelinePointToLayer,
            coordsToLatLng: (coords) => {
                return [coords[0], coords[1]];
            },
            limitMarkersCount: 1,
            limitMarkersCountGlobally: false
        };
        this.issuesOptions = {
            onEachFeature: this.issuesOnEachFeature,
            pointToLayer: this.issuesPointToLayer,
            limitMarkersCount: 1,
            limitMarkersCountGlobally: false
        };
        this.citiesOptions = {
            onEachFeature: this.citiesOnEachFeature,
            pointToLayer: this.citiesPointToLayer,
            limitMarkersCount: 1,
            limitMarkersCountGlobally: false
        };
        this.oilGrooveOptions = {
            onEachFeature: this.oilGrooveOnEachFeature,
            pointToLayer: this.oilGroovePointToLayer,
            limitMarkersCount: 1,
            limitMarkersCountGlobally: false
        };
    },
    methods: {
        loadIssues(data) {
            this.issues = data;
            let issues = this.issues;
            this.issuesMarkers = this.issues
                .map((issue) => {
                    return issue.triggeredDevices.map((device) => {
                        if (!!Number(device.lon) && !!Number(device.lan)) {
                            return {
                                type: "Feature",
                                properties: {
                                    kind: "Issue",
                                    issue: issue
                                },
                                geometry: {
                                    type: "Point",
                                    coordinates: [Number(device.lon), Number(device.lan)]
                                    //
                                }
                            };
                        }
                    });
                })
                .flat();
            this.citiesMarkers = this.issues
                .map((issue) => {
                    return issue.affectedCity.map((city) => {
                        if (!!Number(city.geoLat) && !!Number(city.geoLon)) {
                            return {
                                type: "Feature",
                                properties: {
                                    kind: "city",
                                    issue: issue,
                                    city: city
                                },
                                geometry: {
                                    type: "Point",
                                    coordinates: [Number(city.geoLon), Number(city.geoLat)]
                                    //
                                }
                            };
                        }
                    });
                })
                .flat();

            this.citiesGeojson.features = this.citiesMarkers;
            this.issuesGeojson.features = this.issuesMarkers;
        },
        loadPoints() {
            this.plants = this.points;
            this.markers = this.points
                .map((p) => {
                    if (!!Number(p.lat) && !!Number(p.lon)) {
                        return {
                            type: "Feature",
                            properties: {
                                kind: "Plant",
                                organization: p
                            },
                            geometry: {
                                type: "Point",
                                coordinates: [Number(p.lat), Number(p.lon)]
                                //
                            }
                        };
                    }
                })
                .filter((a) => a);
            this.plantsGeojson.features = this.markers;
        },
        loadDeviseMarkers() {
            this.deviseMarkers = this.plants
                .map((plant) => {
                    return plant.devices;
                })
                .flat()
                .map((devise) => {
                    if (devise.lan) {
                        return {
                            type: "Feature",
                            properties: {
                                kind: "Devise",
                                devise: devise
                            },
                            geometry: {
                                type: "Point",
                                coordinates: [Number(devise.lan), Number(devise.lon)]
                                //
                            }
                        };
                    }
                })
                .filter((a) => a);
            this.deviseGeojson.features = this.deviseMarkers;
        },
        whenClicked(e) {
            if (e.target.feature.geometry.type === "Point") {
                const organizationId = e.target.feature.properties.organization.id;
                window.open(`${appUrl}/organization/${organizationId}`, "_blank").focus();
            }
        },
        whenClickedIssue(e) {
            if (e.target.feature.geometry.type === "Point") {
                const organizationId = e.target.feature.properties.issue.plantId;
                window.open(`${appUrl}/organization/${organizationId}`, "_blank").focus();
            }
        },
        whenClickedCity(e) {
            if (e.target.feature.geometry.type === "Point") {
                const organizationId = e.target.feature.properties.issue.plantId;
                window.open(`${appUrl}/organization/${organizationId}`, "_blank").focus();
            }
        },
        deviseClicked(e) {
            if (e.target.feature.geometry.type === "Point") {
                const plantId = e.target.feature.properties.devise.plantId;
                window.open(`${appUrl}/organization/${plantId}`, "_blank").focus();
            }
        },
        pointToLayer: function (feature, latlng) {
            if (this.selectedFilter.includes(feature.properties.organization.plantKind)) {
                let img;
                if (feature.properties.organization.plantKind == "MINING") {
                    img = require("@/assets/img/mining.png");
                } else {
                    img = require("@/assets/img/plant.png");
                }
                return L.marker(latlng, {
                    icon: L.icon({
                        iconUrl: img,
                        iconSize: [30, 47]
                    })
                });
            }
        },

        deviseOnEachFeature(feature, layer) {
            layer.on({
                click: this.deviseClicked
            });
            layer.bindTooltip(
                "<div>" +
                    "<a href='https://oil-api.kovalev.team/organization/" +
                    feature.properties.devise.id +
                    "'>" +
                    feature.properties.devise.title +
                    "</a></div>",
                {
                    permanent: false,
                    sticky: true
                }
            );
        },
        devisePointToLayer: function (feature, latlng) {
            if (feature.properties.kind == "Devise") {
                return L.marker(latlng, {
                    icon: L.icon({
                        iconUrl: require("@/assets/img/buy.png"),
                        iconSize: [25, 41]
                    })
                });
            }
        },
        pipelineOnEachFeature(feature, layer) {
            layer.on({
                click: this.whenClicked
            });
            layer.bindTooltip(
                "<div> <p>" +
                    feature.properties.clusterCaption +
                    "</p> </div>" +
                    feature.properties.balloonContentBody,
                {
                    permanent: false,
                    sticky: true
                }
            );
        },
        pipelinePointToLayer: function (feature, latlng) {
            if (feature.properties.kind == "Devise") {
                return L.marker(latlng, {
                    icon: L.icon({
                        iconUrl: require("@/assets/img/buy.png"),
                        iconSize: [25, 41]
                    })
                });
            }
        },
        issuesOnEachFeature(feature, layer) {
            layer.on({
                click: this.whenClickedIssue
            });
            layer.bindTooltip(
                "<div> <p>" +
                    feature.properties.issue.issueTitle +
                    "<br/><br/>Затронутые города:" +
                    "<ul>" +
                    feature.properties.issue.affectedCity
                        .map((city) => {
                            return "<li>" + city.addressCity + "</li>";
                        })
                        .join("") +
                    "</ul>" +
                    "<br/>Информация:<br/>" +
                    feature.properties.issue.issueDescription +
                    "</p> </div>",
                {
                    permanent: false,
                    sticky: true
                }
            );
        },
        issuesPointToLayer: function (feature, latlng) {
            return L.marker(latlng, {
                icon: L.icon({
                    iconUrl: require("@/assets/img/red.gif"),
                    iconSize: [41, 41]
                })
            });
        },
        citiesOnEachFeature(feature, layer) {
            layer.on({
                click: this.whenClickedCity
            });
            layer.bindTooltip(
                "<div> <p>" +
                    feature.properties.issue.issueTitle +
                    "<br/><br/>Затронутые города:" +
                    "<ul>" +
                    feature.properties.issue.affectedCity
                        .map((city) => {
                            return "<li>" + city.addressCity + "</li>";
                        })
                        .join("") +
                    "</ul>" +
                    "<br/>Информация:<br/>" +
                    feature.properties.issue.issueDescription +
                    "</p> </div>",
                {
                    permanent: false,
                    sticky: true
                }
            );
        },
        citiesPointToLayer: function (feature, latlng) {
            return L.marker(latlng, {
                icon: L.icon({
                    iconUrl: require("@/assets/img/city_alarm.gif"),
                    iconSize: [25, 25],
                    iconAnchor: [12, 41],
                    popupAnchor: [1, -34],
                    shadowUrl: require("leaflet/dist/images/marker-shadow.png"),
                    shadowSize: [41, 41],
                    shadowAnchor: [12, 41]
                })
            });
        },
        oilGrooveOnEachFeature(feature, layer) {
            layer.on({
                click: this.whenClickedCity
            });
            layer.bindTooltip("<div> 123 </div>", {
                permanent: false,
                sticky: true
            });
        },
        oilGroovePointToLayer: function (feature, latlng) {
            return L.marker(latlng, {
                icon: L.icon({
                    iconUrl: require("@/assets/img/oil_miss.png"),
                    iconSize: [25, 25]
                })
            });
        },
        onEachFeature(feature, layer) {
            layer.on({
                click: this.whenClicked
            });
            layer.bindTooltip(
                "<div>" +
                    "<a href='https://oil-api.kovalev.team/organization/" +
                    feature.properties.organization.id +
                    "'>" +
                    feature.properties.organization.shortName +
                    "</a></div>",
                {
                    permanent: false,
                    sticky: true
                }
            );
        },
        filterObject(item, queryText, itemText) {
            return (
                item?.shortName?.toLowerCase()?.indexOf(queryText.toLowerCase()) > -1 ||
                item?.fullName?.toLowerCase()?.indexOf(queryText.toLowerCase()) > -1 ||
                item?.factAddress?.toLowerCase()?.indexOf(queryText.toLowerCase()) > -1 ||
                item?.factAddress?.toLowerCase()?.indexOf(queryText.toLowerCase()) > -1
            );
        }
    },
    computed: {
        mapStyle() {
            if (this.kind == "main") {
                return {
                    height: "100%",
                    width: "100%",
                    position: "absolute",
                    top: 0,
                    left: 0,
                    right: 0,
                    bottom: 0,
                    "z-index": -1
                };
            } else {
                return {
                    height: "520px",
                    width: "1060px",
                    "margin-top": "20px",
                    // position: "absolute",
                    // top: 0,
                    // left: 0,
                    // right: 0,
                    // bottom: 0,
                    "z-index": 1
                };
            }
        },
        styleFunction() {
            return (obj) => {
                let fillOpacity = 0.1;
                if (obj.properties.types?.всего) {
                    fillOpacity = obj.properties.types.всего / this.maxAll;
                }
                return {
                    weight: 2,
                    color: "#1E3A8A",
                    opacity: 1,
                    fillOpacity: 0.9
                };
            };
        },
        pipelineStyleFunction() {
            return (obj) => {
                let fillOpacity = 0.1;
                if (obj.properties.types?.всего) {
                    fillOpacity = obj.properties.types.всего / this.maxAll;
                }
                return {
                    weight: 2,
                    color: "#000000",
                    opacity: 1,
                    fillOpacity: 0.9
                };
            };
        },
        issuesStyleFunction() {
            return (obj) => {
                let fillOpacity = 0.1;
                if (obj.properties.types?.всего) {
                    fillOpacity = obj.properties.types.всего / this.maxAll;
                }
                return {
                    weight: 2,
                    color: "#000000",
                    opacity: 1,
                    fillOpacity: 0.9
                };
            };
        }
    },
    watch: {
        selectedPlant: function (val) {
            if (val == null || val == undefined || val.length == 0) {
                this.map.setView(this.center, this.zoom);
            } else {
                this.map.setView([val.lat, val.lon], 12);
                this.map.setView([val.lat, val.lon], 12, { animate: true });
            }
        }
    },
    destroyed() {
        IssueService.unsubscribeFromIssuesUpdates(this.subscriptionId);
    }
};
</script>
<style scoped>
@import "~leaflet.markercluster/dist/MarkerCluster.css";
@import "~leaflet.markercluster/dist/MarkerCluster.Default.css";
</style>
