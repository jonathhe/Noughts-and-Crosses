import React from 'react';
import { connect } from 'react-redux';
import './PlayerSection.css'
import { selectOpponent, setPlayerName } from '../../../state/actions'
import PlayerInput from '../../atoms/PlayerInput/PlayerInput'
import OpponentDropdown from '../OpponentDropdown/OpponentDropdown'

//React component that takes in two actions (selectOpponent, setPlayerName) as props from redux
//and renders a section consisting og a PlayerInput component and a OpponentDropdown, which
//respectively ar passed setPlayerName and selectOpponent as props.

const PlayerSection = ({ selectOpponent, setPlayerName, isDisabled }) => {
  return (
    <div>
        <p>Player 1 Name:</p>
        <PlayerInput onSubmit={setPlayerName} placeholder="Player 1" />
        <p>Select Opponent:</p>
        <OpponentDropdown setOpponent={selectOpponent} isDisabled={ isDisabled }/>
    </div>
  );
};

const mapDispatchToProps = {
    selectOpponent,
    setPlayerName
}

const mapStateToProps = state => ({
  player: state.player,
  isDisabled: state.selectOpponentDisabled
});

//Connect the store to this component
export default connect(mapStateToProps, mapDispatchToProps)(PlayerSection);
