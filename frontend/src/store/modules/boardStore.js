import axios from "axios";

const boardStore = {
  namespaced: true,
  state: {
    // 전체 게시글 목록
    boardList: [],

    // 조회할 게시글 정보
    currentBoard: {},

    // 조회 중인 글의 댓글 목록
    commentList: [],
  },
  getters: {
    boardList: state => state.boardList,
    freeBoards: state => state.boardList.filter(board => board.category === 'Free'),
    qnaBoards: state => state.boardList.filter(board => board.category === 'QnA'),
    infoBoards: state => state.boardList.filter(board => board.category === 'Info'),
    currentBoard: state => state.currentBoard,
    commentList: state => state.commentList,
  },
  mutations: {
    SET_BOARD_LIST: (state, boardList) => state.boardList = boardList,
    SET_BOARD: (state, board) => state.currentBoard = board,
    SET_COMMENT_LIST: (state, commentList) => state.commentList = commentList,
  },
  actions: {
    fetchBoardList({ commit }) {
      axios({
        url: 'http://localhost:8888/api/boards/',
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': sessionStorage.getItem('X-AUTH-TOKEN') }
      })
        .then(res => {
          console.log('getallboards', res)
          commit('SET_BOARD_LIST', res.data.content)
        })
        .catch(err => {
          console.log(err)
        })
    },
    createBoard(context, board) {
      for(let key of board.keys())
        console.log(`${key}: ${board.get(key)}`);

      axios({
        url: 'http://localhost:8888/api/boards/',
        method: 'post',
        data: board,
        headers: { "Content-Type": "multipart/form-data", "X-AUTH-TOKEN": sessionStorage.getItem('X-AUTH-TOKEN') }
      })
        .then(res => {
          console.log(res)
        })
        .catch(err => {
          console.log(err)
        })
      },
    fetchCommentList({ commit }, commentList) {
      commit('SET_COMMENT_LIST', commentList)
    },
    fetchBoard({ commit, dispatch }, boardNo) {
      axios({
        url: `http://localhost:8888/api/boards/${boardNo}`,
        method: 'get',
        headers: { 'X-AUTH-TOKEN': sessionStorage.getItem('X-AUTH-TOKEN') }
      })
        .then(res => {
          console.log(res.data.content.board)
          commit('SET_BOARD', res.data.content.board)
          dispatch('fetchCommentList', res.data.content.comments)
        })
        .catch(err => {
          console.log(err)
        })
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
