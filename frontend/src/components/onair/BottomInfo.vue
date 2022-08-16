<template>
	<div id="bottomInfo">
    <v-card
      tile
      outlined
      style="padding:1%;"
    >
      <v-list-item>
        <v-list-item-content>
          <div style="display:flex; width: 100%; align-items:center">
            <div style="width:100%;">

              <!-- 방송 제목 -->
              <div style="display:flex; justify-content:space-between; align-items:center; padding-bottom:10px;">
                <v-list-item-title style="font-weight: 600; font-size:22px;" class="mt-1 mb-1">
                  {{consultingInfo.title}}
                </v-list-item-title>
                <v-list-item-subtitle style="font-size:16px; width:90px;">
                  <div style="width:90px; float:right;"><v-icon style="margin-bottom:1px;" color="#666" small> mdi-broadcast </v-icon> {{ time }}</div>  <!-- db 왓다갓다 할 바엔 그냥 여기서 카운트하는게 나을듯 -->
                </v-list-item-subtitle>
              </div>

              <div id="itemContent">
                <!-- 컨설턴트 이미지 -->
                <v-list-item-avatar
                  size="48"
                  style="display: table-cell; vertical-align: middle;"
                >
                  <img
                    :src="`${IMG_BASE_URL}/${consultingInfo.consultant_no}`"
                    @error="replaceByDefault"
                  />
                </v-list-item-avatar>
                <!-- 컨설턴트 닉네임 / 참여 인원 -->
                <div id="item">
                  <div style="display:flex; width:200px;">
                    <v-list-item-subtitle style="font-size:17px;">
                      {{consultingInfo.consultant}}<follow-button :user_no="consultingInfo.consultant_no"/>
                    </v-list-item-subtitle>

                  </div>
                  <!-- <div style="display:flex;  width:160px;">
                    <v-list-item-subtitle style="font-size: 13px;">
                      팔로잉 24
                    </v-list-item-subtitle>
                    <v-list-item-subtitle style="font-size: 13px;">
                      팔로워 13
                    </v-list-item-subtitle>
                  </div> -->
                </div>
              </div>

              <div style="padding-bottom:10px;">
                {{ consultingInfo.intro }}
              </div>

              <!-- 방송 카테고리 -->
              <v-chip-group>
                <v-chip
                  small
                  :ripple="false"
                  id="categoryChips"
                  v-for="category in consultingInfo.categories"
                  :key="category"
                  :category="category"
                >
                  {{ category }}
                </v-chip>
              </v-chip-group>
            </div>
          </div>
        </v-list-item-content>
      </v-list-item>
    </v-card>
  </div>
</template>

<script>
import defaultProfileSetter from "@/utils/defaultProfileSetter.js";
import { API_BASE_URL } from "@/config";
import { mapState } from "vuex";
import FollowButton from "@/components/user/FollowButton.vue";

const authStore = "authStore";
export default {
	name: 'BottomInfo',
  props:{
    consultingInfo:Object,
  },
  components: {
    FollowButton
  },
  data() {
    return {
      // 임의로 설정한 방송 관심 카테고리, 나중에 DB에서 받아온거로 대체 예정
      consultCategorys: [
        "캐주얼",
        "모던",
        "시크",
      ],
      time: "",

      IMG_BASE_URL: API_BASE_URL + "/users/profile/image",
    }
  },
  computed: {
    ...mapState(authStore, ["loginUser"]),
  },
  mounted() {
    setInterval(this.updateTime.bind(this), 1000);
  },
  methods: {

    replaceByDefault: defaultProfileSetter.replaceByDefault,

    updateTime() {
      //19:37:00방송 시작 : 2022-08-14T19:28:19
      var startDate = this.consultingInfo.startTime.replaceAll('-', '/').replace('T', ' ');
      var sd = new Date(startDate);
      var cd = new Date();

      var diffHour = Math.floor(((cd.getTime() - sd.getTime()) % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
      var diffMin = Math.floor(((cd.getTime() - sd.getTime()) % (1000 * 60 * 60)) / (1000 * 60));
      var diffSec = Math.floor(((cd.getTime() - sd.getTime()) % (1000 * 60)) / (1000));
      this.time = this.zeroPadding(diffHour, 2) + ':' + this.zeroPadding(diffMin, 2) + ":" + this.zeroPadding(diffSec, 2);
    },

    zeroPadding(num, digit) {
      var zero = '';
      for (var i = 0; i < digit; i++){
        zero += 0;
      }
      return (zero + num).slice(-digit);
    },
  }
};
</script>

<style scoped>
#bottomInfo {
  width:100%;
  background-color: palegreen;
}

#itemContent {
  display: table;
  padding-bottom:10px;
}

#item {
  display: table-cell;
  vertical-align: middle;
  padding: 10px;
  width: 200px !important;
}

img {
  object-fit: fill;
}

#categoryChips {
  background-color: #0c0f66;
  color: #fff;
  pointer-events: none;
}

</style>
