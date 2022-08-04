import { auth } from "@/api/auth.js";

const authStore = {
  namespaced: true,
  state: {
    isLogin: false,
    loginUser: null,
  },
  checkUserInfo: function (state) {
    return state.userInfo;
  },
  mutations: {
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = userInfo != null ? true : false;
      state.loginUser = userInfo;
    },
  },
  actions: {
    async logout({commit}) {
      await auth.logout((response) => {
        console.log("로그아웃 성공");
        console.log(response);
        commit("SET_USER_INFO",null);

        sessionStorage.removeItem("X-AUTH-TOKEN");
      },
      (response)=> {
        console.log("로그아웃 실패");
        console.log(response);
      })
    }
    // async userModify({ commit }, user) {
    //   await auth.userModify(user, (response) => {
    //     commit("SET_USER_INFO", response.data.userInfo);
    //   });
    // },
    // async userDelete({ commit }) {
    //   await auth.userDelete((response) => {
    //     if (response.data.message === "success") {
    //       commit("SET_USER_INFO", "");
    //       commit("SET_IS_LOGIN", false);
    //       commit("SET_IS_LOGIN_ERROR", true);
    //     }
    //   });
    // },
  },
};

export default authStore;
