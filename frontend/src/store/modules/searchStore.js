import axios from "axios";
import { API_BASE_URL } from "@/config";

const searchStore = {
  namespaced: true,
  state: {
    consultingResult: [],
    boardResult: [],
    userResult: [],
  },
  getters: {
    consultingResult: state => state.consultingResult,
    boardResult: state => state.boardResult,
    userResult: state => state.userResult,
  },
  mutations: {
    SET_CONSULTING_RESULT: (state, consulting) => {
      state.consultingResult = consulting
    },
    SET_BOARD_RESULT: (state, board) => {
      state.boardResult = board
    },
    SET_USER_RESULT: (state, user) => {
      state.userResult = user
    }
  },
  actions: {
    searchKeyword({ commit }, keyword) {
      axios({
        url: API_BASE_URL + '/main/search',
        method: 'get',
        params: { keyword: keyword }
      })
        .then(res => {
          console.log('검색 성공', res)
          commit('SET_CONSULTING_RESULT', res.data.consulting.content)
          commit('SET_BOARD_RESULT', res.data.board.content)
          commit('SET_USER_RESULT', res.data.user.content)
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
};

export default searchStore;
