import React, { useState } from 'react';
import { CloseCircleTwoTone } from '@ant-design/icons';

const AddDataSource = () => {
    const [form, setForm] = useState([{ parameter: 'url', value: '' }]);
    const [method, setMethod] = useState('GET');

    const handleAddParameter = (e, setForm) =>{
        e.preventDefault();
        const inputState = {
            parameter: '',
            value: '',
        };
        setForm((prev) => [...prev, inputState]);
    };

    const handleFieldChange = (index, event) => {
        event.preventDefault();

        setForm((prev)=>{
            prev.map((item, i)=>{
                if (i !== index) {
                    return item;
                }

                return { ...item, [event.target.name]: e.target.value };
            });
        });
    };

    const handleRemoveField = (index, event) => {
      event.preventDefault();

      setForm((prev)=>prev.filter((item)=> item !== prev[index]));
    };

    const changeMethod = (e) =>{
        e.preventDefault();
        setMethod(e.target.value);
    };
    return (
        <div className="data-source">
            <form>
                { method && (
                <select
                  className="method select"
                  value={method}
                  onChange={(e) => changeMethod(e)}
                >
                    <option>"GET"</option>
                    <option>"POST"</option>
                    <option>"HEAD"</option>
                    <option>"PUT"</option>
                    <option>"DELETE"</option>
                    <option>"CONNECT"</option>
                    <option>"OPTIONS"</option>
                    <option>"TRACE"</option>
                    <option>"PATCH"</option>
                </select>
)}
                { (form
                    && form.map((item, index) => (
                        <div className="row" key={`item-${index}`}>
                            <div className="col left">
                                <input
                                  type="text"
                                  className="form-control"
                                  name="parameter"
                                  placeholder="parameter"
                                  value={item.parameter}
                                  onChange={(e)=>handleFieldChange(index, e)}
                                />
                            </div>
                            <div className="col right">
                                <input
                                  type="text"
                                  className="form-control"
                                  name="value"
                                  placeholder="value"
                                  value={item.value}
                                  onChange={(e)=>handleFieldChange(index, e)}
                                />
                            </div>
                            <CloseCircleTwoTone twoToneColor={"#FF0800"} className="close-button" onClick={(e)=>handleRemoveField(index, e)} />
                        </div>
                    ))
                )}

                <button className="btn btn-primary" onClick={(e)=>handleAddParameter(e, setForm)}>Add Parameter</button>
                <button className="btn submit btn-primary" onClick={(e)=>e.preventDefault()}>submit</button>
            </form>
        </div>
    );
};

export default AddDataSource;
