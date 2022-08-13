const consultingStore = {
  namespaced:true,
  state:{
    participants:[],
    consultingInfo:null,
    chats:[],
  },
  mutations:
  {
    INIT_CONSULTING_INFO:(state) => {
      state.consultingInfo = null;
    },
    SET_CONSULTING_INFO:(state,consultingInfo) => {
      state.consultingInfo = consultingInfo;
    },
    SET_PARTICIPANTS:(state,subscribers)=> {
      // state.participants = subscribers.filter(sub => JSON.parse(sub.stream.connection.data));
      // let arr = [];
      state.participants=[];
      // subscribers.forEach(element => {
      //   state.participants.push(JSON.parse(element.stream.connection.data));
      // });

      subscribers.forEach(element => {
        let namecode = JSON.parse(element.stream.connection.data).clientData.split(':');
        let user = {
          no:Number(namecode[0]),
          nickname:namecode[1],
        }
        state.participants.push(user);
      });
    },
    INIT_PARTICIPANTS:(state) => {
      state.participants = [];
    },
    SET_CHATS:(state,chatArray)=> {
      state.chats = chatArray;
    }
  }
}

export default consultingStore;
