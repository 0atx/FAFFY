import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

import authStore from "@/store/modules/authStore.js";

export default new Vuex.Store({
  plugins: [
    createPersistedState({
      storage: sessionStorage,
    }),
  ],

  modules: {
    authStore,
  },
});
