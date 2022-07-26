import { apiInstance } from "./index.js";

const api = apiInstance();
const auth = {
  login: async function (user, success, fail) {
    await api
      .post(
        `/users/login`,
        JSON.stringify(user)
      )
      .then(success)
      .catch(fail);
  },
  signUp: async function (user, success, fail) {
    await api
    .post(`/users`,
    JSON.stringify(user)
    )
    .then(success)
    .catch(fail);
  }
};

export { auth };
