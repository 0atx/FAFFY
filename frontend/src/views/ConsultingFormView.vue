<template>
  <div id="view">
    <div style="display:flex;width:100%;">
      <div style="width: 80%;">
        <!-- 비디오 표시 영역-->
        <div id="topVideo" class="grey lighten-2 pt-8" v-if="session">
          <div id="mainVideo" class="mb-3">
            <user-video id="myWebcam" :stream-manager="publisher"/>
          </div>
          <div>
            <v-sheet
              id="subVideo"
              max-width="100%"
              class="grey lighten-2"
            >
              <v-slide-group
                v-model="model"
                show-arrows
              >
                <!-- <v-slide-item
                  v-for="n in 15"
                  :key="n"
                  style="margin: 13px;"
                  width="250"
                > -->
                <v-slide-item
                  v-for="sub in subscribers" :key="sub.stream.connection.connectionId"
                  style="margin: 13px;"
                  width="250"
                >
                <user-video :stream-manager="sub"/>
                </v-slide-item>
              </v-slide-group>
            </v-sheet>
          </div>
        </div>
        <!-- 비디오 표시 영역 END -->
        <!-- 중단 옵션 영역 -->
        <div id="centerOption" class="grey lighten-2">
            <div id="optionButton" class="grey lighten-1">
              <v-tooltip bottom v-if="audioValue">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="onButton"><v-icon size="30" color="#fff" @click="toggleAudio">mdi-microphone</v-icon></v-btn>
                </template>
                <span>음소거</span>
              </v-tooltip>
              <v-tooltip bottom v-else>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="offButton"><v-icon size="30" color="#fff" @click="toggleAudio">mdi-microphone-off</v-icon></v-btn>
                </template>
              <span>음소거 해제</span>
              </v-tooltip>

              <v-tooltip bottom v-if="camValue">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="onButton"><v-icon size="30" color="#fff" @click="toggleCam">mdi-video</v-icon></v-btn>
                </template>
                <span>비디오 중지</span>
              </v-tooltip>
              <v-tooltip bottom v-else>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="offButton"><v-icon size="30" color="#fff" @click="toggleCam">mdi-video-off</v-icon></v-btn>
                </template>
                <span>비디오 시작</span>
              </v-tooltip>


              <v-tooltip bottom>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="onButton"><v-icon size="30" color="#fff">mdi-camera</v-icon></v-btn>
                </template>
                <span>사진 촬영</span>
              </v-tooltip>
              <v-tooltip bottom>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="onButton"><v-icon size="30" color="#fff">mdi-image-multiple</v-icon></v-btn>
                </template>
                <span>앨범</span>
              </v-tooltip>

              <!-- <v-tooltip bottom>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="onButton"><v-icon size="30" color="#fff" @click="toggleMirror">mdi-reflect-horizontal</v-icon></v-btn>
                </template>
                <span>좌우 반전</span>
              </v-tooltip> -->

              <v-tooltip bottom v-if="mosaicValue">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="onButton" @click="toggleMosaic"><v-icon size="30" color="#fff">mdi-blur</v-icon></v-btn>
                </template>
                <span>모자이크 해제</span>
              </v-tooltip>
              <v-tooltip bottom v-else>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="offButton" @click="toggleMosaic"><v-icon size="30" color="#fff" >mdi-blur-off</v-icon></v-btn>
                </template>
                <span>모자이크</span>
              </v-tooltip>


              <v-tooltip bottom v-if="!screenOV">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="onButton" @click="startScreenShare"><v-icon size="30" color="#fff">mdi-monitor-share</v-icon></v-btn>
                </template>
                <span>화면 공유 시작</span>
              </v-tooltip>
              <v-tooltip bottom v-else>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="offButton" @click="stopScreenShare"><v-icon size="30" color="#fff">mdi-monitor-off</v-icon></v-btn>
                </template>
                <span>화면 공유 해제</span>
              </v-tooltip>

              <v-tooltip bottom>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="offButton"><v-icon size="30" color="#fff" @click="leaveConsulting">mdi-close</v-icon></v-btn>
                </template>
                <span>방송 나가기</span>
              </v-tooltip>

            </div>
          </div>
        <!-- 중단 옵션 영역 END -->

        <bottom-info v-if="consultingInfo" :consultingInfo="consultingInfo"/>
      </div>
      <div style="width:20%;" id="drawer"  v-if="session">
        <chat-subscriber-tab :session="session"/>
      </div>
    </div>
  </div>
