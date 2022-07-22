import { apiInstance } from "./index.js";

const api = apiInstance();
const member = {
  login: async function (user, success, fail) {
    await api
      .post(
        `/users`,
        null,
        {params:JSON.stringify(user)}
      )
      .then(success)
      .catch(fail);
  },
};

export { member };
