import AT from './actionTypes'
import store from '../store'

const messageConstructor = (game, state) =>{
  if(!game.isGameOver && (state.isFirstPlayerTurn === game.isFirstPlayerTurn) && (state.board !== game.board)){
    return [game.player2 + " made a move", "It's " + game.player1 + "'s turn"]
  }else if(!game.isGameOver){
   return game.isFirstPlayerTurn ? "It's " + game.player1 + "'s turn" : "It's " + game.player2 + "'s turn"
  }else{
    return  game.winner ? "The game is over, " + game.winner + " won!" : "The game is over, it's a draw"
  }
}

//Action dispatched to start the game
//Does a async HTTP GET call to backend to retriewe the game data 
//which is passed as payload to the reducer
export const startGame = () => {


  return async function(dispatch) {
    dispatch({
      type: AT.START_GAME_REQUESTED,
    })

    try{
      const response = await fetch('http://localhost:8080/game/clientBoard', { method: 'GET' })
      const game = await response.json();
    
        if(game){
          const gamePayload = {
            ...game,
            message: "It's " + game.player1 + "'s turn",
            selectOpponentDisabled: false
          }
            dispatch({
                type: AT.START_GAME_RECIEVED,
                payload: gamePayload
              })
        }
    }catch(error){
         dispatch({
            type: AT.START_GAME_FAILED,
            payload: error
          })    
    }
  }
}


//Action dispatched to reset the game
//Does a async HTTP GET call to backend to retriewe the new game data 
//which is passed as payload to the reducer
export const resetGame = () => {
  return async function(dispatch) {
    dispatch({
      type: AT.START_GAME_REQUESTED,
    })

    try{
      const response = await fetch('http://localhost:8080/game/resetBoard', { method: 'GET' })
      const game = await response.json();
        if(game){
          const gameAndMessage={
            ...game,
            message: "Game was reset!",
            selectOpponentDisabled: false
          }
            dispatch({
                type: AT.START_GAME_RECIEVED,
                payload: gameAndMessage
              })
        }
    }catch(error){
         dispatch({
            type: AT.START_GAME_FAILED,
            payload: error
          })    
    }
  }
}


//Action dispatched to set the name of a player
//Takes in a name (string) and adds it to the uri
//Does a async HTTP POST call to backend to retriewe the new game data 
//which is passed as payload to the reducer
export const setPlayerName = (name) => {
  return async function(dispatch) {
    dispatch({
      type: AT.SET_NAME_REQUESTED,
    })


    const uri = 'http://localhost:8080/game/changeName/1/' + name;
    try{
      const response = await fetch(uri, { method: 'POST' })
      const game = await response.json();
        if(game){
          const gameAndMessage = {
            ...game,
            message: "Player 1 changed name to " + name
          }
            dispatch({
                type: AT.SET_NAME_RECIEVED,
                payload: gameAndMessage
              })
        }
    }catch(error){
         dispatch({
            type: AT.SET_NAME_FAILED,
            payload: error
          })    
    }
  }
}


//Action dispatched to set the name of the opponent
//Takes in a opponent (string) and adds it to the uri
//Does a async HTTP POST call to backend to retriewe the new game data 
//which is passed as payload to the reducer
export const selectOpponent = (opponent) => {
  return async function(dispatch) {
    dispatch({
      type: AT.SELECT_OPPONENT_REQUESTED,
    })

    const uri = 'http://localhost:8080/game/changeName/2/' + opponent;
    try{
      const response = await fetch(uri, { method: 'POST' })
      const game = await response.json();
        if(game){
          const gameAndMessage = {
            ...game,
            message: opponent !== "Player 2" ? "Player 2 changed to " + opponent : "Mode changed to 2-player"
          }
            dispatch({
                type: AT.SELECT_OPPONENT_RECIEVED,
                payload: gameAndMessage
              })
        }
    }catch(error){
         dispatch({
            type: AT.SELECT_OPPONENT_FAILED,
            payload: error
          })    
    }
  }
}


//Action dispatched to click a square
//Takes in a square (int) and adds it to the uri
//Does a async HTTP POST call to backend to retriewe the new game data 
//which is passed as payload to the reducer
export const clickSquare = (square) => {

  const state = store.getState()
  
  return async function(dispatch) {
    dispatch({
      type: AT.CLICK_SQUARE_REQUESTED,
    })
    const uri = 'http://localhost:8080/game/sendMove/' + square;

    try{
      const response = await fetch(uri, { method: 'POST' })
      const game = await response.json();
        if(game){
          const gamePayload = {
            ...game,
            message: messageConstructor(game, state),
            selectOpponentDisabled: true
          }
            dispatch({
                type: AT.CLICK_SQUARE_RECIEVED,
                payload: gamePayload
              })
        }
    }catch(error){
         dispatch({
            type: AT.CLICK_SQUARE_FAILED,
            payload: error
          })    
    }
  }
}
