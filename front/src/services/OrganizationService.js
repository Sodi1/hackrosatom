import httpClient from "@/client/httpClient";

export class OrganizationService {
    static async getAll() {
        const response = await httpClient().get("/api/plant");

        return response.data;
    }

    static async getById(id) {
        const response = await httpClient().get(`/api/plant/${id}`);

        return response.data;
    }
}
