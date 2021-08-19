import React from 'react';
import {Link} from "react-router-dom";


const UserList = (props) => {

    const users = props.users

    return (
        <div className={"user-list"}>
            { users.map((user) => (
                <Link to={`user/${user.id}`}>
                    <div className={"user-preview"} key={user.id} >
                            <div>
                                <h3>{user.firstName}</h3>
                                <p>
                                    <span>{user.username}</span>
                                    {user.isAdmin === true && <span className={"admin-tag"}> admin </span>}
                                </p>
                            </div>
                            <p>
                                {user.permission}
                            </p>
                    </div>
                </Link>

            ))
            }
        </div>
    );
};

export default UserList;
