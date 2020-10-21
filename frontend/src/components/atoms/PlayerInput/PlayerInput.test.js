import React, { useState } from 'react';
import Enzyme, { shallow } from 'enzyme'
import Adapter from 'enzyme-adapter-react-16'
import PlayerInput from './PlayerInput'

Enzyme.configure({ adapter: new Adapter() })

function setup() {
    const props = {
        onSubmit: jest.fn(),
        placeholder: 'placeholder',
        setName: jest.fn()
    }
  
    const enzymeWrapper = shallow(<PlayerInput {...props} />)
  
    return {
      props,
      enzymeWrapper,
    }
  }

  
  describe('components', () => {
    describe('Square', () => {
      it('should render self and subcomponents', () => {
        const { enzymeWrapper } = setup()
  
        const playerInputProps = enzymeWrapper.find('input[type="text"]').props()
        expect(playerInputProps.placeholder).toEqual('placeholder')

        const playerInputButtonProps = enzymeWrapper.find('input[type="button"]').props()
        expect(playerInputButtonProps.value).toEqual('Change')
      })
    })
  })