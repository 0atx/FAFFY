<template>
	<video autoplay style="width: 98%; max-width: 540px; border-radius: 5px;" ref="video"/>
</template>

<script>
export default {
	name: 'OvVideo',

	props: {
		streamManager: Object,
	},

	mounted () {
		this.streamManager.addVideoElement(this.$el);
	},

  methods: {
    capture() {
      alert("캡쳐");
    }
  }
};
</script>

<style scoped>
.host-user {
  border: 4px solid #ff7451;
}
</style>
