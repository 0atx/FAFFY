<template>
	<div id="view">

    <!-- 메인 페이지 상단 방송 정보 출력 -->
    <div id="topContent" v-if="topContentLoaded">
      <carousel-3d
        :autoplay="true"
        :autoplay-timeout="5000"
        :controls-visible="true"
        :controls-prev-html="'&#10092;'"
        :controls-next-html="'&#10093;'"
        :controls-width="30"
        :controls-height="40"
        :perspective="10"
        :space="300"
        :width="600"
        :height="250"
        :display="5"
      >

        <slide v-for="(slide, i) in bestConsultings" :index="i" :key="i">
          <template slot-scope="{ index, isCurrent, leftIndex, rightIndex }">
            <div style="display:flex">
              <!-- 방송 진행자 이미지 정보 -->
              <!-- <img style="width:350px; height:250px;" :data-index="index" :class="{ current: isCurrent, onLeft: (leftIndex >= 0), onRight: (rightIndex >= 0) }" :src="slide.src"> -->
              <img style="width:350px; height:250px;" :data-index="index" :class="{ current: isCurrent, onLeft: (leftIndex >= 0), onRight: (rightIndex >= 0) }" :src="`${API_BASE_URL}/users/profile/image/${slide.profileImageNo}`"
              @error="replaceByDefault"
              >

              <!-- 방송 정보 -->
              <v-card
              tile
              style="width:250px; height:250px; background-color:white"
              >
                <v-list-item>
                  <v-list-item-content>
                    <div style="display:flex; align-items:center">
                      <div>
                        <!-- 방송 제목 -->
                        <v-list-item-title style="font-weight: 600; font-size:18px;" class="mt-1 mb-1">
                          {{slide.title}}
                        </v-list-item-title>

                        <!-- 컨설턴트 닉네임 / 참여자 / 참여 인원 -->
                        <v-list-item-subtitle class="mb-1">
                          {{slide.consultant}}
                        </v-list-item-subtitle>
                        <v-list-item-subtitle style="font-size: 12px;">
                          참여자 {{slide.viewCount}} / 최대 인원 {{slide.roomSize}}
                        </v-list-item-subtitle>
                      </div>
                    </div>

                    <!-- 방송 카테고리 -->
                    <v-chip-group column>
                      <v-chip
                        small
                        :ripple="false"
                        id="categoryChips"
                        v-for="category in slide.categories"
                        :key="category"
                        :category="category"
                      >
                        {{ category }}
                      </v-chip>
                    </v-chip-group>

                    <!-- 방송 소개 -->
                    <div id="intro">{{slide.intro}}</div>
                    <div style="text-align:right;">
                      <!-- 임시 버튼, 방송 일자 넣어도 되고 다른 정보 넣거나 없앨 수도 있음. 이 버튼은 눌러도 아무 기능 없게 할거임 -->
                      <v-btn
                        outlined
                        rounded
                        text
                        class="mt-1"
                        :to="{name:'consulting-onair',params:{nickname:slide.consultant,consulting_no:slide.no}}"
                      >
                        참여
                      </v-btn>
                    </div>
                  </v-list-item-content>
                </v-list-item>
              </v-card>
            </div>
          </template>
        </slide>
      </carousel-3d>
    </div>


    <!-- 메인 페이지 중-하단 -->
    <div style="width: 100%;">
      <!-- 중단 부분 컨설팅(방송) 목록 썸네일과 함께 출력 -->
      <div style="height: 500px;padding: 3%;">
        <div class="text-h6 ml-4 mb-2" style="font-weight: 600">
          <router-link to="/auth/sign-in">진행중인 방송<v-icon color="black" class="mb-1"> mdi-chevron-right </v-icon></router-link>
        </div>
        <div style="height: 500px; display:flex; flex-wrap:nowrap; justify-content: space-evenly;">
          <!-- <div v-if="midContentLoaded"> -->
          <!-- 방송 정보 카드 -->
            <v-card
              tile
              v-for="(consult, i) in latestConsultings"
              :key="consult.title"
              :class="i % 2 == 0 ? 'blueCard' : 'orangeCard'"
              style="height: 460px; width: 300px;"
              outlined
            >
              <!-- 방송 진행자 이미지 정보 -->
              <v-img
                height="200"
                :src="`${API_BASE_URL}/users/profile/image/${consult.profileImageNo}`"
              ></v-img>
              <v-list-item>
                <v-list-item-content>
                  <div style="display:flex; align-items:center">
                    <div>

                      <!-- 방송 제목 -->
                      <v-list-item-title style="font-weight: 600; font-size:18px;" class="mt-1 mb-1">
                        {{ consult.title }}
                      </v-list-item-title>

                      <!-- 컨설턴트 닉네임 / 참여 인원 -->
                      <v-list-item-subtitle class="mb-1">
                        {{ consult.nickname }}
                      </v-list-item-subtitle>
                      <v-list-item-subtitle style="font-size: 12px;">
                        참여자 {{ consult.viewCount }} / 최대 인원 {{ consult.roomSize }}
                      </v-list-item-subtitle>
                    </div>
                  </div>

                  <!-- 방송 카테고리 -->
                  <v-chip-group>
                    <v-chip
                      small
                      :ripple="false"
                      id="categoryChips"
                      v-for="category in consult.categorys"
                      :key="category"
                      :category="category"
                    >
                      {{ category }}
                    </v-chip>
                  </v-chip-group>

                  <!-- 방송 소개 -->
                  <div style="font-size:15px;" id="intro">{{ consult.intro }}</div>
                  <div style="text-align:right;">
                    <!-- 참여 버튼. 클릭 시 화상회의 방 진입 -->
                    <v-btn
                      outlined
                      rounded
                      text
                      :to="{name:'consulting-onair',params:{nickname:consult.consultant,consulting_no:consult.no}}"
                    >
                      참여
                    </v-btn>
                  </div>
                </v-list-item-content>
              </v-list-item>
            </v-card>
          <!-- </div> -->
        </div>
      </div>



      <!-- 하단 부분 게시글 목록 -->
      <div class="mt-6" style="padding: 3%;">

        <!-- 좌측 -->
        <div style="display: flex;padding: 2%; justify-content: space-between;">
          <div>
            <!-- 좌측 상단 인기 글 -->
            <div class="mb-4" style="width: 700px;">
              <router-link style="font-weight:600;" class="text-h6" to="/auth/sign-in">게시글1<v-icon color="black" class="mb-1"> mdi-chevron-right </v-icon></router-link>
              <v-data-table
                class="table"
                :headers="headers"
                hide-default-header
                hide-default-footer
                :items="boards"
                :items-per-page="5"
              ></v-data-table>
            </div>
            <!-- 좌측 하단 최신 글 -->
            <div style="width: 700px;">
              <router-link style="font-weight:600;" class="text-h6" to="/auth/sign-in">게시글2<v-icon color="black" class="mb-1"> mdi-chevron-right </v-icon></router-link>
              <v-data-table
                class="table"
                :headers="headers"
                hide-default-footer
                :items="boards"
                :items-per-page="5"
              ></v-data-table>
            </div>
          </div>
          <!-- 우측 이미지 게시글 -->
          <div style="width: 500px; ">
            <router-link style="font-weight:600;" class="text-h6 ml-3" to="/auth/sign-in">게시글3<v-icon color="black" class="mb-1"> mdi-chevron-right </v-icon></router-link>
              <v-row style="margin: 0px;">
                <!-- 반복문 수정 필요 -->
                <v-col
                  v-for="n in 4"
                  :key="n"
                  class="mt-2 d-flex child-flex"
                  cols="6"
                >
                <figure class="imgBoard">
                  <!-- 게시글 이미지 -->
                  <v-img
                    :src="`https://picsum.photos/500/300?image=${n * 5 + 10}`"
                    :lazy-src="`https://picsum.photos/10/6?image=${n * 5 + 10}`"
                    aspect-ratio="1"
                    class="grey lighten-2"
                  >
                    <!-- 게시글 정보 -->
                    <figcaption>
                      <h3>게시글 제목입니다.</h3>
                      <p>게시글 내용 요약해서 보여줄 것 입니다. 아니면 작성자 닉네임 쓸 것 50자 내외로요...</p>
                      <p style="margin-bottom:5px;">별명짓기귀찮다팔구십</p><p>2022-08-01</p>
                      <!-- 클릭 시 해당 게시글 상세정보로 넘어가게 변경 예정 -->
                      <a href="#" class="read">Read More</a>
                    </figcaption>
                    <template v-slot:placeholder>
                      <v-row
                        class="fill-height ma-0"
                        align="center"
                        justify="center"
                      >
                        <v-progress-circular
                          indeterminate
                          color="grey lighten-5"
                        ></v-progress-circular>
                      </v-row>
                    </template>
                  </v-img>
                </figure>
              </v-col>
            </v-row>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Carousel3d, Slide } from 'vue-carousel-3d';
