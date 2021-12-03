import httpClient from "@/client/httpClient";
import { constructUrlWithQueryParams } from "@/client/query-params-utils";

export class TelemetryService {
    static subscribeToDeviceTelemetryUpdates(deviceId, telemetrySize, callback) {
        return setInterval(async () => {
            const url = constructUrlWithQueryParams("/api/plant/telemetry/device/find", { deviceId, telemetrySize });
            const response = await httpClient().get(url);

            callback(response.data);
        }, 1000);
    }

    static unsubscribeFromDeviceTelemetryUpdates(id) {
        clearInterval(id);
    }
}
