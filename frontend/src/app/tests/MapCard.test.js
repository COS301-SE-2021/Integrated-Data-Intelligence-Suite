import React from 'react';
import renderer from 'react-test-renderer';
import MapCard from "../pages/ChartPage/components/MapCard/MapCard";

it('MapCard renders correctly', () => {
    const tree = renderer
        .create(<MapCard/>)
        .toJSON();
    expect(tree).toMatchSnapshot();
});