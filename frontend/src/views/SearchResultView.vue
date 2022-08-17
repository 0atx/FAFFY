<template>
  <v-container style="text-align: start">
    <div class="mt-10 mb-5">
      <h1>"{{ keyword }}" 에 대한 검색 결과입니다.</h1>
    </div>
    <!--방송 검색 결과-->
    <consulting-search-result :keyword="keyword" />
    <!--게시판 검색 결과-->
    <board-search-result :keyword="keyword" />
    <!--유저 검색 결과-->
    <user-search-result :keyword="keyword" />
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
    this.searchKeyword(this.keyword)
  },
}
</script>

<style scoped>

</style>
