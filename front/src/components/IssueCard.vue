<template>
    <div class="card-base issue-card" :class="{ 'warning-card': waitingForFileUpload }">
        <div class="top">
            <div class="title d-flex align-center">
                <v-icon size="25" v-if="waitingForFileUpload" color="#D97706" class="mr-2">
                    error
                </v-icon>
                <span>{{ issue.issueTitle }}</span>
            </div>
            <div v-if="issue.isSatellite" class="satellite">
                <Icon size="25" name="satellite" />
            </div>
            <v-btn icon @click.prevent="deleteIssue">
                <v-icon>delete</v-icon>
            </v-btn>
        </div>
        <div class="description" v-html="issue.issueDescription" />

        <div class="action">
            <v-btn v-if="!waitingForFileUpload" text color="#1560A1">
                <v-icon left>file_download</v-icon>
                Отчет
            </v-btn>

            <v-menu v-else offset-y>
                <template v-slot:activator="{ on, attrs }">
                    <v-btn
                        v-bind="attrs"
                        v-on="on"
                        text
                        color="#D97706"
                        @click.prevent
                    >
                        <v-icon left>upload_file</v-icon>
                        Прикрепить фото
                    </v-btn>
                </template>
                <v-list>
                    <v-list-item @click="showFileUploadWindow">
                        <v-list-item-title>Спутник</v-list-item-title>
                    </v-list-item>
                    <v-list-item @click="showFileUploadWindow">
                        <v-list-item-title>Беспилотник</v-list-item-title>
                    </v-list-item>
                </v-list>
            </v-menu>
        </div>

        <UploadIssueFilesInput
            with-redirect
            :issue-id="issue.issueId"
            ref="uploadInput"
            @uploadFinish="redirectToIssue"
        />
    </div>
</template>

<script>
import Icon from "@/components/Icon";
import { IssueService } from "@/services/IssueService";
import UploadIssueFilesInput from "@/components/UploadIssueFilesInput";

export default {
    name: "IssueCard",
    components: { UploadIssueFilesInput, Icon },
    props: {
        issue: {
            type: Object,
            required: true
        }
    },
    computed: {
        waitingForFileUpload() {
            return this.issue.issueStatus === "WAIT_PHOTO";
        }
    },
    methods: {
        showFileUploadWindow() {
            this.$refs.uploadInput.open();
        },
        async deleteIssue() {
            try {
                await IssueService.deleteIssueById(this.issue.issueId);
            } catch (e) {
                alert("Error");

                console.log(e);
            }

            this.$emit("issueDeleted");
        },
        redirectToIssue() {
            const organizationId = this.$route.params.organizationId;

            this.$router.push(`/organization/${organizationId}/incident/${this.issue.issueId}`);
        }
    }
};
</script>

<style scoped lang="scss">
@keyframes pulse {
    0% {
        box-shadow: 0 0 0 0 rgb(217, 119, 6, 0.7);
    }

    70% {
        box-shadow: 0 0 0 10px rgb(217, 119, 6, 0);
    }

    100% {
        box-shadow: 0 0 0 0 rgb(217, 119, 6, 0);
    }
}

.issue-card {
    padding: 20px 30px;
    height: 100%;
    display: flex;
    flex-direction: column;

    &.warning-card {
        border: 1px solid #d97706;
        box-shadow: 0 0 0 0 rgb(217, 119, 6, 1);
        animation: pulse 2s infinite;
    }

    .top {
        display: flex;
        align-items: center;
        justify-content: space-between;

        .title {
            font-weight: 500;
            font-size: 24px;
        }
    }

    .description {
        font-size: 18px;
        color: #6b7280;
        margin-top: 10px;
        margin-bottom: 10px;
    }

    .action {
        margin-top: auto;
    }
}
</style>
