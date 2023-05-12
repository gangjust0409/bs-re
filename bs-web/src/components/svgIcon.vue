<template>
    <div>
      <svg :style="style">
        <use :href="names"></use>
      </svg>
    </div>
  </template>
  
  <script setup>
  import { defineProps, withDefaults } from "vue";
  const props = defineProps({
    name: {
      type: String,
      default: "",
    },
    style: {
      type: Object,
      default: () => {
        return {
          width: 10,
          height: 10,
          color: "",
        };
      },
    },
  });
  const names = `#${props.name}`;
  </script>
  
  <style lang="scss" scoped></style>
  
  
  