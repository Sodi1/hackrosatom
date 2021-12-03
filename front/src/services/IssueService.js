import httpClient from "@/client/httpClient";
import { constructUrlWithQueryParams } from "@/client/query-params-utils";

export class IssueService {
    static subscribeToIssuesUpdates(callback) {
        return setInterval(async () => {
            const response = await httpClient().get(
                "/api/issue/findIssue?issueStatus=ACTIVE&issueStatus=WAIT_PHOTO"
            );
            callback(response.data);
        }, 1000);
    }

    static unsubscribeFromIssuesUpdates(id) {
        clearInterval(id);
    }

    static subscribeToOrganizationIssueUpdates(organizationId, callback) {
        return setInterval(async () => {
            const response = await this.findByOrganizationId(organizationId);
            const activeIssues = response.filter(i => i.issueStatus !== "INACTIVE");

            return callback(activeIssues);
        }, 1000)
    }

    static unsubscribeFromOrganizationIssueUpdates(subscriptionId) {
        clearInterval(subscriptionId);
    }

    static async findByOrganizationId(id) {
        const url = constructUrlWithQueryParams(`/api/issue/findByPlantId`, { plantId: id });
        const response = await httpClient().get(url);

        return response.data;
    }

    static async createIssue(issue) {
        await httpClient().post("/api/issue", issue);
    }
}
