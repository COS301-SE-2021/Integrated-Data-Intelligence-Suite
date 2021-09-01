import React, { useState } from 'react';
import { CloseCircleTwoTone } from '@ant-design/icons';
import { useHistory, useParams } from 'react-router-dom';

function getSource(id) {
    const re = /^[0-9]+((-)([0-9])+)*$/;
    if (re.exec(id)) {
        return {
            id,
            name: `source${id}`,
            method: 'POST',
            authType: 'Bearer',
            authorization: `token${id}`,
            url: 'https://twitter.com/',
            searchKey: 'query',
            parameters: [
                { parameter: 'lang', value: `EN ${id}`, errors: { parameter: '', value: '' } },
                { parameter: 'date', value: `2020-12-01 ${id}`, errors: { parameter: '', value: '' } },
            ],
        };
    }
        return {
            id: null,
            name: '',
            method: 'GET',
            url: 'https://twitter.com/',
            searchKey: 'q',
            authType: 'apiKey',
            authorization: 'token-value',
            parameters: [
                { parameter: 'lang', value: 'EN', errors: { parameter: '', value: '' } },
                { parameter: 'date', value: '2020-12-01', errors: { parameter: '', value: '' } },
            ],
        };
}

const AddDataSource = () => {
    const { id } = useParams();
    const history = useHistory();
    const [form, setForm] = useState(null);
    const [url, setUrl] = useState(null);
    const [name, setName] = useState(null);
    const [authType, setAuthType] = useState(null);
    const [token, setToken] = useState(null);
    const [queryKey, setQueryKey] = useState(null);
    const [method, setMethod] = useState(null);

    const dataSource = getSource(id);

    const prevIsValid = () => {
        if (form.length === 0) {
            return true;
        }

        const someEmpty = form.some((item)=>item.parameter === '' || item.value === '');

        if (someEmpty) {
            form.map((item, index)=>{
                const allPrev = [...form];
                if (form[index].parameter === '') {
                    allPrev[index].errors.parameter = 'parameter is required';
                }
               if (form[index].value === '') {
                    allPrev[index].errors.value = 'value is required';
                }
               setForm(allPrev);
               return item;
            });
        }

        return !someEmpty;
    };

    const handleAddParameter = (e, setForm) =>{
        e.preventDefault();
        const inputState = {
            parameter: '',
            value: '',

            errors: {
                parameter: null,
                value: null,
            },
        };

        if (prevIsValid()) {
            setForm((prev)=> [...prev, inputState]);
        }
    };

    const handleFieldChange = (index, event) => {
        event.preventDefault();
        event.persist();

        setForm((prev)=>prev.map((item, i)=>{
                if (i !== index) {
                    return item;
                }

                const newForm = { ...item, [event.target.name]: event.target.value };

                newForm.errors = {
                        ...item.errors,
                        [event.target.name]: event.target.value.length > 0 ? null : `${[event.target.name]} Is required`,
                    };
                return newForm;
        }));
    };

    const handleRemoveField = (index, event) => {
        event.preventDefault();
        // console.log("old form", form);
        setForm((prev)=>prev.filter((item)=> item !== prev[index]));
        // console.log("new form", form);
    };

    const changeAuthType = (value) =>{
        setToken('');
        setAuthType(value);
    };

    const handleSubmit = (event) =>{
        event.preventDefault();
        event.persist();
    };
    return (
        <div className="data-source">
            { dataSource && form === null && setForm(dataSource.parameters)}
            { dataSource && method === null && setMethod(dataSource.method)}
            { dataSource && name === null && setName(dataSource.name)}
            { dataSource && url === null && setUrl(dataSource.url) }
            { dataSource && queryKey === null && setQueryKey(dataSource.searchKey)}
            { dataSource && authType === null && setAuthType(dataSource.authType)}
            { dataSource && token === null && setToken(dataSource.authorization)}
            <form>
                { method && (
                    <div className="row">
                        <div className="col left">
                            <select
                              className="method select"
                              value={method}
                              onChange={(e) => setMethod(e.target.value)}
                            >
                                <option>GET</option>
                                <option>POST</option>
                                <option>HEAD</option>
                                <option>PUT</option>
                                <option>DELETE</option>
                                <option>CONNECT</option>
                                <option>OPTIONS</option>
                                <option>TRACE</option>
                                <option>PATCH</option>
                            </select>
                        </div>
                    </div>
                )}
                <div className="row">
                    { authType && (
                        <div className="col left">
                            <select
                              className="method select"
                              value={authType}
                              onChange={(e) => changeAuthType(e.target.value)}
                            >
                                <option value="None">None</option>
                                <option value="Bearer">Bearer</option>
                                <option value="apiKey">Api Key</option>
                            </select>
                        </div>
                    )}
                    { token !== null && (
                        <div className="right col">
                            <input
                              type="text"
                                // className={
                                //     item.errors.value ? 'form-control invalid' : 'form-control'
                                // }
                              name="token"
                              placeholder="token"
                              value={token}
                                // style={{ display: (auth.type === 'None' ? 'none' : 'block') }}
                              onChange={(e)=>setToken(e.target.value)}
                            />
                        </div>
                    )}
                </div>
                { name !== null && (
                    <div className="row">
                        <div className="col left disabled">
                            <input
                              type="text"
                              name="parameter"
                              placeholder="parameter"
                              value="Name"
                              disabled
                            />
                        </div>
                        <div className="right col">
                            <input
                              type="text"
                                // className={
                                //     item.errors.value ? 'form-control invalid' : 'form-control'
                                // }
                              name="name"
                              placeholder="Name"
                              value={name}
                              onChange={(e)=>setName(e.target.value)}
                            />
                        </div>
                    </div>
                )}
                { url !== null && (
                    <div className="row">
                        <div className="col left disabled">
                            <input
                              type="text"
                              name="parameter"
                              placeholder="parameter"
                              value="URL"
                              disabled
                            />
                        </div>
                        <div className="col right">
                            <input
                              type="text"
                                // className={
                                //     item.errors.value ? 'form-control invalid' : 'form-control'
                                // }
                              name="value"
                              placeholder="value"
                              value={url}
                              onChange={(e)=>setUrl(e.target.value)}
                            />
                        </div>
                    </div>
                )}
                { queryKey !== null && (
                    <div className="row">
                        <div className="col left disabled">
                            <input
                              type="text"
                              name="parameter"
                              placeholder="parameter"
                              value="Query Key"
                              disabled
                            />
                        </div>
                        <div className="col right">
                            <input
                              type="text"
                                // className={
                                //     item.errors.value ? 'form-control invalid' : 'form-control'
                                // }
                              name="value"
                              placeholder="value"
                              value={queryKey}
                              onChange={(e)=>setQueryKey(e.target.value)}
                            />
                        </div>
                    </div>
                )}
                { form
                    && form.map((item, index)=>(
                        <div className="row" key={`item-${index}`}>
                            <div className="col left">
                                <input
                                  type="text"
                                  className={
                                        item.errors.parameter ? 'form-control invalid' : 'form-control'
                                    }
                                  name="parameter"
                                  placeholder="parameter"
                                  value={item.parameter}
                                  onChange={(e)=>handleFieldChange(index, e)}
                                />
                                {item.errors.parameter && <div className="invalid feedback">{item.errors.parameter}</div>}
                            </div>
                            <div className="col right">
                                <input
                                  type="text"
                                  className={
                                        item.errors.value ? 'form-control invalid' : 'form-control'
                                    }
                                  name="value"
                                  placeholder="value"
                                  value={item.value}
                                  onChange={(e)=>handleFieldChange(index, e)}
                                />
                                {item.errors.value && <div className="invalid feedback">{item.errors.value}</div>}

                            </div>
                            <CloseCircleTwoTone twoToneColor="#FF0800" className="close-button" onClick={(e)=>handleRemoveField(index, e)} />
                        </div>
                    ))}
                <button className="btn btn-primary" onClick={(e)=>handleAddParameter(e, setForm)}>Add Parameter</button>
                <button className="btn submit btn-primary" onClick={(e)=>handleSubmit(e)}>submit</button>
            </form>
        </div>
    );
};

export default AddDataSource;
