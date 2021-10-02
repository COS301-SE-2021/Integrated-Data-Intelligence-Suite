import { atom } from 'recoil';

const dataFromBackendAtom = atom({
    key: 'dataFromBackend',
    default: null,
});

const userAtom = atom({
    key: 'user',
    default: null,
});

export { userAtom, dataFromBackendAtom };
