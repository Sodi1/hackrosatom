<template>
    <div class="card-base device-card">
        <h4 class="name">{{ device.title }} {{ device.code }}</h4>
        <div class="coordinates">
            <div class="x">x: {{ device.lan }}</div>
            <div class="y">y: {{ device.lon }}</div>
        </div>

        <div class="additional-info">
            <div class="last-update-date">
                <span class="label">Последнее обновление: </span>
                <span class="value">{{ lastUpdatedDate }}</span>
            </div>
            <div class="protocol">
                <span class="label">Протокол: </span>
                <span class="value">{{ protocol }}</span>
            </div>
        </div>

        <DeviceSensorRow v-for="sensor in sensors" style="margin: 15px 0" :sensor="sensor" />
    </div>
</template>

<script>
import DeviceSensorRow from "@/components/DeviceSensorRow";
import { TelemetryService } from "@/services/TelemetryService";

const formatDate = (date) =>
    `${date.toLocaleDateString("ru-RU")} ${date.toLocaleTimeString("ru-RU").slice(0, 5)}`;

export default {
    name: "DeviceCard",
    components: { DeviceSensorRow },
    props: {
        device: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            subscriptionId: null,
            sensors: [],
            lastUpdatedDate: null,
            protocol: null
        };
    },
    mounted() {
        this.subscriptionId = TelemetryService.subscribeToDeviceTelemetryUpdates(
            this.device.id,
            10,
            (data) => this.updateTelemetryData(data)
        );
    },
    methods: {
        updateTelemetryData(data) {
            this.lastUpdatedDate = formatDate(new Date(data.telemetry[0].createdAt));

            this.protocol = data.telemetry[0].protocol;

            const telemetryValues = data.telemetry.map((t) => t.params);

            if (!telemetryValues || !telemetryValues.length) {
                return;
            }

            const aggregatedValues = [];

            telemetryValues[0]
                .map((t) => t.name)
                .forEach((n) => {
                    const values = [];

                    telemetryValues.forEach((t) => {
                        const value = t.find((x) => x.name === n).value;

                        values.push(value);
                    });

                    aggregatedValues.push({ name: n, values: values.reverse() });
                });

            this.sensors = aggregatedValues;
        }
    },
    destroyed() {
        TelemetryService.unsubscribeFromDeviceTelemetryUpdates(this.subscriptionId);
    }
};
</script>

<style scoped lang="scss">
.device-card {
    width: 655px;
}

.name {
    font-size: 21px;
    line-height: 32px;
}

.coordinates {
    display: flex;
    font-size: 12px;
    color: #9ca3af;

    .y {
        margin-left: 10px;
    }
}

.additional-info {
    margin-top: 14px;

    .last-update-date,
    .protocol {
        font-size: 15px;

        .label {
            color: #9ca3af;
        }

        .value {
            font-weight: 500;
        }
    }
}
</style>
