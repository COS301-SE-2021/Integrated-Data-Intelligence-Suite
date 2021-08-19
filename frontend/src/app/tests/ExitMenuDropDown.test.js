import React from 'react';
import renderer from 'react-test-renderer';
import DetailsCard from '../pages/ChartPage/components/DetailsCard/DetailsCard' ;
import {unmountComponentAtNode} from "react-dom";
import ExitMenuDropDown from "../components/ExitMenuDropDown/ExitMenuDropDown";
import {act, render} from "@testing-library/react";
import {Dropdown} from "antd";
import ExitMenuTooltip from "../components/ExitMenuTooltip/ExitMenuTooltip";

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


it('Exit Menu DropDown renders correctly', () => {
    const tree = renderer
        .create(<ExitMenuDropDown/>)
        .toJSON();
    expect(tree).toMatchSnapshot();
});


it("changes value when clicked", () => {
    const onChange = jest.fn();
    act(() => {
        render(<ExitMenuDropDown/>, container);
    });

    // get a hold of the button element and check that it has the correct children
    const button = document.querySelector("#exit_menu_button");
    expect(button.childNodes.length).toBe(1);
});