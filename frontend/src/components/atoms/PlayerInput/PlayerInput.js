import React, { useState } from 'react';
import './PlayerInput.css'

//React component that takes in a function (onSubmit) and a string (placeholder) as props
//and renders a input field and a button which when clicked triggers the onSubmit function
//with the input from the input field

const PlayerInput = ({ onSubmit, placeholder }) => {

  const [name, setName] = useState("");

  return (
    <div>
        <input type="text" placeholder={placeholder} onChange={(e) => setName(e.target.value)}/>
        <input value="Change" type="button" onClick={() => onSubmit(name)}/>
    </div>
  );
};



export default PlayerInput;
