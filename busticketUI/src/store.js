import { configureStore } from "@reduxjs/toolkit";

import { busReducer } from "./store/reducer/busReducer";
import { routeReducer } from "./store/reducer/routeReducer";

export const store = configureStore({
  reducer: {
    buses: busReducer,
    routes: routeReducer,
  },
});
