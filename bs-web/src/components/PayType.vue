<template>
  <div class="pay_status">
    <div
      :class="{ pay_item: true, active: payTypeVal == 1 }"
      @click="changePayType(1)"
    >
      <svg class="icon" aria-hidden="true">
        <use xlink:href="#icon-zhifubaozhifu"></use>
      </svg>
      <span>支付宝支付</span>
    </div>
    <div
      :class="{ pay_item: true, active: payTypeVal == 2 }"
      @click="changePayType(2)"
    >
      <svg class="icon big" aria-hidden="true">
        <use
          xlink:href="#icon-shejiaotubiao-44
"
        ></use>
      </svg>
      <span>本地支付</span>
    </div>
  </div>
</template>

<script setup>
import { ref, defineProps, defineEmits } from "vue";

const emits = defineEmits(["payTypeChange"]);
const payTypeVal = ref(0);

const { payType } = defineProps({
  payType: {
    type: Number,
  },
});
function changePayType(type) {
  payTypeVal.value = type;
  console.log(payTypeVal.value);
  emits("payTypeChange", payTypeVal.value);
}
</script>

<style lang="scss" scoped>
.pay_status {
  display: flex;
  align-items: center;
  justify-content: end;
  flex: 1;

  .pay_item {
    display: flex;
    flex-direction: column;
    justify-content: center;
    margin-right: 20px;
    cursor: pointer;
    padding: 3px;
    &.active,
    &:hover {
      background-color: rgb(223, 222, 222);
      border-radius: 10px;
    }
  }
}
.icon {
  width: 2em;
  height: 2em;
  vertical-align: -0.15em;
  fill: currentColor;
  margin: 0 auto;
  overflow: hidden;
  &.big {
    width: 2.2em;
    height: 2.1em;
  }
}
</style>