import React from 'react';
import { Link } from 'react-router-dom';
import { Popconfirm } from 'antd';
import { EditTwoTone } from '@ant-design/icons';

const colors = {
    red: '#FF120A',
    blue: '#5773FA',
};

const iconSize = '20px';

const UserList = (props) => {
  const { users } = props;

  return (
      <div className="settings-component">
          { users.map((user) => (
              <div>
                  <div className="settings-list-item" key={`user-${user.id}`}>
                      <p className="list-item-title">{user.firstName}</p>
                      <div className="options-container">
                          <p className="permission-text">{user.permission}</p>
                          <Link className="standard button" to={`user/${user.id}`}><EditTwoTone twoToneColor={colors.blue} style={{ fontSize: iconSize, padding: '10px' }} /></Link>
                      </div>
                  </div>
              </div>

      ))}
      </div>
  );
};

export default UserList;
