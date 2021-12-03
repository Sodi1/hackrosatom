<template>
    <v-dialog width="500" persistent :value="isOpen">
        <v-form @submit.prevent="createIssue" ref="createForm">
            <v-card>
                <v-card-title>
                    <span class="text-h5">Новая инцидент</span>
                </v-card-title>
                <v-card-text>
                    <v-text-field
                        v-model="title"
                        label="Название"
                        :disabled="isFormSubmitting"
                        :rules="[(v) => !!v || 'Обазятальное поле']"
                    />
                    <v-textarea
                        v-model="description"
                        label="Описание"
                        no-resize
                        :disabled="isFormSubmitting"
                        :rules="[(v) => !!v || 'Обазятальное поле']"
                    />
                </v-card-text>
                <v-card-actions>
                    <v-spacer />
                    <v-btn text :disabled="isFormSubmitting" @click="resetAndClose">Закрыть</v-btn>
                    <v-btn
                        color="#1560A1"
                        dark
                        type="submit"
                        :loading="isFormSubmitting"
                    >
                        Создать
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-form>
    </v-dialog>
</template>

<script>
import { IssueService } from "@/services/IssueService";

export default {
    name: "CreateIssueModal",
    props: {
        organizationId: {
            type: Number,
            required: true
        }
    },
    data() {
        return {
            isOpen: false,
            title: null,
            description: null,
            isFormSubmitting: false
        };
    },
    methods: {
        open() {
            this.isOpen = true;
        },
        resetAndClose() {
            this.$refs.createForm.reset();
            this.isOpen = false;
        },
        async createIssue() {
            if (!this.$refs.createForm.validate()) {
                return;
            }

            const issue = {
                plantId: this.organizationId,
                issueTitle: this.title,
                issueDescription: this.description,
                isSatellite: false,
                issueStatus: "WAIT_PHOTO"
            };

            this.isFormSubmitting = true;

            try {
                await IssueService.createIssue(issue);

                this.$emit("issueCreate");
                this.resetAndClose();
            } catch (e) {
                alert("Error")

                console.error(e)
            } finally {
                this.isFormSubmitting = false;
            }
        }
    }
};
</script>

<style scoped></style>
