import { apiInstance } from "@/api";

const api = apiInstance();
const user = {
  getUserProfile: async function (user_no, success, fail) {
    await api.get(`/users/${user_no}`).then(success).catch(fail);
  },
  checkEmail: async function (email, success, fail) {
    await api.get(`/users/validation/email/${email}`).then(success).catch(fail);
  },
  checkNickname: async function (nickname, success, fail) {
    await api
      .get(`/users/validation/nickname/${nickname}`)
      .then(success)
      .catch(fail);
  },
};
const follow = {
  getFollowerList: async function (user_no, success, fail) {
    await api.get(`/follow/follower/${user_no}`).then(success).catch(fail);
  },
  getFollowingList: async function (user_no, success, fail) {
    await api.get(`/follow/following/${user_no}`).then(success).catch(fail);
  },
};
export { user, follow };
