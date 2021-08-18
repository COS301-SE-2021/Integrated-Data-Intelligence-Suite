import React from 'react';
import renderer from 'react-test-renderer';
import NetworkGraphCard from '../pages/ChartPage/components/NetworkGraph/NetworkGraphCard';

it('renders correctly', () => {
    const tree = renderer
        .create(<NetworkGraphCard/>)
        .toJSON();
    expect(tree).toMatchSnapshot();
});