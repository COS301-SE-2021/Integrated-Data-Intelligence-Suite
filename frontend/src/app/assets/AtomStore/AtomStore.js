import { atom } from 'recoil';

export const isShowingDeletePopupState = atom({
    key: 'isShowingDeletePopup',
    default: false,
});

export const isShowingAddTrainingDataPopupState = atom({
    key: 'isShowingAddTrainingDataPopup',
    default: false,
});

export const isShowingAddModelPopupState = atom({
    key: 'isShowingAddModelPopup',
    default: false,
});

export const isShowingSetDefaultModelPopupState = atom({
    key: 'isShowingSetDefaultPopup',
    default: false,
});

export const isShowingUploadCSVPopupState = atom({
    key: 'isShowingUploadCSVPopupState',
    default: false,
});

export const isShowingShareModelPopupState = atom({
    key: 'isShowingShareModelPopup',
    default: false,
});

export const isShowingModelCardLoaderState = atom({
    key: 'isShowingModelCardLoader',
    default: false,
});

// export const listOfDataModelsState = atom({
//     key: 'listOfDataModels',
//     default:
//         [{
//             modelID: 'm1111111111111111111111111111111111111111111111111111111111111111111111111111',
//             modelName: 'Itachi',
//             isModelDefault: false,
//         }, {
//             modelID: 'm2',
//             modelName: 'Sasuke',
//             isModelDefault: false,
//         }, {
//             modelID: 'm3',
//             modelName: 'Zabuza',
//             isModelDefault: true,
//         }, {
//             modelID: 'm4',
//             modelName: 'Shisui',
//             isModelDefault: false,
//         }],
// });

export const listOfDataModelsState = atom({
    key: 'listOfDataModels',
    default: null,
});

export const userState = atom({
    key: 'user',
    default: null,
});

export const userSelectedDefaultModelState = atom({
    key: 'userSelectedDefaultModel',
    default: null,
});

export const userSelectedDeleteModelState = atom({
    key: 'userSelectedDeleteModel',
    default: null,
});

export const userSelectedShareModelState = atom({
    key: 'userSelectedShareModel',
    default: null,
});

export const uploadedTrainingSetFileState = atom({
    key: 'uploadedTrainingSetFile',
    default: null,
});

export const uploadedCSVFileState = atom({
    key: 'uploadedCSVFileState',
    default: null,
});

export const dataFromBackendState = atom({
    key: 'dataFromBackend',
    default: null,
});

export const totalLikedState = atom({
    key: 'totalLikes',
    default: [],
});

export const mostProminentSentimentState = atom({
    key: 'mostProminentSentiment',
    default: null,
});

export const numberOfAnomaliesState = atom({
    key: 'numberOfAnomalies',
    default: [],
});

export const averageInteractionState = atom({
    key: 'averageInteraction',
    default: null,
});

export const engagementPerProvinceState = atom({
    key: 'engagementPerProvince',
    default: null,
});

export const wordCloudState = atom({
    key: 'wordCloud',
    default: null,
});

export const dominantWordsState = atom({
    key: 'dominantWords',
    default: null,
});

export const dataFrequencyState = atom({
    key: 'dataFrequency',
    default: null,
});

export const entitiesRelationshipsState = atom({
    key: 'entitiesRelationships',
    default: null,
});

export const patternsRelationshipsState = atom({
    key: 'patternsRelationships',
    default: null,
});

export const numberOfTrendsState = atom({
    key: 'numberOfTrends',
    default: [],
});

export const anomaliesState = atom({
    key: 'anomalies',
    default: null,
});

export const overallSentimentState = atom({
    key: 'overallSentiment',
    default: [],
});

export const reportState = atom({
    ket: 'searchReport',
    default: null,
});

export const mapDataState = atom({
    key: 'mapData',
    default: null,
});

export const currentPdfState = atom({
    key: 'currentPdf',
    default: null,
});

export const displayAnalyticsPdfState = atom({
    key: 'displayAnalyticsPdf',
    default: false,
});

export const backendDataState = atom({
    key: 'backendData',
    default: '',
});
