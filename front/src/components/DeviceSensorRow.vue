<template>
    <div class="device-sensor-card">
        <div class="icon icon-green">
            <Icon :size="35" :name="sensorIconName" />
        </div>

        <div class="device-info">
            <div class="name">{{ sensor.name }}</div>
            <div class="readings">
                <div class="value">{{ lastValue }}</div>
                <div class="delta" :class="{ 'delta-green': delta > 0, 'delta-red': delta < 0 }">
                    <v-icon v-if="this.delta !== 0">
                        {{ this.delta > 0 ? "trending_up" : "trending_down" }}
                    </v-icon>
                    <span style="margin-left: 5px">{{ this.delta }} %</span>
                </div>
            </div>
        </div>

        <DeviceSensorChart class="chart" :values="sensor.values" />
    </div>
</template>

<script>
import DeviceSensorChart from "@/components/DeviceSensorChart";
import Icon from "@/components/Icon";

const sensorToIconMap = {
    "Свет над водой": "light-sensor",
    "Свет под водой": "light-sensor",
    "Температура воздуха": "temperature-sensor",
    "Температура воды": "temperature-sensor",
    "Углеводороды": "carbohydrates-sensor",
    "Уровень газа": "gas-sensor"
};

export default {
    name: "DeviceSensorRow",
    components: { Icon, DeviceSensorChart },
    props: {
        sensor: {
            type: Object,
            required: true
        }
    },
    computed: {
        lastValue() {
            return this.sensor.values[this.sensor.values.length - 1];
        },
        delta() {
            const currentValue = this.sensor.values.at(-1);
            const previousValue = this.sensor.values.at(-2);

            if (!currentValue || !previousValue) {
                return 0;
            }

            const delta = ((currentValue - previousValue) / previousValue) * 100;

            return delta === 0 ? 0 : delta.toFixed(2);
        },
        sensorIconName() {
            return sensorToIconMap[this.sensor.name] ?? "device-navigation";
        }
    }
};
</script>

<style scoped lang="scss">
.device-sensor-card {
    display: flex;
    align-items: center;
}

.icon {
    width: 70px;
    height: 70px;
    display: flex;
    align-items: center;
    justify-content: center;

    &.icon-green {
        background: #d1fae5;
        fill: #059669;
    }

    &.icon-red {
        background: #fee2e2;
        fill: #dc2626;
    }
}

.device-info {
    margin-left: 16px;

    .name {
        font-weight: bold;
        font-size: 14px;
        color: #6b7280;
    }

    .readings {
        display: flex;
        align-items: center;
        width: 200px;

        .value {
            font-size: 34px;
            line-height: 32px;
        }

        .delta {
            font-size: 14px;
            margin-left: 10px;
            font-weight: bold;
            display: flex;
            align-items: center;

            &.delta-green,
            &.delta-green .v-icon {
                color: #059669;
            }

            &.delta-red,
            &.delta-red .v-icon {
                color: #dc2626;
            }
        }
    }
}

.chart {
    margin-left: 10px;
}
</style>
