<template>
  <div id="app">
    <router-view/>
    <main-tabbar/>
  </div>
</template>

<script>
import MainTabbar from './widgt/MainTabbar.vue'


  export default {
    name: 'App',
    components:{
        MainTabbar
    }
  }
</script>

<style>
  @import url("./assets/css/base.css");
</style>
