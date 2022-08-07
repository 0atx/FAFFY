import axios from "axios";
import router from '@/router'

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
          alert('게시글이 등록되었습니다.')
          router.push({ name: 'board' })
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
          router.go(router.currentRoute)
        })
        .catch(err => {
          console.log('실패', err)
        })
    },
    deleteBoard({ commit }, boardNo) {
      axios({
        url: 'http://localhost:8888/api/boards/',
        method: 'delete',
        headers: { "X-AUTH-TOKEN": sessionStorage.getItem('X-AUTH-TOKEN') },
        data: {
          no: boardNo
        }
      })
        .then(res => {
          console.log('삭제 성공', res)
          alert('게시글이 정상적으로 삭제되었습니다.')
          commit('SET_BOARD', {})
          router.push({ name: 'board' })
        })
        .catch(err => {
          console.log(err)
        })
    },
    deleteComment(context, commentNo) {
      axios({
        url: `http://localhost:8888/api/comments/${commentNo}`,
        method: 'delete',
        headers: { "X-AUTH-TOKEN": sessionStorage.getItem('X-AUTH-TOKEN') },
      })
        .then(res => {
          console.log('댓글 삭제', res)
          alert('댓글이 정상적으로 삭제되었습니다.')
          router.go(router.currentRoute)
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
};

export default boardStore;
