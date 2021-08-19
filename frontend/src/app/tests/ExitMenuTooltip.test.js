import React from 'react';
import renderer from 'react-test-renderer';
import ExitMenuTooltip from "../components/ExitMenuTooltip/ExitMenuTooltip";
import {BrowserRouter} from "react-router-dom";

it('ExitMenuTooltip renders correctly', () => {
    const tree = renderer
        .create(
            <BrowserRouter>
                <ExitMenuTooltip/>
            </BrowserRouter>
        )
        .toJSON();
    expect(tree).toMatchSnapshot();
});