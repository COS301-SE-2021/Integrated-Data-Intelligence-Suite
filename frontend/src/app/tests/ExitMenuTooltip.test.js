import React from 'react';
import renderer from 'react-test-renderer';
import ExitMenuTooltip from "../components/ExitMenuTooltip";

it('ExitMenuTooltip renders correctly', () => {
    const tree = renderer
        .create(<ExitMenuTooltip/>)
        .toJSON();
    expect(tree).toMatchSnapshot();
});