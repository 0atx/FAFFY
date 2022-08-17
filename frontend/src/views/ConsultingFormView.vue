<template>
  <div id="view">
    <div style="display:flex;width:100%;">
      <div style="width: 100%;">
        <!-- 비디오 표시 영역-->
        <!-- 방장 기준일때 -->
        <div v-if="isHost">
          <div id="topVideo" class="grey lighten-2 pt-4" v-if="session">
            <v-btn class="text-h6" id="icon" style="float:right; margin-right:1%; padding: 0px 8px; background-color:white" elevation="0" :ripple="false" v-if="!drawer" @click="drawer=!drawer">
              <v-icon class="mt-1 mr-1" color="#666" size="22">mdi-arrow-left</v-icon> 참여자 / 채팅
            </v-btn>
            <div style="width: 100%; height:360px;">
              <div v-if="isShare" style="display:flex; justify-content:center;" id="mainVideo" class="mb-3">
                <div style="margin: 0 5%;">
                  <user-video id="myWebcam" style="width: 500px; height: 360px;" :stream-manager="publisher" v-on:capture-event="captureSignal"/>
                </div>
                <div v-for="sub in subscribers" :key="sub.stream.connection.connectionId">
                  <user-video id="myWebcam" v-if="checkScreen(sub)" style="height: 360px;" :stream-manager="sub" v-on:capture-event="captureSignal"/>
                </div>
              </div>
              <div v-else id="mainVideo" class="mb-3">
                <user-video id="myWebcam" style="width: 500px; height: 360px; margin:0 auto;" :stream-manager="publisher" v-on:capture-event="captureSignal"/>
              </div>
            </div>
            <div>
              <v-sheet
                id="subVideo"
                max-width="100%"
                height="280"
                class="grey lighten-2"
              >
                <v-slide-group
                  v-model="model"
                  show-arrows
                >
                  <v-slide-item
                    v-for="sub in subscribers" :key="sub.stream.connection.connectionId"
                    style="margin: 13px;"
                  >
                  <user-video v-if="!checkScreen(sub)" style="width: 331px; height: 260px;" :stream-manager="sub" v-on:capture-event="captureSignal"/>
                  </v-slide-item>
                </v-slide-group>
              </v-sheet>
            </div>
          </div>
        </div>
        <!-- 참가자 기준일때 -->
        <div v-else>
          <div id="topVideo" class="grey lighten-2 pt-4" v-if="session">
            <v-btn class="text-h6" id="icon" style="float:right; margin-right:1%; padding: 0px 8px; background-color:white" elevation="0" :ripple="false" v-if="!drawer" @click="drawer=!drawer">
              <v-icon class="mt-1 mr-1" color="#666" size="22">mdi-arrow-left</v-icon> 참여자 / 채팅
            </v-btn>
            <div style="width: 100%; height:360px;">
              <div v-if="isShare" style="display:flex; justify-content:center;" id="mainVideo" class="mb-3">
                <div style="margin: 0 2%;" v-for="sub in subscribers" :key="sub.stream.connection.connectionId">
                  <user-video id="myWebcam" v-if="checkHost(sub) && !checkScreen(sub)" style="width: 500px; height: 360px;" :stream-manager="sub" v-on:capture-event="captureSignal"/>
                </div>
                <div v-for="sub in subscribers" :key="sub.stream.connection.connectionId">
                  <user-video id="myWebcam" v-if="checkHost(sub) && checkScreen(sub)" style="height: 360px;" :stream-manager="sub" v-on:capture-event="captureSignal"/>
                </div>
              </div>
              <div v-else id="mainVideo" class="mb-3">
                <div v-for="sub in subscribers" :key="sub.stream.connection.connectionId">
                  <user-video id="myWebcam" v-if="checkHost(sub) && !checkScreen(sub)" style="width: 500px; height: 360px; margin:0 auto;" :stream-manager="sub" v-on:capture-event="captureSignal"/>
                </div>
              </div>
            </div>
            <div>
              <v-sheet
                id="subVideo"
                max-width="100%"
                class="grey lighten-2"
                height="280"
              >
                <v-slide-group
                  v-model="model"
                  show-arrows
                >
                  <v-slide-item
                    style="margin: 13px;"
                  >
                  <user-video v-if="!isHost" style="width: 331px; height: 260px; object-fit: fill;" :stream-manager="publisher" v-on:capture-event="captureSignal"/>
                  </v-slide-item>
                  <v-slide-item
                    v-for="sub in subscribers" :key="sub.stream.connection.connectionId"
                    style="margin: 13px;"
                  >
                  <user-video v-if="!checkHost(sub) && !checkScreen(sub)" style="width: 331px; height: 260px; object-fit: fill;" :stream-manager="sub" v-on:capture-event="captureSignal"/>
                  </v-slide-item>

                </v-slide-group>
              </v-sheet>
            </div>
          </div>
        </div>
        <!-- 비디오 표시 영역 END -->
        <!-- 중단 옵션 영역 -->
        <div id="centerOption" class="grey lighten-2">
            <div id="optionButton" class="grey lighten-1">
              <v-tooltip bottom v-if="!audioValue">
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

              <v-tooltip bottom v-if="!camValue">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="onButton"><v-icon size="30" color="#fff" @click="toggleCam">mdi-video</v-icon></v-btn>
                </template>
                <span>비디오 시작</span>
              </v-tooltip>
              <v-tooltip bottom v-else>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="offButton"><v-icon size="30" color="#fff" @click="toggleCam">mdi-video-off</v-icon></v-btn>
                </template>
                <span>비디오 중지</span>
              </v-tooltip>

              <v-tooltip bottom>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="onButton" @click="showAlbum"><v-icon size="30" color="#fff">mdi-image-multiple</v-icon></v-btn>
                </template>
                <span>앨범</span>
              </v-tooltip>
