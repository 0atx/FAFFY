import axios from "axios";

const boardStore = {
  namespaced: true,
  state: {
    // 임시로 설정한 글 목록입니다
    articleList: [],
    currentArticle: {},
  },
  getters: {
    articleList: state => state.articleList,
    freeArticles: state => state.articleList.filter(article => article.category === 'Free'),
    qnaArticles: state => state.articleList.filter(article => article.category === 'QnA'),
    infoArticles: state => state.articleList.filter(article => article.category === 'Info'),
    currentArticle: state => state.currentArticle,
  },
  mutations: {
    SET_ARTICLES: (state, articles) => state.articleList = articles,
    SET_ARTICLE: (state, article) => state.currentArticle = article,
  },
  actions: {
    fetchArticles({ commit }) {
      axios({
        url: 'http://localhost:8888/api/boards/',
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': sessionStorage.getItem('X-AUTH-TOKEN') }
      })
        .then(res => {
          console.log('getallboards', res)
          commit('SET_ARTICLES', res.data.content)
        })
        .catch(err => {
          console.log(err)
        })
    },
    createArticle(context, article) {
      axios({
        url: 'http://localhost:8888/api/boards/',
        method: 'post',
        data: article,
        headers: { "X-AUTH-TOKEN": sessionStorage.getItem('X-AUTH-TOKEN') }
      })
        .then(res => {
          console.log(res)
          this.$router.push({ name: 'board' })
        })
        .catch(err => {
          console.log(err)
        })
    },
    fetchArticle({ commit }, articleNo) {
      axios({
        url: `http://localhost:8888/api/boards/${articleNo}`,
        method: 'get',
        headers: { 'X-AUTH-TOKEN': sessionStorage.getItem('X-AUTH-TOKEN') }
      })
        .then(res => {
          console.log(res.data.content.board)
          commit('SET_ARTICLE', res.data.content.board)
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
};

export default boardStore;
