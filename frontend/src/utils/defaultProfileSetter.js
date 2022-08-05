import img from "@/assets/images/default_profile.png";
const defaultProfileSetter = {
  replaceByDefault(e) {
    console.log("기본이미지 세팅");
    e.target.src = img;
  },
};

export default defaultProfileSetter;
