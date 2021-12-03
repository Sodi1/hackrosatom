<template>
    <aside>
        <router-link to="/">
            <img src="../assets/img/logo.svg" alt="Logo" class="logo" />
        </router-link>

        <SidebarNavigation />

        <div>
            <img src="../assets/img/avatar.jpeg" alt="User avatar" />
        </div>
    </aside>
</template>

<script>
import SidebarNavigation from "@/components/SidebarNavigation";
export default {
    name: "Sidebar",
    components: { SidebarNavigation }
};
</script>

<style scoped lang="scss">
aside {
    width: 80px;
    position: fixed;
    top: 0;
    bottom: 0;
    z-index: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;
    border-right: 3px solid #d7e7f5;
    padding: 16px 0;

    .logo {
        width: 60px;
    }
}
</style>
