<template>
    <Layout>
        <template v-if="organization">
            <h2 class="mt-4">{{ organization.fullName }}</h2>

            <div class="d-flex">
                <div class="left-side">
                    <div class="d-flex mt-4">
                        <SingleValueCard
                            label="Количество датчиков"
                            :value="organization.devices.length"
                        />
                        <SingleValueCard label="Зона покрытия" value="3.5 км" class="ml-4" />
                        <SingleValueCard
                            label="Количество инцидентов"
                            :value="organization.devices.length"
                            class="ml-4"
                        />
                    </div>

                    <div class="cards mt-4">
                        <OrganizationInformationCard :organization="organization" />
                    </div>
                </div>
            </div>
            <MapComponent v-if="points" kind="devise" :points="points" class="map" />
        </template>
    </Layout>
</template>

<script>
import Layout from "@/components/Layout";
import DeviceCard from "@/components/DeviceCard";
import { OrganizationService } from "@/services/OrganizationService";
import OrganizationInformationCard from "@/components/OrganizationInformationCard";
import MapComponent from "@/components/MapComponent";
import SingleValueCard from "@/components/SingleValueCard";

export default {
    name: "OrganizationView",
    components: { OrganizationInformationCard, DeviceCard, Layout, SingleValueCard, MapComponent },
    data() {
        return {
            organization: null,
            points: null
        };
    },
    mounted() {
        const id = this.$route.params.organizationId;

        OrganizationService.getById(id).then((org) => {
            this.organization = org;
            this.points = [this.organization];
        });
    }
};
</script>

<style scoped lang="scss"></style>
