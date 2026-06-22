const initialState = {
  routes: [],
};

export const routeReducer = (state = initialState, action) => {
  if (action.type === "SEARCH_ROUTES") {
    console.log("Payload:", action.payload);

    return {
      ...state,
      routes: action.payload,
    };
  }

  return state;
};