import {consulting} from "@/api/consulting.js";
import { API_BASE_URL } from "@/config";
import defaultProfileSetter from "@/utils/defaultProfileSetter.js";

export default {
	name: "MainView",
  components: {
    Carousel3d,
    Slide,
  },
	data() {
		return {
      API_BASE_URL: API_BASE_URL,
      bestConsultings:[],
      latestConsultings:[],
      topContentLoaded:false,
      midContentLoaded:false,
      // slides: [
      //   {
      //     consultant:"park2",
      //     consultant_no:3,
      //     intro:"소개",
      //     title:"제목임",
      //     categories:["hiphop","daily"],
      //     no:299,
      //     profileImageNo:3,
      //     viewCount:1,
      //     roomSize:10,
      //   },
      //   {
      //     consultant:"park2",
      //     consultant_no:3,
      //     intro:"소개2",
      //     title:"제목임2",
      //     categories:["hiphop","daily"],
      //     no:21,
      //     profileImageNo:3,
      //     viewCount:1,
      //     roomSize:10,
      //   },
      //   {
      //     consultant:"park2",
      //     consultant_no:3,
      //     intro:"소개3",
      //     title:"제목임3",
      //     categories:["hiphop"],
      //     no:69,
      //     profileImageNo:3,
      //     viewCount:1,
      //     roomSize:10,
      //   },
      //   // {
      //   //   src: "https://picsum.photos/500/300?image=20"
      //   // },
      //   // {
      //   //   src: "https://picsum.photos/500/300?image=25"
      //   // },
      //   // {
      //   //   src: "https://picsum.photos/500/300?image=30"
      //   // },
      //   // {
      //   //   src: "https://picsum.photos/500/300?image=35"
      //   // }
      // ],

      consults: [
        {
          src: "https://cdn.vuetifyjs.com/images/cards/cooking.png",
          title: "방송 1",
          intro: "정렬할 요소에 position 값을 absolute 로 설정하여 중앙정렬 하는 것에는 두가지 방법이 있다. 부모 요소(라기보단 기준이 될 요소)에 position:relative를",
          nickname: "별명짓기귀찮다",
          viewCount: "3",
          roomSize: "10",
          categorys: [
            "캐주얼",
            "모던",
          ],
        },
        {
          src: "https://cdn.vuetifyjs.com/images/cards/cooking.png",
          title: "방송 2",
          intro: "2014년 응용수학을 바탕으로 최적화 의사 결정을 연구하던 과학자가 난제에 가까운 산학 협력 프로젝트를 만났다. 한 부동산개발 업체가 캐나다 토론토의 한 고층 콘도미니엄을 분양하면",
          nickname: "별명짓기귀찮다",
          viewCount: "3",
          roomSize: "10",
          categorys: [
            "캐주얼",
            "모던",
          ],
        },
        {
          src: "https://cdn.vuetifyjs.com/images/cards/cooking.png",
          title: "방송 3",
          intro: "방송 정보",
          nickname: "별명짓기귀찮다",
          viewCount: "3",
          roomSize: "10",
          categorys: [
            "캐주얼",
            "모던",
          ],
        },
        {
          src: "https://cdn.vuetifyjs.com/images/cards/cooking.png",
          title: "방송 4",
          intro: "방송 정보",
          nickname: "별명짓기귀찮다",
          viewCount: "3",
          roomSize: "10",
          categorys: [
            "캐주얼",
            "모던",
          ],
        }
      ],
      // 임의로 설정한 방송 관심 카테고리, 나중에 DB에서 받아온거로 대체 예정
      consultCategorys: [
        "캐주얼",
        "모던",
        "시크",
      ],
      headers: [
        { text: '분류', align:'start', value:'category', width: '5%'},
        { text: '게시글 제목', align: 'center',value:'title', width: '65%'},
        { text: '작성자', align: 'end',value:'writer', width: '10%'},
        { text: '작성 일자', align: 'end',value:'date', width: '10%'},
        { text: '조회수', align: 'end',value:'hit', width: '5%'},
      ],
      // 임시 게시글 기록, DB에서 받아와서 넘겨줘야 함
      boards: [
        {
          category: '자유',
          title: '게시글 제목입니다.',
          writer: '별명짓기귀찮다',
          date: '2022-08-01',
          hit: '56'
        },
        {
          category: '자유',
          title: '게시글 제목입니다.22',
          writer: '별명짓기귀찮다',
          date: '2022-08-01',
          hit: '56'
        },
        {
          category: '자유',
          title: '게시글 제목입니다.33',
          writer: '별명짓기귀찮다',
          date: '2022-08-01',
          hit: '56'
        },
        {
          category: '자유',
          title: '게시글 제목입니다.44',
          writer: '별명짓기귀찮다',
          date: '2022-08-01',
          hit: '56'
        },
        {
          category: '자유',
          title: '게시글 제목입니다.55',
          writer: '별명짓기귀찮다',
          date: '2022-08-01',
          hit: '56'
        },
      ],
    };
	},

	mounted() {
    window.scrollTo(0, 0);
    // 방송 목록 요청-----
    // 상단 참여자수 순 목록
    consulting.getBestConsultings(5)
    .then((data)=> {
      console.log(data);
      this.bestConsultings = data["content"];
      this.topContentLoaded=true;
    })
    .catch((error)=> {
      console.log(error);
    })
    // 중단 생성 순 목록
    consulting.getLatestConsultings(5)
    .then((data)=> {
      console.log(data);
      this.latestConsultings = data["content"];
      this.midContentLoaded=true;
    })
    .catch((error)=> {
      console.log(error);
    })
    console.log("hi");
    // 방송 목록 요청 END
  },

	methods: {
    replaceByDefault: defaultProfileSetter.replaceByDefault,

  },
};
</script>

