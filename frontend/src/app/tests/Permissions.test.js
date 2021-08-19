import React from 'react';
import renderer from 'react-test-renderer';
import Permissions from "../pages/PermissionsPage/Permissions";

it('renders correctly', () => {
    const tree = renderer
        .create(<Permissions/>)
        .toJSON();
    expect(tree).toMatchSnapshot();
});