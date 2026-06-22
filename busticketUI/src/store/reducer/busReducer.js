const initialState = {
  buses: [],
};

export const busReducer = (state = initialState, action) => {
  if (action.type === "GET_MY_BUSES") {
    return {
      ...state,
      buses: action.payload,
    };
  }

  return state;
};
