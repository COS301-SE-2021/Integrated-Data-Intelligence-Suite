import { render, screen } from '@testing-library/react';
// import '../Mocks/matchMedia.mock';
import './Mocks/matchMedia.mock';
import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import App from './App';

test('renders learn react link', () => {
  render(<BrowserRouter><App /></BrowserRouter>);
  const linkElement = screen.getByText('Login');
  expect(linkElement).toBeTruthy();
});
