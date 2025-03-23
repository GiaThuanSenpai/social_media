import { Avatar, Card, CardHeader, IconButton } from '@mui/material'
import React from 'react'
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import { useSelector } from 'react-redux';

const UserChatCard = ({chat}) => {
    const { message, auth } = useSelector(store => store)

    return (
        <Card>
            <CardHeader
                avatar={
                    <Avatar sx={{
                        width: "3.5rem", height: "3.5rem",
                        fontSize: "1.5rem", bgcolor: "#191c29", color: "rgb(88,199,250)"
                    }}
                        src={auth.user.imgAvatar} />
                }
                action={<IconButton>
                    <MoreHorizIcon />
                </IconButton>}
                title={auth.user.user_id === chat.users[0].user_id?
                    chat.users[1].firstName +" " +chat.users[1].lastName:
                    chat.users[0].firstName +" " + chat.users[0].lastName
                }
                subheader={"New message"}
            >
            </CardHeader>
        </Card>
    )
}

export default UserChatCard