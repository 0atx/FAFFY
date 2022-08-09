<template>
	<div id="rightChat" style="height: 70vh">
    <v-card class="overflow-auto" style="height: 65vh; width: 100%">
      <!-- :key 는 나중에 생성시간이나 id 등 고유값을 가지는 것으로 변경-->
      <v-card class="mx-auto" oulined color="transparent" id="chat" v-for="chat in chats" :key="chat.no">
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
  </div>
</template>

<script>
export default {
	name: 'RightChat',
  components: {

  },
  data() {
    return {
      chats: [
        // { no: 1, nickname: '이준성', msg: '갈매기'},
        // { no: 2, nickname: '류경하', msg: '기러기'},
        // { no: 3, nickname: '박윤하', msg: '기차표'},
        // { no: 4, nickname: '이용우', msg: '표인봉'},
        // { no: 5, nickname: '김수만', msg: '봉우리'},
        // { no: 6, nickname: '김명석', msg: '리어카타는거보다 기차타는게 낫고 기차타는것 보다는 비행기 타는게 낫지않음?'},
        // { no: 7, nickname: '김명석', msg: '리어카타는거보다 기차타는게 낫고 기차타는것 보다는 비행기 타는게 낫지않음?'},
      ],
      message: '',
    }
  },
  methods: {
    // 채팅 입력(추후변경) + 스크롤 고정 필요
    submitChat(msg) {
      this.chats.push({ no: 9, nickname: '익명의 유저', msg: msg })
      this.message = ''
    }
  }
};
</script>

<style scoped>
#rightChat {
  width:100%;
  height:100%;
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
</style>
