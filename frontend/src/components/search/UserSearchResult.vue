<template>
<v-container class="d-flex flex-column">
    <v-row class="d-flex justify-space-between">
      <h2 class="ml-4">유저 검색 결과 </h2>
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
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-col style="height:350px; display:flex; justify-content:center; align-items:center;" v-if="!isResultExist" cols="12">
          <div style="text-align:center;">
            <v-icon color="#333" large block> mdi-account-off-outline </v-icon>
            <h2>'{{ keyword }}'에 대한 검색 결과가 없습니다.</h2>
          </div>
        </v-col>
        <v-simple-table v-else>
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-start" style="width: 20%;">
                  닉네임
                </th>
                <th class="text-start" style="width: 25%;">
                  이메일
                </th>
                <th class="text-start" style="width: 35%;">
                  관심 카테고리
                </th>
                <th class="text-start" style="width: 10%;">
                  팔로잉 수
                </th>
                <th class="text-start" style="width: 10%;">
                  팔로워 수
                </th>
              </tr>
            </thead>
            <!--각 유저 각 행을 클릭시 프로필로 이동하거나 다른 처리 가능-->
            <tbody>
              <tr
                v-for="user in currentPage"
                :key="user.nickname"
                @click="userDetail(user.no)"
                style="cursor: pointer"
              >
                <td class="d-flex flex-row align-center">

                  <!--프로필 이미지 + 닉네임 -->
                    <v-avatar color="#fff" size="36px" rounded>
                      <img
                        :src="`${API_BASE_URL}/users/profile/image/${user.no}`"
                        @error="replaceByDefault"
                      />
                    </v-avatar>
                  <div class="ml-2">{{ user.nickname }}</div>

                </td>

                <!-- 이메일 -->
                <td>{{ user.email }}</td>

                <!-- 관심 카테고리 -->
                <td>
                  <v-chip-group>
                    <v-chip
                      small
                      :ripple="false"
                      id="categoryChip"
                      v-for="category in user.categories"
                      :key="category"
                      :category="category"
                    >
                      {{ category }}
                    </v-chip>
                  </v-chip-group>
                </td>

                <!-- 팔로잉 수 -->
                <td><v-icon small> mdi-heart </v-icon> {{ user.followingCount }} </td>

                <!-- 팔로워 수 -->
                <td><v-icon small> mdi-heart </v-icon> {{ user.followerCount }} </td>

              </tr>
            </tbody>
          </template>
        </v-simple-table>
      </v-col>
    </v-row>

    <!-- 페이지네이션 -->
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
  name: 'UserSearchResult',
  props: {
    keyword: String,
  },
  data() {
    return {
      page: 1,
      itemsPerPage: 5,
      totalVisible: 7,
      API_BASE_URL: API_BASE_URL,
      items: ['닉네임', '팔로워'],
      sortBy: '',
      type: { '닉네임': 'nickname', '팔로워': 'followers' }
    }
  },
  computed: {
    ...mapGetters(searchStore, ['userResult']),
    totalPages() {
      return this.userResult.length % this.itemsPerPage > 0 ? parseInt(this.userResult.length/this.itemsPerPage)+1 : parseInt(this.userResult.length/this.itemsPerPage)
    },
    // 페이지네이션 - 현재 페이지 게시물
    currentPage() {
      const start = (this.page-1)*this.itemsPerPage
      const end = start+this.itemsPerPage
      return this.userResult.slice(start, end)
    },
    isResultExist() {
      return !_.isEmpty(this.userResult)
    }
  },
  methods: {
    replaceByDefault: defaultProfileSetter.replaceByDefault,
    userDetail(userNo) {
      this.$router.push({ name: 'user-detail', params: { no: userNo }})
    },
    sortResult(sortBy) {
      this.userResult.sort((a, b) => {
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

<style>
#categoryChip {
  background-color: #0c0f66;
  color: #fff;
  pointer-events: none;
}
</style>
