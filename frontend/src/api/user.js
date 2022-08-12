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
  getProfileImg:async function (user_no,success,fail) {
    await api.get(`/users/profile/image/${user_no}`).then(success).catch(fail);
  }
};
const follow = {
  getFollowerList: async function (user_no, success, fail) {
    await api.get(`/follow/follower/${user_no}`).then(success).catch(fail);
  },
  getFollowingList: async function (user_no, success, fail) {
    await api.get(`/follow/following/${user_no}`).then(success).catch(fail);
  },
  follow:async function(followMapping,success,fail) {
    await api
    .post(`/follow`,JSON.stringify(followMapping),null)
    .then(success)
    .catch(fail);
  },
  followCancel:async function(followMapping,success,fail) {
    console.log(followMapping)
    await api
    .delete(`/follow`,{data:followMapping})
    .then(success)
    .catch(fail);
  }
};
const consulting = {
  getParticipatedList: async function (user_no, success, fail) {
    await api.get(`/users/profile/${user_no}/history/parti`)
    .then(success)
    .catch(fail);
  },
  getConsultingList: async function (user_no, success, fail) {
    await api.get(`/users/profile/${user_no}/history/consult`)
    .then(success)
    .catch(fail);
  },
  getHistoryDetail: async function (payload, success, fail) {
    await api.get(`/users/profile/${payload.user_no}/history/${payload.consulting_no}`)
    .then(success)
    .catch(fail);
  },
};
const userBoard = {
  getUserBoardList: async function (user_no, success, fail) {
    await api.get(`/users/profile/${user_no}/board`)
    .then(success)
    .catch(fail);
  }
}
export { user, follow, consulting, userBoard };
