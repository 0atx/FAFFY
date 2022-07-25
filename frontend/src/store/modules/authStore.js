import { auth } from "@/api/auth.js";

const authStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: null,
  },
  checkUserInfo: function (state) {
    return state.userInfo;
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = true;
      state.userInfo = userInfo;
    },
  },
  actions: {
    async userConfirm({commit},user) {
      await auth.login(
        user,
        (response)=> {
          console.log("로그인 요청 성공!");
          const access_token = response.data["content"];
          sessionStorage.setItem("X-AUTH-TOKEN", access_token);
          commit("SET_USER_INFO",response.data);
          commit("SET_IS_LOGIN",true);
        },
        (response)=> {
          console.log("로그인 요청 실패!");
          commit("SET_IS_LOGIN",false);
          commit("SET_IS_LOGIN_ERROR",true);
          console.log(response);
        });
    }
    // async userConfirm({ commit }, user) {
    //   await auth.login(
    //     user,
    //     (response) => {
    //         let accessToken = response.data["access-token"];
    //         let refreshToken = response.data["refresh-token"];
    //         commit("SET_IS_LOGIN", true);
    //         commit("SET_IS_LOGIN_ERROR", false);
    //         sessionStorage.setItem("access-token", accessToken);
    //         sessionStorage.setItem("refresh-token", refreshToken);
    //     },
    //     (response) => {
    //       console.log(response.message);
    //       commit("SET_IS_LOGIN", false);
    //       commit("SET_IS_LOGIN_ERROR", true);
    //     }
    //   );
    // },
    // async getLoginedUser({ commit }) {
    //   await auth.getUserById((response) => {
    //     commit("SET_USER_INFO", response.data.userInfo);
    //   });
    // },
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
