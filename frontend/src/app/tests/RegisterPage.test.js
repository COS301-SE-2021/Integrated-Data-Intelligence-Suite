import React from 'react';
import renderer from 'react-test-renderer';
import '../Mocks/matchMedia.mock';
import RegisterPage from "../pages/RegisterPage/RegisterPage";
import {BrowserRouter} from "react-router-dom";

it('renders correctly', () => {
    const tree = renderer
        .create(
            <BrowserRouter>
                <RegisterPage/>
            </BrowserRouter>
        )
        .toJSON();
    expect(tree).toMatchSnapshot();
});