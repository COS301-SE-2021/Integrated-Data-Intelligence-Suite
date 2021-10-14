import React, { useEffect, useState } from 'react';
import { CloseCircleTwoTone } from '@ant-design/icons';
import { useHistory, useParams } from 'react-router-dom';
import { Button, message } from 'antd';

const getSource = (id, structure) => {
    const [data, setData] = useState(structure);
    const [isPending, setIsPending] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (id !== 'new') {
            const abortCont = new AbortController();
            const requestBody = {
                id,
            };

            fetch(`${process.env.REACT_APP_BACKEND_HOST}/getSourceById`,
                {
                    signal: abortCont.signal,
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(requestBody),
                })
                .then((res) => {
                    if (!res.ok) {
                        throw Error(res.error);
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
        }
            return { data, isPending, error };
    }, [id]);
    return { data, isPending, error };
};

const AddDataSource = ({ dataSource }) => {
    // const { id } = useParams();
    const history = useHistory();
    const [form, setForm] = useState(null);
    const [url, setUrl] = useState(null);
    const [name, setName] = useState(null);
    const [authType, setAuthType] = useState(null);
    const [token, setToken] = useState(null);
    const [queryKey, setQueryKey] = useState(null);
    const [method, setmethod] = useState(null);
    const [date, setDate] = useState(null);
    const [interactions, setInteractions] = useState(null);
    const [collections, setCollections] = useState(null);
    const [location, setLocation] = useState(null);

    const [submitLoading, setSubmitLoading] = useState(false);


    // const { data: dataSource, isPending, error } = getSource(id, structure);

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
            id: dataSource.id,
            name,
            method,
            url,
            searchKey: queryKey,
            authType,
            authorization: token,
            parameters: form,
            date,
            interactions,
            collections,
            location,
        };

        // console.log(newSource);
        let link = '';
        if (dataSource.id === null) {
            link = '/addNewApiSource';
        } else {
            link = '/updateAPI';
        }
        const abortCont = new AbortController();
        // console.log(`${process.env.REACT_APP_BACKEND_HOST}${link}`);
        fetch(`${process.env.REACT_APP_BACKEND_HOST}${link}`,
            {
                signal: abortCont.signal,
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(newSource),
            }).then((res) => {
            if (!res.ok) {
                throw Error(res.error);
            }
            return res.json();
        })
            .then((data) => {
                setSubmitLoading(false);
                // console.log('data is here', data);
                // console.log('structure looks like ', data.source);
                if (data.success) {
                    // history.goBack();
                    message.success(data.message);
                } else {
                    message.error(data.message);
                }
            })
            .catch((err) => {
                setSubmitLoading(false);

                if (err.name === 'AbortError') console.log('Fetch Aborted');
                else {
                    message.error(err.message);
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
        <>
            { dataSource && form === null && mapForm([dataSource.parameters])}
            { dataSource && method === null && setmethod(dataSource.method)}
            { dataSource && name === null && setName(dataSource.name)}
            { dataSource && url === null && setUrl(dataSource.url) }
            { dataSource && queryKey === null && setQueryKey(dataSource.searchKey)}
            { dataSource && authType === null && setAuthType(dataSource.authType)}
            { dataSource && token === null && setToken(dataSource.authorization)}
            { dataSource && date === null && setDate(dataSource.date)}
            { dataSource && interactions === null && setInteractions(dataSource.interactions)}
            { dataSource && collections === null && setCollections(dataSource.collections)}
            { dataSource && location === null && setLocation(dataSource.location)}
            <form className="edit source-form">
                { method && (
                    <div className="row">
                        <div className="col left">
                            <select
                              className="method select"
                              value={method}
                              onChange={(e) => setmethod(e.target.value)}
                            >
                                <option>GET</option>
                                <option>POST</option>
                                <option>HEAD</option>
                                <option>PUT</option>
                                <option>DELETE</option>
                                <option>CONNECT</option>
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
                { date !== null && (
                    <div className="row">
                        <div className="col left disabled">
                            <input
                              type="text"
                              name="Parameter"
                              placeholder="Parameter"
                              value="Date"
                              disabled
                            />
                        </div>
                        <div className="right col">
                            <input
                              type="text"
                                // className={
                                //     item.errors.value ? 'form-control invalid' : 'form-control'
                                // }
                              name="Date"
                              placeholder="Date"
                              value={date}
                              onChange={(e)=>setDate(e.target.value)}
                            />
                        </div>
                    </div>
                )}
                { interactions !== null && (
                    <div className="row">
                        <div className="col left disabled">
                            <input
                              type="text"
                              name="Parameter"
                              placeholder="Parameter"
                              value="interactions"
                              disabled
                            />
                        </div>
                        <div className="right col">
                            <input
                              type="text"
                                // className={
                                //     item.errors.value ? 'form-control invalid' : 'form-control'
                                // }
                              name="interactions"
                              placeholder="interactions"
                              value={interactions}
                              onChange={(e)=>setInteractions(e.target.value)}
                            />
                        </div>
                    </div>
                )}
                { collections !== null && (
                    <div className="row">
                        <div className="col left disabled">
                            <input
                              type="text"
                              name="Parameter"
                              placeholder="Parameter"
                              value="collections"
                              disabled
                            />
                        </div>
                        <div className="right col">
                            <input
                              type="text"
                                // className={
                                //     item.errors.value ? 'form-control invalid' : 'form-control'
                                // }
                              name="Collections"
                              placeholder="Collections"
                              value={collections}
                              onChange={(e)=>setCollections(e.target.value)}
                            />
                        </div>
                    </div>
                )}
                { location !== null && (
                    <div className="row">
                        <div className="col left disabled">
                            <input
                              type="text"
                              name="Parameter"
                              placeholder="Parameter"
                              value="Location"
                              disabled
                            />
                        </div>
                        <div className="right col">
                            <input
                              type="text"
                                // className={
                                //     item.errors.value ? 'form-control invalid' : 'form-control'
                                // }
                              name="Location"
                              placeholder="Location"
                              value={location}
                              onChange={(e)=>setLocation(e.target.value)}
                            />
                        </div>
                    </div>
                )}
                { form
                    && form.map((item, index)=>(
                        <div className="row with-icon" key={`item-${index}`}>
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
                <div className="button-container">
                    <Button className="btn btn-primary" onClick={(e)=>handleAddParameter(e, setForm)}>Add Parameter</Button>
                    <Button
                      className="btn submit"
                      type="primary"
                        loading={submitLoading}
                      onClick={(e)=>handleSubmit(e)}
                    >
                        Submit
                    </Button>
                </div>
            </form>
        </>
    );
};

export default AddDataSource;
