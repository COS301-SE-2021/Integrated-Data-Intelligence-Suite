import React from 'react';
import renderer from 'react-test-renderer';
import { unmountComponentAtNode } from 'react-dom';
import SearchBar from '../components/SearchBar/SearchBar';

let container = null;
beforeEach(() => {
  // setup a DOM element as a render target
  container = document.createElement('div');
  document.body.appendChild(container);
});

afterEach(() => {
  // cleanup on exiting
  unmountComponentAtNode(container);
  container.remove();
  container = null;
});

it('SearchBar renders correctly', () => {
  const tree = renderer
    .create(<SearchBar />)
    .toJSON();
  expect(tree).toMatchSnapshot();
});
