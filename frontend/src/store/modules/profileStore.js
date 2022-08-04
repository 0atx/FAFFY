import { user, follow } from "@/api/user.js";

const profileStore = {
  namespaced: true,
  state: {
    userProfile: null,
    followerList: [],
    followingList: [],
  },
  getCurrentProfile: function (state) {
    return state.userProfile;
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
  },
  actions: {
    async getUserProfile({ commit }, user_no) {
      user.getUserProfile(
        user_no,
        (response) => {
          console.log(response.data["content"]);
          commit("SET_USER_PROFILE", response.data["content"]);
        },
        (response) => {
          console.log(response);
          commit("SET_USER_PROFILE", null);
        }
      );
    },
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
  },
};

export default profileStore;
