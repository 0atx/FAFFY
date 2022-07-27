<template>
  <div id="board">
    <div>
      <div id="route">
        <p class="text-h6" style="font-weight: 600">
          <router-link to="/profile/1/user-detail"> Profile </router-link>
        </p>
        <v-icon color="black" class="mb-3"> mdi-chevron-right </v-icon>
        <p class="text-h6" style="font-weight: 600">Board</p>
      </div>
      <div id="content" class="mt-4">
        <p class="text-h6" style="font-weight: 600">작성 게시글</p>
        <hr />
        <div id="info">게시글 목록 컴포넌트가 들어올 자리입니다.</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
#board {
  background-color: #fff;
  padding: 5%;
  text-align: left;
  height: 100%;
  display: flex;
  justify-content: start;
  align-items: flex-start;
  cursor: default;
}

#route {
  display: flex;
  justify-content: start;
}

a {
  color: black;
  text-decoration: none;
}

#board div {
  width: 100%;
}

#content {
  height: 300px;
  background-color: #fff;
  padding: 2%;
}

button:before {
  background-color: transparent;
}

#info {
  height: 200px;
}
</style>
