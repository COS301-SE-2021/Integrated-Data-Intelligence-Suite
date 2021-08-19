import React from 'react';
import renderer from 'react-test-renderer';
import RegisterPage from "../pages/RegisterPage/RegisterPage";

it('renders correctly', () => {
    const tree = renderer
        .create(<RegisterPage/>)
        .toJSON();
    expect(tree).toMatchSnapshot();
});