<template>
  <v-list style="padding: 0">
    <v-list-item-group>
      <user-list-item v-for="u in list" :key="u.nickname" :user="u">
      </user-list-item>
    </v-list-item-group>
  </v-list>
</template>

<script>
import UserListItem from "./UserListItem.vue";
export default {
  components: { UserListItem },
  name: "UserList",
  props: {
    list: Array,
  },
};
</script>

<style scoped>

</style>
