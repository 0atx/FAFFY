import {user} from "@/api/user.js"

const profileStore = {
  namespaced:true,
  state:{
    userProfile:null,
  },
  getCurrentProfile:function(state) {
    return state.userProfile;
  },
  mutations: {
    SET_USER_PROFILE : (state, userProfile) => {
      state.userProfile = userProfile;
    }
  },
  actions: {
    async getUserProfile({commit},user_no) {
      await user.getUserProfile(user_no,
        (response) => {
          console.log("성공");
          console.log(response.data["content"]);
          commit("SET_USER_PROFILE",response.data["content"]);
        },
        (response) => {
          console.log("실패");
          console.log(response);
          commit("SET_USER_PROFILE",null);
        });
    }
  }
}


export default profileStore;
