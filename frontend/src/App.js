import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { startGame, resetGame } from './state/actions';
import './App.css';
import Board from './components/molecules/Board/Board';
import PlayerSection from './components/molecules/PlayerSection/PlayerSection';


//Main react component responsible for rendering the whole app
//Takes in two actions (startGame, resetGame), and array of strings (messages) 
//and a string (error) from redux. The component uses react hooks to dispatch the 
//startGame action when the component mounts. The component also uses react hooks
//controll other components when an update occurs.
//The component renders the whole app including, Board and PlayerSection components.
//it also renders a reset button which when clicked dispatches the resetGame action.
//Lastly it renders a section for displaying messages and error, and maps the messages
//array to paragraphs.
const App = ({ startGame, messages, error, resetGame }) => {
  
  //Fetch game data when the component mounts on the client
  useEffect(() => {
    startGame();
  }, [startGame]);

  //Update scroll position to the bottom
  useEffect(()=>{
    document.getElementById("informationSection").scrollTop = document.getElementById("informationSection").scrollHeight;
  })

  return (
    <div className="AppWrapper">
      <div className="App">
        <Board />
        <PlayerSection />
        <input id="resetButton" type="button" value="Reset Game" onClick={() => resetGame()} />
      </div>
      <section id="informationSection">
        {messages.map((message, i) => <p key={'m'+i}>{message}</p>)}
        <p>{error}</p>
      </section>
    </div>
  );
};

const mapDispatchToProps = {
  startGame,
  resetGame
};

const mapStateToProps = state => ({
  messages: state.messages,
  error: state.error
});

//Connect the store to this component
export default connect(
  mapStateToProps,
  mapDispatchToProps
)(App);
