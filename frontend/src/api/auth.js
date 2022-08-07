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
  logout: async function(success,fail) {
    api.defaults.headers["X-AUTH-TOKEN"] =
      sessionStorage.getItem("X-AUTH-TOKEN");
    await api
    .post(
      `/users/logout`,
      null
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
  },
  updateProfile: async function(formData,success,fail) {
    await api
    .put(`/users`,
    formData,
    { headers: { 'Content-Type': 'multipart/form-data' }}
    )
    .then(success)
    .catch(fail);
  },
  deleteProfileImg: async function(user_no,success,fail) {
    await api
    .delete(`/users/profile/image/${user_no}`)
    .then(success)
    .catch(fail);
  }
};

export { auth };
