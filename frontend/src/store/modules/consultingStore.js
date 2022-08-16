const consultingStore = {
  namespaced:true,
  state:{
    participants:[],
    consultingInfo:null,
    chats:[],
    isShare:false,
    snapshotList:[],
    myMosaic:false,
    remoteValue:false,
  },
  mutations:
  {
    INIT_CONSULTING_INFO:(state) => {
      state.consultingInfo = null;
      state.myMosaic = false;
      state.remoteValue = false;
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
        let data = JSON.parse(element.stream.connection.data);
        let namecode = data.clientData.split(':');
        let user = {
          no:Number(namecode[0]),
          nickname:namecode[1],
          mosaicValue:data.mosaicValue,
        }
        state.participants.push(user);
      });
    },
    INIT_PARTICIPANTS:(state) => {
      state.participants = [];
    },
    SET_CHATS:(state,chatArray)=> {
      state.chats = chatArray;
    },
    SET_SHARESCREEN:(state, status) => {
      state.isShare = status;
    },
    SET_SNAPSHOT_LIST:(state,list) => {
      state.snapshotList = list;
    },
    SET_MOSAIC:(state,mosaic) => {
      state.myMosaic = mosaic;
    },
    SET_REMOTE:(state,remote) => {
      state.remoteValue = remote;
    }
  }
}

export default consultingStore;
