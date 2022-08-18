import { follow, consulting, userBoard } from "@/api/user.js";
import axios from "axios";
import { API_BASE_URL } from "@/config";

const profileStore = {
  namespaced: true,
  state: {
    userProfile: null,
    followerList: [],
    followingList: [],
    participatedList: [],
    consultingList: [],
    consultingHistoryList: [],
    userBoardList: [],
    historyDetail: {},
    snapshotList: [],
  },
  getCurrentProfile: function (state) {
    return state.userProfile;
  },
  getters: {
    participatedList: state => state.participatedList,
    consultingList: state => state.consultingList,
    consultingHistoryList: state => state.consultingHistoryList,
    userBoardList: state => state.userBoardList.reverse(),
    historyDetail: state => state.historyDetail,
    snapshotList: state => state.snapshotList,
  },
  mutations: {
    SET_USER_PROFILE: (state, userProfile) => {
      state.userProfile = userProfile;
    },
    SET_FOLLOWER_LIST: (state, followerList) => {
      state.followerList = followerList;
    },
    SET_FOLLOWING_LIST: (state, followingList) => {
      state.followingList = followingList;
    },
    SET_PARTICIPATED_LIST: (state, participatedList) => {
      state.participatedList = participatedList;
    },
    SET_CONSULTING_LIST: (state, consultingList) => {
      state.consultingList = consultingList;
    },
    SET_CONSULTING_HISTORY_LIST: (state, consultingHistoryList) => {
      state.consultingHistoryList = consultingHistoryList;
    },
    SET_USER_BOARD_LIST: (state, userBoardList) => {
      state.userBoardList = userBoardList;
    },
    SET_HISTORY_DETAIL: (state, historyDetail) => {
      state.historyDetail = historyDetail
    },
    PUSH_SNAPSHOT: (state, snapshot) => {
      state.snapshotList.push(snapshot)
    },
    RESET_SNAPSHOT: (state) => {
      state.snapshotList = []
    },
    SET_USER_PROFILE_FOLLOWERCOUNT: (state, followerCnt) => {
      state.userProfile.followerCount = followerCnt;
    }

  },
  actions: {
    async loadFollower({ commit }, user_no) {
      follow.getFollowerList(
        user_no,
        (response) => {
          commit("SET_FOLLOWER_LIST", response.data["content"]);
        },
        () => {
          commit("SET_FOLLOWER_LIST", []);
        }
      );
    },
    async loadFollowing({ commit }, user_no) {
      follow.getFollowingList(
        user_no,
        (response) => {
          commit("SET_FOLLOWING_LIST", response.data["content"]);
        },
        () => {
          commit("SET_FOLLOWING_LIST", []);
        }
      );
    },
    async loadParticipatedList({ commit }, user_no) {
      consulting.getParticipatedList(
        user_no,
        (response) => {
          commit("SET_PARTICIPATED_LIST", response.data["content"]);
        },
        () => {
          commit("SET_PARTICIPATED_LIST", []);
        }
      );
    },
    async loadConsultingList({ commit }, user_no) {
      consulting.getConsultingList(
        user_no,
        (response) => {
          commit("SET_CONSULTING_LIST", response.data["content"]);
        },
        () => {
          commit("SET_CONSULTING_LIST", []);
        }
      );
    },
    async loadConsultingListByDate({ commit }, user_no) {
      consulting.getConsultingHistoryByDate(
        user_no,
        (response) => {
          commit("SET_CONSULTING_HISTORY_LIST", response.data["content"]);
        },
        () => {
          commit("SET_CONSULTING_LIST", []);
        }
      );
    },
    async loadBoardList({ commit }, user_no) {
      userBoard.getUserBoardList(
        user_no,
        (response) => {
          commit("SET_USER_BOARD_LIST", response.data["content"]);
        },
        () => {
        }
      );
    },
    loadHistoryDetail({ commit, dispatch, state }, payload) {
      axios({
        url: API_BASE_URL + `/users/profile/${payload.user_no}/history/${payload.consulting_no}`,
        method: 'get',
      })
        .then(res => {
          commit("SET_HISTORY_DETAIL", res.data)
          dispatch('resetSnapshotList')
        })
        .then(() => {
          state.historyDetail.consultingDto.snapshotNoList.forEach((snapshotNo) => {
            dispatch('loadSnapshotList', snapshotNo)
          })
        })
        .catch(() => {
        })
    },
    loadSnapshotList({ commit }, snapshotNo) {
      axios({
        url: API_BASE_URL + `/consultings/snapshot/${snapshotNo}`,
        method: 'get',
        responseType: 'blob',
      })
        .then(res => {
          const url = window.URL.createObjectURL(new Blob([res.data], { type: res.headers['content-type'] } ))
          commit('PUSH_SNAPSHOT', url)
        })
        .catch(() => {
        })
    },
    resetSnapshotList({ commit }) {
      commit('RESET_SNAPSHOT')
    }
  },
};

export default profileStore;
