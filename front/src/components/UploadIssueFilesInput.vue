<template>
    <input
        type="file"
        multiple
        class="d-none"
        ref="fileInput"
        accept="image/*"
        @input="uploadSelectedFiles"
    />
</template>

<script>
import { FileService } from "@/services/FileService";

export default {
    name: "UploadIssueFilesInput",
    props: {
        issueId: {
            type: Number,
            required: true
        }
    },
    data() {
        return {
            uploadFiles: []
        };
    },
    methods: {
        async uploadSelectedFiles(e) {
            const files = e.target.files;
            await FileService.uploadIssueFiles(files, this.issueId);

            this.$emit("uploadFinish");
        },
        open() {
            this.$refs.fileInput.dispatchEvent(new MouseEvent("click"));
        }
    }
};
</script>

<style scoped></style>
