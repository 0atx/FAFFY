import Vue from "vue";
import VueRouter from "vue-router";
import MainView from "@/views/MainView.vue";
import GoogleAuthHandler from "@/views/handler/GoogleAuthHandler";
import NaverAuthHandler from "@/views/handler/NaverAuthHandler";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "main",
    component: MainView,
  },
  {
    path: "/auth",
    name: "auth",
    redirect: "/auth/sign-in",
    component: () => import("@/views/AuthView.vue"),
    children: [
      {
        path: "sign-in",
        name: "sign-in",
        component: () => import("@/components/auth/SignInView.vue"),
      },
      {
        path: "sign-up",
        name: "sign-up",
        component: () => import("@/components/auth/SignUpView.vue"),
      },
      {
        path: "google/redirect",
        name: "google",
        component: GoogleAuthHandler,
      },
      {
        path: "naver/redirect",
        name: "naver",
        component: NaverAuthHandler,
      }
    ],
  },
  {
    path: "/profile/:no",
    name: "profile",
    redirect: "/profile/:no/user-detail",
    component: () => import("@/views/ProfileView.vue"),
    children: [
      {
        path: "user-detail",
        name: "user-detail",
        component: () => import("@/components/user/ProfileDetailView.vue"),
      },
      {
        path: "history",
        name: "user-history",
        component: () => import("@/components/user/HistoryView.vue"),
      },
      {
        path: "history-detail/:consultNo",
        name: "user-history-detail",
        component: () => import("@/components/user/HistoryDetailView.vue"),
      },
      {
        path: "user-board",
        name: "user-board",
        component: () => import("@/components/user/BoardView.vue"),
      },
      {
        path: "follower",
        name: "follower",
        component: () => import("@/components/user/FollowerView.vue"),
      },
      {
        path: "following",
        name: "following",
        component: () => import("@/components/user/FollowingView.vue"),
      }
    ],
  },
  {
    path: "/edit-profile",
    name: "edit-profile",
    component: () => import("@/views/EditProfileView.vue"),
  },
  {
    path: "/consulting/ready",
    name: "consulting-ready",
    component: () => import("@/views/ConsultingReadyView.vue"),
  },
  {
    path: "/consulting/onair",
    name: "consulting-onair",
    component: () => import("@/views/ConsultingView.vue"),
  },
  {
    path: "/board",
    name: "board",
    component: () => import("@/views/BoardView.vue"),
  },
  {
    path: "/board/new",
    name: "board-new",
    component: () => import("@/views/CreateBoardView.vue"),
  },
  {
    path: "/board/:boardNo",
    name: "board-detail",
    component: () => import("@/views/BoardDetailView.vue"),
  },
  {
    path: "/board-update/:boardNo",
    name: "board-update",
    component: () => import("@/views/UpdateBoardView.vue"),
  },
  {
    path: "/search/:keyword",
    name: "search",
    component: () => import("@/views/SearchResultView.vue"),
  },

];

const router = new VueRouter({
  mode: "history",
  routes,
  scrollBehavior(){
    return { top: 0 }
  },
});

export default router;
