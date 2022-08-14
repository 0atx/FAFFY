<template>
<v-container class="d-flex flex-column">
    <v-row class="d-flex justify-space-between">
      <h2 class="ml-4">유저 검색 결과</h2>
      <v-btn v-if="users.length != 0" text>인기 순</v-btn>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-col style="height:350px; display:flex; justify-content:center; align-items:center;" v-if="users.length == 0" cols="12">
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
              >
                <td class="d-flex flex-row align-center">

                  <!--프로필 이미지 + 닉네임 -->
                  <v-avatar
                    size="36px">
                    <img
                      alt="Avatar"
                      :src="user.img"
                    >
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
                <td><v-icon small> mdi-heart </v-icon> {{ user.followings }} </td>

                <!-- 팔로워 수 -->
                <td><v-icon small> mdi-heart </v-icon> {{ user.followers }} </td>

              </tr>
            </tbody>
          </template>
        </v-simple-table>
      </v-col>
    </v-row>

    <!-- 페이지네이션 -->
    <v-row v-if="users.length != 0">
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
  name: 'UserSearchResult',
  props: {
    keyword: String,
  },
  data() {
    return {
      page: 1,
      itemsPerPage: 5,
      totalVisible: 7,
      users: [
        {
          no: 1,
          img: 'https://mblogthumb-phinf.pstatic.net/20151215_146/rlatnals8712_1450141030738pC6eR_PNG/20151215_094901.png?type=w2',
          nickname: '이준성',
          email: 'ssafy@ssafy.com',
          followings: 5,
          followers: 10,
          categories: ['힙합', '시크', '모던'],
        },
        {
          no: 1,
          img: 'https://mblogthumb-phinf.pstatic.net/20151215_146/rlatnals8712_1450141030738pC6eR_PNG/20151215_094901.png?type=w2',
          nickname: '류경하',
          email: 'ssafy@ssafy.com',
          followings: 15,
          followers: 17,
          categories: ['힙합', '시크', '모던'],
        },
        {
          no: 1,
          img: 'https://mblogthumb-phinf.pstatic.net/20151215_146/rlatnals8712_1450141030738pC6eR_PNG/20151215_094901.png?type=w2',
          nickname: '김명석',
          email: 'ssafy@ssafy.com',
          followings: 21,
          followers: 13,
          categories: ['힙합', '시크', '모던'],
        },
        {
          no: 1,
          img: 'https://mblogthumb-phinf.pstatic.net/20151215_146/rlatnals8712_1450141030738pC6eR_PNG/20151215_094901.png?type=w2',
          nickname: '김수만',
          email: 'ssafy@ssafy.com',
          followings: 213,
          followers: 100,
          categories: ['힙합', '시크'],
        },
      ]
    }
  },
  computed: {
    totalPages() {
      return this.users.length % this.itemsPerPage > 0 ? parseInt(this.users.length/this.itemsPerPage)+1 : parseInt(this.users.length/this.itemsPerPage)
    },
    // 페이지네이션 - 현재 페이지 게시물
    currentPage() {
      const start = (this.page-1)*this.itemsPerPage
      const end = start+this.itemsPerPage
      return this.users.slice(start, end)
    },
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
