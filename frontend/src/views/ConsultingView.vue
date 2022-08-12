<template>
	<div id="main-container" class="container">
		<!-- 방송 들어가기 전-->
		<div id="join" v-if="!session">
			<div id="join-dialog" class="jumbotron vertical-center">
				<h1>방송 들어가기 </h1>
				<div class="form-group">
					<p>
						<label>참가자명 : </label>
						<input v-model="myUserName" class="form-control" type="text" required>
					</p>
					<p>
						<label>화상회의명 : </label>
						<input v-model="mySessionId" class="form-control" type="text" required>
					</p>
					<p class="text-center">
						<button class="btn btn-lg btn-success" @click="joinSession()">방송 입장</button>
					</p>
				</div>
			</div>
		</div>
		<!-- 방송 들어간 후-->
		<div id="session" v-if="session">
			<div id="session-header">
				<h1 id="session-title">방송제목:{{ consultingInfo.title }}</h1>
				<input class="btn btn-large btn-danger" type="button" id="buttonLeaveSession" @click="leaveSession"
					value="Leave session">
			</div>

			<div id="video-container" class="col-md-6">
				<user-video :stream-manager="publisher" @click.native="updateMainVideoStreamManager(publisher)" />
				<div id="main-video" class="col-md-6">
					<button @click="changeCamValue" type="button">
						<p v-if="camValue">Cam Off</p>
						<p v-if="!camValue">Cam On</p>
					</button>
					/
					<button @click="changeAudioValue" type="button">
						<p v-if="audioValue">Audio Off</p>
						<p v-if="!audioValue">Audio On</p>
					</button>
				</div>
				<div id="session-header-screenShare">
					<input v-if="!screenOV" class="btn btn-large btn-danger" type="button" id="buttonLeaveSession"
						@click="startScreenShare" value="화면 공유 시작">
					<input v-if="screenOV" class="btn btn-large btn-danger" type="button" id="buttonLeaveSession"
						@click="stopScreenShare" value="화면 공유 종료">
				</div>
				<user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub"
					@click.native="updateMainVideoStreamManager(sub)" />
				<div id="chat">
					메세지 : <input v-model="message" type="text">
					<input type="button" @click="send" value="보내기">
					<div v-for="(item, idx) in recvList" :key="idx">
						<p>{{ item.userName }} : {{ item.content }}</p>
					</div>
				</div>
			</div>

		</div>
	</div>
</template>

<script>
import axios from 'axios';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '../components/video/UserVideo';
import {mapState} from "vuex";
import {consulting} from "@/api/consulting.js";
// import Chat from '@/components/chat/Chat';

axios.defaults.headers.post['Content-Type'] = 'application/json';

// 서버 URL _ AWS 주소
const OPENVIDU_SERVER_URL = "https://" + "13.124.166.155" + ":8443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
	name: 'ConsultingView',
	components: {
		UserVideo,
		// Chat
	},
  computed: {
    ...mapState("authStore",["loginUser"]),
  },
  mounted() {
    // 뒤로가기 막기
    this.preventBack();
    // this.mySessionId = this.host_no;
    console.log("넘겨받은 값");
    console.log(this.$route.params.nickname);
    this.nickname = this.$route.params.nickname;
    this.consulting_no = this.$route.params.consulting_no;
    console.log(this.consulting_no);
    if (this.nickname == undefined) {
      if ( !confirm("이상하게 들어왔는데 입장합니까"))
        this.$router.push('/');
      else {
        this.mySessionId = ""+this.consulting_no;
        this.nickname = "temp";
        this.consulting_no = this.mySessionId;
      }
      return;
    }
    this.mySessionId= ""+this.consulting_no;
    this.myUserName=this.loginUser.nickname;
    // 방송 정보 요청 후 값 세팅
    consulting.getConsulting(this.consulting_no)
    .then((data)=> {
      console.log("방송 정보 요청 성공");
      console.log(data);
      this.consultingInfo = data.content;
      console.log(this.consultingInfo.title);
    })
    .catch((error)=> {
      console.log("방송 정보 요청 실패");
      console.log(error);
    })
    this.joinSession();
      // 방장인지 체크
    if (this.nickname==this.loginUser.nickname) {
      this.isHost = true;
      alert("방장 어서오고");
    }
  },
  beforeRouteLeave(to,from,next) {
    console.log("leave!!");
    this.leaveSession();
    next();
  },
	data() {
		return {
			OV: undefined,
			session: undefined,
			mainStreamManager: undefined,
			publisher: undefined,
			subscribers: [],
			camValue: true,
			audioValue: true,
      isHost:false,
			mySessionId: 'session' + Math.floor(Math.random() * 100),
			myUserName: 'participant' + Math.floor(Math.random() * 100),
      consulting_no:"",
			// 화면 공유할 때 사용할 session
			screenOV: undefined,
			screenSession: undefined,
			screenShareName: undefined,

			message: "",
			recvList: [],
      // 방송 정보
      consultingInfo:null,
		}
	},
	methods: {
    // 뒤로가기 막는 함수
    preventBack: function() {
      history.pushState(null, null, location.href);
      window.onbeforeunload = null;

      window.onpopstate = function () {
          history.go(1);
      };
    },
		send() {
			this.session.signal({
				data: JSON.stringify({
					message: this.message,  // Any string (optional)
					userName: this.myUserName,
				}),
				to: [],                     // Array of Connection objects (optional. Broadcast to everyone if empty)
				type: 'my-chat'             // The type of message (optional)
			})
				.then(() => {
					console.log('Message successfully sent');
					this.message = "";
				})
				.catch(error => {
					console.error(error);
				});
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
        console.log("subscrobers");
        console.log(this.subscribers)
			});

			// 끝낸 모든 스트림들에 대해서
			this.session.on('streamDestroyed', ({ stream }) => {
				const index = this.subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
					this.subscribers.splice(index, 1);
				}
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
          }
          this.recvList.push(msg)
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
		// CAM On / Off
		changeCamValue() {
			this.camValue = !this.camValue;
			if (this.camValue)
				console.log("CAM Off -> On");
			else console.log("CAM On -> Off");
			this.publisher.publishVideo(this.camValue);
		},
		// Audio On / Off
		changeAudioValue() {
			this.audioValue = !this.audioValue;
			if (this.audioValue)
				console.log("Audio Off -> On");
			else console.log("Audio On -> Off");
			this.publisher.publishAudio(this.audioValue);
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
		// 세션 종료
		async leaveSession() {
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

      // this.$router.push({name:"main"});
		},
		updateMainVideoStreamManager(stream) {
			if (this.mainStreamManager === stream) return;
			this.mainStreamManager = stream;
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
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
					}), {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
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
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
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
    }
	}
}
</script>
