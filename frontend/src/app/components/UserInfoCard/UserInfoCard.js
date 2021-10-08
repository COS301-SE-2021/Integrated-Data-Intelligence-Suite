import React from 'react';
import { Card, Skeleton } from 'antd';
import { useRecoilValue } from 'recoil';
import ExitMenuDropDown from '../ExitMenuDropDown/ExitMenuDropDown';
import { userState } from '../../assets/AtomStore/AtomStore';

const { Meta } = Card;
function UserInfoCard() {
    const user = useRecoilValue(userState);
    return (
        <>
            { user
            && (
                <Card id="user_avatar_card">
                    <Skeleton loading={false} avatar active>

                        {/* The drop down menu that allows the user to log out or lock the app */}
                        <ExitMenuDropDown />

                        {/* The user Avatar Image */}
                        <Meta
                          id="meta_id"
                          className="user_meta_card"
                          title={user.username}
                        />
                    </Skeleton>
                </Card>
            )
            }
        </>
    );
}

export default UserInfoCard;
