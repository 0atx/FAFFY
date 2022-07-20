<template>
  <div class="header">
    <img class="logo" alt="Vue logo" src="../../assets/images/faffy_logo_1.png">

    <search-bar />

    <b-nav class="nav" v-if="userInfo">
      <b-nav-item disabled>@@@님 환영합니다 나올 자리</b-nav-item>
      <b-nav-item-dropdown id="my-nav-dropdown" text="Dropdown" toggle-class="nav-link-custom" right>
        <b-dropdown-item>내 프로필</b-dropdown-item>
        <b-dropdown-item>방송 시작</b-dropdown-item>
        <b-dropdown-divider></b-dropdown-divider>
        <b-dropdown-item>로그아웃</b-dropdown-item>
      </b-nav-item-dropdown>
    </b-nav>

    <b-nav class="nav" v-else>
      <b-nav-item to="/some/route/" exact exact-active-class="active">회원가입</b-nav-item>
      <b-nav-item to="/some/route/" exact exact-active-class="active">로그인</b-nav-item>
    </b-nav>
  </div>


</template>

<script>
import SearchBar from './SearchBar.vue'
export default {
    // eslint-disable-next-line vue/multi-word-component-names
    name: 'Header',
    components: { SearchBar },
    data() {
      return {
        userInfo: false,
      }
    }
}
</script>

<style>
.header {
  display: flex;
  align-items: center;
}

.logo {
  float: left;
}

.nav {
  float: right;
}
</style>
