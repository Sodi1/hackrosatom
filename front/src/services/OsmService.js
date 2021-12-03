import httpClient from "@/client/httpClient";
import { constructUrlWithQueryParams } from "@/client/query-params-utils";

export class OsmService {
    static async search(filters) {
        const url = constructUrlWithQueryParams(`https://osm.kovalev.team/search.php`, filters);

        const response = await httpClient().get(url);

        return response.data;
    }
}
