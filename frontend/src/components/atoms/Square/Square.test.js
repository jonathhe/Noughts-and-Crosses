import React from 'react';
import Enzyme, { shallow } from 'enzyme'
import Adapter from 'enzyme-adapter-react-16'
import Square from './Square'

Enzyme.configure({ adapter: new Adapter() })

function clickableSetup() {
    const props = {
        onClickSquare: jest.fn(),
        value: ''
    }
  
    const enzymeWrapper = shallow(<Square {...props} />)
  
    return {
      props,
      enzymeWrapper
    }
  }

  function unclickableSetup() {
    const props = {
        onClickSquare: jest.fn(),
        value:{
          selected: 'X'
        }
    }

    const jsdomAlert = window.alert;
    window.alert = () => {};
  
    const enzymeWrapper = shallow(<Square {...props} />)
  
    return {
      props,
      enzymeWrapper,
      jsdomAlert
    }
  }
  
  describe('components', () => {
    describe('Square', () => {
      it('should render self and subcomponents', () => {
        const { enzymeWrapper } = clickableSetup()
  
        expect(enzymeWrapper.find('div').hasClass('Square')).toBe(true)
  
  
        const button = enzymeWrapper.find('input').props()
        expect(button.value).toEqual('')
      })
  
      it('should call onClickSquare if value is equal to empty string', () => {
        const { enzymeWrapper, props } = clickableSetup()
        enzymeWrapper.find('input').simulate('click');
        expect(props.onClickSquare.mock.calls.length).toBe(1)
      })

      it('should not call onClickSquare if value is not equal to empty string', () => {
        const { enzymeWrapper, props, jsdomAlert } = unclickableSetup()
        enzymeWrapper.find('input').simulate('click');
        expect(jsdomAlert)
        expect(props.onClickSquare.mock.calls.length).toBe(0)
      })
    })
  })