</template>

<script>
// import CenterOption from '@/components/onair/CenterOption.vue'
import BottomInfo from '@/components/onair/BottomInfo.vue'
import ChatSubscriberTab from '@/components/onair/ChatSubscriberTab.vue'
import UserVideo from '@/components/video/UserVideo';
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import {consulting} from "@/api/consulting.js";

axios.defaults.headers.post['Content-Type'] = 'application/json';

import {OPENVIDU_URL,OPENVIIDU_SECRET} from "@/config";
import {mapState,mapMutations} from "vuex";

export default {
  name: 'ConsultingFormView',
  components: {
    // CenterOption,
    BottomInfo,
    ChatSubscriberTab,
    UserVideo,

  },
  data() {
    return {
      //
      OV: undefined,
			session: undefined,
			mainStreamManager: undefined,
			publisher: undefined,
			subscribers: [],
			camValue: true,
			audioValue: true,
      mosaicValue:false,
      isHost:false,
			mySessionId: "",
			myUserName: "",
      consulting_no:"",
			// 화면 공유할 때 사용할 session
			screenOV: undefined,
			screenSession: undefined,
			screenShareName: undefined,

			message: "",
			recvList: [],

      // 비디오 영역 데이터
      model: null,
      page: 1,
      itemsPerPage: 4,

      // 방송 퇴장 시 무한루프 방지용
      leaveTrigger:false,
    }
  },
  computed: {
    ...mapState("authStore",["loginUser"]),
    ...mapState("consultingStore",["participants","consultingInfo"]),

    // 페이지네이션 - 전체 페이지
    totalPages() {
      return this.now.length % this.itemsPerPage > 0 ? parseInt(this.now.length/this.itemsPerPage)+1 : parseInt(this.now.length/this.itemsPerPage)
    },
    // 페이지네이션 - 현재 페이지 게시물
    currentPage() {
      const start = (this.page-1)*this.itemsPerPage
      const end = start+this.itemsPerPage
      return this.now.slice(start, end)
    },
  },
  mounted() {
    // 뒤로가기 막기
    this.preventBack();

    // this.mySessionId = this.host_no;
    console.log("넘겨받은 값");
    this.nickname = this.$route.params.nickname;
    this.consulting_no = this.$route.params.consulting_no;
    console.log(this.nickname);
    console.log(this.consulting_no);

    // 비정상 접근 감지
    if (this.nickname == undefined) {
      alert("비정상적인 접근입니다.");
      this.$router.push('/');

      return;
    }

    this.mySessionId = ""+this.consulting_no;
    this.myUserName=this.loginUser.no+":"+this.loginUser.nickname;

    // 방송 정보 요청 후 값 세팅
    consulting.getConsulting(this.consulting_no)
    .then((data)=> {
      console.log("방송 정보 요청 성공");
      console.log(data);
      this.SET_CONSULTING_INFO(data.content);
      console.log(this.consultingInfo.title);
    })
    .catch((error)=> {
      console.log("방송 정보 요청 실패");
      console.log(error);
    })
    console.log("조인 전에")
    console.log(this.mySessionId);
    this.INIT_CONSULTING_INFO();
    this.INIT_PARTICIPANTS();
    this.SET_CHATS([]);
    this.joinSession();
      // 방장인지 체크
    if (this.nickname==this.loginUser.nickname) {
      this.isHost = true;
    }
  },
  beforeRouteLeave(to,from,next) {
    console.log("leave!!");
    this.leaveSession();
    next();
    location.reload();
  },
  methods: {
    ...mapMutations("consultingStore",["INIT_CONSULTING_INFO","SET_CONSULTING_INFO","SET_PARTICIPANTS","INIT_PARTICIPANTS","SET_CHATS"]),

    // 뒤로가기 방지
    preventBack: function() {
      history.pushState(null, null, location.href);
      window.onbeforeunload = null;

      window.onpopstate = function () {
          history.go(1);
      };
    },

		// 세션(화상회의) 시작
		joinSession() {
			// openVidu 객체 생성
			this.OV = new OpenVidu();
			// 세션 생성
			this.session = this.OV.initSession();

			// 세션 내에서 이벤트 발생시 할 작업들

			// 수신되는 모든 스트림들에 대해서
			this.session.on('streamCreated', ({ stream }) => {
				const subscriber = this.session.subscribe(stream);
				this.subscribers.push(subscriber);
        this.participants.push()
        console.log("subscrobers stream");
        this.SET_PARTICIPANTS(this.subscribers);
			});

			// 끝낸 모든 스트림들에 대해서
			this.session.on('streamDestroyed', ({ stream }) => {
				const index = this.subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
					this.subscribers.splice(index, 1);
				}
        this.SET_PARTICIPANTS(this.subscribers);

			});

			// 보든 비동기 예외에 대해서
			this.session.on('exception', ({ exception }) => {
				console.warn(exception);
			});
			// 채팅 수신
			this.session.on('signal', (event) => {
        let data = JSON.parse(event.data);
        let type = event.type;
				// event = JSON.parse(event.data);
				console.log(data); // Message
				// console.log(event.from); // Connection object of the sender
				console.log(type); // The type of message
        // 방송 종료 signal일 경우
        if (event.type == "signal:naga") {
          if (!this.isHost) {
            alert(data.message);
            console.log("okay bye");
            this.leaveSession();
          }
        // 채팅 signal일 경우
        } else if (event.type == "signal:my-chat") {
          const msg = {
            userName: data.userName,
            content: data.message,
            created_at:data.created_at,
          }
          this.recvList.push(msg)
          this.SET_CHATS(this.recvList);
        }
			});
			// 유효한 유저 토큰으로 세션에 연결

			// 'getToken' 은 서버측에서 수행해야할 작업을 시뮬레이션
			// 'token' 매개 변수는 사백엔드에서 검색하여 반환해야 함
			this.getToken(this.mySessionId).then(token => {

				this.session.connect(token, { clientData: this.myUserName })
					.then(() => {

						// --- Get your own camera stream with the desired properties ---

						let publisher = this.OV.initPublisher(undefined, {
							audioSource: undefined, // The source of audio. If undefined default microphone
							videoSource: undefined, // The source of video. If undefined default webcam
							publishAudio: this.camValue,  	// Whether you want to start publishing with your audio unmuted or not
							publishVideo: this.audioValue,  	// Whether you want to start publishing with your video enabled or not
							resolution: '320x240',  // The resolution of your video
							frameRate: 30,			// The frame rate of your video
							insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
							mirror: false       	// Whether to mirror your local video or not
						});
            console.log("-----------")
            console.log(publisher);
            console.log("-----------")

						this.mainStreamManager = publisher;
						this.publisher = publisher;

						// --- Publish your stream ---

						this.session.publish(this.publisher);

            console.log("방장체크"+this.nickname);
            console.log("내닉넴:"+this.loginUser.nickname);
            if (!this.isHost){
              const log = {
                consulting_no:this.mySessionId,
                user_no:this.loginUser.no,
              }
              console.log(log);
              consulting.createViewLog(log)
              .then((data)=> {
                console.log("로그 생성");
                console.log(data);
              })
              .catch((error)=> {
                console.log(error);
              })
            }
					})
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					});
			});
			window.addEventListener('beforeunload', this.leaveSession)
    },

    updateMainVideoStreamManager(stream) {
			if (this.mainStreamManager === stream) return;
			this.mainStreamManager = stream;
		},

    // Audio On / Off
		toggleAudio() {
			this.audioValue = !this.audioValue;
			this.publisher.publishAudio(this.audioValue);
		},
    // CAM On / Off
		toggleCam() {
			this.camValue = !this.camValue;
			this.publisher.publishVideo(this.camValue);
		},
    toggleMirror() {
      this.mirror = !this.mirror;
      this.publisher.mirror = this.mirror;
    },
    toggleMosaic() {
      this.mosaicValue = !this.mosaicValue;
    },

    // 화면 공유 시작
		startScreenShare() {
			this.screenOV = new OpenVidu();
			this.screenSession = this.screenOV.initSession();
			this.screenShareName = this.myUserName + "'s Screen",

				this.getToken(this.mySessionId).then(token => {
					console.log(token);
					this.screenSession.connect(token, { clientData: this.screenShareName })
						.then(() => {
							let publisher = this.screenOV.initPublisher("html-element-id", { videoSource: "screen", publishAudio: false });

							try {
								publisher.once('accessAllowed', () => {
									let test = publisher.stream.getMediaStream().getVideoTracks();
									console.log(test);
									publisher.stream.getMediaStream().getVideoTracks()[0].addEventListener('ended', () => {
										console.log('User pressed the "Stop sharing" button');
										this.stopScreenShare();
									});
									this.screenSession.publish(publisher);
								});

								publisher.once('accessDenied', (event) => {
									console.error(event, 'ScreenShare: Access Denied');
									this.stopScreenShare();
								});
							} catch (error) {
								console.log(error);
							}

						})
				}).catch(error => {
					console.error(error);
					this.screenOV = undefined;
					this.screenSession = undefined;
				})

		},
		// 화면 공유 종료
		stopScreenShare() {
			this.screenSession.disconnect();
			this.screenOV = undefined;
			this.screenSession = undefined;
		},
    /**
		 * --------------------------
		 * SERVER-SIDE RESPONSIBILITY
		 * --------------------------
		 * These methods retrieve the mandatory user token from OpenVidu Server.
		 * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
		 * the API REST, openvidu-java-client or openvidu-node-client):
		 *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
		 *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
		 *   3) The Connection.token must be consumed in Session.connect() method
		 */

		getToken(mySessionId) {
			return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
		createSession(sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
					}), {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIIDU_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_URL}"`)) {
								location.assign(`${OPENVIDU_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
		createToken(sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIIDU_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => reject(error.response));
			});
		},
    async NAGA() {
      console.log("NAGA!");
      this.session.signal({
				data: JSON.stringify({
					message:"방송이 종료되었습니다."
				}),
				to: [],                     // Array of Connection objects (optional. Broadcast to everyone if empty)
				type: 'naga'             // The type of message (optional)
			})
				.then(() => {
					console.log('Message successfully sent');
					this.message = "";
				})
				.catch(error => {
					console.error(error);
				});
    },
    // 세션 종료
		async leaveSession() {
      if (this.leaveTrigger) return;
      // 내가 방장일 경우 방송 종료
      if (this.isHost) {
        console.log("방장인데 불좀 꺼줄래");
        await consulting.deleteConsulting({consulting_no:this.consulting_no,user_no:this.loginUser.no},()=> {
          console.log("good");
        },()=> {
          console.log("TT");
        })
        this.NAGA();
      }
      // --- Leave the session by calling 'disconnect' method over the Session object ---
			if (this.session) this.session.disconnect();

			this.session = undefined;
			this.mainStreamManager = undefined;
			this.publisher = undefined;
			this.subscribers = [];
      this.OV = undefined;

			window.removeEventListener('beforeunload', this.leaveSession);
      this.leaveTrigger=true;
      this.$router.push({name:"main"});
		},
    leaveConsulting() {
      if (confirm("정말로 퇴장하시겠습니까?")) {
        this.leaveSession();
      }
    }
  }
}
</script>

<style scoped>
#view {
  background-color: white;
  text-align: left;
}
#topVideo {
  width:100%;
  background-color: paleturquoise;
  text-align: center;
}
#centerOption {
  width:100%;
  padding: 10px;
  padding-bottom: 20px;
}

#optionButton {
  width:60%;
  height: 70px;
  margin: 0 20%;
  text-align: center;
  padding-top: 12px;
  border-radius: 40px;
}

.onButton {
  width:45px;
  height:45px;
  background-color:#0c0f66;
  margin: 0 10px;
}

.offButton {
  width:45px;
  height:45px;
  background-color:#ff7451;
  margin: 0 10px;
}
</style>
