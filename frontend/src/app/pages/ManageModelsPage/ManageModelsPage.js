import React, { useEffect, useState } from 'react';
import { Route } from 'react-router-dom';
import Switch from 'react-bootstrap/Switch';
import { BsCloudUpload, IoCopyOutline, RiAddLine } from 'react-icons/all';
import SideBar from '../../components/SideBar/SideBar';
import './ManageModelsPage.css';
import SimpleCard from '../../components/SimpleCard/SimpleCard';
import ModelCard from '../../components/ModelCard/ModelCard';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import SimplePopup from '../../components/SimplePopup/SimplePopup';
import {
    isShowingDeletePopupState,
    isShowingAddTrainingDataPopupState,
    isShowingAddModelPopupState,
    listOfDataModelsState,
    isShowingSetDefaultModelPopupState,
    userSelectedDefaultModelState,
    userSelectedDeleteModelState,
    uploadedTrainingSetFileState,
    isShowingShareModelPopupState,
    userSelectedShareModelState,
    userState,
    isShowingModelCardLoaderState,
} from '../../assets/AtomStore/AtomStore';
import '../../components/SimpleButton/SimpleButton.css';
import CustomDivider from '../../components/CustomDivider/CustomDivider';
import UploadDropZone from '../../components/UploadDropZone/UploadDropZone';
import InputBoxWithLabel from '../../components/InputBoxWithLabel/InputBoxWithLabel';
import ModelCardLoader from '../../components/ModelCardLoader/ModelCardLoader';

const mock_add_obj = [
    {
        modelID: 'm1',
        modelName: 'Itachi',
        isModelDefault: false,
    },
    {
        modelID: 'm2',
        modelName: 'Sasuke',
        isModelDefault: false,
    },
    {
        modelID: 'm3',
        modelName: 'Zabuza',
        isModelDefault: true,
    },
    {
        modelID: 'm4',
        modelName: 'Shisui',
        isModelDefault: false,
    },
    {
        modelID: 'm5',
        modelName: 'Naruto',
    },
];
const mock_delete_obj = [
    {
        modelID: 'm1',
        modelName: 'Itachi',
        isModelDefault: true,
    },
    {
        modelID: 'm2',
        modelName: 'Sasuke',
        isModelDefault: false,
    },
    {
        modelID: 'm4',
        modelName: 'Shisui',
        isModelDefault: false,
    },
];
const mock_set_default_response_obj = [
    {
        modelID: 'm1',
        modelName: 'Itachi',
        isModelDefault: true,
    },
    {
        modelID: 'm2',
        modelName: 'Sasuke',
        isModelDefault: false,
    },
    {
        modelID: 'm3',
        modelName: 'Zabuza',
        isModelDefault: false,
    },
    {
        modelID: 'm4',
        modelName: 'Shisui',
        isModelDefault: false,
    },
];
const mock_upload_training_data_response_obj = [
    {
        modelID: 'm1',
        modelName: 'Itachi',
        isModelDefault: true,
    },
    {
        modelID: 'm2',
        modelName: 'Sasuke',
        isModelDefault: false,
    },
    {
        modelID: 'm3',
        modelName: 'Zabuza',
        isModelDefault: false,
    },
    {
        modelID: 'm4',
        modelName: 'Shisui',
        isModelDefault: false,
    },
    {
        modelID: 'm5',
        modelName: 'Hinata',
        isModelDefault: false,
    },
];

