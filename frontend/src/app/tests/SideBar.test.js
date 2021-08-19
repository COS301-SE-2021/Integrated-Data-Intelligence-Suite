import React from 'react';
import renderer from 'react-test-renderer';
import SideBar from '../components/SideBar/SideBar';
import {BrowserRouter} from "react-router-dom";

it('renders correctly', () => {
    const tree = renderer
        .create(<BrowserRouter>

                <SideBar/>
            </BrowserRouter>
        )
        .toJSON();
    expect(tree).toMatchSnapshot();
});