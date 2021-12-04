<template>
    <Layout v-if="issue">
        <div class="mt-4 d-flex justify-space-between">
            <h3>{{ issue.issueTitle }}</h3>

            <div>
                <v-btn
                    color="#1560A1"
                    dark
                    :loading="reportBuildingInProgress"
                    @click="buildReport"
                >
                    Сформировать отчет
                </v-btn>
                <v-btn
                    color="#1560A1"
                    class="ml-2"
                    :href="`https://oil-report.kovalev.team/report/${issue.issueId}.pdf`"
                    target="_blank"
                    style="color: white !important;"
                >
                    Скачать отчет
                </v-btn>
                <v-btn outlined color="#1560A1" class="ml-2" @click="$refs.uploadInput.open()">
                    Добавить фотографии
                </v-btn>
            </div>
        </div>

        <div class="photos mt-2">
            <div v-for="(image, index) in issue.fileImages" :key="image.id">
                <img
                    :src="image.filePath"
                    alt="task-image"
                    @click="$refs.carousel.openGallery(index)"
                />
            </div>

            <PhotoSwipeImageCarousel ref="carousel" :items="issuePhotos" />
        </div>

        <div class="d-flex mt-2 align-start">
            <OrganizationInformationCard
                v-if="organization"
                :organization="organization"
                style="width: 50%"
            />

            <div class="card-base ml-4">
                <div v-html="issue.issueDescription" />
            </div>

            <div class="card-base ml-4 flex-grow-1">
                <h3 class="mb-4">Близлежащие города</h3>

                <div v-for="city in issue.affectedCity">
                    {{ city.addressCity }}
                </div>
            </div>
        </div>

        <div class="predictions mt-4">
            <div v-for="i in predictImages" class="d-flex">
                <img-comparison-slider class="mb-4">
                    <img slot="first" :src="i.originalImg" height="300" width="300" />
                    <img slot="second" :src="i.predictImg" height="300" width="300" />
                </img-comparison-slider>

                <div class="metrics ml-4">
                    <div v-for="m in i.metrics" class="mb-3">
                        <div class="name">{{ m.name }}:</div>
                        <div class="value">{{ m.value }}</div>
                    </div>
                </div>
            </div>
        </div>

        <UploadIssueFilesInput
            v-if="issue"
            ref="uploadInput"
            :issue-id="issue.issueId"
            @uploadFinish="loadIssue"
        />
    </Layout>
</template>

<script>
import Layout from "@/components/Layout";
import { IssueService } from "@/services/IssueService";
import PhotoSwipeImageCarousel from "@/components/PhotoSwipeImageCarousel";
import { OrganizationService } from "@/services/OrganizationService";
import OrganizationInformationCard from "@/components/OrganizationInformationCard";
import UploadIssueFilesInput from "@/components/UploadIssueFilesInput";

export default {
    name: "IncidentView",
    components: {
        UploadIssueFilesInput,
        OrganizationInformationCard,
        PhotoSwipeImageCarousel,
        Layout
    },
    data() {
        return {
            issue: null,
            organization: null,
            report: null,
            reportBuildingInProgress: false
        };
    },
    computed: {
        issuePhotos() {
            const images = this.issue.fileImages;

            if (!images || !images.length) {
                return [];
            }

            const result = [];
            for (const i of images) {
                const image = new Image();
                const src = i.filePath;

                image.src = src;
                image.onload = () => {
                    result.push({
                        src,
                        w: image.width < 300 ? 300 : image.width,
                        h: image.height < 300 ? 300 : image.height
                    });
                };
            }

            return result;
        },
        predictImages() {
            if (!this.report?.predict?.img?.length) {
                return [];
            }

            const productionUrl = "https://oil.kovalev.team";

            return this.report.predict.img.map((i) => ({
                originalImg: `${productionUrl}/back-img/${i.originalImg}`,
                predictImg: `${productionUrl}/back-img/${i.predictImg}`,
                metrics: i.metrics
            }));
        }
    },
    async mounted() {
        await this.loadIssue();
        this.organization = await OrganizationService.getById(this.issue.plantId);
    },
    methods: {
        async loadIssue() {
            const id = this.$route.params.incidentId;
            this.issue = await IssueService.getIssueById(id);
        },
        async buildReport() {
            this.reportBuildingInProgress = true;

            try {
                this.report = await IssueService.buildReport(this.issue.issueId);
            } catch (e) {
                alert("Error");

                console.error(e);
            } finally {
                this.reportBuildingInProgress = false;
            }
        }
    }
};
</script>

<style scoped lang="scss">
.photos {
    display: flex;
    flex-wrap: wrap;

    img {
        width: 80px;
        height: 80px;
        border: #1560a1 1px solid;
        margin-right: 8px;
        margin-bottom: 8px;
        object-fit: cover;
        cursor: pointer;
    }
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

.metrics {
    .name {
        font-weight: 500;
    }

    .value {
        color: #4b5563;
    }
}
</style>
