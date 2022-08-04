import axios from "axios";

const boardStore = {
  namespaced: true,
  state: {
    // 전체 게시글 목록
    articleList: [],

    // 조회할 게시글 정보
    currentArticle: {},

    // 조회 중인 글의 댓글 목록
    commentList: [],
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
    SET_COMMENT_LIST: (state, commentList) => state.commentList = commentList,
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
    },
    fetchCommentList({ commit }, commentList) {
      commit('SET_COMMENT_LIST', commentList)
    },
    createComment(context, commentForm) {
      axios({
        url: 'http://localhost:8888/api/comments',
        method: 'post',
        headers: { "X-AUTH-TOKEN": sessionStorage.getItem('X-AUTH-TOKEN') },
        data: commentForm
      })
        .then(res => {
          console.log('성공', res)
        })
        .catch(err => {
          console.log('실패', err)
        })
    }
  },
};

export default boardStore;