<!--
              <v-tooltip bottom>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="onButton"><v-icon size="30" color="#fff" @click="toggleMirror">mdi-reflect-horizontal</v-icon></v-btn>
                </template>
                <span>좌우 반전</span>
              </v-tooltip> -->

              <v-tooltip bottom v-if="!mosaicValue">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="onButton" @click="toggleMosaic"><v-icon size="30" color="#fff">mdi-blur</v-icon></v-btn>
                </template>
                <span>모자이크</span>
              </v-tooltip>
              <v-tooltip bottom v-else>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="offButton" @click="toggleMosaic"><v-icon size="30" color="#fff" >mdi-blur-off</v-icon></v-btn>
                </template>
                <span>모자이크 해제</span>
              </v-tooltip>

              <v-tooltip bottom v-if="!remoteValue">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="onButton" @click="toggleRemote"><v-icon size="30" color="#fff">mdi-motion-sensor</v-icon></v-btn>
                </template>
                <span>모션 인식 촬영</span>
              </v-tooltip>
              <v-tooltip bottom v-else>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="offButton" @click="toggleRemote"><v-icon size="30" color="#fff">mdi-motion-sensor-off</v-icon></v-btn>
                </template>
                <span>모션 인식 해제</span>
              </v-tooltip>

              <v-tooltip bottom v-if="!screenOV && isHost">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn elevation="0" v-bind="attrs" v-on="on" :ripple="false" icon class="onButton" @click="startScreenShare"><v-icon size="30" color="#fff">mdi-monitor-share</v-icon></v-btn>
                </template>
                <span>화면 공유 시작</span>
              </v-tooltip>
              <v-tooltip bottom v-else-if="screenOV && isHost">
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
      <div style="width:20%;" id="drawer" v-if="drawer && session">
        <chat-subscriber-tab :session="session" v-if="drawer" @hideDrawer="hideDrawer"/>
      </div>

      <!-- 스냅샷 캔버스 -->

      <template>
        <v-row justify="center">
          <v-dialog eager
            v-model="snapshotDialog"
            persistent
            max-width="600"
            max-height="600"
          >
            <v-card>
              <v-card-title class="ml-2 text-h5; font-weight: 600;">
                캡쳐 완료!
              </v-card-title>
              <v-card-text>
                <canvas id="drawCanvas" width="500px" height="375px" style="border:1px; solid black" ref="snapshot_canvas"/>

              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                  color="#ff7451"
                  text
                  @click="snapshotDialog = false"
                >
                  취소
                </v-btn>
                <v-btn
                  color="#0c0f66"
                  text
                  @click="upload"
                >
                  업로드
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-row>
      </template>

      <!-- 앨범 -->
      <template>
        <v-row justify="center">
          <v-dialog eager
            v-model="albumDialog"
            persistent
            max-width="1000"
            max-height="800"
          >
            <v-card>
              <v-card-title class="text-h5; font-weight: 600;">
                앨범
              </v-card-title>
              <v-card-text>
                <div v-for="img_no in snapshotList" :key="img_no">
                  <img
                      :src="`${IMG_BASE_URL}/` + img_no"
                      alt="snapshot"
                  />
                </div>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                  color="#0c0f66"
                  text
                  @click="albumDialog = false"
                >
                  닫기
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-row>
      </template>
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
import { API_BASE_URL } from "@/config";

axios.defaults.headers.post['Content-Type'] = 'application/json';

