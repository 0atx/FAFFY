import axios from "axios";
import { API_BASE_URL } from "@/config";

const searchStore = {
  namespaced: true,
  state: {
    consultingResult: [],
    boardResult: [],
    userResult: [
      {
        no: 1,
        img: 'https://mblogthumb-phinf.pstatic.net/20151215_146/rlatnals8712_1450141030738pC6eR_PNG/20151215_094901.png?type=w2',
        nickname: '이준성',
        email: 'ssafy@ssafy.com',
        followings: 5,
        followers: 10,
        categories: ['힙합', '시크', '모던'],
      },
      {
        no: 2,
        img: 'https://mblogthumb-phinf.pstatic.net/20151215_146/rlatnals8712_1450141030738pC6eR_PNG/20151215_094901.png?type=w2',
        nickname: '류경하',
        email: 'ssafy@ssafy.com',
        followings: 15,
        followers: 17,
        categories: ['힙합', '시크', '모던'],
      },
      {
        no: 3,
        img: 'https://mblogthumb-phinf.pstatic.net/20151215_146/rlatnals8712_1450141030738pC6eR_PNG/20151215_094901.png?type=w2',
        nickname: '김명석',
        email: 'ssafy@ssafy.com',
        followings: 21,
        followers: 13,
        categories: ['힙합', '시크', '모던'],
      },
      {
        no: 4,
        img: 'https://mblogthumb-phinf.pstatic.net/20151215_146/rlatnals8712_1450141030738pC6eR_PNG/20151215_094901.png?type=w2',
        nickname: '김수만',
        email: 'ssafy@ssafy.com',
        followings: 213,
        followers: 100,
        categories: ['힙합', '시크'],
      },
    ],
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
          // commit('SET_USER_RESULT', res.data.user.content)
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
};

export default searchStore;
