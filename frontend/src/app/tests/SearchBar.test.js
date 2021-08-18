import React from 'react';
import renderer from 'react-test-renderer';
import SearchBar from '../components/SearchBar/SearchBar';

it('SearchBar renders correctly', () => {
    const tree = renderer
        .create(<SearchBar/>)
        .toJSON();
    expect(tree).toMatchSnapshot();
});