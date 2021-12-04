import Vue from "vue";
import VueRouter from "vue-router";
import Home from "@/views/Home.vue";

Vue.use(VueRouter);

const routes = [
    {
        path: "/",
        name: "Home",
        component: Home
    },
    {
        path: "/organization/:organizationId",
        name: "Organization",
        component: () => import("@/views/OrganizationView.vue")
    },
    {
        path: "/organization/:organizationId/devices",
        name: "Organization devices",
        component: () => import("@/views/OrganizationDevicesView.vue")
    },
    {
        path: "/organization/:organizationId/incidents",
        name: "Organization incidents",
        component: () => import("@/views/OrganizationIncidentsView.vue")
    },
    {
        path: "/organization/:organizationId/incident/:incidentId",
        name: "Incident",
        component: () => import("@/views/IncidentView.vue")
    }
];

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes
});

export default router;
