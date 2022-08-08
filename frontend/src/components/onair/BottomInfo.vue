<template>
	<div id="bottomInfo">
    여기에서 방송 및 컨설턴트 정보 출력해야함ㅎㅎ
    <v-card
            tile
            v-for="(consult, i) in consults"
            :key="consult.title"
            :class="i % 2 == 0 ? 'blueCard' : 'orangeCard'"
            style="height: 300px; width: 300px;"
            outlined
          >
            <!-- 방송 진행자 이미지 정보 -->
            <v-img
              height="200"
              :src="consult.src"
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
                <div style="font-size:15px;" id="introduce">{{ consult.intro }}</div>
                <div style="text-align:right;">
                  <!-- 참여 버튼. 클릭 시 화상회의 방 진입 -->
                  <v-btn
                    outlined
                    rounded
                    text
                  >
                    참여
                  </v-btn>
                </div>
              </v-list-item-content>
            </v-list-item>
          </v-card>
  </div>
</template>

<script>
export default {
	name: 'BottomInfo',
  components: {

  },
  data() {
    return {

    }
  },
  methods: {
  }
};
</script>

<style scoped>
#bottomInfo {
  width:100%;
  height:100%;
  background-color: palegreen;
}
</style>
