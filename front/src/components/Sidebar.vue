<template>
    <aside>
        <router-link to="/">
            <img src="../assets/img/logo.svg" alt="Logo" class="logo" />
        </router-link>

        <SidebarNavigation class="mt-12" />

        <div class="mt-auto">
            <img src="../assets/img/avatar.jpeg" alt="User avatar" class="avatar" />
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
    border-right: 3px solid #d7e7f5;
    padding: 16px 0;

    .logo {
        width: 60px;
    }

    .avatar {
        width: 50px;
        height: 50px;
        border-radius: 50%;
    }
}
</style>
