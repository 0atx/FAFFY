<template>
  <v-container class="d-flex flex-column">
    <v-row class="d-flex justify-space-between">
      <h2 class="ml-4">방송 검색 결과</h2>
      <v-btn v-if="consultings.length != 0" class="sortBtn" text>인기 순</v-btn>
    </v-row>
    <v-row>
      <v-col style="height:100px; display:flex; justify-content:center; align-items:center;" v-if="consultings.length == 0" cols="12">
        <h2>검색 결과가 없습니다.</h2>
      </v-col>
      <v-col cols="3" v-for="consulting in currentPage" :key="consulting.host">
        <v-card outlined elevation="0" class="mx-auto" max-width="344">
          <!--방송화면 or 썸네일 -->
          <v-img
            :src="consulting.img"
            height="200px"
          ></v-img>

          <v-list-item>
            <v-list-item-content>
              <div style="display:flex; align-items:center">
                <div>

                  <!-- 방송 제목 -->
                  <v-list-item-title style="font-weight: 600; font-size:18px;" class="mt-1 mb-1">
                    {{ consulting.title }}
                  </v-list-item-title>

                  <!-- 컨설턴트 닉네임 / 참여 인원 -->
                  <v-list-item-subtitle class="mb-1">
                    {{ consulting.host }}
                  </v-list-item-subtitle>
                  <v-list-item-subtitle style="font-size: 12px;">
                    <v-icon small>mdi-account-multiple</v-icon> {{ consulting.currentMember }} / {{ consulting.memberLimit }}
                  </v-list-item-subtitle>
                </div>
              </div>

              <!-- 방송 카테고리 -->
              <v-chip-group>
                <v-chip
                  small
                  :ripple="false"
                  id="consultingCategory"
                  v-for="category in consulting.categories"
                  :key="category"
                  :category="category"
                >
                  {{ category }}
                </v-chip>
              </v-chip-group>

              <!--방송 내용 소개(필요 없을 경우 제거-->
              <v-expand-transition>
                <div>
                  <v-card-text class="pt-0 pb-0 intro" v-show="!consulting.show">
                    2014년 응용수학을 바탕으로 최적화 의사 결정을 연구하던 과학자가 난제에 가까운 산학 협력 프로젝트를 만났다. 한 부동산개발 업체가 캐나다 토론토의 한 고층 콘도미니엄을 분양하면
                    <v-btn
                      icon
                      class="iconBtn"
                      :ripple="false"
                      @click="consulting.show = !consulting.show"
                    >
                      <v-icon>{{ consulting.show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
                    </v-btn>
                  </v-card-text>
                  <v-card-text class="pt-0 pb-0 intro" v-show="consulting.show">
                    2014년 응용수학을 바탕으로 최적화 의사 결정을 연구하던 과학자가 난제에 가까운 산학 협력 프로젝트를 만났다. 한 부동산개발 업체가 캐나다 토론토의 한 고층 콘도미니엄을 분양하면2014년 응용수학을 바탕으로 최적화 의사 결정을 연구하던 과학자가 난제에 가까운 산학 협력 프로젝트를 만났다. 한 부동산개발 업체가 캐나다 토론토의 한 고층 콘도미니엄을 분양하면
                    <v-btn
                      icon
                      class="iconBtn"
                      :ripple="false"
                      @click="consulting.show = !consulting.show"
                    >
                      <v-icon>{{ consulting.show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
                    </v-btn>
                  </v-card-text>
                </div>
              </v-expand-transition>
              <div style="text-align:right;">
                <!-- 참여 버튼. 클릭 시 화상회의 방 진입 -->
                <v-btn
                  outlined
                  rounded
                  text
                >
                  참여
                </v-btn>
              </div>
            </v-list-item-content>
          </v-list-item>

        </v-card>
      </v-col>
    </v-row>

    <!--페이지네이션-->
    <v-row v-if="consultings.length != 0">
      <v-col cols="12">
        <v-pagination
          v-model="page"
          circle
          color="#0c0f66"
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
                { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '류경하', memberLimit: 10, currentMember: 6, categories: ['힙합', '빈티지'], show: false},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '이준성', memberLimit: 40, currentMember: 13, categories: ['힙합', '빈티지'], show: false},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '김명석', memberLimit: 27, currentMember: 14, categories: ['힙합', '빈티지'], show: false},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '김수만', memberLimit: 33, currentMember: 33, categories: ['힙합', '빈티지'], show: false},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '류경하2', memberLimit: 10, currentMember: 6, categories: ['힙합', '빈티지'], show: false},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '이준성2', memberLimit: 40, currentMember: 13, categories: ['힙합', '빈티지'], show: false},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '김명석2', memberLimit: 27, currentMember: 14, categories: ['힙합', '빈티지'], show: false},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '김수만2', memberLimit: 33, currentMember: 33, categories: ['힙합', '빈티지'], show: false},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '류경하3', memberLimit: 10, currentMember: 6, categories: ['힙합', '빈티지'], show: false},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '이준성3', memberLimit: 40, currentMember: 13, categories: ['힙합', '빈티지'], show: false},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '김명석3', memberLimit: 27, currentMember: 14, categories: ['힙합', '빈티지'], show: false},
        { no: 1, img: 'http://www.yomidog.com/preSaleUpFile/190724_%EC%84%B1%EB%B6%81%ED%8F%AC%EB%A9%94_638.JPG', title: '강아지라네', host: '김수만3', memberLimit: 33, currentMember: 33, categories: ['힙합', '빈티지'], show: false},
      ],
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
#consultingCategory {
  background-color: #0c0f66;
  color: white;
  pointer-events: none;
}

.intro {
  padding: 0;
  font-size:15px;
}

.iconBtn::before {
  background-color: transparent;
}

.sortBtn::before {
  background-color: transparent;
}
</style>
