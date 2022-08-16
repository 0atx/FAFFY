<template>
	<!-- <video autoplay style="width: 100%; max-width: 700px; max-height: 350px; object-fit: cover; border-radius: 5px;" ref="video"/> -->
	<video autoplay :width="width" :height="height" style="object-fit: cover; border-radius: 5px;" ref="video"/>

</template>

<script>
export default {
	name: 'OvVideo',

	props: {
		streamManager: Object,
    width:Number,
    height:Number,
	},

	mounted () {
		this.streamManager.addVideoElement(this.$el);
	},
};
</script>

<style scoped>
.host-user {
  border: 4px solid #ff7451;
}
</style>
