import { render, screen } from '@testing-library/react';
// import '../Mocks/matchMedia.mock';
import '../app/Mocks/matchMedia.mock';
import App from './App';
import React from "react";
import {BrowserRouter} from "react-router-dom";

test('renders learn react link', () => {
  render(<BrowserRouter><App/></BrowserRouter>);
  const linkElement = screen.getByText('Login');
  expect(linkElement).toBeTruthy();
});