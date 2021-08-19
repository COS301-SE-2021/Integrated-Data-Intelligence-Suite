import React from 'react';
import renderer from 'react-test-renderer';
import LogoutPage from "../pages/LogoutPage/LogoutPage";

it('renders correctly', () => {
    const tree = renderer
        .create(<LogoutPage/>)
        .toJSON();
    expect(tree).toMatchSnapshot();
});