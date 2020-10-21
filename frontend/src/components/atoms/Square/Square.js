import React from 'react';
import './Square.css'


//React component that takes in a function (onClickSquare), a string (value) and an int (index) as props
//and renders a button which when clicked triggers an internal function (doClick) which triggers the 
//onClickSquare function with input from the index if the value is not and empty string.
//Otherwise the function will trigger an allert.

const Square = ({onClickSquare, value, index}) => {

  const doClick = (value, index) =>{
    if(value !== ""){
      alert("The square is already clicked")
    }else{
      onClickSquare(index);
    }
  }
  
  return (
    <div className="Square">
        <input className="SquareInput" value={value.selected ? value.selected : "" } type="button" onClick={() => doClick(value.selected ? value.selected : "", index)}/>
    </div>
  );
}

export default Square;