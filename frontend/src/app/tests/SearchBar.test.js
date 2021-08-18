// ComponentName.test.js
import React from 'react';
import { shallow } from 'enzyme';
import SearchBar from '../components/SearchBar/SearchBar';
describe("SearchBar", () => {
  it("should render my component", () => {
    const wrapper = shallow(<SearchBar />);
  });
});