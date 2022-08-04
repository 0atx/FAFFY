import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

import authStore from "@/store/modules/authStore.js";
import boardStore from "@/store/modules/boardStore.js";
import profileStore from "@/store/modules/profileStore.js"
import commentStore from "@/store/modules/commentStore.js"
export default new Vuex.Store({
  plugins: [
    createPersistedState({
      storage: sessionStorage,
    }),
  ],

  modules: {
    authStore,
    boardStore,
    profileStore,
    commentStore,
  },
});
