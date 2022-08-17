<template>
  <div>
    Please wait...
  </div>
</template>

<script>
import { mapMutations, mapState } from 'vuex'
import { auth } from "@/api/auth";

export default {
  name: 'NaverAuthHandler',
  created() {
    const code = this.$route.query.code;
    const state = this.$route.query.state;
    if (code) {
      auth.socialCallback(
        'naver',
        {code, state},
        (response) => {
          let token = response.data["token"];
          let user = response.data["user"];
          sessionStorage.setItem("X-AUTH-TOKEN", token);
          this.SET_USER_INFO(user);
          this.$router.push({ name: "main" });
        },
        () => {
          alert("socialCallback failed");
          this.$router.push({ name: "main" });
        }
      )
    }
  },
  computed: {
    ...mapState("authStore" ,["loginUser"]),
  },
  methods: {
    ...mapMutations("authStore", [
      "SET_USER_INFO"
    ]),
  }
}
</script>

<style>

</style>

