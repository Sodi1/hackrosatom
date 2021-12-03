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
                <Icon size="30" name="satellite" />
            </div>
        </div>
        <div class="description" v-html="issue.issueDescription" />

        <div class="action">
            <v-btn v-if="!waitingForFileUpload" text color="#1560A1">
                <v-icon left>file_download</v-icon>
                Отчет
            </v-btn>

            <v-btn
                v-else
                text
                color="#D97706"
                :loading="fileUploadInProcess"
                @click="showFileUploadWindow"
            >
                <v-icon left>upload_file</v-icon>
                Прикрепить фото
            </v-btn>
        </div>

        <input type="file" multiple class="d-none" ref="fileInput" @input="uploadSelectedFiles" />
    </div>
</template>

<script>
import Icon from "@/components/Icon";
import { FileService } from "@/services/FileService";

export default {
    name: "IssueCard",
    components: { Icon },
    props: {
        issue: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            uploadFiles: [],
            fileUploadInProcess: false
        };
    },
    computed: {
        waitingForFileUpload() {
            return this.issue.issueStatus === "WAIT_PHOTO";
        }
    },
    methods: {
        async uploadSelectedFiles(e) {
            const files = e.target.files;

            this.fileUploadInProcess = true;
            try {
                await FileService.uploadIssueFiles(files, this.issue.issueId);

                this.$emit("issueCreate");
            } catch (e) {
                alert("Error");

                console.error(e);
            } finally {
                this.fileUploadInProcess = false;
            }
        },
        showFileUploadWindow() {
            this.$refs.fileInput.dispatchEvent(new MouseEvent("click"));
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
    }

    .action {
        margin-top: 60px;
    }
}
</style>
