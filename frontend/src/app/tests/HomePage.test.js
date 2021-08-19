import React from 'react';
import renderer from 'react-test-renderer';
import HomePage from "../pages/HomePage/HomePage";
import {BrowserRouter} from "react-router-dom";
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
        .create(<BrowserRouter>

                <HomePage/>
            </BrowserRouter>
        )
        .toJSON();
    expect(tree).toMatchSnapshot();
});