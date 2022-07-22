import axios from "axios";
import { API_BASE_URL } from "@/config";
// 1학기때 썼던 JWT 관련 코드
// axios.interceptors.request.use(
//   async function (config) {
//     const accessToken = sessionStorage.getItem("access-token");
//     const splitedUrl = config.url.split("/");
//     const endpoint = splitedUrl[splitedUrl.length - 1];
//     if (endpoint !== "refresh" && accessToken) {
//       const refreshToken = sessionStorage.getItem("refresh-token");
//       const response = await refreshTokenRequest(accessToken, refreshToken);

//       const new_accessToken = response.data["access-token"];
//       const new_refreshToken = response.data["refresh-token"];

//       sessionStorage.setItem("access-token", new_accessToken);
//       sessionStorage.setItem("refresh-token", new_refreshToken);

//       config.headers["access-token"] = new_accessToken;
//       config.headers["refresh-token"] = new_refreshToken;

//       return config;
//     }
//     return config;
//   },
//   function (error) {
//     return Promise.reject(error);
//   }
// );

// axios.interceptors.response.use(
//   function (response) {
//     // 응답 데이터를 가공
//     // ...
//     return response;
//   },
//   function (error) {
//     // 오류 응답을 처리
//     // ...
//     if (error.response.status === 401) {
//       sessionStorage.removeItem("access-token");
//       sessionStorage.removeItem("refresh-token");
//       alert("로그인 시간이 만료되었습니다. 다시 로그인 해주세요");
//       window.location.href = "/user/login";
//     }
//   }
// );

// const refreshTokenRequest = async (accessToken, refreshToken) => {
//   const config = {
//     headers: {
//       "access-token": accessToken,
//       "refresh-token": refreshToken,
//     },
//   };
//   const response = await axios.post(
//     `${process.env.VUE_APP_BACKEND_URL}/member/refresh`,
//     null,
//     config
//   );
//   return response;
// };

// axios 객체 생성
function apiInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      "Content-type": "application/json",
    },
  });
  return instance;
}

export { apiInstance };
