<!--
작성자 : 박윤하
프로필 뷰 - 팔로워 목록
-->
<template>
  <div id="follower">
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
          팔로워 목록
        </p>
        <hr />
        <div id="followerList">
          <v-list style="padding: 0">
            <v-list-item-group>
              <template v-for="(user, index) in users">

                <v-list-item
                  :key="user.info"
                >
                  <template v-slot:default>
                    <v-list-item-avatar>
                      <v-img :src="user.img"></v-img>
                    </v-list-item-avatar>

                    <v-list-item-content id="itemContent">
                      <div id="item">
                      <v-list-item-title class="mb-1" v-html="user.nickname"></v-list-item-title>
                      <v-list-item-subtitle class="mt-1" v-html="user.info"></v-list-item-subtitle>
                      </div>

                      <!-- 유저가 해당 유저를 팔로우 하고 있지 않다면 현재 버튼
                      아니라면 팔로우 취소 버튼을 만들어서 나타나게 해줘야 함 -->
                      <div id="followBtn">
                        <dark-button class="mb-2" :btnValue="followValue" />
                      </div>
                    </v-list-item-content>
                  </template>
                </v-list-item>

                <v-divider
                  v-if="index < users.length - 1"
                  :key="index"
                  inset
                ></v-divider>
              </template>
            </v-list-item-group>
          </v-list>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import DarkButton from "@/components/common/DarkButton.vue";

export default {
  name: "FollowerView",
  components: {
    DarkButton,
  },
  data() {
    return {
      // user를 팔로우하고 있는 유저 리스트 가져와서 넣어야 함
      users: [
        {
          img: 'http://localhost:8080/img/default_profile.777a4c74.png',
          nickname: '별명짓기귀찮다',
          info: '50자로 확정했습니다 내 맘대로ㅎㅎ',
        },
        {
          img: 'http://localhost:8080/img/default_profile.777a4c74.png',
          nickname: '별명짓기귀찮다2',
          info: '한 줄 소개 들어갈 부분입니다. 근데 한 줄 소개는 글자수 제한을 몇자로 하지 100자로?',
        },
        {
          img: 'http://localhost:8080/img/default_profile.777a4c74.png',
          nickname: '별명짓기귀찮다3',
          info: '넓이 고정이 안돼;;;',
        },
        {
          img: 'http://localhost:8080/img/default_profile.777a4c74.png',
          nickname: '별명짓기귀찮다4',
          info: '는줄 알았는데 되네ㅎ',
        },
        {
          img: 'http://localhost:8080/img/default_profile.777a4c74.png',
          nickname: '별명짓기귀찮다',
          info: '50자로 확정했습니다 내 맘대로ㅎㅎ',
        },
        {
          img: 'http://localhost:8080/img/default_profile.777a4c74.png',
          nickname: '별명짓기귀찮다2',
          info: '한 줄 소개 들어갈 부분입니다. 근데 한 줄 소개는 글자수 제한을 몇자로 하지 100자로?',
        },
        {
          img: 'http://localhost:8080/img/default_profile.777a4c74.png',
          nickname: '별명짓기귀찮다3',
          info: '넓이 고정이 안돼;;;',
        },
        {
          img: 'http://localhost:8080/img/default_profile.777a4c74.png',
          nickname: '별명짓기귀찮다4',
          info: '는줄 알았는데 되네ㅎ',
        },
        {
          img: 'http://localhost:8080/img/default_profile.777a4c74.png',
          nickname: '별명짓기귀찮다',
          info: '50자로 확정했습니다 내 맘대로ㅎㅎ',
        },
        {
          img: 'http://localhost:8080/img/default_profile.777a4c74.png',
          nickname: '별명짓기귀찮다2',
          info: '한 줄 소개 들어갈 부분입니다. 근데 한 줄 소개는 글자수 제한을 몇자로 하지 100자로?',
        },
        {
          img: 'http://localhost:8080/img/default_profile.777a4c74.png',
          nickname: '별명짓기귀찮다3',
          info: '넓이 고정이 안돼;;;',
        },
        {
          img: 'http://localhost:8080/img/default_profile.777a4c74.png',
          nickname: '별명짓기귀찮다4',
          info: '는줄 알았는데 되네ㅎ',
        },
        {
          img: 'http://localhost:8080/img/default_profile.777a4c74.png',
          nickname: '별명짓기귀찮다',
          info: '50자로 확정했습니다 내 맘대로ㅎㅎ',
        },
        {
          img: 'http://localhost:8080/img/default_profile.777a4c74.png',
          nickname: '별명짓기귀찮다2',
          info: '한 줄 소개 들어갈 부분입니다. 근데 한 줄 소개는 글자수 제한을 몇자로 하지 100자로?',
        },
        {
          img: 'http://localhost:8080/img/default_profile.777a4c74.png',
          nickname: '별명짓기귀찮다3',
          info: '넓이 고정이 안돼;;;',
        },
        {
          img: 'http://localhost:8080/img/default_profile.777a4c74.png',
          nickname: '별명짓기귀찮다4',
          info: '는줄 알았는데 되네ㅎ',
        },
        {
          img: 'http://localhost:8080/img/default_profile.777a4c74.png',
          nickname: '별명짓기귀찮다',
          info: '50자로 확정했습니다 내 맘대로ㅎㅎ',
        },
        {
          img: 'http://localhost:8080/img/default_profile.777a4c74.png',
          nickname: '별명짓기귀찮다2',
          info: '한 줄 소개 들어갈 부분입니다. 근데 한 줄 소개는 글자수 제한을 몇자로 하지 100자로?',
        },
        {
          img: 'http://localhost:8080/img/default_profile.777a4c74.png',
          nickname: '별명짓기귀찮다3',
          info: '넓이 고정이 안돼;;;',
        },
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
#follower {
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

#follower div {
  width: 100%;
}

#content {
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
