import { atom } from 'recoil';

export const isShowingDeletePopupState = atom({
    key: 'isShowingDeletePopup',
    default: false
});

export const isShowingAddTrainingDataPopupState = atom({
    key: 'isShowingAddTrainingDataPopup',
    default: false
});

export const isShowingAddModelPopupState = atom({
    key: 'isShowingAddModelPopup',
    default: false
});

export const isShowingSetDefaultModelPopupState = atom({
    key: 'isShowingSetDefaultPopup',
    default: false
});

export const isShowingUploadCSVPopupState = atom({
    key: 'isShowingUploadCSVPopupState',
    default: false
});

export const isShowingShareModelPopupState = atom({
    key: 'isShowingShareModelPopup',
    default: false
});

export const listOfDataModelsState = atom({
    key: 'listOfDataModels',
    default:
        [{
            modelID: 'm1',
            modelName: 'Itachi',
            isModelDefault: false
        }, {
            modelID: 'm2',
            modelName: 'Sasuke',
            isModelDefault: false
        }, {
            modelID: 'm3',
            modelName: 'Zabuza',
            isModelDefault: true
        }, {
            modelID: 'm4',
            modelName: 'Shisui',
            isModelDefault: false
        }]
});

export const userSelectedDefaultModelState = atom({
    key: 'userSelectedDefaultModel',
    default: ''
});

export const userSelectedDeleteModelState = atom({
    key: 'userSelectedDeleteModel',
    default: ''
});

export const userSelectedShareModelState = atom({
    key: 'userSelectedShareModel',
    default: ''
});

export const uploadedTrainingSetFileState = atom({
    key: 'uploadedTrainingSetFile',
    default: null
});

export const uploadedCSVFileState = atom({
    key: 'uploadedCSVFileState',
    default: null
});



