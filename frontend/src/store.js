import { createStore, applyMiddleware } from 'redux';
import { composeWithDevTools } from 'redux-devtools-extension';
import reducer from './state/reducer';
import thunk from 'redux-thunk';

//Redux store for state handling, with only one reducer for simplification
const store = createStore(
  reducer,
  composeWithDevTools(
  applyMiddleware(thunk)
  )
);

export default store;