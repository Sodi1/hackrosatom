import { constructUrlWithQueryParams } from "@/client/query-params-utils";
import httpClient from "@/client/httpClient";

export class FileService {
    static async uploadIssueFiles(files, issueId) {
        const formData = new FormData();
        for (const file of files) {
            formData.append("files", file);
        }

        const url = constructUrlWithQueryParams("/api/upload", { issueId });
        await httpClient().post(url, formData);
    }
}
