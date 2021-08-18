import React from 'react';
import renderer from 'react-test-renderer';
import DetailsCard from '../pages/ChartPage/components/DetailsCard/DetailsCard' ;

it('DetailsCard renders correctly', () => {
    const tree = renderer
        .create(<DetailsCard/>)
        .toJSON();
    expect(tree).toMatchSnapshot();
});