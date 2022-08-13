<template>
  <div>
    Please wait...
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import { auth } from "@/api/auth";

export default {
  name: 'AuthHandler',
  created() {
    const code = this.$route.query.code
    if (code) {
      auth.socialCallback(
        'google',
        code,
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
    else{
      console.log("code is null");
    }
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

