<template>
  <div>
    <v-container>
      <div class="d-flex justify-space-between">
        <h1>진행 중인 방송 목록</h1>
        <v-col
        class="d-flex"
        cols="2"
      >
        <v-select
          :items="items"
          label="정렬 기준"
          dense
          solo
          v-model="sortBy"
        ></v-select>
      </v-col>
      </div>
      <v-row>
        <v-col style="height:500px; display:flex; justify-content:center; align-items:center;" v-if="!isResultExist" cols="12">
          <div style="text-align:center;">
            <v-icon color="#333" large block> mdi-broadcast-off </v-icon>
            <h2>현재 진행 중인 방송이 없습니다.</h2>
          </div>
        </v-col>
        <v-col
          v-for="(consulting, i) in currentPage"
          :key="i"
          cols="12"
          v-else
          @click="watchConsulting(consulting.nickname, consulting.no)"
          style="cursor: pointer"
        >
          <v-card>
            <div class="d-flex flex-row">
              <v-avatar
                class="ma-3"
                size="125"
                tile
              >
              <v-img :src="`${API_BASE_URL}/users/profile/image/${consulting.consultant_no}`"></v-img>
              </v-avatar>
              <div class="d-flex flex-column">
                <v-card-title
                  class="text-h5"
                  v-text="consulting.title"
                ></v-card-title>

                <v-card-subtitle style="text-align:start">
                  {{ consulting.consultant }}<br/>
                  <v-icon small>mdi-account-multiple</v-icon>{{ consulting.viewCount }}/{{ consulting.roomSize }}<br/>
                  {{ consulting.intro }}
                </v-card-subtitle>

                <v-card-actions>
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
                </v-card-actions>
              </div>
            </div>
          </v-card>
        </v-col>
      </v-row>
      <v-row v-if="isResultExist">
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
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import _ from 'lodash'
import { API_BASE_URL } from "@/config";
const searchStore = "searchStore"

