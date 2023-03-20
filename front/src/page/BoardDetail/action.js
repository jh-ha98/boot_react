import axios from 'axios';

export const writeComment = (mutate, param) => {
  axios.post('/api/comment/write', param)
    .then(res => {
      alert(res.data.msg);
      mutate();
    })
    .catch((err) => {
      console.error(err);
      alert(err.response.data.msg);
    });
};