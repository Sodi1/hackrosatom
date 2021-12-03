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
        path: "/organization/:id",
        name: "Organization",
        component: () => import("@/views/OrganizationView.vue")
    },
    {
        path: "/organization/:id/devices",
        name: "Organization devices",
        component: () => import("@/views/OrganizationDevicesView.vue")
    },
    {
        path: "/organization/:id/incidents",
        name: "Organization incidents",
        component: () => import("@/views/OrganizationIncidentsView.vue")
    }
];

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes
});

export default router;
