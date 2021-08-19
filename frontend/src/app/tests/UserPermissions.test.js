import React from 'react';
import renderer from 'react-test-renderer';
import UserPermissions from "../pages/UserPermissionsPage/UserPermissions";
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




jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useParams: () => ({
        id: '476a7747-42f0-4af3-8290-d855a2d7e055'
    })
}));

it('renders correctly', () => {
    const tree = renderer
        .create(<UserPermissions/>)
        .toJSON();
    expect(tree).toBeTruthy();
});