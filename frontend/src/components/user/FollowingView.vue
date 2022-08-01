<template>
  <div id="following">
    <div>
      <div id="route">
        <p class="text-h6" style="font-weight: 600">
          <router-link to="/profile/1/user-detail"> Profile </router-link>
        </p>
        <v-icon color="black" class="mb-3"> mdi-chevron-right </v-icon>
        <p class="text-h6" style="font-weight: 600">Following</p>
      </div>
      <div id="content" class="mt-4">
        <p class="text-h6" style="font-weight: 600">
          팔로잉 목록
        </p>
        <hr />
        <div id="followingList">
          <v-list>
            <template v-for="(user, index) in users">
              <v-divider
                v-if="user.divider"
                :key="index"
                :inset="user.inset"
              ></v-divider>

              <v-list-item
                v-else
                :key="user.nickname"
              >
                <v-list-item-avatar>
                  <v-img :src="user.img"></v-img>
                </v-list-item-avatar>

                <v-list-item-content id="itemContent">
                  <div id="item">
                  <v-list-item-title class="mb-1" v-html="user.nickname"></v-list-item-title>
                  <v-list-item-subtitle class="mt-1" v-html="user.info"></v-list-item-subtitle>
                  </div>
                  <div id="followBtn">
                    <v-btn
                      elevation="0"
                      class="pt-1"
                      icon
                      small
                      color="#ff7451"
                    >
                      <v-icon> mdi-heart </v-icon>
                    </v-btn>
                    <!--<dark-button class="mb-2" :btnValue="followValue" />-->
                  </div>
                </v-list-item-content>
              </v-list-item>
            </template>
          </v-list>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
//import DarkButton from "@/components/common/DarkButton.vue";

export default {
  name: "ProfileCard",
  components: {
    //DarkButton,
  },
  data() {
    return {
      users: [
        {
          img: 'http://localhost:8080/img/default_profile.777a4c74.png',
          nickname: '별명짓기귀찮다',
          info: '50자로 확정했습니다 내 맘대로ㅎㅎ',
        },
        { divider: true, inset: true },
        {
          img: 'http://localhost:8080/img/default_profile.777a4c74.png',
          nickname: '별명짓기귀찮다2',
          info: '한 줄 소개 들어갈 부분입니다. 근데 한 줄 소개는 글자수 제한을 몇자로 하지 100자로?',
        },
        { divider: true, inset: true },
        {
          img: 'http://localhost:8080/img/default_profile.777a4c74.png',
          nickname: '별명짓기귀찮다3',
          info: '넓이 고정이 안돼;;;',
        },
        { divider: true, inset: true },
        {
          img: 'http://localhost:8080/img/default_profile.777a4c74.png',
          nickname: '별명짓기귀찮다4',
          info: '는줄 알았는데 되네ㅎ',
        },
      ],

      followValue: "팔로우"
    };
  }
}
</script>

<style scoped>
#following {
  background-color: #fff;
  padding: 5%;
  text-align: left;
  height: 100%;
  display: flex;
  justify-content: start;
  align-items: flex-start;
  cursor: default;
}

#route {
  display: flex;
  justify-content: start;
}

a {
  color: black;
  text-decoration: none;
}

#following div {
  width: 100%;
}

#content {
  height: 300px;
  background-color: #fff;
  padding: 2%;
}

#itemContent {
  display: table;
}

#item {
  display: table-cell;
  vertical-align:middle;
  padding-right: 10px;
  width: 500px !important;
}

#followBtn {
  display: table-cell;
  vertical-align:middle;
  width: 60px !important;
}

button:before {
  background-color: transparent;
}
</style>
