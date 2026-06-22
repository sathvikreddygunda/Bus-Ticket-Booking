import axios from "axios";

const getMyBusesApi = "http://localhost:8080/api/bus/my-buses";

export const getMyBuses = () => {
  return async (dispatch) => {
    const config = {
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    };

    const response = await axios.get(getMyBusesApi, config);

    dispatch({
      type: "GET_MY_BUSES",
      payload: response.data,
    });
  };
};
