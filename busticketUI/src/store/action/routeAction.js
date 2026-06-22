import axios from "axios";

export const searchRoutes = (source, destination, journeyDate) => {
  return async (dispatch) => {
    const response = await axios.get("http://localhost:8080/api/route/search", {
      params: {
        source,
        destination,
        journeyDate,
      },
    });

    console.log("Search API Response:", response.data);

    console.log("Length:", response.data.length);

    const action = {
      type: "SEARCH_ROUTES",
      payload: response.data,
    };

    dispatch(action);
  };
};
