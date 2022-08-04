import { apiInstance } from "@/api";

const api = apiInstance();
const category = {
  getCategories: async function (success, fail) {
    await api.get(`/category`).then(success).catch(fail);
  },
};

export { category };
