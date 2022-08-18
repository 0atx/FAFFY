<template>
  <v-container class="d-flex flex-column">
    <v-row class="d-flex justify-space-between">
      <h2 class="ml-4">방송 검색 결과</h2>
      <div
        class="pt-0"
      >
        <v-select
          style="width:50%; float:right;"
          :items="items"
          label="정렬 기준"
          color="#0c0f66"
          dense
          outlined
          hide-details
          v-model="sortBy"
        ></v-select>
      </div>
    </v-row>
    <v-row>
      <v-col style="height:500px; display:flex; justify-content:center; align-items:center;" v-if="!isResultExist" cols="12">
        <div style="text-align:center;">
          <v-icon color="#333" large block> mdi-broadcast-off </v-icon>
          <h2>'{{ keyword }}'에 대한 검색 결과가 없습니다.</h2>
        </div>
      </v-col>
      <v-col v-else cols="3" v-for="consulting in currentPage" :key="consulting.host">
        <v-card outlined elevation="0" class="mx-auto" style="height: 460px; width: 300px;">
          <!--방송화면 or 썸네일 -->
          <img
            :src="`${API_BASE_URL}/users/profile/image/${consulting.consultant_no}`"
            height="200px"
            width="100%"
            @error="replaceByDefault"
          />

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
                    {{ consulting.consultant }}
                  </v-list-item-subtitle>
                  <v-list-item-subtitle style="font-size: 12px;">
                    <v-icon small>mdi-account-multiple</v-icon> {{ consulting.viewCount }} / {{ consulting.roomSize }}
                  </v-list-item-subtitle>
                </div>
              </div>

              <!-- 방송 소개 -->
              <div class="pt-1 mb-1" style="font-size:15px;" v-if="consulting.intro.length > 80" id="intro">{{ consulting.intro.slice(0,80) }}...</div>
              <div class="pt-1 mb-1" style="font-size:15px;" v-else id="intro">{{ consulting.intro }}</div>

              <!-- 방송 카테고리 -->
              <v-chip-group style="min-height: 40px;" column>
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

              <div style="text-align:right;">
                <!-- 참여 버튼. 클릭 시 화상회의 방 진입 -->
                <v-btn
                  outlined
                  rounded
                  text
                  :to="{name:'consulting-onair',params:{nickname:consulting.consultant,consulting_no:consulting.no}}"
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
</template>

<script>
import _ from 'lodash'
import { mapGetters } from 'vuex'
import { API_BASE_URL } from "@/config";
import defaultProfileSetter from "@/utils/defaultProfileSetter.js";
const searchStore = "searchStore"

export default {
  name: 'ConsultingSearchconsulting',
  props: {
    keyword: String,
  },
  data() {
    return {
      API_BASE_URL: API_BASE_URL,
      page: 1,
      itemsPerPage: 4,
      totalVisible: 7,
      sortBy: '',
      items: ['제목', '현재 인원', '전체 인원'],
      type: { '제목': 'title', '현재 인원': 'viewCount', '전체 인원': 'roomSize' }
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
  methods: {
    replaceByDefault: defaultProfileSetter.replaceByDefault,
    sortResult(sortBy) {
      this.boardResult.sort((a, b) => {
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
  watch: {
    sortBy() {
      this.sortResult(this.sortBy)
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

#intro {
  height: 65px;
  display: flex;
  padding: 0;
  justify-content: flex-start;
  pointer-events: none;
  font-size: 15px;
}

.iconBtn::before {
  background-color: transparent;
}

.sortBtn::before {
  background-color: transparent;
}
</style>
