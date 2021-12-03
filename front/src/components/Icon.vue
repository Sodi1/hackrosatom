<template>
    <svg :width="computedWidth" :height="computedHeight">
        <use :href="`#${name}`" />
    </svg>
</template>

<script>
export default {
    name: "Icon",
    props: {
        width: {
            type: Number,
            required: false
        },
        height: {
            type: Number,
            required: false
        },
        size: {
            type: Number,
            required: false
        },
        name: {
            type: String,
            required: true
        }
    },
    computed: {
        computedWidth() {
            return this.width ?? this.size;
        },
        computedHeight() {
            return this.height ?? this.size;
        }
    }
};
</script>
