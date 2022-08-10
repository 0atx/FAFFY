<template>
<v-container class="d-flex flex-column">
    <v-row class="d-flex justify-space-between">
      <h2>유저 검색 결과</h2>
      <v-btn text>인기 순</v-btn>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-simple-table>
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-start" style="width: 17%;">
                  닉네임
                </th>
                <th class="text-start" style="width: 33%;">
                  이메일
                </th>
                <th class="text-start" style="width: 17%;">
                  팔로워 수
                </th>
                <th class="text-start" style="width: 33%;">
                  관심 카테고리
                </th>
              </tr>
            </thead>
            <!--각 유저 각 행을 클릭시 프로필로 이동하거나 다른 처리 가능-->
            <tbody>
              <tr
                v-for="user in users"
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
                  {{ user.nickname}}
                </td>

                <!-- 이메일 -->
                <td>{{ user.email }}</td>

                <!-- 팔로워 수 -->
                <td><v-icon>mdi-heart</v-icon>{{ user.followers }}</td>

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
              </tr>
            </tbody>
          </template>
        </v-simple-table>
      </v-col>
    </v-row>

    <!-- 페이지네이션 -->
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
  name: 'UserSearchResult',
  data() {
    return {
      headers: [
        { text: '닉네임', align:'start', value:'nickname', width: '17%'},
        { text: '이메일', align: 'center',value:'email', width: '33%'},
        { text: '팔로워 수', align: 'end',value:'followers', width: '17%'},
        { text: '관심 카테고리', align: 'end',value:'categories', width: '33%'},
      ],
      users: [
        {
          no: 1,
          img: 'https://mblogthumb-phinf.pstatic.net/20151215_146/rlatnals8712_1450141030738pC6eR_PNG/20151215_094901.png?type=w2',
          nickname: '이준성',
          email: 'ssafy@ssafy.com',
          followers: 10,
          categories: ['힙합', '시크', '모던'],
        },
        {
          no: 1,
          img: 'https://mblogthumb-phinf.pstatic.net/20151215_146/rlatnals8712_1450141030738pC6eR_PNG/20151215_094901.png?type=w2',
          nickname: '류경하',
          email: 'ssafy@ssafy.com',
          followers: 17,
          categories: ['힙합', '시크', '모던'],
        },
        {
          no: 1,
          img: 'https://mblogthumb-phinf.pstatic.net/20151215_146/rlatnals8712_1450141030738pC6eR_PNG/20151215_094901.png?type=w2',
          nickname: '김명석',
          email: 'ssafy@ssafy.com',
          followers: 13,
          categories: ['힙합', '시크', '모던'],
        },
        {
          no: 1,
          img: 'https://mblogthumb-phinf.pstatic.net/20151215_146/rlatnals8712_1450141030738pC6eR_PNG/20151215_094901.png?type=w2',
          nickname: '김수만',
          email: 'ssafy@ssafy.com',
          followers: 100,
          categories: ['힙합', '시크'],
        },
      ]
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
