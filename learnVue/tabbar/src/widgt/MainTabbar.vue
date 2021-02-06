<template>
  <tab-bar>
      <tab-bar-item path="/index" active-color="#3ef9d2">
        <template #item-icon><img  src="../assets/img/tabbar/index_unchecking.png" alt="" /></template>
        <template #item-icon-active><img  src="../assets/img/tabbar/index_checking.png" alt="" /></template>
        <template #item-text><div>首页</div></template>
      </tab-bar-item>
      <tab-bar-item path="/about" active-color="#3ef9d2">
        <template #item-icon><img src="../assets/img/tabbar/about_unchencking.png" alt="" /></template>
        <template #item-icon-active><img src="../assets/img/tabbar/about_checking.png" alt="" /></template>
        <template #item-text><div>新风</div> </template>
      </tab-bar-item>
      <tab-bar-item path="/user" active-color="#3ef9d2">
        <template #item-icon><img  src="../assets/img/tabbar/user_unchecking.png" alt="" /></template>
        <template #item-icon-active><img  src="../assets/img/tabbar/user_checking.png" alt="" /></template>
        <template #item-text><div>我的</div></template>
      </tab-bar-item>
      <tab-bar-item path="/shop" active-color="#3ef9d2">
        <template #item-icon><img  src="../assets/img/tabbar/shop_unchecking.png" alt="" /></template>
        <template #item-icon-active><img  src="../assets/img/tabbar/shop_checking.png" alt="" /></template>
        <template #item-text><div>商城</div></template>
      </tab-bar-item>
    </tab-bar>

</template>

<script>
import TabBar from "../components/tabbar/TabBar.vue";
import TabBarItem from "../components/tabbar/TabBarItem.vue";

export default {
  components: {
    TabBar,
    TabBarItem,
  },
};
</script>

<style>
</style>