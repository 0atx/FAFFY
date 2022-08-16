<template>
  <div id="view">
    <!-- 메인 페이지 상단 방송 정보 출력 -->
    <div id="topContent" v-if="topContentLoaded">
      <v-carousel
        v-if="bestConsultings.length == 0"
        style="width:90%; margin:0 auto;"
        cycle
        height="auto"
        :show-arrows="false"
        hide-delimiters
        >
        <v-carousel-item
          style="width:100%; height:250px; margin:0 auto;"
          v-for="(banner, i) in banners"
          :key="i"
          :src="require(`@/assets/images/${banner.name}`)"
        >
        </v-carousel-item>
      </v-carousel>
      <carousel-3d
        v-else
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
              <img style="width:250px; height:250px;" :data-index="index" :class="{ current: isCurrent, onLeft: (leftIndex >= 0), onRight: (rightIndex >= 0) }" :src="`${API_BASE_URL}/users/profile/image/${slide.consultant_no}`"
              @error="replaceByDefault"
              >
              <!-- 방송 정보 -->
              <v-card
                tile
                style="width:350px; height:250px; background-color:white"
              >
                <v-list-item>
                  <v-list-item-content>
                    <div style="display:flex; align-items:center">
                      <div>
                        <!-- 방송 제목 -->
                        <v-list-item-title style="font-weight: 600; font-size:22px;" class="mt-1 mb-1">
                          {{slide.title}}
                        </v-list-item-title>
                        <!-- 컨설턴트 닉네임 / 참여자 / 참여 인원 -->
                        <v-list-item-subtitle style="font-size: 18px;" class="mb-1">
                          {{slide.consultant}}
                        </v-list-item-subtitle>
                        <v-list-item-subtitle style="font-size: 14px;">
                          <v-icon small>mdi-account-multiple</v-icon> {{slide.viewCount}} / {{slide.roomSize}}
                        </v-list-item-subtitle>
                      </div>
                    </div>
                    <!-- 방송 카테고리 -->
                    <v-chip-group column>
                      <v-chip
                        small
                        :ripple="false"
                        id="categoryChips"
                        v-for="(category, i) in slide.categories"
                        :key="i"
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
          <router-link to="/consultings">진행중인 방송<v-icon color="black" class="mb-1"> mdi-chevron-right </v-icon>
          </router-link>
        </div>
        <div style="height: 500px; display:flex; flex-wrap:nowrap; justify-content: space-evenly;">
          <!-- <div v-if="midContentLoaded"> -->
          <v-col style="height:500px; display:flex; justify-content:center; align-items:center;" v-if="latestConsultings.length == 0" cols="12">
            <div style="text-align:center;">
              <v-icon color="#333" large block> mdi-broadcast-off </v-icon>
              <h2>현재 진행중인 방송이 없습니다.</h2>
            </div>
          </v-col>
          <!-- 방송 정보 카드 -->
            <v-card
              v-else
              tile
              v-for="(consult, i) in latestConsultings"
              :key="i"
              :class="i % 2 == 0 ? 'blueCard' : 'orangeCard'"
              style="height: 460px; width: 300px;"
              outlined
            >
              <!-- 방송 진행자 이미지 정보 -->
              <img
                height="200"
                width="100%"
                :src="`${API_BASE_URL}/users/profile/image/${consult.consultant_no}`"
                @error="replaceByDefault"
              />
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
                        <v-icon small>mdi-account-multiple</v-icon> {{ consult.viewCount }} / {{ consult.roomSize }}
                      </v-list-item-subtitle>
                    </div>
                  </div>
                  <!-- 방송 카테고리 -->
                  <v-chip-group>
                    <v-chip
                      small
                      :ripple="false"
                      id="categoryChips"
                      v-for="(category, i) in consult.categorys"
                      :key="i"
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
            <div class="mb-4" style="width: 700px; min-height:250px;">
              <router-link style="font-weight:600;" class="text-h6" to="/board">인기 게시글<v-icon color="black"
                  class="mb-1"> mdi-chevron-right </v-icon>
              </router-link>
              <v-col style="height:250px; display:flex; justify-content:center; align-items:center;" v-if="hitBoardList.length == 0" cols="12">
                <div style="text-align:center;">
                  <v-icon color="#333" large block> mdi-clipboard-text-off-outline </v-icon>
                  <h4>작성된 게시글이 없습니다.</h4>
                </div>
              </v-col>
              <v-data-table v-else class="table" :headers="headers" hide-default-footer :items="hitBoardList"
                :items-per-page="5">
                <template v-slot:[`item.title`]="{ item }">
                  <router-link :to="{ name: 'board-detail', params: { boardNo: item.no } }">
                    {{ item.title }}
                  </router-link>
                </template>
              </v-data-table>
            </div>
            <!-- 좌측 하단 최신 글 -->
            <div class="mb-4" style="width: 700px; min-height:250px;">
              <router-link style="font-weight:600;" class="text-h6" to="/board">최신 게시글<v-icon color="black"
                  class="mb-1"> mdi-chevron-right </v-icon>
              </router-link>
              <v-col style="height:250px; display:flex; justify-content:center; align-items:center;" v-if="latestBoardList.length == 0" cols="12">
                <div style="text-align:center;">
                  <v-icon color="#333" large block> mdi-clipboard-text-off-outline </v-icon>
                  <h4>작성된 게시글이 없습니다.</h4>
                </div>
              </v-col>
              <v-data-table v-else class="table" :headers="headers" hide-default-footer :items="latestBoardList"
                :items-per-page="5">
                <template v-slot:[`item.title`]="{ item }">
                  <router-link :to="{ name: 'board-detail', params: { boardNo: item.no } }">
                    {{ item.title }}
                  </router-link>
                </template>
              </v-data-table>
            </div>
          </div>
          <!-- 우측 이미지 게시글 -->
          <div v-if="imgSrcList.length >=0" style="width: 500px; ">
            <router-link style="font-weight:600;" class="text-h6 ml-3" to="/board">이미지 게시글
            <v-icon color="black"
                class="mb-1"> mdi-chevron-right </v-icon>
            </router-link>
            <v-row style="margin: 0px;">
              <!-- 반복문 수정 필요 -->
              <v-col v-for="(imgBoard,i) in imgBoardList" :key="i" class="mt-2 d-flex child-flex" cols="6">
                <figure class="imgBoard">
                  <!-- 게시글 이미지 -->
                  <v-img :src= imgSrcList[i]
                    :lazy-src= imgSrcList[i] aspect-ratio="1"
                    class="grey lighten-2">
                    <!-- 게시글 정보 -->
                    <figcaption>
                      <h3>{{imgBoard.title}}</h3>
                      <p>{{imgBoard.content}}</p>
                      <p style="margin-bottom:5px;">{{imgBoard.user.nickname}}</p>
                      <p>{{imgBoard.dateTime}}</p>
                      <router-link :to="{ name: 'board-detail', params: { boardNo: imgBoard.no } }" class="read">
                      Read More
                      </router-link>
                    </figcaption>
                    <template v-slot:placeholder>
                      <v-row class="fill-height ma-0" align="center" justify="center">
                        <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
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
import { consulting } from "@/api/consulting.js";
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
      banners: [
        { name: "banner_1.png" },
        { name: "banner_2.png" },
        { name: "banner_3.png" },
        { name: "banner_4.png" },
        { name: "banner_5.png" },
        { name: "banner_6.png" },
      ],
      hitBoardList: [],
      latestBoardList: [],
      imgBoardList: [],
      imgDetailList:[],
      imgSrcList:[],
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
        { text: '분류', align: 'start', value: 'category', width: '12%' },
        { text: '제목', align: 'start', value: 'title', width:'52%' },
        { text: '작성자', align: 'end', value: 'user.nickname', width: '12%' },
        { text: '작성 일자', align: 'end', value: 'dateTime', width: '12%' },
        { text: '조회수', align: 'end', value: 'hit', width: '12%' },
      ],
    };
  },
  created: function () {
    this.getLatestBoardList(),
    this.getHitBoardList(),
    this.getImgBoardList()
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

    this.getLatestBoardList();

  },
  methods: {
    replaceByDefault: defaultProfileSetter.replaceByDefault,
    getLatestBoardList() { // 최신 게시물
      this.$axios.get(API_BASE_URL+'/main/board/date').then(response => {
        this.latestBoardList = response.data;
        // 날짜 형식 바꾸기
        for (var i = 0; i < this.latestBoardList.length; i++) {
          this.latestBoardList[i].dateTime = this.latestBoardList[i].dateTime.substr(0, 10).replaceAll('-','.');
          if(this.latestBoardList[i].category === "Free") {
            this.latestBoardList[i].category = "자유"
          } else if(this.latestBoardList[i].category === "QnA") {
            this.latestBoardList[i].category = "질문"
          } else if(this.latestBoardList[i].category === "Info") {
            this.latestBoardList[i].category = "정보"
          }
        }
      }).catch(error => {
        console.log(error)
      })
    },
    getHitBoardList() { // 인기 게시물
      this.$axios.get(API_BASE_URL+'/main/board/hit').then(response => {
        this.hitBoardList = response.data;
        // 날짜 형식 바꾸기
        for (var i = 0; i < this.latestBoardList.length; i++) {
          this.hitBoardList[i].dateTime = this.hitBoardList[i].dateTime.substr(0, 10).replaceAll('-','.');
          if(this.hitBoardList[i].category === "Free") {
            this.hitBoardList[i].category = "자유"
          } else if(this.hitBoardList[i].category === "QnA") {
            this.hitBoardList[i].category = "질문"
          } else if(this.hitBoardList[i].category === "Info") {
            this.hitBoardList[i].category = "정보"
          }
        }
      }).catch(error => {
        console.log(error)
      })
    }, getImgBoardList() { // 이미지 게시물 (이미지만)
      this.$axios.get(API_BASE_URL+'/main/board/image').then(response => {
        this.imgBoardList = response.data;
        // 날짜 형식 바꾸기
        for (var i = 0; i < this.imgBoardList.length; i++) {
          this.imgBoardList[i].dateTime = this.imgBoardList[i].dateTime.substr(0, 10);
        }
        this.getImgDetail();
      }).catch(error => {
        console.log(error)
      })
    },
    getImgDetail() { // 이미지 게시물 디테일 가져오기
    console.log(this.imgBoardList.length);
      for(var j = 0; j < this.imgBoardList.length; j++){
        var k = j;
        this.$axios.get(API_BASE_URL + '/boards/' + this.imgBoardList[k].no).then(response => {
          this.imgSrcList.push('https://i7a802.p.ssafy.io/api/boards/file/' + response.data.content.board.fileNo);
        }).catch(error => {
          console.log(error)
        })
      }
    }
  },
};
</script>

