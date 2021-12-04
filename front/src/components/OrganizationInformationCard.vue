<template>
    <div class="card-base">
        <h4 class="card-heading">Общая информация</h4>

        <div v-if="organization.legalAddress" class="info-block">
            <div class="label">Юридический адрес:</div>
            <div class="text">{{ organization.legalAddress }}</div>
        </div>
        <div v-if="organization.factAddress" class="info-block">
            <span class="label">Фактический адрес: </span>
            <div class="text">{{ organization.factAddress }}</div>
        </div>
        <div v-if="organization.listProduced" class="info-block">
            <span class="label">Продукция: </span>
            <div class="text">{{ organization.listProduced }}</div>
        </div>
        <div v-if="organization.refineryStatus" class="info-block">
            <span class="label">Cтатус: </span>
            <div class="text">{{ organization.refineryStatus }}</div>
        </div>
        <div class="info-block">
            <span class="label">lat: </span>
            <span class="text">{{ organization.lat }}</span>

            <span class="label ml-5">lon: </span>
            <span class="text">{{ organization.lon }}</span>
        </div>
    </div>
</template>

<script>
export default {
    name: "OrganizationInformationCard",
    props: {
        organization: {
            type: Object,
            required: false
        }
    }
};
</script>

<style scoped lang="scss">
.card-heading {
    font-size: 21px;
    margin-bottom: 25px;
    font-weight: normal;
}

.info-block {
    &:not(:last-child) {
        margin-bottom: 20px;
    }

    .label {
        font-weight: 500;
        font-size: 18px;
    }

    .text {
        margin-top: 4px;
    }
}
</style>
