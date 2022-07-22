<template>
  <div class="view">
    <sign-in />
      <!--<router-view></router-view>-->
  </div>
</template>

<script>
import SignIn from '@/components/auth/SignInView.vue';
export default {
  components: { SignIn },
	name: "AuthView",
};
</script>

<style scoped>

</style>
