<template>
    <div class="card-base" style="width: 240px; padding: 16px 24px">
        <div class="label">{{ label }}:</div>
        <div class="value">{{ value }}</div>
    </div>
</template>

<script>
export default {
    name: "SingleValueCard",
    props: {
        label: {
            type: String,
            required: true
        },
        value: {
            type: String,
            required: true
        }
    }
};
</script>

<style scoped lang="scss">
.label {
    color: #4B5563;
    font-weight: 500;
}

.value {
    font-weight: bold;
    font-size: 36px;
    margin-top: 12px;
    line-height: 32px;
}
</style>
