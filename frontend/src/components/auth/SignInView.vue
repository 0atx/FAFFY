<!--
작성자 : 박윤하
로그인 페이지
-->
<template>
  <v-row wrap>
    <v-col xs12 class="text-xs-center" mt-5>
      <h1>로그인</h1>
    </v-col>
    <!-- css 임시 -->
    <v-col xs12 sm6 offset-sm3 mt-3>
      <form>
        <v-row column>
          <v-col>
            <v-text-field
              name="email"
              label="이메일"
              id="email"
              type="email"
              required></v-text-field>
          </v-col>
          <v-col>
            <v-text-field
              name="password"
              label="비밀번호"
              id="password"
              type="password"
              required></v-text-field>
          </v-col>

          <!-- 비밀번호 찾기  -->
          <v-col>
            비밀번호를 잊으셨나요?
          </v-col>

          <v-col class="text-xs-center" mt-5>
            <v-btn color="primary" type="submit">로그인</v-btn>
          </v-col>
        </v-row>
      </form>

      <!-- 구분선 아래에 소셜 로그인 -->
      <v-divider color="black"></v-divider>

      <!-- 임시 소셜 로그인 배치 -->
      <v-row class="justify">
        <v-btn icon>
          <v-icon>mdi-magnify</v-icon>
        </v-btn>

        <v-btn icon>
          <v-icon>mdi-heart</v-icon>
        </v-btn>
      </v-row>

    </v-col>
  </v-row>
</template>

<script>
export default {
  name: "SignIn",
};
</script>

<style scoped>
.justify {
  justify-content: space-around;
}
</style>
