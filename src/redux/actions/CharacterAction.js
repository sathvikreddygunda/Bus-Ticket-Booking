import axios from "axios";

// fn will be called from component
export const getAll = (page) => {
  return async (dispatch) => {
    // call api with page no
    const response = await axios.get(
      `https://rickandmortyapi.com/api/character/?page=${page}`,
    );

    // Action obj preparation
    let action = {
      type: "GET_ALL",
      payload: response.data.results,
    };

    // sending action obj => reducer
    dispatch(action);
  };
};
