<template>
    <div><p style="color:white; font-size: 50px; margin-top:37%;margin-left:8%;">FAFFY</p></div>
</template>

<script>
export default {

}
</script>

<style scoped>
*, *::before, *::after {
  box-sizing: border-box;
}

html, body {
  height: 100%;
}

body {
  display: grid;
  align-items: center;
  justify-items: center;
  margin: 0;
  background-color: #000;
}

div {
  width: 300px;
  height: 300px;
  border-radius: 50%;
  font-weight: 600;
  margin-top: 15%;
  margin-left: 40%;
  text-shadow: /* White glow */ 0 0 7px #fff, 0 0 10px #fff, 0 0 21px #fff;
  box-shadow:
    0 0 20px #fff,
    -20px 0 80px #f0f,
    20px 0 80px #0ff,
    inset 0 0 50px #fff,
    inset 50px 0 80px #f0f,
    inset -50px 0 80px #0ff,
    inset 50px 0 300px #f0f,
    inset -50px 0 300px #0ff;
  animation: pulsate 6s linear infinite;
}

@keyframes pulsate {
  50% {
    box-shadow:
      0 0 20px #fff,
      20px 0 80px #f0f,
      -20px 0 80px #0ff,
      inset 0 0 50px #fff,
      inset -50px 0 80px #f0f,
      inset 50px 0 80px #0ff,
      inset -50px 0 300px #f0f,
      inset 50px 0 300px #0ff;
  }
}
</style>
