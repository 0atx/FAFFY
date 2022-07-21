<!--
작성자 : 박윤하
로그인 페이지
 -->
<template>
  <v-container fluid>
    <v-layout row wrap>
      <v-flex xs12 class="text-xs-center" mt-5>
        <h1>로그인</h1>
      </v-flex>
      <!-- css 임시 -->
      <v-flex xs12 sm6 offset-sm3 mt-3>
        <form>
          <v-layout column>
            <v-flex>
              <v-text-field
                name="email"
                label="이메일"
                id="email"
                type="email"
                required></v-text-field>
            </v-flex>
            <v-flex>
              <v-text-field
                name="password"
                label="비밀번호"
                id="password"
                type="password"
                required></v-text-field>
            </v-flex>

            <!-- 비밀번호 찾기  -->
            <v-flex>
              비밀번호를 잊으셨나요?
            </v-flex>

            <v-flex class="text-xs-center" mt-5>
              <v-btn color="primary" type="submit">로그인</v-btn>
            </v-flex>
          </v-layout>
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

      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: "SignIn",
};
</script>

<style>
.justify {
  justify-content: space-around;
}
</style>
