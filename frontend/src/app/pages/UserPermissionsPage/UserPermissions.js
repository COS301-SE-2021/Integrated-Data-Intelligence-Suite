import React, { useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import { CloseCircleTwoTone } from '@ant-design/icons';
import useGet from '../../functions/useGet';

const UserPermissions = () => {
  const { id } = useParams();
  const history = useHistory();
  const [permission, setPermission] = useState(null);
  const [submit, setSubmit] = useState(false);
  const [user, setUser] = useState(null);
  const [adminStatus, setAdmin] = useState(null);

  const {
    data: users,
    isPending,
    error,
  } = useGet(`/user/getUser/${id}`);

  const changeAdmin = (value) => {
    setAdmin(value);
  };

  const enableSubmit = (value) => {
    setPermission(value);
    setSubmit(true);
  };

  const submitChanges = (e) => {
    e.preventDefault();
    setSubmit(false);
    const requestBody = {
      username: user.username,
      admin: adminStatus,
      newPermission: permission
    };
    // console.log('userdata ', user.user);
    // console.log('body is ', requestBody);
    fetch(`${process.env.REACT_APP_BACKEND_HOST}/changeUser`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(requestBody),
    })
      .then(() => {
        // console.log('uploaded');
        history.go(-1);
      });
  };

  return (
      <div className="user-info">
          {isPending && <div>loading </div>}
          {error && <div>{error}</div>}
          {users && user === null && setUser(users.user[0])}
          {user && permission === null && setPermission(user.permission)}
          {user && adminStatus === null && setAdmin(user.admin)}
          {user && (
          <div className="form-container">
              <div className="form-header">
                  <h2>{user.firstName}</h2>
                  <CloseCircleTwoTone
                    className="back-button-form"
                    onClick={() => history.go(-1)}
                  />
              </div>

              <form onSubmit={submitChanges}>
                  <label>Username</label>
                  <br />
                  <input type="text" value={user.username} />
                  <br />
                  <label>
                      Admin status:
                      <input
                        type="checkbox"
                        checked={adminStatus}
                        onChange={(e) => changeAdmin(e.target.checked)}
                      />
                  </label>
                  <br />
                  <label>Permission</label>
                  <br />
                  <select
                    value={permission}
                    onChange={(e) => enableSubmit(e.target.value)}
                  >
                      <option value="VIEWING">VIEWING</option>
                      <option value="IMPORTING">IMPORTING</option>
                  </select>
                  <br />

                  {!submit && <button type="submit" disabled className="disabled">submit</button>}
                  {submit && <button type="submit" className="enabled">submit</button>}

              </form>
          </div>
      )}
      </div>
  );
};

export default UserPermissions;
