import React from 'react';
import renderer from 'react-test-renderer';
import SettingsPage from "../pages/SettingsPage/SettingsPage";

it('renders correctly', () => {
    const tree = renderer
        .create(<SettingsPage/>)
        .toJSON();
    expect(tree).toMatchSnapshot();
});