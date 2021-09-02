import React from 'react';
import renderer from 'react-test-renderer';
import DetailsCard from '../pages/ChartPage/components/DetailsCard/DetailsCard' ;
import {unmountComponentAtNode} from "react-dom";
import ExitMenuDropDown from "../components/ExitMenuDropDown/ExitMenuDropDown";
import {act, render} from "@testing-library/react";
import {Dropdown} from "antd";
import ExitMenuTooltip from "../components/ExitMenuTooltip/ExitMenuTooltip";
import {BrowserRouter} from "react-router-dom";

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

/*********************************/
/***********Rendering*************/
/********************************/

it('Exit Menu DropDown renders correctly', () => {
    const tree = renderer
        .create(<ExitMenuDropDown/>)
        .toJSON();
    expect(tree).toMatchSnapshot();
});


it("Exit menu dropdown button show the correct icon", () => {
    const onChange = jest.fn();
    act(() => {
        render(<BrowserRouter><ExitMenuDropDown/></BrowserRouter>, container);
    });

    // get a hold of the button element and check that it has the correct children
    const button = document.querySelector("#exit_menu_button");
    expect(button.childNodes.length).toBe(1);
});



/*********************************/
/***********Events*************/
/********************************/
it("Drop down menu shows once exit menu button has been clicked", () => {
    const onChange = jest.fn();
    act(() => {
        render(<BrowserRouter><ExitMenuDropDown/></BrowserRouter>, container);
    });

    // get a hold of the button element
    const button = document.querySelector("#exit_menu_button");

    //Trigger the click event
    act(() => {
        button.dispatchEvent(new MouseEvent("click", {bubbles: true}));
    });

    //Check that the drop down menu has shown
    expect(button.classList.contains('ant-dropdown-open')).toBeTruthy();
});

it("Drop down menu CLOSES once exit menu button has been clicked", () => {
    const onChange = jest.fn();
    act(() => {
        render(<BrowserRouter><ExitMenuDropDown/></BrowserRouter>, container);
    });

    // get a hold of the button element
    const button = document.querySelector("#exit_menu_button");

    //Trigger the click event to open the dropdown
    act(() => {
        button.dispatchEvent(new MouseEvent("click", {bubbles: true}));//Opens the Menu
    });

    //Click it again to close it
    act(() => {
        button.dispatchEvent(new MouseEvent("click", {bubbles: true}));//Closes the menu
    });

    //Check that the drop down menu has closed
    expect(button.classList.contains('ant-dropdown-open')).toBeFalsy();
});

