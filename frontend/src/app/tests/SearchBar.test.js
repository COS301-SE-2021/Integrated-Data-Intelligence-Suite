// ComponentName.test.js
// import React from 'react';
// import Enzyme, { shallow } from 'enzyme';
// import Adapter from 'enzyme-adapter-react-16';
// import SearchBar from '../components/SearchBar/SearchBar';
// describe("SearchBar", () => {
//   Enzyme.configure({ adapter: new Adapter() });
//   it("should render my component", () => {
//     const wrapper = shallow(<SearchBar />);
//   });
// });

import React from 'react';
import renderer from 'react-test-renderer';
import SearchBar from '../components/SearchBar/SearchBar';

it('renders correctly', () => {
    const tree = renderer
        .create(<SearchBar/>)
        .toJSON();
    expect(tree).toMatchSnapshot();
});