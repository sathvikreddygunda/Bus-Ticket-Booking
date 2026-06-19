import { createStore, applyMiddleware, combineReducers } from "redux";
import { thunk } from "redux-thunk";
import { characterReducer } from "./reducers/CharacterReducer";

const rootReducer = combineReducers({
  characterReducer,
});

export const store = createStore(rootReducer, applyMiddleware(thunk));