<style scoped>
#view {
  background-color: white;
  padding: 0 5%;
  text-align: left;
}

#topContent {
  height:350px;
  width: 100%;
  padding-top:2%;
  display:flex;
  align-items:center;
}

.carousel-ed-container template {
  margin: 0;
}

.carousel-3d-container .carousel-3d-slide .title {
  font-size: 22px;
}

a {
  color: black !important;
  text-decoration: none;
}

.orangeCard {
  --c: #fff; /* the color */
  --b: 1px;    /* border length*/
  --d: 10px;    /* the cube depth */

  --_s: calc(var(--d) + var(--b));
  color: var(--c);
  border: solid #0000;
  border-width: var(--b) var(--b) var(--_s) var(--_s);
  background:
    conic-gradient(at left var(--d)  bottom var(--d),
      #0000 90deg, rgb(255 255 255 /0.3) 0 225deg,rgb(255 255 255 /0.6) 0) border-box,
    conic-gradient(at left var(--_s) bottom var(--_s),
      #0000 90deg,var(--c) 0) 0 100%/calc(100% - var(--b)) calc(100% - var(--b))  border-box;
  transform: translate(calc(var(--d)/-1),var(--d));
  clip-path:
    polygon(
      var(--d) 0%,
      var(--d) 0%,
      100% 0%,
      100% calc(100% - var(--d)),
      100% calc(100% - var(--d)),
      var(--d) calc(100% - var(--d))
    );
  transition: 0.5s;
}

.orangeCard:hover {
  --c: #ff7451;
  transform: translate(0,0);
  clip-path:
    polygon(
      0% var(--d),
      var(--d) 0%,
      100% 0%,
      100% calc(100% - var(--d)),
      calc(100% - var(--d)) 100%,
      0% 100%
  );
}

.blueCard {
  --c: #fff; /* the color */
  --b: 1px;    /* border length*/
  --d: 10px;    /* the cube depth */

  --_s: calc(var(--d) + var(--b));
  color: var(--c);
  border: solid #0000;
  border-width: var(--b) var(--b) var(--_s) var(--_s);
  background:
    conic-gradient(at left var(--d)  bottom var(--d),
      #0000 90deg, rgb(255 255 255 /0.3) 0 225deg,rgb(255 255 255 /0.6) 0) border-box,
    conic-gradient(at left var(--_s) bottom var(--_s),
      #0000 90deg,var(--c) 0) 0 100%/calc(100% - var(--b)) calc(100% - var(--b))  border-box;
  transform: translate(calc(var(--d)/-1),var(--d));
  clip-path:
    polygon(
      var(--d) 0%,
      var(--d) 0%,
      100% 0%,
      100% calc(100% - var(--d)),
      100% calc(100% - var(--d)),
      var(--d) calc(100% - var(--d))
    );
  transition: 0.5s;
}

.blueCard:hover {
  --c: #0c0f66;
  transform: translate(0,0);
  clip-path:
    polygon(
      0% var(--d),
      var(--d) 0%,
      100% 0%,
      100% calc(100% - var(--d)),
      calc(100% - var(--d)) 100%,
      0% 100%
  );
}

#introduce {
  height: 80px;
  display: flex;
  padding: 0;
  justify-content: flex-start;
  pointer-events: none;
  font-size:15px;
}

#categoryChips {
  background-color: #0c0f66;
  color: #fff;
  pointer-events: none;
}

.imgBoard {
	position: relative;
  width: 100%;
  height:100%;
	box-shadow: 1px 1px 3px rgba(0,0,0,0.4);
}
.imgBoard img {
	width: 100%;
	filter: saturate(50%);
	transition: all 0.3s ease 0.1s;
	backface-visibility: hidden;
}
.imgBoard figcaption {
	position: absolute;
	top: 50%; left: 0;
	width: 100%;
  height: 100%;
	padding: 35px 10px;
	box-sizing: border-box;
	z-index: 1;
	line-height: 1em;
	color: #fff;
	font-size: 16px;
	opacity:1;
	transition: all 0.5s ease;
	transform: rotatex(90deg) translate(0, -50%);
	transform-origin: 0% 0%;
}
.imgBoard figcaption h3 {
  line-height: 1em;
	font-weight: 800;
}
.imgBoard figcaption p {
  font-size: 0.8em;
  font-weight: 500;
  margin: 0 0 15px;
}
.imgBoard figcaption .read {
	border: 2px solid #fff;
	padding: 0.5em 1em;
	font-size: 0.8em;
	color: #fff !important;
	text-decoration: none;
	transition: all 0.3s ease;
}
.imgBoard figcaption .read:hover {
	background: #fff;
	color: #000 !important;
}

.imgBoard:hover img {
	transform: rotatex(-180deg);
	opacity: 0;
	transition-delay: 0;
}
.imgBoard:hover figcaption {
	transform: rotatex(0deg) translate(0, -50%);
  background-color: #000;
	opacity: 0.8;
	transition-delay: 0.35s;
}

</style>
