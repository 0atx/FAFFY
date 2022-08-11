import { follow, consulting, userBoard } from "@/api/user.js";

const profileStore = {
  namespaced: true,
  state: {
    userProfile: null,
    followerList: [],
    followingList: [],
    participatedList: [],
    consultingList: [],
    userBoardList: [],
  },
  getCurrentProfile: function (state) {
    return state.userProfile;
  },
  getters: {
    participatedList: state => state.participatedList.reverse(),
    consultingList: state => state.consultingList.reverse(),
    userBoardList: state => state.userBoardList.reverse(),
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
    SET_USER_BOARD_LIST: (state, userBoardList) => {
      state.userBoardList = userBoardList;
    },

  },
  actions: {
    async loadFollower({ commit }, user_no) {
      console.log("팔로워 리스트 불러오기");
      follow.getFollowerList(
        user_no,
        (response) => {
          console.log("팔로워 목록 불러오기 성공");
          commit("SET_FOLLOWER_LIST", response.data["content"]);
        },
        (response) => {
          console.log("팔로워 목록 불러오기 실패");
          console.log(response);
          commit("SET_FOLLOWER_LIST", []);
        }
      );
    },
    async loadFollowing({ commit }, user_no) {
      console.log("팔로잉 리스트 불러오기");
      follow.getFollowingList(
        user_no,
        (response) => {
          console.log("팔로잉 목록 불러오기 성공");
          commit("SET_FOLLOWING_LIST", response.data["content"]);
        },
        (response) => {
          console.log("팔로잉 목록 불러오기 실패");
          console.log(response);
          commit("SET_FOLLOWING_LIST", []);
        }
      );
    },
    async loadParticipatedList({ commit }, user_no) {
      console.log("방송 참가 기록 불러오기");
      consulting.getParticipatedList(
        user_no,
        (response) => {
          console.log("방송 참가 기록 불러오기 성공");
          commit("SET_PARTICIPATED_LIST", response.data["content"]);
        },
        (response) => {
          console.log("방송 참가 기록 불러오기 실패");
          console.log(response);
          commit("SET_PARTICIPATED_LIST", []);
        }
      );
    },
    async loadConsultingList({ commit }, user_no) {
      console.log("방송 진행 기록 불러오기");
      consulting.getConsultingList(
        user_no,
        (response) => {
          console.log("방송 진행 기록 불러오기 성공");
          console.log(response)
          commit("SET_CONSULTING_LIST", response.data["content"]);
        },
        (response) => {
          console.log("방송 진행 기록 실패");
          console.log(response);
          commit("SET_CONSULTING_LIST", []);
        }
      );
    },
    async loadBoardList({ commit }, user_no) {
      console.log("작성 게시글 불러오기");
      userBoard.getUserBoardList(
        user_no,
        (response) => {
          console.log("작성 게시글 불러오기 성공");
          commit("SET_USER_BOARD_LIST", response.data["content"]);
        },
        (response) => {
          console.log("작성 게시글 불러오기 실패");
          console.log(response);
          commit("SET_USER_BOARD_LIST", []);
        }
      );
    },
  },
};

export default profileStore;
