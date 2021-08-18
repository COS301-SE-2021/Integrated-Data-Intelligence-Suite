import React from 'react';
import renderer from 'react-test-renderer';
import SideBar from '../components/SideBar/SideBar';

it('renders correctly', () => {
    const tree = renderer
        .create(<SideBar/>)
        .toJSON();
    expect(tree).toMatchSnapshot();
});