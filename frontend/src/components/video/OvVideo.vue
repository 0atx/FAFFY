<template>
	<video autoplay style="width: 98%; max-width: 540px; border-radius: 5px;" />
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
};
</script>

<style scoped>
.host-user {
  border: 4px solid #ff7451;
}
</style>
