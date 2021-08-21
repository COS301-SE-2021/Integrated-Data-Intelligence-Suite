import React from 'react';
import renderer from 'react-test-renderer';
import UserInfoCard from "../components/UserInfoCard/UserInfoCard";
import {unmountComponentAtNode} from "react-dom";


let container = null;
beforeEach(() => {
    // setup a DOM element as a render target
    container = document.createElement("div");
    document.body.appendChild(container);
});

afterEach(() => {
    // cleanup on exiting
    unmountComponentAtNode(container);
    container.remove();
    container = null;
});


it('renders correctly', () => {
    const tree = renderer
        .create(<UserInfoCard/>)
        .toJSON();
    expect(tree).toMatchSnapshot();
});
