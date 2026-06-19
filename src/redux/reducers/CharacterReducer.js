const initialState = {
  characters: [],
};

// recevies state and action
export const characterReducer = (state = initialState, action) => {
  // handle Action GET_ALL
  if (action.type === "GET_ALL") {
    return {
      ...state,
      characters: action.payload,
    };
  }
  return state;
};
