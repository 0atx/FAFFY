<template>
  <v-container fluid>
    <v-row style="padding-left: 14%; background-color:#0c0f66; color: #fff" class="text-left mt-5 pt-5">
      <h1>진행 중인 방송 목록</h1>
    </v-row>
    <v-row style="padding-left: 14%; background-color:#0c0f66; color: #fff" class="text-left mb-5 pb-5">
      <h4>관심 있는 방송에 참여해보세요.</h4>
    </v-row>
    <v-container>
      <v-row style="width: 100%; margin: 0 auto;">
        <v-col cols="7" class="pr-0" align="end">
          <!--검색어 입력-->
          <v-text-field
            style="width:55%; float:right;"
            color="#0c0f66"
            v-model="keyword"
            placeholder="방송 제목을 입력하세요"
            append-icon="mdi-magnify"
            dense
            outlined
            hide-details
            @keyup.enter="search(keyword)"
            @click:append="search(keyword)"
          ></v-text-field>
        </v-col>
        <v-col cols="2" class="pl-0 pr-0" style="display:flex;">
          <v-btn :ripple="false" elevation="0" id="searchBtn" style="width:10%;" @click="searchKeyword(keyword)">검색</v-btn>
          <v-btn :ripple="false" elevation="0" id="resetBtn" style="width:15%;" @click="resetSearch">초기화</v-btn>
        </v-col>
        <v-col cols="3">
          <v-select
            style="width:50%; float:right;"
            :items="items"
            color="#0c0f66"
            label="정렬 기준"
            v-model="sortBy"
            dense
            outlined
            hide-details
          ></v-select>
        </v-col>
      </v-row>
      <v-row style="width: 100%; margin: 0 auto;">
        <v-col style="height:480px; display:flex; justify-content:center; align-items:center;" v-if="!isResultExist" cols="12">
          <div style="text-align:center;">
            <v-icon color="#333" large block> mdi-broadcast-off </v-icon>
            <h2>{{ nodata }}</h2>
          </div>
        </v-col>
        <v-col
          v-for="(consulting, i) in currentPage"
          :key="i"
          cols="12"
          v-else
          @click="watchConsulting(consulting.consultant, consulting.no)"
          style="cursor: pointer"
        >
          <v-card
            tile
            outlined
          >
            <div class="d-flex flex-row">
              <v-avatar
                class="ma-2"
                size="300"
                tile
              >
                <img :src="`${API_BASE_URL}/users/profile/image/${consulting.consultant_no}`" @error="replaceByDefault" />
              </v-avatar>
              <div class="d-flex flex-column">
                <v-card-title
                  style="font-weight: 600; font-size:24px; text-align:left;"
                  v-text="consulting.title"
                ></v-card-title>


                <v-card-subtitle class="pt-1 mb-1" style="text-align:start; font-size:20px; padding: 0px; padding-left:16px;">
                  {{ consulting.consultant }}
                </v-card-subtitle>
                <v-card-subtitle style="font-size:16px; text-align:start; padding-top:0;">
                  <v-icon small>mdi-account-multiple</v-icon> {{ consulting.viewCount }} / {{ consulting.roomSize }}<br>
                </v-card-subtitle>

                <v-card-text style="text-align: left; min-height:100px;">
                  {{ consulting.intro }}
                </v-card-text>

                <v-card-actions class="pt-0">
                  <v-chip-group>
                    <v-chip
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
      <v-row v-if="isResultExist" style="width: 100%; margin: 0 auto;">
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
  </v-container>
</template>

<script>
import _ from 'lodash'
import { mapActions, mapState } from 'vuex'
import { API_BASE_URL } from "@/config";
import { consulting } from "@/api/consulting.js";
import defaultProfileSetter from "@/utils/defaultProfileSetter.js";

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
      keyword: '',
      nodata: '현재 진행 중인 방송이 없습니다.',
      items: ['제목', '현재 인원', '전체 인원'],
      type: { '제목': 'title', '현재 인원': 'viewCount', '전체 인원': 'roomSize' },
      consultingsResult: [],
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
    replaceByDefault: defaultProfileSetter.replaceByDefault,
    async watchConsulting(nickname, consultingNo) {
      const res = await this.$dialog.confirm({
          text: '<br>' + nickname +'님의 방송에 참여하시겠습니까?',
          icon: true,
          actions: {
            false : {
              text: '취소', color: '#ff7451'
            },
            true : {
              text: '확인', color: '#0c0f66'
            },
          }
        });
      if (res) {
        this.$router.push({name:'consulting-onair', params:{ nickname: nickname, consulting_no: consultingNo }})
      }
    },
    sortResult(sortBy) {
      this.consultingsResult.sort((a, b) => {
        if (b[this.type[sortBy]] > a[this.type[sortBy]]) {
          return 1
        } else if (b[this.type[sortBy]] < a[this.type[sortBy]]) {
          return -1
        } else {
          return 0
        }
      })
    },
    // 검색 카테고리 별 정렬
    search(keyword) {
      this.keyword = keyword
      this.sortBy = '';
      this.searchKeyword(this.keyword)

      this.keyword = '';
    },
    // 검색 결과 초기화
    resetSearch() {
      this.keyword = '';
      this.sortBy = '';
      this.nodata= '현재 진행 중인 방송이 없습니다.';
      consulting.getAllLatestConsultings()
      .then((data)=> {
        this.consultingsResult = data["content"];
      })
      .catch(()=> {
      })
    },
  },
  computed: {
    ...mapState(searchStore, ['consultingResult']),
    totalPages() {
      return this.consultingsResult.length % this.itemsPerPage > 0 ? parseInt(this.consultingResult.length/this.itemsPerPage)+1 : parseInt(this.consultingResult.length/this.itemsPerPage)
    },
    currentPage() {
      const start = (this.page-1)*this.itemsPerPage
      const end = start+this.itemsPerPage
      return this.consultingsResult.slice(start, end)
    },
    isResultExist() {
      return !_.isEmpty(this.consultingsResult)
    },
  },
  mounted() {
    consulting.getAllLatestConsultings()
    .then((data)=> {
      this.consultingsResult = data["content"];
    })
    .catch(()=> {
    })
  },
  watch: {
    sortBy() {
      this.sortResult(this.sortBy)
    },
    consultingResult() {
      this.consultingsResult = this.consultingResult;
      if(this.consultingResult.length == 0) {
        this.nodata = "검색 결과가 없습니다";
      }
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

button {
  margin-left: 10px;
  height: 40px !important;
}

#searchBtn {
  background-color: #0c0f66;
  color: white;
}

#resetBtn {
  background-color: #ff7451;
  color: white;
}
</style>
