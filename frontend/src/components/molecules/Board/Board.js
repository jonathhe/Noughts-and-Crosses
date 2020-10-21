import React from 'react';
import Square from '../../atoms/Square/Square';
import { connect } from 'react-redux';
import './Board.css'
import { clickSquare } from '../../../state/actions'

//React component that takes in an array (board) and a action (clickSquare) as props from
//redux and renders a list with listelements mapped from the board array as Square components.
//The square component is passed the porps value from the array, a generated key, a calculated index
//based on the array position and an action from redux called clickSquare as onClickSquare.

const Board = ({ board, clickSquare }) => {
  return (
    <ul className="Board">
      {board.map((row, i) => (
        <li key={"L"+i}>
          {row.map((square, j) => (
            <Square value={square} key={"S"+j} index={i*3+j} onClickSquare={clickSquare} />
          ))}
        </li>
      ))}
    </ul>
  );
};

const mapDispatchToProps = {
  clickSquare
}

const mapStateToProps = state => ({
  board: state.board
});

//Connect the store to this component
export default connect(mapStateToProps, mapDispatchToProps)(Board);
