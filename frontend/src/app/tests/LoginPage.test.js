import React from 'react';
import renderer from 'react-test-renderer';
import '../Mocks/matchMedia.mock';
import LoginPage from "../pages/LoginPage/LoginPage";
import {BrowserRouter} from "react-router-dom";

it('renders correctly', () => {
    const tree = renderer
        .create(<BrowserRouter>

                <LoginPage/>
            </BrowserRouter>
        )
        .toJSON();
    expect(tree).toMatchSnapshot();
});