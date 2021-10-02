import { atom, useRecoilState } from 'recoil';

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



