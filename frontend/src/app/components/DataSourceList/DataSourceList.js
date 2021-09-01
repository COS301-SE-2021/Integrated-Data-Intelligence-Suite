import React from 'react';
import {Divider} from "antd";
import {Link, Redirect} from "react-router-dom";


const DataSourceList = () => {
    // const { sources } = props;

    const sources = [
        {
            id: 1,
            title: 'source1',
            parameters: [
                { key: 'key1', value: 'value1' },
                { key: 'key2', value: 'value2' },
                { key: 'key3', value: 'value3' },
                { key: 'key4', value: 'value4' },
                { key: 'key5', value: 'value5' },
                { key: 'key6', value: 'value6' },
                { key: 'key7', value: 'value7' },
            ],
        },
        {
            id: 2,
            title: 'source2',
            parameters: [
                { key: 'key1', value: 'value1' },
                { key: 'key2', value: 'value2' },
                { key: 'key3', value: 'value3' },
                { key: 'key4', value: 'value4' },
                { key: 'key5', value: 'value5' },
                { key: 'key6', value: 'value6' },
                { key: 'key7', value: 'value7' },
            ],
        },
        {
            id: 3,
            title: 'source3',
            parameters: [
                { key: 'key1', value: 'value1' },
                { key: 'key2', value: 'value2' },
                { key: 'key3', value: 'value3' },
                { key: 'key4', value: 'value4' },
                { key: 'key5', value: 'value5' },
                { key: 'key6', value: 'value6' },
                { key: 'key7', value: 'value7' },
            ],
        },
    ];


    return (
        <div className={'source-list'}>
            <div className={'add-source'}>
                <Link to='/settings/source/new' className={'standard-filled'}>new Source</Link>
            </div>
            { sources.map((source) =>(
                <div>
                    <div className={'source-preview'} key={source.id}>
                        <p className={'source-title'}>{source.title}</p>
                        <div className={'button-div'}>
                            <button className={'standard'} type={'button'} >Edit</button>
                            <button className={'warning'} type={'button'}>Delete</button>
                        </div>
                    </div>
                    {/*<Divider/>*/}
                </div>
            ))}
        </div>
    );
};

export default DataSourceList;
