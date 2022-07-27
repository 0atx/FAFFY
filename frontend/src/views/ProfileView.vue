<template>
  <div id="view">
      <v-row
        id="ProfileView"
        class="pt-10"
      >
        <v-col
        class="pl-0 pr-0"
          cols="6"
          md="4"
        >
          <profile-card />
        </v-col>
        <v-col
          class="pl-0 pr-0"
          cols="12"
          md="8"
        >
          <router-view></router-view>
        </v-col>
      </v-row>
  </div>
</template>

<script>
import ProfileCard from '@/components/user/ProfileCard.vue'

export default {
	name: "ProfileView",
  components: {
    ProfileCard
  }
};
</script>

<style scoped>
#view {
  padding-left: 10%;
  padding-right: 10%;
}

#profileView {
  display: flex;
}

#profileCard {
  padding-right: 0;
}


</style>
