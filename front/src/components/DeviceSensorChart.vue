<template>
    <canvas ref="chart" width="315" height="80"></canvas>
</template>

<script>
import Chart from "chart.js/auto";
import equal from "deep-equal";

const createChartData = (values) => ({
    labels: new Array(values.length).fill(""),
    datasets: [
        {
            label: "",
            data: values
        }
    ]
});

export default {
    name: "DeviceSensorChart",
    props: {
        values: {
            type: Array,
            required: true
        }
    },
    data() {
        return {
            chart: null
        };
    },
    mounted() {
        const ctx = this.$refs.chart.getContext("2d");
        const data = this.values;

        this.chart = new Chart(ctx, {
            type: "line",
            data: createChartData(data),
            options: {
                responsive: false,
                plugins: {
                    legend: { display: false },
                    tooltip: { enabled: false }
                },
                scales: {
                    y: { display: false },
                    x: {
                        ticks: { display: false }
                    }
                }
            }
        });
    },
    watch: {
        values() {
            const currentValues = this.chart.data.datasets[0].data;
            if (equal(currentValues, this.values)) {
                return;
            }

            this.chart.data.datasets[0].data.shift();
            this.chart.data.datasets[0].data.push(this.values.at(-1));

            this.chart.update();
        }
    }
};
</script>

<style scoped></style>
