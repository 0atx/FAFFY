import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);


const routes = [
  {
    path:"/auth",
    name:"auth",
    redirect:"/auth/sign-in",
    component:()=>import("@/views/AuthView.vue"),
    children: [
      {
        path:"sign-in",
        name:"sign-in",
        component:()=>import("@/components/auth/SignInView.vue")
      },
      {
        path:"sign-up",
        name:"sign-up",
        component:()=>import("@/components/auth/SignUpView.vue")
      }
    ]
  }
]

const router = new VueRouter({
  mode: "history",
  base: "localhost/",
  routes,
});

export default router;
