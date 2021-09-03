import React, { useState } from 'react';
import {
  Layout, Row, Col, Divider,
} from 'antd';
import { CloseCircleTwoTone } from '@ant-design/icons';
import { useHistory } from 'react-router-dom';
import DataSourceList from '../../components/DataSourceList/DataSourceList';
import Permissions from '../PermissionsPage/Permissions';
import Profile from '../ProfilePage/ProfilePage';
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
  const [component, setComponent] = useState('Permissions');
  const [user, setUser] = useState(getLocalUser());
  const history = useHistory();
  const colorOne = '#E8E8E9';

  return (
      <Layout className="bodyDiv">
          <div className="header white-background" />
          <Content id="settings-container" className="outer-container" style={{ margin: '0', minHeight: '100vh' }}>
              <Row className="row">
                  <Col flex="auto" style={{ opacity: 0 }}>.</Col>
                  <Col style={{ padding: '30px 10px' }} className="left-column" flex="160px">

                      { user && user.isAdmin && <div id="Permissions" className="option active" onClick={() => setComponent('Permissions')}>Permissions</div>}

                      <div id="Profile" className="option" onClick={() => setComponent('Profile')}>Profile</div>

                      <div id="Account" className="option" onClick={() => setComponent('Account')}>Account</div>

                      <div id="Data Sources" className="option" onClick={() => setComponent('Data Sources')}>Data Sources</div>
                  </Col>
                  <Col style={{ padding: '0 0px 30px 0', backgroundColor: colorOne }} className="right-column" flex="600px">
                      <div>
                          <div className="component-title">
                              {component}
                              <CloseCircleTwoTone className="back-button" onClick={() => history.go(-1)} />
                          </div>
                          { component === 'Permissions' && user && user.isAdmin && setActive(component) && <Permissions />}
                          { component === 'Profile' && setActive(component) && <Profile />}
                          { component === 'Account' && setActive(component) && <div><h1>Page not implemented</h1></div>}
                          { component === 'Data Sources' && setActive(component) && <DataSourceList /> }
                      </div>
                  </Col>
                  <Col flex="auto" style={{ backgroundColor: colorOne, color: colorOne }}>
                      <div>.</div>
                  </Col>
              </Row>
          </Content>
      </Layout>

  );
};
export default SettingsPage;
