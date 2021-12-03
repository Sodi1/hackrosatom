<template>
    <div style="min-height: 100vh; background: #f9fbff">
        <Sidebar />

        <div class="wrapper">
            <slot />
        </div>
    </div>
</template>

<script>
import Sidebar from "@/components/Sidebar";

export default {
    name: "Layout",
    components: { Sidebar }
};
</script>

<style lang="scss">
.wrapper {
    width: 1410px;
    margin: 0 auto;
    padding-left: 80px;
}
</style>
