import React from 'react';
import renderer from 'react-test-renderer';
import '../../setupTests';
import MapCard from "../pages/ChartPage/components/MapCard/MapCard";
import Enzyme, {mount} from 'enzyme';
import {Map} from "react-leaflet";
import {Card} from "antd";
import Adapter from 'enzyme-adapter-react-16';

// it('MapCard renders correctly', () => {
//     const tree = renderer
//         .create(<MapCard>
//             <Map/>
//         </MapCard>  )
//         .toJSON();
// });


it('test map', () => {
    //  Enzyme.configure({adapter: new Adapter()});
    // const div = global.document.createElement('div');
    // global.document.body.appendChild(div);
    //
    // const wrapper = mount(<Map/>, {attachTo: div});

    const tree = renderer
        .create(<Card>

                <MapCard/>
            </Card>
        )
        .toJSON();
    expect(tree).toMatchSnapshot();
});