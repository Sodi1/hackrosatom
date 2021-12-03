<template>
    <div >
        <MapComponent v-if="points" kind="main" :points="points" class="map" />
    </div>
</template>


<script>
import MapComponent from "@/components/MapComponent";
import { OrganizationService } from "@/services/OrganizationService";

export default {
    name: "Home",
    components: {
        MapComponent
    },
    data() {
        return {
            orgTypeOptions: [],
            points: null,
        };
    },
    async mounted() {
        this.points = await OrganizationService.getAll({});
    }
};
</script>
<style scoped>
.map {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    z-index: 0;
}
</style>