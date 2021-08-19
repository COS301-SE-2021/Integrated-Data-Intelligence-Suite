import React from 'react';
import renderer from 'react-test-renderer';
import UserPermissions from "../pages/UserPermissionsPage/UserPermissions";

it('renders correctly', () => {
    const tree = renderer
        .create(<UserPermissions/>)
        .toJSON();
    expect(tree).toMatchSnapshot();
});