<template>
  <div class="header">
    <b-nav>
      <b-nav-item><img alt="Vue logo" src="../../assets/images/logo.png"></b-nav-item>
      <b-nav-form @submit.stop.prevent="alert('alert 필요없는데..일단 검색창')">
      <b-form-input aria-label="Input" class="mr-1"></b-form-input>
      <b-button type="submit">검색 이모지 넣을 자리</b-button>
    </b-nav-form>

    <b-nav-item to="/some/route/" exact exact-active-class="active">회원가입</b-nav-item>
        <b-nav-item to="/some/route/" exact exact-active-class="active">로그인</b-nav-item>


      <b-nav-item disabled>@@@님 환영합니다 나올 자리</b-nav-item>
      <b-nav-item-dropdown
      id="my-nav-dropdown"
      text="Dropdown"
      toggle-class="nav-link-custom"
      right
    >
      <b-dropdown-item>내 프로필</b-dropdown-item>
      <b-dropdown-item>방송 시작</b-dropdown-item>
      <b-dropdown-divider></b-dropdown-divider>
      <b-dropdown-item>로그아웃</b-dropdown-item>
    </b-nav-item-dropdown>
    </b-nav>
  </div>
</template>

<script>
export default {
  name: 'HeaderNav',
}
</script>

<style>

</style>
