import React, { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';
import {FiUser, VscFilePdf} from 'react-icons/all';
import SideBar from '../../components/SideBar/SideBar';
import UserList from '../../components/UserList/UserList';
import useGet from '../../functions/useGet';
import { userState } from '../../assets/AtomStore/AtomStore';
import SimplePopup from '../../components/SimplePopup/SimplePopup';
import UserPermissions from '../UserPermissionsPage/UserPermissions';

const ManageUsersPage = () => {
    const localUser = useRecoilValue(userState);
    const [users, setUsers] = useState(null);

    const [currentUser, setCurrentUser] = useState(null);
    const [showUser, setShowUser] = useState(false);

    const { data, isPending, error } = useGet('/user/getAll');

    function handlePreview(id) {
        setCurrentUser(id);
    }

    function closePopup() {
        setCurrentUser(null);
        setShowUser(false);
    }

    useEffect(()=>{
        if (currentUser !== null) {
            setShowUser(true);
        }
    }, [currentUser]);

    function extractUsers(data) {
        // console.log(data);
        if (data && data.status.toLowerCase() === 'ok') {
            if (data.data.success) {
                setUsers(data.data.users);
                return true;
            }
        }

        const lst = [];
        lst.push(localUser);

        setUsers(lst);
        return true;
    }
    return (
        <>
            {error && users === null && extractUsers(null)}
            {data && users === null && extractUsers(data)}

            {
                showUser
                    ? (
                        <SimplePopup
                          closePopup={closePopup}
                          popupTitle="Manage User"
                        >
                            <UserPermissions userID={currentUser} />
                        </SimplePopup>
                    ) :
                    null
            }
            <div className="default-page-container">
                <SideBar currentPage="8" />
                <div className="reports-content-section">
                    <div className="content-page-title ">Users</div>
                    {
                        users &&
                        (
                            <div className="reports-content-grid">
                                { users.map((user) => (
                                    <div className="report-card" key={user.id}>
                                        <FiUser className="icon clickable pink-icon" style={{ fontSize: 36, color: '#E80057FF' }} onClick={()=>handlePreview(user.id)} />
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

                        )
                    }
                </div>
            </div>
        </>
    );
};

export default ManageUsersPage;
