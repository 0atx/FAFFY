import Vue from "vue";
import VueRouter from "vue-router";
import MainView from "@/views/MainView.vue";

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
    ],
  },
  {
    path: "/profile/:id",
    name: "profile",
    redirect: "user-detail",
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
        path: "history-detail",
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
    path: "/consulting",
    name: "consulting",
    redirect: "/consulting/ready",
    component: () => import("@/views/ConsultingView.vue"),
    children: [
      {
        path: "ready",
        name: "consulting-ready",
        component: () => import("@/views/ConsultingFormView.vue"),
      },
      {
        path: "onair",
        name: "consulting-onair",
        component: () => import("@/views/ConsultingView.vue"),
      },
    ],
  },
  {
    path: "/board",
    name: "board",
    component: () => import("@/views/BoardView.vue"),
  },
];

const router = new VueRouter({
  mode: "history",
  routes,
});

export default router;
