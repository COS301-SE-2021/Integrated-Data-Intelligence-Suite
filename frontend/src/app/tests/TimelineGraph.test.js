import React from 'react';
import renderer from 'react-test-renderer';
import TimelineGraph from "../components/ContentSection/TimelineGraph";

it('renders correctly', () => {
    const tree = renderer
        .create(<TimelineGraph/>)
        .toJSON();
    expect(tree).toMatchSnapshot();
});