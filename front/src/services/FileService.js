import { constructUrlWithQueryParams } from "@/client/query-params-utils";
import httpClient from "@/client/httpClient";

export class FileService {
    static async uploadIssueFiles(files, issueId) {
        const formData = new FormData();
        files.forEach((f) => formData.append("files", f));

        const url = constructUrlWithQueryParams("/api/upload", { issueId });
        await httpClient().post(url, formData);
    }
}