export default {
  name: 'ConsultingListView',
  data() {
    return {
      API_BASE_URL: API_BASE_URL,
      page: 1,
      itemsPerPage: 4,
      totalVisible: 7,
      sortBy: '',
      items: ['제목', '현재 인원', '전체 인원'],
      type: { '제목': 'title', '현재 인원': 'viewCount', '전체 인원': 'roomSize' },
      consultings: [
        {
          src: "https://cdn.vuetifyjs.com/images/cards/cooking.png",
          title: "방송 1",
          intro: "정렬할 요소에 position 값을 absolute 로 설정하여 중앙정렬 하는 것에는 두가지 방법이 있다. 부모 요소(라기보단 기준이 될 요소)에 position:relative를",
          nickname: "별명짓기귀찮다",
          viewCount: "3",
          roomSize: "10",
          categories: [
            "캐주얼",
            "모던",
          ],
        },
        {
          src: "https://cdn.vuetifyjs.com/images/cards/cooking.png",
          title: "방송 2",
          intro: "2014년 응용수학을 바탕으로 최적화 의사 결정을 연구하던 과학자가 난제에 가까운 산학 협력 프로젝트를 만났다. 한 부동산개발 업체가 캐나다 토론토의 한 고층 콘도미니엄을 분양하면",
          nickname: "별명짓기귀찮다",
          viewCount: "3",
          roomSize: "10",
          categories: [
            "캐주얼",
            "모던",
          ],
        },
        {
          src: "https://cdn.vuetifyjs.com/images/cards/cooking.png",
          title: "방송 3",
          intro: "방송 정보",
          nickname: "별명짓기귀찮다",
          viewCount: "3",
          roomSize: "10",
          categories: [
            "캐주얼",
            "모던",
          ],
        },
        {
          src: "https://cdn.vuetifyjs.com/images/cards/cooking.png",
          title: "방송 4",
          intro: "방송 정보",
          nickname: "별명짓기귀찮다",
          viewCount: "3",
          roomSize: "10",
          categories: [
            "캐주얼",
            "모던",
          ],
        },
        {
          src: "https://cdn.vuetifyjs.com/images/cards/cooking.png",
          title: "방송 1",
          intro: "정렬할 요소에 position 값을 absolute 로 설정하여 중앙정렬 하는 것에는 두가지 방법이 있다. 부모 요소(라기보단 기준이 될 요소)에 position:relative를",
          nickname: "별명짓기귀찮다",
          viewCount: "3",
          roomSize: "10",
          categories: [
            "캐주얼",
            "모던",
          ],
        },
        {
          src: "https://cdn.vuetifyjs.com/images/cards/cooking.png",
          title: "방송 2",
          intro: "2014년 응용수학을 바탕으로 최적화 의사 결정을 연구하던 과학자가 난제에 가까운 산학 협력 프로젝트를 만났다. 한 부동산개발 업체가 캐나다 토론토의 한 고층 콘도미니엄을 분양하면",
          nickname: "별명짓기귀찮다",
          viewCount: "3",
          roomSize: "10",
          categories: [
            "캐주얼",
            "모던",
          ],
        },
        {
          src: "https://cdn.vuetifyjs.com/images/cards/cooking.png",
          title: "방송 3",
          intro: "방송 정보",
          nickname: "별명짓기귀찮다",
          viewCount: "3",
          roomSize: "10",
          categories: [
            "캐주얼",
            "모던",
          ],
        },
        {
          src: "https://cdn.vuetifyjs.com/images/cards/cooking.png",
          title: "방송 4",
          intro: "방송 정보",
          nickname: "별명짓기귀찮다",
          viewCount: "3",
          roomSize: "10",
          categories: [
            "캐주얼",
            "모던",
          ],
        },
        {
          src: "https://cdn.vuetifyjs.com/images/cards/cooking.png",
          title: "방송 1",
          intro: "정렬할 요소에 position 값을 absolute 로 설정하여 중앙정렬 하는 것에는 두가지 방법이 있다. 부모 요소(라기보단 기준이 될 요소)에 position:relative를",
          nickname: "별명짓기귀찮다",
          viewCount: "3",
          roomSize: "10",
          categories: [
            "캐주얼",
            "모던",
          ],
        },
        {
          src: "https://cdn.vuetifyjs.com/images/cards/cooking.png",
          title: "방송 2",
          intro: "2014년 응용수학을 바탕으로 최적화 의사 결정을 연구하던 과학자가 난제에 가까운 산학 협력 프로젝트를 만났다. 한 부동산개발 업체가 캐나다 토론토의 한 고층 콘도미니엄을 분양하면",
          nickname: "별명짓기귀찮다",
          viewCount: "3",
          roomSize: "10",
          categories: [
            "캐주얼",
            "모던",
          ],
        },
        {
          src: "https://cdn.vuetifyjs.com/images/cards/cooking.png",
          title: "방송 3",
          intro: "방송 정보",
          nickname: "별명짓기귀찮다",
          viewCount: "3",
          roomSize: "10",
          categories: [
            "캐주얼",
            "모던",
          ],
        },
        {
          src: "https://cdn.vuetifyjs.com/images/cards/cooking.png",
          title: "방송 4",
          intro: "방송 정보",
          nickname: "별명짓기귀찮다",
          viewCount: "33",
          roomSize: "10",
          categories: [
            "캐주얼",
            "모던",
          ],
        },
        {
          src: "https://cdn.vuetifyjs.com/images/cards/cooking.png",
          title: "방송 1",
          intro: "정렬할 요소에 position 값을 absolute 로 설정하여 중앙정렬 하는 것에는 두가지 방법이 있다. 부모 요소(라기보단 기준이 될 요소)에 position:relative를",
          nickname: "별명짓기귀찮다",
          viewCount: "213123",
          roomSize: "10",
          categories: [
            "캐주얼",
            "모던",
          ],
        },
        {
          src: "https://cdn.vuetifyjs.com/images/cards/cooking.png",
          title: "방송 2",
          intro: "2014년 응용수학을 바탕으로 최적화 의사 결정을 연구하던 과학자가 난제에 가까운 산학 협력 프로젝트를 만났다. 한 부동산개발 업체가 캐나다 토론토의 한 고층 콘도미니엄을 분양하면",
          nickname: "별명짓기귀찮다",
          viewCount: "33333",
          roomSize: "10",
          categories: [
            "캐주얼",
            "모던",
          ],
        },
      ],
    }
  },
  methods: {
    ...mapActions(searchStore, ['searchKeyword']),
    watchConsulting(nickname, consultingNo) {
      this.$router.push({name:'consulting-onair', params:{ nickname: nickname, consulting_no: consultingNo }})
    },
    sortResult(sortBy) {
      this.consultingResult.sort((a, b) => {
        if (b[this.type[sortBy]] > a[this.type[sortBy]]) {
          return 1
        } else if (b[this.type[sortBy]] < a[this.type[sortBy]]) {
          return -1
        } else {
          return 0
        }
      })
    }
  },
  computed: {
    ...mapGetters(searchStore, ['consultingResult']),
    totalPages() {
      return this.consultingResult.length % this.itemsPerPage > 0 ? parseInt(this.consultingResult.length/this.itemsPerPage)+1 : parseInt(this.consultingResult.length/this.itemsPerPage)
    },
    currentPage() {
      const start = (this.page-1)*this.itemsPerPage
      const end = start+this.itemsPerPage
      return this.consultingResult.slice(start, end)
    },
    isResultExist() {
      return !_.isEmpty(this.consultingResult)
    },
  },
  created() {
    this.searchKeyword('')
  },
  watch: {
    sortBy() {
      this.sortResult(this.sortBy)
    }
  }
}
</script>

<style>
#consultingCategory {
  background-color: #0c0f66;
  color: #fff;
  pointer-events: none;
}
</style>
