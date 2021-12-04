<template>
    <nav>
        <router-link :to="`/organization/${organizationId}`" class="nav-link" exact>
            <Icon :width="36" :height="36" name="info-navigation" />
        </router-link>
        <router-link :to="`/organization/${organizationId}/devices`" class="nav-link">
            <Icon :width="40" :height="40" name="device-navigation" />
        </router-link>
        <router-link
            :to="`/organization/${organizationId}/incidents`"
            class="nav-link incidents-link"
        >
            <v-icon size="40" title="Инциденты">error</v-icon>
            <div v-if="incidentsCount" class="incidents-count">
                {{ incidentsCount < 10 ? incidentsCount : "9+" }}
            </div>
        </router-link>
    </nav>
</template>

<script>
import Icon from "@/components/Icon";
import { IssueService } from "@/services/IssueService";
export default {
    name: "SidebarNavigation",
    components: { Icon },
    data() {
        return {
            organizationId: null,
            incidentsCount: 0,
            subscriptionId: null
        };
    },
    created() {
        this.organizationId = this.$route.params.organizationId;
    },
    mounted() {
        this.subscriptionId = IssueService.subscribeToOrganizationIssueUpdates(
            this.organizationId,
            (activeIssues) => (this.incidentsCount = activeIssues.length)
        );
    },
    destroyed() {
        IssueService.unsubscribeFromOrganizationIssueUpdates(this.subscriptionId);
    }
};
</script>

<style scoped lang="scss">
nav {
    display: flex;
    flex-direction: column;
    align-items: center;

    .nav-link {
        width: 80px;
        height: 80px;
        display: flex;
        align-items: center;
        justify-content: center;
        fill: #9ca3af;

        i {
            color: #9ca3af;
        }

        &.router-link-active {
            background: #d7e7f5;
            fill: #1560a1;

            i {
                color: #1560a1;
            }
        }
    }

    .incidents-link {
        position: relative;

        .incidents-count {
            position: absolute;
            top: 14px;
            right: 16px;
            font-weight: bold;
            width: 22px;
            height: 22px;
            border-radius: 50%;
            color: white;
            background: #dc2626;
            display: flex;
            align-items: center;
            justify-content: center;
        }
    }
}
</style>