import {OPENVIDU_URL,OPENVIDU_SECRET} from "@/config";
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
      drawer: true,
      //
      OV: undefined,
			session: undefined,
			mainStreamManager: undefined,
			publisher: undefined,
			subscribers: [],
      subsNoScreen: [],
			camValue: true,
			audioValue: false,
      mosaicValue:false,
      remoteValue:false,
      isHost:false,
      isShare:false,
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

      // 스냅샷 영역
      canvas:null,
      snapshotDialog:false,

      // 앨범
      albumDialog:false,
      IMG_BASE_URL: API_BASE_URL + "/consultings/snapshot",

      // 방송 퇴장 시 무한루프 방지용
      leaveTrigger:false,
    }
  },
  computed: {
    ...mapState("authStore",["loginUser"]),
    ...mapState("consultingStore",["participants","consultingInfo","snapshotList"]),

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
    this.canvas = this.$refs.snapshot_canvas;

    this.nickname = this.$route.params.nickname;
    this.consulting_no = this.$route.params.consulting_no;

    // 비정상 접근 감지
    if (this.nickname == undefined) {
      this.$dialog.message.info('비정상적인 접근입니다.', {
        position: "top",
        timeout: 2000,
        color: "#ff7451",
      });
      this.$router.push('/');

      return;
    }

    this.mySessionId = ""+this.consulting_no;
    this.myUserName=this.loginUser.no+":"+this.loginUser.nickname;

    // 방송 정보 요청 후 값 세팅
    consulting.getConsulting(this.consulting_no)
    .then((data)=> {
      data = data.content;
      // 이미 종료된 방송인지 확인
      if (data.endTime != null)
      {
        alert("이미 종료된 방송입니다.");
        this.$router.push('/');
        return;
      } // 시청자 수 체크
      else if (data.roomSize <= data.viewCount) {
        alert("최대 인원수를 초과했습니다.");
        this.$router.push('/');
        return;
      }

      this.SET_CONSULTING_INFO(data);
      this.getSnapshotList();
    })
    .catch(()=> {
    })


    this.INIT_CONSULTING_INFO();
    this.INIT_PARTICIPANTS();
    this.SET_CHATS([]);
    this.joinSession();
    this.updateViewCount();
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
    ...mapMutations("consultingStore",["INIT_CONSULTING_INFO","SET_CONSULTING_INFO","SET_PARTICIPANTS",
    "INIT_PARTICIPANTS","SET_SHARESCREEN","SET_CHATS","SET_SNAPSHOT_LIST","SET_MOSAIC","SET_REMOTE"]),

    hideDrawer() {
      this.drawer = !this.drawer
    },

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

        let namecode = JSON.parse(stream.connection.data).clientData.split(':');
        console.log("네임코드 확인 중!!! : ", namecode[1]);
        console.log(this.nickname + '님의 화면');

        if(!(namecode[1] == this.nickname + '님의 화면')) {
          this.subscribers.push(subscriber);
          this.subsNoScreen.push(subscriber);
        } else {
          this.subscribers.push(subscriber);
          this.isShare = true;
          this.SET_SHARESCREEN(this.isShare);
          console.log("Screen 시작 : " + this.isShare);
        }

        console.log("subscrobers stream");
        this.SET_PARTICIPANTS(this.subsNoScreen);
        // 시청자 수 업데이트
        this.updateViewCount();
			});

			// 끝낸 모든 스트림들에 대해서
			this.session.on('streamDestroyed', ({ stream }) => {
				const index = this.subscribers.indexOf(stream.streamManager, 0);
				let namecode = JSON.parse(stream.connection.data).clientData.split(':');

        if(namecode[1] == this.nickname + '님의 화면') {
          this.isShare = false;
          this.SET_SHARESCREEN(this.isShare);
          console.log("Screen 종료 : " + this.isShare);
        }
        // 모자이크 종료시키기
        let no = namecode[0];
        let mosaicValue = false;
        this.participants.forEach(element => {
          if (element.no == no) {
            console.log("퇴장 찾았다");
            element.mosaicValue = mosaicValue;
          }
        });
        if (index >= 0) {
					this.subscribers.splice(index, 1);
				}
        this.SET_PARTICIPANTS(this.subscribers);
        this.updateViewCount();

			});

			// 보든 비동기 예외에 대해서
			this.session.on('exception', ({ exception }) => {
				console.warn(exception);
			});
			// 시그널 수신
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
            confirm("방송이 종료되었습니다.");
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
          // 파일 signal일 경우
        } else if (event.type=="signal:upload") {
          this.getSnapshotList();
        } else if (event.type=="signal:mosaic") {
          let no = data.no;
          let mosaicValue = data.mosaicValue;
          this.participants.forEach(element => {
            if (element.no == no) {
              element.mosaicValue = mosaicValue;
            }
          });
        }
			});
			// 유효한 유저 토큰으로 세션에 연결

			// 'getToken' 은 서버측에서 수행해야할 작업을 시뮬레이션
			// 'token' 매개 변수는 사백엔드에서 검색하여 반환해야 함
			this.getToken(this.mySessionId).then(token => {

				this.session.connect(token, { clientData: this.myUserName,mosaicValue:false, })
					.then(() => {

						// --- Get your own camera stream with the desired properties ---

						let publisher = this.OV.initPublisher(undefined, {
							audioSource: undefined, // The source of audio. If undefined default microphone
							videoSource: undefined, // The source of video. If undefined default webcam
							publishAudio: this.audioValue,  	// Whether you want to start publishing with your audio unmuted or not
							publishVideo: this.camValue,  	// Whether you want to start publishing with your video enabled or not
							resolution: '500x400',  // The resolution of your video
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
            console.log("-----------")
            console.log("subs", this.subscribers);
            console.log("-----------")
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
    toggleMosaic() {
      this.mosaicValue = !this.mosaicValue;
      this.SET_MOSAIC(this.mosaicValue);
      this.mosaicSignal();
    },
    toggleRemote() {
      this.remoteValue = !this.remoteValue;
      this.SET_REMOTE(this.remoteValue);
    },

    // 화면 공유 시작
		startScreenShare() {
			this.screenOV = new OpenVidu();
			this.screenSession = this.screenOV.initSession();
			this.screenShareName = this.myUserName + "님의 화면",

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
          this.screenShareName = undefined;
				})

		},
		// 화면 공유 종료
		stopScreenShare() {
			this.screenSession.disconnect();
			this.screenOV = undefined;
			this.screenSession = undefined;
      this.screenShareName = undefined;
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
							password: OPENVIDU_SECRET,
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
							password: OPENVIDU_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => reject(error.response));
			});
		},
    showAlbum() {
      this.albumDialog = true;
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
      // 무한루프 방지
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
    async leaveConsulting() {
      const res = await this.$dialog.confirm({
        text: '<br>정말로 퇴장하시겠습니까?',
        icon: true,
        actions: {
          false : {
            text: '취소', color: '#ff7451'
          },
          true : {
            text: '확인', color: '#0c0f66'
          },
        }
      });
      if (res) {
        this.leaveSession();
      }
    },
    checkHost(sub) {
      let namecode = JSON.parse(sub.stream.connection.data).clientData.split(':');
      return namecode[0] == this.consultingInfo.consultant_no;
    },
    checkScreen(sub) {
      let namecode = JSON.parse(sub.stream.connection.data).clientData.split(':');
      return namecode[1] == this.nickname + '님의 화면';
    },
    captureSignal(video) {
      this.snapshotDialog = true;
			let context = this.canvas.getContext("2d");

			context.drawImage(video, 0, 0, 500,400);
      console.log("캡쳐 완료");
    },
    upload() {
      const imgBase64 = this.canvas.toDataURL('image/jpeg','multipart/form-data');
			const decodImg = atob(imgBase64.split(',')[1]);

			let array = [];
			for (let i = 0; i < decodImg.length; i++) {
				array.push(decodImg.charCodeAt(i));
			}

			const file = new Blob([ new Uint8Array(array) ], {
				type : 'image/jpeg'
			});
			const fileName = 'web_snapshot_'+Date.now()+'.jpg';
			let formData = new FormData();
			formData.append('file', file, fileName);
      formData.append('consulting_no',this.consulting_no);
      console.log(formData);

      consulting.uploadSnapshot(formData)
      .then(response=> {
        console.log(response);
        this.session.signal({
          data: JSON.stringify({
          message:"파일 업로드 완료"
          }),
          to: [],                     // Array of Connection objects (optional. Broadcast to everyone if empty)
          type: 'upload'             // The type of message (optional)
          })
            .then(() => {
              console.log('upload Message successfully sent');
              this.message = "";
            })
            .catch(error => {
              console.error(error);
				});
      })
      .catch(response => {
        console.log(response);
      })
      this.snapshotDialog = false;
    },
    async getSnapshotList() {
      await consulting.getConsultingSnapshots(this.consulting_no)
      .then((data)=> {
        console.log("스냅샷 불러오기 성공");
        console.log(data);
        this.SET_SNAPSHOT_LIST(data.content);
      })
      .catch((error)=> {
        console.log("스냅샷 불러오기 실패");
        console.log(error);
      })
    },
    async mosaicSignal() {
      await this.session.signal({
          data: JSON.stringify({
            no:this.loginUser.no,
            mosaicValue:this.mosaicValue,
          }),
          to: [],                     // Array of Connection objects (optional. Broadcast to everyone if empty)
          type: 'mosaic'             // The type of message (optional)
          })
            .then(() => {
              console.log('mosaic signal successfully sent');
            })
            .catch(error => {
              console.error(error);
				});
    },
    async updateViewCount() {
      await consulting.setViewCount(this.participants.length+1,this.consulting_no);
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
  padding: 13px 10px;
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

#drawer {
  position: fixed;
  top: 50;
  right: 0;
}

#icon::before {
  background-color: transparent;
}

</style>
