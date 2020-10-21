import AT from './actionTypes'

const initialState = () =>{
    return{
        board: [],
        isFirstPlayersTurn: true,
        player1: "Player 1",
        player2: "Easy",
        isGameOver: false,
        selectOpponentDisabled: false,
        messages: [],
        error:"",
    }
}

//Reducers responsible for setting the redux state based on which action that is dispatched
const reducer = (state = initialState(), action) => {
  switch (action.type) {

    case AT.START_GAME_RECIEVED:
      return state = {
        ...state,
        board: action.payload.board,
        isFirstPlayersTurn: action.payload.isFirstPlayersTurn,
        isGameOver: action.payload.isGameOver,
        messages: state.messages.concat(action.payload.message),
        player1: action.payload.player1,
        player2: action.payload.player2,
        selectOpponentDisabled: action.payload.selectOpponentDisabled,
        error: ""
      }

    case AT.START_GAME_FAILED:
        return state = {
          ...state,
          error: "Start game failed"
        }

    case AT.CLICK_SQUARE_RECIEVED:
      return state = {
        ...state,
        board: action.payload.board,
        isFirstPlayersTurn: action.payload.isFirstPlayersTurn,
        isGameOver: action.payload.isGameOver,
        messages: state.messages.concat(action.payload.message),
        player1: action.payload.player1,
        player2: action.payload.player2,
        selectOpponentDisabled: action.payload.selectOpponentDisabled,
        error: ""
      }

    case AT.CLICK_SQUARE_FAILED:
      return state = {
        ...state,
        error: "Square can't be clicked"
      }

    case AT.SET_NAME_RECIEVED:
    return state = {
      ...state,
      board: action.payload.board,
      isFirstPlayersTurn: action.payload.isFirstPlayersTurn,
      isGameOver: action.payload.isGameOver,
      messages: state.messages.concat(action.payload.message),
      player1: action.payload.player1,
      player2: action.payload.player2,
      error: ""
    }

    case AT.SET_NAME_FAILED:
    return state = {
      ...state,
      error: "Failed to set player name"
    }

    case AT.SELECT_OPPONENT_RECIEVED:
    return state = {
      ...state,
      board: action.payload.board,
      isFirstPlayersTurn: action.payload.isFirstPlayersTurn,
      isGameOver: action.payload.isGameOver,
      messages: state.messages.concat(action.payload.message),
      player1: action.payload.player1,
      player2: action.payload.player2,
      error: ""
    }

    case AT.SELECT_OPPONENT_FAILED:
    return state = {
      ...state,
      error: "Failed to select opponent"
    }
    default:
      return state;
  }
};

export default reducer;
