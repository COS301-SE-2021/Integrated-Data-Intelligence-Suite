import { Redirect } from 'react-router-dom';
import React from 'react';
import { useSetRecoilState } from 'recoil';
import { userAtom } from '../../assets/userAtom';

const LogoutPage = () => {
  const setUser = useSetRecoilState(userAtom);
  setUser(null);
  return (
      <Redirect to="/login" />
  );
};

export default LogoutPage;
