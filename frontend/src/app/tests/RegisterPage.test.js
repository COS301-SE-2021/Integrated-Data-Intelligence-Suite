import React from 'react';
import renderer from 'react-test-renderer';
import '../Mocks/matchMedia.mock';
import RegisterPage from "../pages/RegisterPage/RegisterPage";
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
        .create(
            <BrowserRouter>
                <RegisterPage/>
            </BrowserRouter>
        )
        .toJSON();
    expect(tree).toMatchSnapshot();
});