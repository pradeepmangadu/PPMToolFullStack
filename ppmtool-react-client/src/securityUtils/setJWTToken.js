import axios from "axios";

const setJWTToken = token => {
  if (token) {
    axios.defaults.headers.common["Authorization"] = token;
  } else {
    delete axios.delete.headers.common["Authorization"];
  }
};
export default setJWTToken;
