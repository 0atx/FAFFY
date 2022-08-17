<template>
	<div id="topVideo" class="grey lighten-2 pt-8">
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
          <v-slide-item
            v-for="sub in subscribers2" :key="sub.stream.connection.connectionId"
            style="margin: 13px;"
            width="250"
          >
          <user-video :stream-manager="sub"/>
          </v-slide-item>
        </v-slide-group>
      </v-sheet>
    </div>
  </div>
</template>

<script>
import UserVideo from '@/components/video/UserVideo';
export default {
	name: 'TopVideo',
  components: {
    UserVideo,
  },
  data() {
    return {
      model: null,
      page: 1,
      itemsPerPage: 4,
    }
  },
  computed: {
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
  methods: {
  }
};
</script>

<style scoped>
#topVideo {
  width:100%;
  background-color: paleturquoise;
  text-align: center;
}

</style>
