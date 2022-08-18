import img from "@/assets/images/default_profile.png";
const defaultProfileSetter = {
  replaceByDefault(e) {
    e.target.src = img;
  },
};

export default defaultProfileSetter;
