<template>
	<div id="rightChat" style="height: 100vh;background:white;">
    <!--
    <v-card class="overflow-auto" style="height: 65vh; width: 100%"> -->
      <!-- :key 는 나중에 생성시간이나 id 등 고유값을 가지는 것으로 변경-->
      <!--
      <v-card class="mx-auto" outlined color="transparent" id="chat" v-for="chat in chats" :key="chat.no">
        <p class="mb-1 mt-1 chatMsg">{{ chat.nickname }}: {{ chat.msg }}</p>
      </v-card>
    </v-card>
    <div style="height: 5vh">
      <v-row>
        <v-col class="p0" cols="9">
          <v-text-field
            label="채팅 입력하기"
            outlined
            hide-details
            box-sizing="border-box"
            @keyup.enter="submitChat(message)"
            v-model="message">
          </v-text-field>
        </v-col>
        <v-col cols="3">
          <v-btn height="100%" width="100%" color="primary" @click="submitChat(message)">전송</v-btn>
        </v-col>
      </v-row>
    </div>
    -->

    <v-list>
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title class="text-h6 pt-1">
            <v-btn id="icon" icon small elevation="0" :ripple="false" class="mb-1 pr-2" @click="$emit('hideDrawer')">
              <v-icon size="22">mdi-arrow-right</v-icon>
            </v-btn>
            채팅
            <v-btn
              id="tapBtn"
              class="mb-1" style="margin-left:58%; cursor: pointer"
              elevation="0"
              icon
              small
              color="gray"
              :ripple="false"
              @click="$emit('change')"
            >
              <v-icon> mdi-account-multiple </v-icon>
            </v-btn>
          </v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
    <v-divider></v-divider>
    <div id="chatArea" class="overflow-auto" style="height: 80vh; width: 100%">
      <v-card class="mb-2"  elevation="0" style="width: 100%">
        <v-card :style="chat.userName == loginUser.nickname ? 'width: 100%; text-align:right; padding-right:4px' : 'width:100%'" class="mx-auto" outlined color="transparent" id="chat" v-for="chat in chats" :key="chat.no">
          <div
            :class="chat.nickname == loginUser.nickname ? 'ml-2 mr-2' : 'ml-1 mr-2'"
            style="font-size: 0.8rem;"
          >{{ chat.userName }}</div>
          <v-chip
            :color="chat.userName == loginUser.nickname ? '#ff7451' : '#0c0f66'"
            dark
            style="height:40px;white-space: normal;pointer-events: none;"
            class="pa-3"
          >
            {{ chat.content }}
          </v-chip>
          <div
            class="ml-1 mr-1 mb-1"
            style="font-size: 0.5rem;vertical-align: start;"
          >{{ chat.created_at }}</div>
        </v-card>
      </v-card>
    </div>
    <div id="chatForm" style="height: 10vh;width: 20%; padding:0px 5px; padding-top:5px;" class="p0" cols="12">
      <v-text-field
        placeholder="채팅 입력하기"
        color="#0c0f66"
        filled
        rounded
        no-details
        hide-details
        box-sizing="border-box"
        append-icon="mdi-send"
        @keyup.enter="send"
        @click:append="send"
        v-model="message">
      </v-text-field>
    </div>

  </div>
</template>

<script>
import { mapState} from "vuex";
const authStore = "authStore";
export default {
	name: 'RightChat',
  props:{
    session:Object,
  },
  components: {

  },
  mounted() {
    console.log("탭이에요");
    console.log(this.session);
    console.log("탭이에요end");
    document.getElementById("chatArea").scrollTop = document.getElementById("chatArea").scrollHeight;
  },
  data() {
    return {
      // chats: [
      //   { no: 1, nickname: '이준성', msg: '갈매기'},
      //   { no: 2, nickname: '류경하', msg: '기러기'},
      //   { no: 3, nickname: '박윤하', msg: '기차표'},
      //   { no: 4, nickname: '이용우', msg: '표인봉'},
      //   { no: 5, nickname: '김수만', msg: '봉우리'},
      //   { no: 6, nickname: '김명석', msg: '리어카타는거보다 기차타는게 낫고 기차타는것 보다는 비행기 타는게 낫지않음?'},
      //   { no: 7, nickname: '김명석', msg: '리어카타는거보다 기차타는게 낫고 기차타는것 보다는 비행기 타는게 낫지않음?'},
      // ],
      message: '',
    }
  },
  computed: {
    ...mapState(authStore, ["loginUser"]),
    ...mapState("consultingStore",["chats"]),
  },
  methods: {
    // 채팅 입력(추후변경) + 스크롤 고정 필요
    submitChat(msg) {
      this.chats.push({ no: 9, nickname: '익명의 누군가', msg: msg, created_at: new Date().toLocaleTimeString() })
      this.message = ''
    },
    send() {
      const now = new Date();
      let time = now.getHours()+":"+now.getMinutes()+":"+now.getSeconds();
			this.session.signal({
				data: JSON.stringify({
					message: this.message,  // Any string (optional)
					userName: this.loginUser.nickname,
          created_at:time,
				}),
				to: [],                     // Array of Connection objects (optional. Broadcast to everyone if empty)
				type: 'my-chat'             // The type of message (optional)
			})
				.then(() => {
					console.log('Message successfully sent');
					this.message = "";
          document.getElementById("chatArea").scrollTop = document.getElementById("chatArea").scrollHeight;
				})
				.catch(error => {
					console.error(error);
				});
		},

  }
};
</script>

<style scoped>
#rightChat {
  width:100%;
  background-color: white;
  border: 1px solid rgb(214, 214, 214);
  /* background-color: palevioletred; */
}

#chat {
  margin-top: 1px;
  margin-bottom: 1px;
}

.chatMsg {
  white-space: pre-wrap;
}

div {
  box-sizing: border-box;
}

#chatForm{
  position:fixed;
}

#tapBtn::before {
  background-color: transparent;
}

#icon::before {
  background-color: transparent;
}

::v-deep ::-webkit-scrollbar {
  width: 10px;
  background: #f0f0f0;
  border-radius: 10px;
}

::v-deep ::-webkit-scrollbar-thumb {
  background: #0c0f66;
  border-radius: 10px;
}
</style>
