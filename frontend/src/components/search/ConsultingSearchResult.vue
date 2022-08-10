<template>
  <v-container class="d-flex flex-column">
    <v-row class="d-flex justify-space-between">
      <h2>방송 검색 결과</h2>
      <v-btn text>인기 순</v-btn>
    </v-row>
    <v-row>
      <v-col cols="3" v-for="consulting in currentPage" :key="consulting.host">
        <v-card class="mx-auto" max-width="344">
        <!--방송화면 or 썸네일 -->
        <v-img
          :src="consulting.img"
          height="200px"
        ></v-img>

        <!-- 방송 제목 -->
        <v-card-title class="d-flex justify-space-between">
          <div>
            {{ consulting.title }}
          </div>

          <!--인원 현황-->
          <div>
            <p><v-icon>mdi-account-multiple</v-icon>{{ consulting.currentMember }}/{{ consulting.memberLimit }}</p>
          </div>
        </v-card-title>

        <!--컨설턴트-->
        <v-card-subtitle>
          {{ consulting.host }}
        </v-card-subtitle>

        <!--카테고리-->
        <v-card-text>
          <v-chip-group>
            <v-chip
              small
              :ripple="false"
              v-for="category in consulting.categories"
              :key="category"
              :category="category"
              class="consultingCategory"
            >
              {{ category }}
            </v-chip>
          </v-chip-group>
        </v-card-text>

        <!-- 태그, 방송 참여하기 버튼 등등 -->
        <v-card-actions>
          <v-btn
            color="#c0f66 lighten-2"
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

        <!--방송 내용 소개(필요 없을 경우 제거-->
        <v-expand-transition>
          <div v-show="show">
            <v-divider></v-divider>

            <v-card-text>
              방송 내용 소개
            </v-card-text>
          </div>
        </v-expand-transition>
      </v-card>
      </v-col>
    </v-row>

    <!--페이지네이션-->
    <v-row>
      <v-col cols="12">
        <v-pagination
          v-model="page"
          :length="totalPages"
          prev-icon="mdi-menu-left"
          next-icon="mdi-menu-right"
          :total-visible="totalVisible">
        </v-pagination>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'ConsultingSearchconsulting',
  data() {
    return {
      consultings: [
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '류경하', memberLimit: 10, currentMember: 6, categories: ['힙합', '빈티지']},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '이준성', memberLimit: 40, currentMember: 13, categories: ['힙합', '빈티지']},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '김명석', memberLimit: 27, currentMember: 14, categories: ['힙합', '빈티지']},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '김수만', memberLimit: 33, currentMember: 33, categories: ['힙합', '빈티지']},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '류경하2', memberLimit: 10, currentMember: 6, categories: ['힙합', '빈티지']},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '이준성2', memberLimit: 40, currentMember: 13, categories: ['힙합', '빈티지']},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '김명석2', memberLimit: 27, currentMember: 14, categories: ['힙합', '빈티지']},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '김수만2', memberLimit: 33, currentMember: 33, categories: ['힙합', '빈티지']},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '류경하3', memberLimit: 10, currentMember: 6, categories: ['힙합', '빈티지']},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '이준성3', memberLimit: 40, currentMember: 13, categories: ['힙합', '빈티지']},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '김명석3', memberLimit: 27, currentMember: 14, categories: ['힙합', '빈티지']},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '김수만3', memberLimit: 33, currentMember: 33, categories: ['힙합', '빈티지']},
      ],
      show: false,
      page: 1,
      itemsPerPage: 4,
      totalVisible: 7,
    }
  },
  computed: {
    totalPages() {
      return this.consultings.length % this.itemsPerPage > 0 ? parseInt(this.consultings.length/this.itemsPerPage)+1 : parseInt(this.consultings.length/this.itemsPerPage)
    },
    currentPage() {
      const start = (this.page-1)*this.itemsPerPage
      const end = start+this.itemsPerPage
      return this.consultings.slice(start, end)
    }
  }
}
</script>

<style scoped>
.consultingCategory {
  background-color: #0c0f66;
  color: black;
  pointer-events: none;
}
</style>