export default function ManageModelsPage() {
    function getLocalUser() {
        const localUser = localStorage.getItem('user');
        if (localUser) {
            // console.log('user logged in is ', localUser);
            return JSON.parse(localUser);
        }
        return null;
    }

    const localUser = getLocalUser();

    /*
    * DELETE MODEL
    */
    const [isShowingDeletePopup, toggleDeletePopup] = useRecoilState(isShowingDeletePopupState);
    const deleteDataModel = () => {
        // console.log(`User Chose this model as Default: ${userSelectedDeleteModel}`);

        /*
        - API_REQUEST_BODY: ID of data model that has been deleted to backend
        - API_RESPONSE_OBJ: updated list of data models
        */
        const url = '/deleteUserModelsByUser';
        const API_REQUEST_BODY = {
            modelID: userSelectedDeleteModel,
            userID: localUser.id,
        };
        const API_REQUEST_OBJ = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(API_REQUEST_BODY),
        };
        let API_RESPONSE_OBJ = null;
        setIsShowingModelCardLoader(true);
        updateListOfDataModels([{}]);
        fetch(`http://localhost:9000${url}`, API_REQUEST_OBJ)
            .then((response) => response.json())
            .then((json) => {
                API_RESPONSE_OBJ = json;
                updateListOfDataModels(API_RESPONSE_OBJ);
                // updateListOfDataModels(mock_delete_obj);
                setIsShowingModelCardLoader(false);
            })
            .catch((err) => {
                console.log('error while retrieving data from backend');
                console.log(err.message);
                setIsShowingModelCardLoader(false);
            });

        // Close the popup
        toggleDeletePopup(false);
    };
    const handleCloseDeletePopup = () => {
        toggleDeletePopup(false);
        setUserSelectedDeleteModel(null);
    };
    const deletePopupComponent = (
        <SimplePopup
            closePopup={() => handleCloseDeletePopup()}
            popupTitle="Delete Model"
            popupID="delete-model-popup"
            popupExtraClassNames="confirmationPopup"
        >
            <div id="delete-model-popup-msg">Are you sure you want to delete this modal?</div>
            <div id="delete-model-popup-btn-container">
                <button
                    id="delete-model-popup-btn-yes"
                    onClick={() => deleteDataModel()}
                >
                    Yes
                </button>
                <button
                    id="delete-model-popup-btn-no"
                    onClick={() => handleCloseDeletePopup()}
                >
                    No
                </button>
            </div>
        </SimplePopup>
    );

    /*
    * ADD MODEL
    */
    const [isShowingAddModelPopup, toggleAddModelPopup] = useRecoilState(isShowingAddModelPopupState);
    const handleAddModel = () => {
        // console.log(`Model Id Entered in Add: ${modelId}`);
        /*
        -  API_REQUEST_OBJ: new id model to add
        -  API_RESPONSE_OBJ: updated list of data models
         */
        const url = '/addUserModel';
        const API_REQUEST_BODY = {
            modelID: modelId,
            userID: localUser.id,
        };
        const API_REQUEST_OBJ = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(API_REQUEST_BODY),
        };
        let API_RESPONSE_OBJ = null;
        setIsShowingModelCardLoader(true);
        fetch(`http://localhost:9000${url}`, API_REQUEST_OBJ)
            .then((response) => response.json())
            .then((json) => {
                API_RESPONSE_OBJ = json;
                updateListOfDataModels(API_RESPONSE_OBJ);
                setIsShowingModelCardLoader(false);
            })
            .catch((err) => {
                // setIsShowingModelCardLoader(false);
                setIsShowingModelCardLoader(false);
                console.log('error while retrieving data from backend');
                console.log(err.message);
            });

        // close popup
        toggleAddModelPopup(true);
    };
    const addModelPopupComponent = (
        <SimplePopup
            closePopup={() => toggleAddModelPopup(false)}
            popupTitle="Add Data Model"
        >
            <div className="add-model-container">
                <div className="input-container">
                    <div className="label">ID</div>
                    <input
                        type="text"
                        id="modelIdInput"
                        placeholder="model id"
                        value={modelId}
                        onChange={(event) => setModelId(event.currentTarget.value)}
                    />
                </div>
                <div className="button-container">
                    <button
                        onClick={() => handleAddModel()}
                        className="simple-btn"
                        id="add-model-btn"
                    >
                        Add Model
                    </button>
                </div>
            </div>
        </SimplePopup>
    );

    /*
    * UPLOAD TRAINING DATA
    */
    const uploadedTrainingDataFileArrayObj = useRecoilValue(uploadedTrainingSetFileState);
    const [isShowingAddTrainingDataPopup, toggleAddTrainingDataPopup] = useRecoilState(isShowingAddTrainingDataPopupState);
    const handleUploadedTrainingData = () => {
        // console.log(`[Uploaded training data set]: ${uploadedTrainingDataFileArrayObj}`);
        // console.log(`[uploading training data set]: ${JSON.stringify(uploadedTrainingDataFileArrayObj)}`);

        // fetch values of input boxes
        const model_name = document.getElementById('input-training-model-name').value;
        const date = document.getElementById('input-training-date').value;
        const interaction = document.getElementById('input-training-interaction').value;
        const text = document.getElementById('input-training-text').value;
        const location = document.getElementById('input-training-location').value;
        const isTrending = document.getElementById('input-training-isTrending').value;

        /*
          - API_REQUEST_BODY: ID of data model that has been deleted to backend
          - API_RESPONSE_OBJ: updated list of data models
        */
        const url = '/trainUpload';
        const API_REQUEST_BODY_TRAIN = {
            file: uploadedTrainingDataFileArrayObj,
            c1: text,
            c2: location,
            c3: interaction,
            c4: date,
            c5: isTrending,
            modelName: model_name,
            userID: localUser.id,
        };
        console.log(API_REQUEST_BODY_TRAIN);

        const formData = new FormData();
        formData.append('file', new Blob(API_REQUEST_BODY_TRAIN.file), API_REQUEST_BODY_TRAIN.file[0].name);
        formData.append('c1', API_REQUEST_BODY_TRAIN.c1);
        formData.append('c2', API_REQUEST_BODY_TRAIN.c2);
        formData.append('c3', API_REQUEST_BODY_TRAIN.c3);
        formData.append('c4', API_REQUEST_BODY_TRAIN.c4);
        formData.append('c5', API_REQUEST_BODY_TRAIN.c5);
        formData.append('modelName', API_REQUEST_BODY_TRAIN.modelName);
        formData.append('user', API_REQUEST_BODY_TRAIN.user);

        const API_REQUEST_OBJ_TRAIN = {
            method: 'POST',
            body: formData,
        };

        let API_RESPONSE_OBJ = null;
        setIsShowingModelCardLoader(true);
        fetch(`http://localhost:9000${url}`, API_REQUEST_OBJ_TRAIN)
            .then((response) => response.json())
            .then((json) => {
                API_RESPONSE_OBJ = json;
                updateListOfDataModels(API_RESPONSE_OBJ);
                // updateListOfDataModels(mock_upload_training_data_response_obj);
                setIsShowingModelCardLoader(false);
            })
            .catch((err) => {
                console.log('error while retrieving data from backend');
                console.log(err.message);
                setIsShowingModelCardLoader(false);
            });
    };
    const addTrainingDataPopupComponent = (
        <SimplePopup
            closePopup={() => toggleAddTrainingDataPopup(false)}
            popupTitle="Upload Training Data"
        >
            <div id="upload-content-div">
                <CustomDivider DividerTitle="Upload your file"/>
                <UploadDropZone/>
                <CustomDivider DividerTitle="Match Columns"/>
                <div id="upload-training-data-form">
                    <InputBoxWithLabel
                        inputLabel="Model Name"
                        inputID="input-training-model-name"
                    />
                    <InputBoxWithLabel
                        inputLabel="Date"
                        inputID="input-training-date"
                    />
                    <InputBoxWithLabel
                        inputLabel="Interaction"
                        inputID="input-training-interaction"
                    />
                    <InputBoxWithLabel
                        inputLabel="Text"
                        inputID="input-training-text"
                    />
                    <InputBoxWithLabel
                        inputLabel="Location"
                        inputID="input-training-location"
                    />
                    <InputBoxWithLabel
                        inputLabel="isTrending"
                        inputID="input-training-isTrending"
                    />
                </div>

                <button
                    type="button"
                    id="upload-training-data-btn"
                    onClick={() => handleUploadedTrainingData()}
                    className="simple-btn"
                >
                    Upload Training Data
                </button>
            </div>
        </SimplePopup>
    );

    /*
    * SELECT DEFAULT MODEL
     */
    const [isShowingSetDefaultModelPopup, toggleSetDefaultModelPopup] = useRecoilState(isShowingSetDefaultModelPopupState);
    const setUserSelectedDefaultModel = useSetRecoilState(userSelectedDefaultModelState);
    const setNewDefaultDataModel = () => {
        // console.log(`User Chose this model as Default: ${userSelectedDefaultModel}`);

        /*
        - API_REQUEST_BODY: ID of data model that has been deleted to backend
        - API_RESPONSE_OBJ: updated list of data models
        */
        const url = '/selectModel';
        const API_REQUEST_BODY = {
            modelID: userSelectedDefaultModel,
            userID: localUser.id
        };
        const API_REQUEST_OBJ = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(API_REQUEST_BODY),
        };
        let API_RESPONSE_OBJ = null;
        setIsShowingModelCardLoader(true);
        fetch(`http://localhost:9000${url}`, API_REQUEST_OBJ)
            .then((response) => response.json())
            .then((json) => {
                API_RESPONSE_OBJ = json;
                updateListOfDataModels(API_RESPONSE_OBJ);
                setIsShowingModelCardLoader(false);
            })
            .catch((err) => {
                console.log('error while retrieving data from backend');
                console.log(err.message);
                setIsShowingModelCardLoader(false);
            });

        // updateListOfDataModels(mock_set_default_response_obj);

        // Close the popup
        toggleSetDefaultModelPopup(false);
    };
    const handleCloseSetDefaultPopup = () => {
        toggleSetDefaultModelPopup(false);
        setUserSelectedDefaultModel(null);
    };
    const setDefaultModelPopupComponent = (
        <SimplePopup
            closePopup={() => handleCloseSetDefaultPopup()}
            popupTitle="Set Default"
            popupID="delete-model-popup"
            popupExtraClassNames="confirmationPopup"
        >
            <div id="delete-model-popup-msg">
                Do you want to make this model your default data model?
            </div>
            <div id="delete-model-popup-btn-container">
                <button
                    id="delete-model-popup-btn-yes"
                    onClick={() => setNewDefaultDataModel()}
                >
                    Yes
                </button>
                <button
                    id="delete-model-popup-btn-no"
                    onClick={() => handleCloseSetDefaultPopup()}
                >
                    No
                </button>
            </div>
        </SimplePopup>
    );

    /*
    * SHARE MODEL
     */
    const [isShowingShareModelPopup, toggleShareModelPopup] = useRecoilState(isShowingShareModelPopupState);
    const [userSelectedShareModel, setUserSelectedShareModel] = useRecoilState(userSelectedShareModelState);
    const handleCloseShareModelPopup = () => {
        toggleShareModelPopup(false);
        setUserSelectedShareModel(null);
    };
    const shareModelPopupComponent = (
        <SimplePopup
            closePopup={() => handleCloseShareModelPopup()}
            popupTitle="Share Model"
        >
            <div id="share-model-container">
                <div id="share-model-id-icon">ID</div>
                <div
                    id="share-model-id-value"
                >
                    {userSelectedShareModel}
                </div>

                {isTextCopied
                    ? (
                        <button
                            id="share-model-copy-btn"
                            onClick={() => copyIDtoClipboard()}
                        >
                            Copied!
                            <IoCopyOutline id="share-model-copy-icon"/>
                        </button>
                    )
                    : (
                        <button
                            id="share-model-copy-btn"
                            onClick={() => copyIDtoClipboard()}
                        >
                            Copy
                            <IoCopyOutline id="share-model-copy-icon"/>
                        </button>
                    )}
            </div>
        </SimplePopup>
    );

    /*
    * Card Loader
    */
    const [isShowingModelCardLoader, setIsShowingModelCardLoader] = useRecoilState(isShowingModelCardLoaderState);
    const modelCardLoadingComponent = (
        <>
            <ModelCardLoader/>
            <ModelCardLoader/>
            <ModelCardLoader/>
            <ModelCardLoader/>
            <ModelCardLoader/>
        </>
    );

    /*
    * Models, List of Data Models
    */
    const [listOfDataModels, updateListOfDataModels] = useRecoilState(listOfDataModelsState);
    const [modelId, setModelId] = useState('');

    /*
    * User Selection
    */
    const userSelectedDefaultModel = useRecoilValue(userSelectedDefaultModelState);
    const [userSelectedDeleteModel, setUserSelectedDeleteModel] = useRecoilState(userSelectedDeleteModelState);

    /*
    * User and Clipboard
    */
    const userAtom = useRecoilValue(userState);
    const [isTextCopied, setIsTextCopied] = useState(false);
    const copyIDtoClipboard = () => {
        /* Get the text field */
        const copyText = document.getElementById('share-model-id-value').innerHTML;

        /* Copy the text inside the text field */
        navigator.clipboard.writeText(copyText)
            .then(setIsTextCopied(true))
            .then(setTimeout(() => {
                setIsTextCopied(false);
            }, 1300));
    };

    /*
    * GET ALL MODELS FROM BACKEND
    * */
    const response_from_use_post = null;
    const getAllModelsFromBackend = (url, body, header) => {
        const [data, setData] = useState(null);
        const [isPending, setIsPending] = useState(true);
        const [error, setError] = useState(null);

        useEffect(() => {
            const abortCont = new AbortController();
            updateListOfDataModels(null);
            setIsShowingModelCardLoader(true);
            fetch(`${process.env.REACT_APP_BACKEND_HOST}${url}`,
                {
                    signal: abortCont.signal,
                    method: 'POST',
                    headers: header,
                    body: JSON.stringify(body),
                })
                .then((res) => {
                    if (!res.ok) {
                        setIsShowingModelCardLoader(false);
                        throw Error(res.error);
                    }
                    return res.json();
                })
                .then((data) => {
                    console.log('manage models');
                    console.log(data);
                    setData(data);
                    setIsPending(false);
                    setError(null);
                    setIsShowingModelCardLoader(false);
                })
                .catch((error) => {
                    // setData(mock_add_obj);
                    if (error.name !== 'AbortError') {
                        setError(error.message);
                        setIsPending(false);
                    }
                    setIsShowingModelCardLoader(false);
                });
        }, [url]);
        return {
            data,
            isPending,
            error,
        };
    };

    const {
        data,
        isPending,
        error,
    } = getAllModelsFromBackend('/getAllModelsByUser', { userID: localUser.id }, { 'Content-Type': 'application/json' });

    return (
        <>
            <Switch>
                <Route exact path="/manageModels">
                    {
                        isShowingDeletePopup
                            ? deletePopupComponent
                            : null
                    }
                    {
                        isShowingAddTrainingDataPopup
                            ? addTrainingDataPopupComponent
                            : null
                    }
                    {
                        isShowingAddModelPopup
                            ? addModelPopupComponent
                            : null
                    }
                    {
                        isShowingSetDefaultModelPopup
                            ? setDefaultModelPopupComponent
                            : null
                    }
                    {
                        isShowingShareModelPopup
                            ? shareModelPopupComponent
                            : null
                    }
                    <div id="manage-models-page-container">
                        <SideBar currentPage="6"/>
                        <div id="manage-models-page-content">
                            <SimpleCard
                                cardID="manage-models-card"
                                cardTitle="Manage Your Data Models"
                                titleOnTop
                            >
                                <div id="manage-models-btn-row">
                                    <button
                                        className="simple-btn simple-btn-hover"
                                        onClick={() => toggleAddTrainingDataPopup(true)}
                                    >
                                        <BsCloudUpload
                                            className="simple-btn-icon simple-btn-hover"
                                        />
                                        Upload Training Data
                                    </button>

                                    <button
                                        className="simple-btn simple-btn-hover"
                                        onClick={() => toggleAddModelPopup(true)}
                                    >
                                        <RiAddLine className="simple-btn-icon simple-btn-hover"/>
                                        Add Model
                                    </button>
                                </div>

                                <div id="manage-models-card-row">
                                    {
                                        isShowingModelCardLoader
                                            ? modelCardLoadingComponent && updateListOfDataModels([{}])
                                            : null
                                    }
                                    {data && listOfDataModels === null && updateListOfDataModels(data) && (setIsShowingModelCardLoader(false))}
                                    {
                                        listOfDataModels !== null
                                        &&
                                        listOfDataModels.map((obj) => (
                                            <ModelCard
                                                modelID={obj.modelID}
                                                modelName={obj.modelName}
                                                isModelDefault={obj.isModelDefault}
                                                key={obj.modelID}
                                            />
                                        ))
                                    }
                                </div>
                            </SimpleCard>
                        </div>
                    </div>
                </Route>
            </Switch>
        </>
    );
}
