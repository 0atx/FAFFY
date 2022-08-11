<!--
작성자: 류경하
설명: 게시판 좌측 메뉴(질문응답/자유/정보 게시판)
최종 수정일: 2022.08.10
최종 수정자: 박윤하
-->
<template>
  <v-card
    height="86vh"
    width="256"
    class="mx-auto"
    elevation="0"
    style="text-align:left;"
  >
    <v-navigation-drawer permanent>
      <v-list
        dense
        nav
      >
        <v-list-item
          v-for="item in items"
          :key="item.title"
          link
          @click="boardType(item.title)"
        >
          <v-list-item-icon>
            <v-icon color="#0c0f66">{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title style="font-size:16px">{{ item.title }} </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
  </v-card>
</template>
<script>
export default {
  name: 'SideBar',
  data () {
    return {
      items: [
        { title: '전체', icon: 'mdi-view-dashboard' },
        { title: '자유', icon: 'mdi-clipboard-text' },
        { title: '질문', icon: 'mdi-help-box' },
        { title: '정보', icon: 'mdi-image' },
      ],
      right: null,
    }
  },
  methods: {
    // 세부 게시판 글 조회
    boardType(type) {
      console.log(`${type} 게시판 보기`)
      this.$emit('changeBoardType', type)
    },
    moveBoard() {
      this.$router.go(this.$router.currentRoute)
    }
  }
}
</script>

<style>

</style>
