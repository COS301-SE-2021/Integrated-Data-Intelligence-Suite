import React from 'react';
import renderer from 'react-test-renderer';
import DetailsCard from '../pages/ChartPage/components/DetailsCard/DetailsCard' ;
import {unmountComponentAtNode} from "react-dom";
import ExitMenuDropDown from "../components/ExitMenuDropDown/ExitMenuDropDown";

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



it('DetailsCard renders correctly', () => {
    const tree = renderer
        .create(<ExitMenuDropDown/>)
        .toJSON();
    expect(tree).toMatchSnapshot();
});