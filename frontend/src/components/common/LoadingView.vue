<template>
  <div style="margin:0 auto; margin-top: 16%;">
    <div style="margin:0 auto;">
      <div class="load1">
        <div class="load2">
          <div class="load3">
            <div class="load4">
              <div class="load5">
                <div class="load6">
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div style="margin:0 auto; margin-right: 65%" class="loading">
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style lang="scss" scoped>
$blue:#0c0f66;
$red:#ff7451;
$yellow:#f4c20d;
$green:#3cba54;
$background:#fff;

@mixin flex($flex,$center){
  display:$flex;
  justify-content:$center;
  align-items:$center;
  display:-webkit-$flex;
  display:-ms-$flex;
  display:$flex;
  -webkit-justify-content:$center;
  -ms-flex-pack:$center;
  justify-content:$center;
  -webkit-align-items:$center;
  -ms-flex-align:$center;
  align-items:$center;
}
@mixin shape($size){
  width:$size;
  height:$size;
}
@mixin border($style,$width,$border-top-color,$border-right-color,$border-bottom-color,$border-left-color){
  border-style:$style;
  border-width:$width;
  border-top-color:$border-top-color;
  border-right-color:$border-right-color;
  border-bottom-color:$border-bottom-color;
  border-left-color:$border-left-color;
}
body{
  background:$background;
  height:100vh;
  @include flex(flex,center);
  overflow:hidden;
}
.load1{
  @include shape(200px);
  @include border(solid,3px,$blue,$background,$background,$blue);
  border-radius:50%;
  @include flex(flex,center);
  animation:rotateClockwise 1.8s linear infinite;
}
.load2{
  @include shape(180px);
  @include border(solid,3px,$background,$red,$red,$background);
  border-radius:50%;
  @include flex(flex,center);
  animation: rotateCounterClockwise 1.2s linear infinite;
}
.load3{
  @include shape(160px);
  @include border(solid,3px,$yellow,$background,$background,$yellow);
  border-radius:50%;
  @include flex(flex,center);
  animation:rotateClockwise 1.4s linear infinite;
}
.load4{
  @include shape(140px);
  @include border(solid,3px,$background,$blue,$blue,$background);
  border-radius:50%;
  @include flex(flex,center);
  animation:rotateCounterClockwise 1.6s linear infinite;
}
.load5{
  @include shape(120px);
  @include border(solid,3px,$green,$background,$background,$green);
  border-radius:50%;
  @include flex(flex,center);
  animation:rotateClockwise 2s linear infinite;
}
.load6{
  @include shape(100px);
  @include border(solid,3px,$background,$red,$red,$background);
  border-radius:50%;
  animation:rotateCounterClockwise 2.3s linear infinite;
}
.loading::after {
  content: "Loading";
  font-weight: 600;
  position:fixed;
  font-size:2em;
  color:$blue;
  animation: type 2s linear infinite, colorChange 4s .5s infinite;
}

@keyframes rotateClockwise{
  0%{
    transform:rotate(0deg);
  }
  100%{
    transform:rotate(360deg);
  }
}
@keyframes rotateCounterClockwise{
  0%{
    transform:rotate(0deg);
  }
  100%{
    transform:rotate(-360deg);
  }
}

@keyframes colorChange{
  10%{
    color:$blue;
  }
  20%{
    color:$red;
  }
  40%{
    color:$yellow;
  }
  60%{
    color:$blue;
  }
  80%{
    color:$green;
  }
  90%{
    color:$red;
  }
  100%{
    color:$blue;
  }
}
@keyframes type {
  0% {
      content: "Loading";
  }
  33% {
      content: "Loading.";
  }
  66% {
      content: "Loading..";
  }
  100% {
      content: "Loading...";
  }
}
</style>>
