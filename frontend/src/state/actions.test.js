import configureMockStore from 'redux-mock-store'
import thunk from 'redux-thunk'
import * as actions from './actions'
import AT from './actionTypes'
import fetchMock from 'fetch-mock'

const middlewares = [thunk]
const mockStore = configureMockStore(middlewares)

describe('async actions', () => {
  afterEach(() => {
    fetchMock.restore()
  })

  it('creates START_GAME_RECIEVED when the calls for starting game has been done', () => {
    fetchMock.getOnce('http://localhost:8080/game/clientBoard', { 
        body: {"board":[[{},{},{}],[{},{},{}],[{},{},{}]],"isFirstPlayerTurn":true,"isGameOver":false,"winner":null,"player1":"Mr Placeholder","player2":"Mr Easy"}
     })

    const expectedActions = [
      { type: AT.START_GAME_REQUESTED},
      { type: AT.START_GAME_RECIEVED, payload: {
            board:[[{},{},{}],[{},{},{}],[{},{},{}]], 
            isFirstPlayerTurn: true,
            isGameOver: false,
            winner: null,
            player1: "Mr Placeholder",
            player2: "Mr Easy", 
            message: "It's Mr Placeholder's turn",
            selectOpponentDisabled: false
        }
      }
]
    
    const store = mockStore({
        board: [],
        isFirstPlayersTurn: true,
        player1: "Player 1",
        player2: "Easy",
        isGameOver: false,
        messages: [],
        error:"",
    })

    return store.dispatch(actions.startGame()).then(() => {
      // return of async actions
      expect(store.getActions()).toEqual(expectedActions)
    })
  })
})


it('creates CLICK_SQUARE_RECIEVED when the calls for starting game has been done', () => {
    fetchMock.postOnce('http://localhost:8080/game/sendMove/0', { 
        body: {"board":[[{selected: "X"},{},{}],[{},{},{}],[{},{},{}]],"isFirstPlayerTurn":true,"isGameOver":false,"winner":null,"player1":"Mr Placeholder","player2":"Mr Easy"}
     })

    const expectedActions = [
      { type: AT.CLICK_SQUARE_REQUESTED},
      { type: AT.CLICK_SQUARE_RECIEVED, payload: {
            board:[[{selected: "X"},{},{}],[{},{},{}],[{},{},{}]], 
            isFirstPlayerTurn: true,
            isGameOver: false,
            winner: null,
            player1: "Mr Placeholder",
            player2: "Mr Easy", 
            message: "It's Mr Placeholder's turn",
            selectOpponentDisabled: true,
        }
      }
]
    
    const store = mockStore({
        board: [],
        isFirstPlayersTurn: true,
        player1: "Player 1",
        player2: "Easy",
        isGameOver: false,
        messages: [],
        error:"",
    })

    return store.dispatch(actions.clickSquare(0)).then(() => {
      // return of async actions
      expect(store.getActions()).toEqual(expectedActions)
    })
})
