<template>
  <v-container style="text-align: start">
    <div>
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

export default {
  name: 'SearchResultView',
  data() {
    return {
    }
  },
  components: {
    BoardSearchResult,
    ConsultingSearchResult,
    UserSearchResult
  },
  computed: {
      keyword() { return this.$route.params.keyword }
  }
}
</script>

<style>

</style>
