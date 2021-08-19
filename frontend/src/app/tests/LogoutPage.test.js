import React from 'react';
import renderer from 'react-test-renderer';
import LogoutPage from "../pages/LogoutPage/LogoutPage";
import {BrowserRouter} from "react-router-dom";

it('renders correctly', () => {
    const tree = renderer
        .create(<BrowserRouter>
                <LogoutPage/>
            </BrowserRouter>
        )
        .toJSON();
    expect(tree).toMatchSnapshot();
});