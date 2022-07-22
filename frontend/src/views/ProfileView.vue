<template>
  <v-container fluid>
    <v-row wrap id="block">
      <v-col cols="4">
        <profile-card />
      </v-col>
      <v-col cols="8">
      <div></div>
      <!--<router-view></router-view>-->
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import ProfileCard from '../components/user/ProfileCard.vue'

export default {
	name: "ProfileView",
  components: {
    ProfileCard
  }
};
</script>

<style scoped>
* {
  height: 100%;
  padding: 0px;
}

div {
  background-color: gray;
}
</style>
