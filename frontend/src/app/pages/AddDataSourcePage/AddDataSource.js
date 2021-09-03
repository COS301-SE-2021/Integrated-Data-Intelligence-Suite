import React, { useEffect, useState } from 'react';
import { CloseCircleTwoTone } from '@ant-design/icons';
import { useHistory, useParams } from 'react-router-dom';
import {Button, message} from 'antd';

const getSource = (id, structure) => {
    const [data, setData] = useState(null);
    const [isPending, setIsPending] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const abortCont = new AbortController();
        const requestBody = {
            id,
        };

        fetch('http://localhost:9001/Import/getSourceById',
            {
                signal: abortCont.signal,
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(requestBody),
            })
            .then((res) => {
                if (!res.ok) {
                    throw Error(res.error());
                }
                return res.json();
            })
            .then((data) => {
                console.log('data is here', data);
                console.log('structure looks like ', data.source);
                if (data.success) {
                    setData(data.source);
                } else {
                    setData(structure);
                }
                setIsPending(false);
                setError(null);
            })
            .catch((err) => {
                if (err.name === 'AbortError') console.log('Fetch Aborted');
                else {
                    // console.log(err.message)
                    // setError(err.message);
                    setData(structure);
                    setError(null);
                    setIsPending(false);
                }
            });

        return () => abortCont.abort();
    }, [id]);
    return { data, isPending, error };
};

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
    const [submitLoading, setSubmitLoading] = useState(false);

    const structure = {
            id: null,
            name: '',
            method: 'GET',
            url: '',
            searchKey: '',
            authType: 'none',
            authorization: '',
            parameters: [],
        };
    const { data: dataSource, isPending, error } = getSource(id, structure);

    const prevIsValid = () => {
        if (form.length === 0) {
            return true;
        }

        const someEmpty = form.some((item)=>item.parameter === '' || item.value === '');

        // TODO display error

        // if (someEmpty) {
        //     form.map((item, index)=>{
        //         const allPrev = [...form];
        //         if (form[index].parameter === '') {
        //             allPrev[index].errors.parameter = 'parameter is required';
        //         }
        //        if (form[index].value === '') {
        //             allPrev[index].errors.value = 'value is required';
        //         }
        //        setForm(allPrev);
        //        return item;
        //     });
        // }

        return !someEmpty;
    };

    const handleAddParameter = (e, setForm) =>{
        e.preventDefault();
        const inputState = {
            parameter: '',
            value: '',
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
        if (value === dataSource.authType && token === '') {
            setToken(dataSource.authorization);
        } else if (value === 'none') {
            setToken('');
        }
        setAuthType(value);
    };

    const handleSubmit = (event) =>{
        event.preventDefault();
        event.persist();
        setSubmitLoading(true);

        const newSource = {
            id,
            name,
            method,
            url,
            searchKey: queryKey,
            authType,
            authorization: token,
            parameters: form,
        };

        console.log(newSource);
        let link = '';
        if (dataSource.id === null) {
            link = 'http://localhost:9001/Import/addApiSource';
        } else {
            link = 'http://localhost:9001/Import/updateAPI';
        }
        const abortCont = new AbortController();
        fetch(link,
            {
                signal: abortCont.signal,
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(newSource),
            }).then((res) => {
            if (!res.ok) {
                throw Error(res.error());
            }
            return res.json();
        })
            .then((data) => {
                setSubmitLoading(false);
                console.log('data is here', data);
                console.log('structure looks like ', data.source);
                if (data.success) {
                    // history.goBack();
                    message.success(data.message);
                } else {
                    message.error(data.message);
                }

            })
            .catch((err) => {
                if (err.name === 'AbortError') console.log('Fetch Aborted');
                else {
                    message.error(err.message)
                }
            });
};

    const mapForm = (obj1) =>{
        const params = [];
        // eslint-disable-next-line func-names
        obj1.forEach(function (item) {
            // eslint-disable-next-line guard-for-in,no-restricted-syntax
            for (const i in item) {
                params.push({
                    parameter: i,
                    value: item[i],
                });
            }
        });

        console.log('should be correct structure now', params);
        setForm(params);
    };
    return (
        <div className="data-source">
            { dataSource && form === null && mapForm([dataSource.parameters])}
            { dataSource && method === null && setMethod(dataSource.method)}
            { dataSource && name === null && setName(dataSource.name)}
            { dataSource && url === null && setUrl(dataSource.url) }
            { dataSource && queryKey === null && setQueryKey(dataSource.searchKey)}
            { dataSource && authType === null && setAuthType(dataSource.authType)}
            { dataSource && token === null && setToken(dataSource.authorization)}
            <form>
                <div className="row"><CloseCircleTwoTone className="back-button" onClick={() => history.go(-1)} /></div>
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
                                <option value="none">None</option>
                                <option value="bearer">Bearer</option>
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
                              name="Token"
                              placeholder="Token"
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
                              name="Parameter"
                              placeholder="Parameter"
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
                              name="Name"
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
                              name="Parameter"
                              placeholder="Parameter"
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
                              name="URL"
                              placeholder="URL"
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
                              placeholder="Query Key"
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
                                  // className={
                                  //       item.errors.parameter ? 'form-control invalid' : 'form-control'
                                  //   }
                                  name="parameter"
                                  placeholder="Parameter"
                                  value={item.parameter}
                                  onChange={(e)=>handleFieldChange(index, e)}
                                />
                                {/* {item.errors.parameter && <div className="invalid feedback">{item.errors.parameter}</div>} */}
                            </div>
                            <div className="col right">
                                <input
                                  type="text"
                                  // className={
                                  //       item.errors.value ? 'form-control invalid' : 'form-control'
                                  //   }
                                  name="value"
                                  placeholder="Value"
                                  value={item.value}
                                  onChange={(e)=>handleFieldChange(index, e)}
                                />
                                {/* {item.errors.value && <div className="invalid feedback">{item.errors.value}</div>} */}

                            </div>
                            <CloseCircleTwoTone twoToneColor="#FF0800" className="close-button" onClick={(e)=>handleRemoveField(index, e)} />
                        </div>
                    ))}
                <button className="btn btn-primary" onClick={(e)=>handleAddParameter(e, setForm)}>Add Parameter</button>
                <Button
                  className="btn submit btn-primary"
                  type="primary"
                  loading={submitLoading}
                  onClick={(e)=>handleSubmit(e)}
                >
                    Submit
                </Button>
            </form>
        </div>
    );
};

export default AddDataSource;
