import axios from "axios";
import router from '@/router'
import { API_BASE_URL } from "@/config";

const boardStore = {
  namespaced: true,
  state: {
    // 전체 게시글 목록
    boardList: [],

    // 조회할 게시글 정보
    currentBoard: {},

    // 조회 중인 글의 댓글 목록
    commentList: [],

    // 조회할 게시글의 이미지
    currentImage: null,
  },
  getters: {
    boardList: state => state.boardList,
    freeBoards: state => state.boardList.filter(board => board.category === 'Free'),
    qnaBoards: state => state.boardList.filter(board => board.category === 'QnA'),
    infoBoards: state => state.boardList.filter(board => board.category === 'Info'),
    currentBoard: state => state.currentBoard,
    commentList: state => state.commentList,
    currentImage: state => state.currentImage,
  },
  mutations: {
    SET_BOARD_LIST: (state, boardList) => state.boardList = boardList,
    SET_BOARD: (state, board) => state.currentBoard = board,
    SET_COMMENT_LIST: (state, commentList) => state.commentList = commentList,
    SET_IMAGE: (state, img) => state.currentImage = img,
  },
  actions: {
    fetchBoardList({ commit }) {
      axios({
        url: API_BASE_URL + '/boards',
        method: 'get',
        headers: {
          'X-AUTH-TOKEN': sessionStorage.getItem('X-AUTH-TOKEN') }
      })
        .then(res => {
          commit('SET_BOARD_LIST', res.data.content)
        })
        .catch(() => {
        })
    },
    createBoard({ dispatch }, board) {
      axios({
        url: API_BASE_URL + '/boards',
        method: 'post',
        headers: { "X-AUTH-TOKEN": sessionStorage.getItem('X-AUTH-TOKEN'),
        "Content-Type": "multipart/form-data" },
        data: board
      })
        .then(res => {
          router.push({ name: 'board-detail', params: { boardNo: res.data.content.no } })
        })
        .then(() => {
          dispatch('fetchBoardList')
        })
        .catch(() => {
        })
      },
    fetchCommentList({ commit }, commentList) {
      commit('SET_COMMENT_LIST', commentList)
    },
    fetchBoard({ commit, dispatch }, boardNo) {
      axios({
        url: API_BASE_URL + `/boards/${boardNo}`,
        method: 'get',
        headers: { 'X-AUTH-TOKEN': sessionStorage.getItem('X-AUTH-TOKEN') }
      })
        .then(res => {
          commit('SET_BOARD', res.data.content.board)
          dispatch('fetchCommentList', res.data.content.comments)
          if (res.data.content.board.fileNo !== 0) {
            dispatch('fetchImage', res.data.content.board.fileNo)
          } else {
            commit('SET_IMAGE', null)
          }
        })
        .catch(() => {
        })
    },
    createComment(context, commentForm) {
      axios({
        url: API_BASE_URL + '/comments',
        method: 'post',
        headers: { "X-AUTH-TOKEN": sessionStorage.getItem('X-AUTH-TOKEN') },
        data: commentForm
      })
        .then(() => {
          router.go(router.currentRoute)
        })
        .catch(() => {
        })
    },
    deleteBoard({ dispatch, commit }, boardNo) {
      axios({
        url: API_BASE_URL + '/boards',
        method: 'delete',
        headers: { "X-AUTH-TOKEN": sessionStorage.getItem('X-AUTH-TOKEN') },
        data: {
          no: boardNo
        }
      })
        .then(() => {
          commit('SET_BOARD', {})
        })
        .then(() => {
          dispatch('fetchBoardList')
        })
        .then(() => {
          router.push({ name: 'board' })
        })
        .catch(() => {
        })
    },
    deleteComment(context, commentNo) {
      axios({
        url: API_BASE_URL + `/comments/${commentNo}`,
        method: 'delete',
        headers: { "X-AUTH-TOKEN": sessionStorage.getItem('X-AUTH-TOKEN') },
      })
        .then(() => {
          router.go(router.currentRoute)
        })
        .catch(() => {
        })
    },
    updateBoard(context, formData) {
      axios({
        url: API_BASE_URL + '/boards',
        method: 'put',
        headers: { "X-AUTH-TOKEN": sessionStorage.getItem('X-AUTH-TOKEN'),
      "Content-Type": "multipart/form-data" },
        data: formData
      })
        .then(() => {
          router.push({ name: 'board-detail', params: { boardNo: formData.get('no')}})
        })
        .catch(() => {
        })
    },
    fetchImage({ commit }, fileNo) {
      axios({
        url: API_BASE_URL + `/boards/file/${fileNo}`,
        method: 'get',
        responseType: 'blob',
        headers: { "X-AUTH-TOKEN": sessionStorage.getItem('X-AUTH-TOKEN') },
      })
        .then(res => {
          const url = window.URL.createObjectURL(new Blob([res.data], { type: res.headers['content-type'] } ))
          commit('SET_IMAGE', url)
        })
        .catch(() => {
        })
    },
    increaseHit(context,no) {
      console.log("조회수axios")
      console.log(no);
      axios({
        url: `${API_BASE_URL}/boards/view/${no}`,
        method: 'put',
        headers: { "X-AUTH-TOKEN": sessionStorage.getItem('X-AUTH-TOKEN') },
      })
        .then(res => {
          console.log('조회수 늘리기 성공', res)
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
};

export default boardStore;
