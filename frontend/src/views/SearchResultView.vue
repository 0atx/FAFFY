<template>
  <v-container style="text-align: start">
    <div class="mt-10 mb-5">
      <h1>"{{ keyword }}" 에 대한 검색 결과입니다.</h1>
    </div>
    <!--방송 검색 결과-->
    <consulting-search-result />
    <!--게시판 검색 결과-->
    <board-search-result />
    <!--유저 검색 결과-->
    <user-search-result />
  </v-container>
</template>

<script>
import BoardSearchResult from '@/components/search/BoardSearchResult.vue'
import ConsultingSearchResult from '@/components/search/ConsultingSearchResult.vue'
import UserSearchResult from '@/components/search/UserSearchResult.vue'
import { mapActions } from 'vuex';
const searchStore = "searchStore";

export default {
  name: 'SearchResultView',
  data() {
    return {
      // 검색어
      keyword: this.$route.params.keyword
    }
  },
  components: {
    BoardSearchResult,
    ConsultingSearchResult,
    UserSearchResult
  },
  methods: {
    ...mapActions(searchStore, ['searchKeyword']),
  },
  created() {
    console.log(this.keyword+'로 방송을 검색')
    console.log(this.keyword+'로 게시글을 검색')
    console.log(this.keyword+'로 유저목록을 검색')
    this.searchKeyword(this.keyword)
  },
}
</script>

<style scoped>

</style>
