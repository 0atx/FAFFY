<template>
  <v-container class="d-flex flex-column">
    <v-row class="d-flex justify-space-between">
      <h2>방송 검색 결과</h2>
      <v-btn text>인기 순</v-btn>
    </v-row>
    <v-row>
      <v-col cols="3" v-for="result in results" :key="result.host">
        <v-card class="mx-auto" max-width="344">
        <v-img
          :src="result.img"
          height="200px"
        ></v-img>

        <v-card-title class="d-flex justify-space-between">
          <div>
            {{ result.title }}
          </div>
          <div>
            <p><v-icon>mdi-account-multiple</v-icon>{{ result.currentMember }}/{{ result.memberLimit }}</p>
          </div>
        </v-card-title>

        <v-card-subtitle>
          {{ result.host }}
        </v-card-subtitle>

        <!-- 태그, 방송 참여하기 버튼 등등 -->
        <v-card-actions>
          <v-btn
            color="orange lighten-2"
            text
          >
            방송 보기
          </v-btn>

          <v-spacer></v-spacer>

          <v-btn
            icon
            @click="show = !show"
          >
            <v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
          </v-btn>
        </v-card-actions>

        <v-expand-transition>
          <div v-show="show">
            <v-divider></v-divider>

            <v-card-text>
              I'm a thing. But, like most politicians, he promised more than he could deliver. You won't have time for sleeping, soldier, not with all the bed making you'll be doing. Then we'll go with that data file! Hey, you add a one and two zeros to that or we walk! You're going to do his laundry? I've got to find a way to escape.
            </v-card-text>
          </div>
        </v-expand-transition>
      </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'ConsultingSearchResult',
  data() {
    return {
      results: [
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '류경하', memberLimit: 10, currentMember: 6},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '이준성', memberLimit: 40, currentMember: 13},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '김명석', memberLimit: 27, currentMember: 14},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '김수만', memberLimit: 33, currentMember: 33},
      ]
    }
  }
}
</script>

<style>

</style>
