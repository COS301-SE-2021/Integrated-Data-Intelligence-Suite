import React from 'react';
import renderer from 'react-test-renderer';
import RegisterButton from "../pages/RegisterPage/RegisterButton";

it('renders correctly', () => {
    const tree = renderer
        .create(<RegisterButton/>)
        .toJSON();
    expect(tree).toMatchSnapshot();
});