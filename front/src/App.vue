<template>
    <v-app>
        <router-view />

        <SvgIcons />
    </v-app>
</template>

<script>
import SvgIcons from "@/components/SvgIcons";

export default {
    components: { SvgIcons }
};
</script>

<style>
a {
    color: inherit !important;
    text-decoration: none !important;
}

body {
    color: #04153e;
}

.card-base {
    background: white;
    border: 1px solid #eaeaea;
    padding: 12px 20px;
}
</style>
