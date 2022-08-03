import axios from "axios";

const boardStore = {
  namespaced: true,
  state: {
    // 임시로 설정한 글 목록입니다
    articleList: [
      { articleNo: 1, title: '첫번째 글1', writer: '류경하1', category: '자유', content: '내용1', created_at: '2010-07-30', comments: 1 },
      { articleNo: 2, title: '첫번째 글11', writer: '류경하12', category: '질문', content: '내용1', created_at: '2010-07-12', comments: 71 },
      { articleNo: 3, title: '첫번째 글12', writer: '류경하13', category: '후기', content: '내용1', created_at: '2010-07-15', comments: 75 },
      { articleNo: 4, title: '첫번째 글1312', writer: '류경하14', category: '후기', content: '내용1', created_at: '2010-07-13', comments: 17 },
      { articleNo: 5, title: '첫번째 글44', writer: '류경하11', category: '자유', content: '내용1', created_at: '2010-07-14', comments: 27 },
      { articleNo: 6, title: '첫번째 글23', writer: '류경하122', category: '자유', content: '내용1', created_at: '2010-03-10', comments: 37 },
      { articleNo: 7, title: '첫번째 글2', writer: '류경하1113', category: '질문', content: '내용1', created_at: '2010-02-10', comments: 47 },
      { articleNo: 8, title: '첫번째 글2', writer: '류경하144', category: '질문', content: '내용1', created_at: '2010-01-10', comments: 57 },
      { articleNo: 9, title: '첫번째 글1', writer: '류경하12', category: '자유', content: '내용1', created_at: '2010-12-10', comments: 107 },
      { articleNo: 10, title: '첫번째 글1', writer: '류경하3133', category: '자유', content: '내용1', created_at: '2010-11-10', comments: 777 },
      { articleNo: 11, title: '첫번째 글12', writer: '류경하415', category: '후기', content: '내용1', created_at: '2010-10-10', comments: 97 },
      { articleNo: 12, title: '첫번째 글21', writer: '류경하421', category: '질문', content: '내용1', created_at: '2010-08-10', comments: 27 },
      { articleNo: 13, title: '첫번째 글33', writer: '류경하171', category: '자유', content: '내용1', created_at: '2010-09-10', comments: 47 },
      { articleNo: 14, title: '첫번째 글44', writer: '류경하881', category: '자유', content: '내용1', created_at: '2010-07-01', comments: 57 },
   ],
    currentArticle: {no: 1, category: '질문', title: 'cors 개빡치네', content: '내용1', hit: '3355', 'writer': '류경하'},
  },
  getters: {
    articleList: state => state.articleList,
    freeArticles: state => state.articleList.filter(article => article.category === '자유'),
    qnaArticles: state => state.articleList.filter(article => article.category === '질문'),
    reviewArticles: state => state.articleList.filter(article => article.category === '후기'),
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
          console.log(res)
          commit('SET_ARTICLES', res.data)
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
        headers: { 'X-AUTH-TOKEN': sessionStorage.getItem('X-AUTH-TOKEN') }
      })
        .then(res => {
          console.log(res)
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
          console.log(res)
          commit('SET_ARTICLE', res.data)
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
};

export default boardStore;
