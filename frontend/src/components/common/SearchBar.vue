<template>
  <b-form class="searchBar">
    <b-form-input
      id="inline-form-input-name"
      placeholder="검색"
    ></b-form-input>
    <b-button type="submit">검색</b-button>
  </b-form>
</template>

<script>
export default {
    name: 'SearchBar',
}
</script>

<style>
.searchBar {
  margin : 0 auto;
  display: flex;
  height: 38px;
}

button {
  margin-left: 2px;
  width: 80px;
}
</style>
