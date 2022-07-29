const boardStore = {
  namespaced: true,
  state: {
    // 임시로 설정한 글 목록입니다
    articleList: [
      { articleNo: 1, title: '첫번째 글', writer: '류경하1', category: '자유', content: '내용1', created_at: '2010-07-10' },
      { articleNo: 2, title: '두번째 글', writer: '류경하2', category: '질문', content: '내용2', created_at: '2010-07-10' },
      { articleNo: 3, title: '세번째 글', writer: '류경하3', category: '자유', content: '내용3', created_at: '2010-07-10' },
      { articleNo: 4, title: '네번째 글', writer: '류경하4', category: '후기', content: '내용4', created_at: '2010-07-10' },
   ],
  },
  getters: {
    articleList: state => state.articleList
  },
  mutations: {

  },
  actions: {

  },
};

export default boardStore;
