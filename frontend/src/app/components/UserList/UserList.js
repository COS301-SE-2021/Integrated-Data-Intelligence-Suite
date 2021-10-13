import React, {useEffect, useState} from 'react';
import { Link } from 'react-router-dom';
import { Popconfirm } from 'antd';
import { DeleteOutlined, EditTwoTone } from '@ant-design/icons';
import { ImShare, VscFilePdf } from 'react-icons/all';

const colors = {
    red: '#FF120A',
    blue: '#5773FA',
};

const iconSize = '30px';

const UserList = (props) => {
    const { users } = props;
    console.log(users);

    const [currentUser, setCurrentUser] = useState(null);
    const [showUser, setShowUser] = useState(false);

    function handlePreview(id) {
        setCurrentUser(id);
    }

    useEffect(()=>{
        if (currentUser !== null) {
           setShowUser(true);
        }
    }, [currentUser]);

    return (
        <div className="reports-content-grid">
            { users.map((user) => (
                <div className="report-card" key={user.id}>
                    <VscFilePdf className="icon clickable pink-icon" style={{ fontSize: 36, color: '#E80057FF' }} onClick={()=>handlePreview(user.id)} />
                    <div className="text-container">
                        <div className="report-title clickable" onClick={()=>handlePreview(user.id)}>{user.username}</div>
                        <div className="report-date clickable" onClick={()=>handlePreview(user.id)}>{user.permission}</div>
                    </div>
                    <div className="report-button-container">

                        {/* <DeleteOutlined */}
                        {/*    // onClick={()=>handlePreview(report.id, null, 'delete')} */}
                        {/*    style={{ */}
                        {/*        fontSize: iconSize, */}
                        {/*        color: colors.red, */}
                        {/*        marginTop: '0', */}
                        {/*        cursor: 'pointer', */}
                        {/*    }} */}
                        {/* /> */}
                    </div>
                </div>

              // <div>
              //     <div className="settings-list-item" key={`user-${user.id}`}>
              //         <p className="list-item-title">{user.firstName}</p>
              //         <div className="options-container">
              //             <p className="permission-text">{user.permission}</p>
              //             <Link className="standard button" to={`user/${user.id}`}><EditTwoTone twoToneColor={colors.blue} style={{ fontSize: iconSize, padding: '10px' }} /></Link>
              //         </div>
              //     </div>
              // </div>

      ))}
        </div>
  );
};

export default UserList;
