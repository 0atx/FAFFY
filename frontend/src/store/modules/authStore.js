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
      await auth.logout(() => {
        this.$dialog.message.info('로그아웃 되었습니다.', {
          position: "top",
          timeout: 2000,
          color: "#ff7451",
        });
        commit("SET_USER_INFO",null);
        commit("SET_FOLLOWING_LIST",[]);
        sessionStorage.removeItem("X-AUTH-TOKEN");
      },
      ()=> {
        commit("SET_USER_INFO",null);
        commit("SET_FOLLOWING_LIST",[]);
        sessionStorage.removeItem("X-AUTH-TOKEN");

      })
    },
    async loadFollowing({ commit ,state}) {
      follow.getFollowingList(
        state.loginUser.no,
        (response) => {
          commit("SET_FOLLOWING_LIST", response.data["content"]);
        },
        () => {
          commit("SET_FOLLOWING_LIST", []);
        }
      );
    },
  },
};

export default authStore;
