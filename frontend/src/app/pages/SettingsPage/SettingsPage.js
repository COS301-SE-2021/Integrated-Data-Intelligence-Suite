import React, { useState } from 'react';
import {
  Layout, Row, Col, Divider,
} from 'antd';
import { CloseCircleTwoTone, CloseOutlined, LeftCircleTwoTone } from '@ant-design/icons';
import { useHistory } from 'react-router-dom';
import DataSourceList from '../../components/DataSourceList/DataSourceList';
import Users from '../PermissionsPage/Permissions';
import ProfilePage from '../ProfilePage/ProfilePage';
// import AddDataSource from '../AddDataSourcePage/AddDataSource';

const { Content } = Layout;

const setActive = (component) => {
  const options = document.getElementsByClassName('option');
  // console.log(options);
  for (let i = 0; i < options.length; i += 1) {
    // console.log(options[i].id);
    if (options[i].id === component) {
      options[i].className = 'option active';
    } else {
      options[i].className = 'option';
    }
  }
  return true;
};

function getLocalUser() {
  const localUser = localStorage.getItem('user');
  if (localUser) {
    // console.log("user logged in is ", localUser)
    return JSON.parse(localUser);
  }
  return null;
}

const SettingsPage = () => {
  const [component, setComponent] = useState('Users');
  const [user, setUser] = useState(getLocalUser());
  const history = useHistory();
  const colors = ['#E8E8E9', '#F1F2F8'];

  return (
      <Layout className="bodyDiv">
          <div className="header white-background" />
          <Content id="settings-container" className="outer-container" style={{ margin: '0', minHeight: '100vh' }}>
              <Row className="row">
                  <Col flex="auto" style={{ backgroundColor: colors[0], color: colors[0] }}>.</Col>
                  <Col style={{ padding: '30px 10px', backgroundColor: colors[0] }} className="left-column" flex="160px">

                      <div id="Profile" className="option" onClick={() => setComponent('Profile')}>Profile</div>

                      { user && user.isAdmin && <div id="Users" className="option active" onClick={() => setComponent('Users')}>Manage Users</div>}

                      { user && user.isAdmin && <div id="Data Sources" className="option" onClick={() => setComponent('Data Sources')}>Data Sources</div>}
                  </Col>
                  <Col style={{ padding: '0 0px 30px 0', backgroundColor: colors[1], maxWidth: '900px' }} className="right-column" flex="70%">
                      <div>
                          <div className="component-title-wrapper">
                              <div className="content-title">{component}</div>
                              <LeftCircleTwoTone twoToneColor="#5773FA" className="back-button" onClick={() => history.go(-1)} />
                          </div>
                          { component === 'Profile' && setActive(component) && <ProfilePage />}
                          { component === 'Users' && user && user.isAdmin && setActive(component) && <Users />}
                          { component === 'Data Sources' && user && user.isAdmin && setActive(component) && <DataSourceList /> }
                      </div>
                  </Col>
                  <Col flex="auto" style={{ backgroundColor: colors[1], color: colors[1] }}>
                      <div>.</div>
                  </Col>
              </Row>
          </Content>
      </Layout>

  );
};
export default SettingsPage;