<style scoped>
#view {
  background-color: white;
  padding: 0 5%;
  width:100%;
  text-align: left;
}

#topContent {
  height: 350px;
  width: 100%;
  padding-top: 2%;
  display: flex;
  align-items: center;
}

.carousel-3d-container template {
  margin: 0;
}

.carousel-3d-container .carousel-3d-slide .title {
  font-size: 22px;
}

.carousel-3d-slide:hover {
  filter: brightness(98%);
}

a {
  color: black !important;
  text-decoration: none;
}

.orangeCard {
  --c: #fff;
  /* the color */
  --b: 1px;
  /* border length*/
  --d: 10px;
  /* the cube depth */

  --_s: calc(var(--d) + var(--b));
  color: var(--c);
  border: solid #0000;
  border-width: var(--b) var(--b) var(--_s) var(--_s);
  background:
    conic-gradient(at left var(--d) bottom var(--d),
      #0000 90deg, rgb(255 255 255 /0.3) 0 225deg, rgb(255 255 255 /0.6) 0) border-box,
    conic-gradient(at left var(--_s) bottom var(--_s),
      #0000 90deg, var(--c) 0) 0 100%/calc(100% - var(--b)) calc(100% - var(--b)) border-box;
  transform: translate(calc(var(--d)/-1), var(--d));
  clip-path:
    polygon(var(--d) 0%,
      var(--d) 0%,
      100% 0%,
      100% calc(100% - var(--d)),
      100% calc(100% - var(--d)),
      var(--d) calc(100% - var(--d)));
  transition: 0.5s;
}

.orangeCard:hover {
  --c: #ff7451;
  transform: translate(0, 0);
  clip-path:
    polygon(0% var(--d),
      var(--d) 0%,
      100% 0%,
      100% calc(100% - var(--d)),
      calc(100% - var(--d)) 100%,
      0% 100%);
}

.blueCard {
  --c: #fff;
  /* the color */
  --b: 1px;
  /* border length*/
  --d: 10px;
  /* the cube depth */

  --_s: calc(var(--d) + var(--b));
  color: var(--c);
  border: solid #0000;
  border-width: var(--b) var(--b) var(--_s) var(--_s);
  background:
    conic-gradient(at left var(--d) bottom var(--d),
      #0000 90deg, rgb(255 255 255 /0.3) 0 225deg, rgb(255 255 255 /0.6) 0) border-box,
    conic-gradient(at left var(--_s) bottom var(--_s),
      #0000 90deg, var(--c) 0) 0 100%/calc(100% - var(--b)) calc(100% - var(--b)) border-box;
  transform: translate(calc(var(--d)/-1), var(--d));
  clip-path:
    polygon(var(--d) 0%,
      var(--d) 0%,
      100% 0%,
      100% calc(100% - var(--d)),
      100% calc(100% - var(--d)),
      var(--d) calc(100% - var(--d)));
  transition: 0.5s;
}

.blueCard:hover {
  --c: #0c0f66;
  transform: translate(0, 0);
  clip-path:
    polygon(0% var(--d),
      var(--d) 0%,
      100% 0%,
      100% calc(100% - var(--d)),
      calc(100% - var(--d)) 100%,
      0% 100%);
}

#intro {
  height: 95px;
  display: flex;
  padding: 0;
  justify-content: flex-start;
  pointer-events: none;
  font-size: 15px;
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
  top: 50%;
  left: 0;
  width: 100%;
  height: 100%;
  padding: 35px 10px;
  box-sizing: border-box;
  z-index: 1;
  line-height: 1em;
  color: #fff;
  font-size: 16px;
  opacity: 1;
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

.imgBoard:hover figcaption {
  transform: rotatex(0deg) translate(0, -50%);
  background-color: #000;
  opacity: 0.8;
  transition-delay: 0.35s;
}
</style>
