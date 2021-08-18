import React from 'react';
import renderer from 'react-test-renderer';
import UserInfoCard from "../components/SideBar/UserInfoCard";

it('renders correctly', () => {
    const tree = renderer
        .create(<UserInfoCard/>)
        .toJSON();
    expect(tree).toMatchSnapshot();
});