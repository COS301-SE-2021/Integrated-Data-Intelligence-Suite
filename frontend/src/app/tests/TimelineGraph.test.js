import React from 'react';
import renderer from 'react-test-renderer';
import TimelineGraph from "../../app/pages/ChartPage/components/TimelineGraph/TimelineGraph";
import {BrowserRouter} from "react-router-dom";

it('renders correctly', () => {
    const tree = renderer
        .create(<BrowserRouter>
            <TimelineGraph/>
        </BrowserRouter>)
        .toJSON();
    expect(tree).toMatchSnapshot();
});