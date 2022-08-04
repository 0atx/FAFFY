import axios from "axios";

const commentStore = {
  namespaced: true,
  state: {
    commentList: [],
  },
  getters: {
  },
  mutations: {
  },
  actions: {
    createComment(context, commentForm) {
      axios({
        url: 'http://localhost:8888/api/comments',
        method: 'post',
        headers: { "X-AUTH-TOKEN": sessionStorage.getItem('X-AUTH-TOKEN') },
        data: commentForm
      })
        .then(res => {
          console.log('성공', res)
        })
        .catch(err => {
          console.log('실패', err)
        })
    }
  },
};

export default commentStore;
