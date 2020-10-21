import React from 'react';
import './OpponentDropdown.css';
import PlayerInput from '../../atoms/PlayerInput/PlayerInput';

//React component that takes in a function (setOpponent) as props
//and renders a dropdown menu with options for selectiong opponents ansd an PlayerInput
//components if the option "2 Player" is selected. Rendering of the PlayerInput component
//and dispatching the redux action is controlled by the internal function onSelectOpponent.
//If the "2 Player" is selected the action is passed as props to the PlayerInput component.

const OpponentDropdown = ({ setOpponent, isDisabled }) => {
  const onSelectOpponent = val => {
    if (val !== '2 Player') {
      setOpponent(val)
      document.getElementById("Player2Input").style.display = "none";
    } else {
      setOpponent("2 Player")
      document.getElementById("Player2Input").style.display = "block";
    }
  };

  return (
    <div>
      <select onChange={e => onSelectOpponent(e.target.value)} disabled={isDisabled}>
        <option value="Easy">Easy</option>
        <option value="Medium">Medium</option>
        <option value="Hard">Hard</option>
        <option value="2 Player">2 Player</option>
      </select>
      <div id="Player2Input">
        <p>Player 2 Name:</p>
        <PlayerInput onSubmit={setOpponent} placeholder="Player 2" disabled={isDisabled}/>
      </div>
    </div>
  );
};

export default OpponentDropdown;
