import { auth } from "@/api/auth.js";
import {follow} from "@/api/user.js";
const authStore = {
  namespaced: true,
  state: {
    isLogin: false,
    loginUser: null,
    followingList:[],
    naverState: null,
  },
  getters: {
    checkUserInfo: function (state) {
      return state.loginUser;
    }
  },
  mutations: {
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = userInfo != null ? true : false;
      state.loginUser = userInfo;
    },
    SET_FOLLOWING_LIST:(state,followingList) => {
      state.followingList = followingList;
    },
    SET_NAVER_STATE:(state, data) => {
      state.naverState = data;
    }
  },
  actions: {
    async logout({commit}) {
      await auth.logout((response) => {
        alert("로그아웃 되었습니다.");
        console.log("로그아웃 성공");
        console.log(response);
        commit("SET_USER_INFO",null);
        commit("SET_FOLLOWING_LIST",[]);
        sessionStorage.removeItem("X-AUTH-TOKEN");
      },
      (response)=> {
        console.log("로그아웃 실패");
        console.log(response);
        commit("SET_USER_INFO",null);
        commit("SET_FOLLOWING_LIST",[]);
        sessionStorage.removeItem("X-AUTH-TOKEN");

      })
    },
    async loadFollowing({ commit ,state}) {
      console.log("팔로잉 리스트 불러오 ");
      follow.getFollowingList(
        state.loginUser.no,
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

export default authStore;
