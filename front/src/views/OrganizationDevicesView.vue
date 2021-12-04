<template>
    <Layout>
        <Loader v-if="isLoading" />
        <div v-else class="mt-4 d-flex justify-space-between">
            <DeviceCard v-for="device in devices" :device="device" class="mb-6" />
        </div>
    </Layout>
</template>

<script>
import Layout from "@/components/Layout";
import { OrganizationService } from "@/services/OrganizationService";
import DeviceCard from "@/components/DeviceCard";
import Loader from "@/components/Loader";

export default {
    name: "OrganizationDevicesView",
    components: { Loader, DeviceCard, Layout },
    data() {
        return {
            isLoading: true,
            devices: []
        };
    },
    mounted() {
        const organizationId = this.$route.params.organizationId;

        OrganizationService.getById(organizationId).then((org) => {
            this.isLoading = false;
            this.devices = org.devices;
        });
    }
};
</script>

<style scoped></style>
