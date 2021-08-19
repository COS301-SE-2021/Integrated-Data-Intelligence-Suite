import React from 'react';
import renderer from 'react-test-renderer';
import LoginButton from "../pages/LoginPage/LoginButton";

it('renders correctly', () => {
    const tree = renderer
        .create(<LoginButton/>)
        .toJSON();
    expect(tree).toMatchSnapshot();
});