<template>
    <Layout>
        <h2 v-if="!issues.length" class="mt-4">Инцидентов нет</h2>

        <div class="issue-cards mt-4">
            <router-link :to="`/organization/${organizationId}/incident/${i.issueId}`" v-for="i in issues" >
                <IssueCard :issue="i" @issueDeleted="loadIssues" />
            </router-link>
        </div>

        <CreateIssueModal
            :organization-id="organizationId"
            ref="createIssueModal"
            @issueCreate="loadIssues"
        />

        <v-btn
            dark
            absolute
            right
            fab
            color="#1560A1"
            style="bottom: 10px"
            @click="$refs.createIssueModal.open()"
        >
            <v-icon>add</v-icon>
        </v-btn>
    </Layout>
</template>

<script>
import Layout from "@/components/Layout";
import IssueCard from "@/components/IssueCard";
import { IssueService } from "@/services/IssueService";
import CreateIssueModal from "@/components/CreateIssueModal";

export default {
    name: "OrganizationIncidentsView",
    components: { CreateIssueModal, IssueCard, Layout },
    data() {
        return {
            issues: [],
            organizationId: null
        };
    },
    created() {
        this.organizationId = parseInt(this.$route.params.organizationId);
    },
    mounted() {
        this.loadIssues();
    },
    methods: {
        loadIssues() {
            IssueService.findByOrganizationId(this.organizationId).then((issues) => {
                this.issues = issues.sort((a, b) => b.issueStatus.localeCompare(a.issueStatus));
            });
        }
    }
};
</script>

<style scoped lang="scss">
.issue-cards {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 20px;
}
</style>
