import { Avatar, Card, CardActions, CardContent, CardHeader, CardMedia, Divider, IconButton, Typography } from '@mui/material'
import React, { useState } from 'react'
import MoreVertIcon from '@mui/icons-material/MoreVert';
import { red } from '@mui/material/colors';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ShareIcon from '@mui/icons-material/Share';
import ChatBubbleIcon from '@mui/icons-material/ChatBubble';
import BookmarkBorderIcon from '@mui/icons-material/BookmarkBorder';
import BookmarkIcon from '@mui/icons-material/Bookmark';
import { useDispatch, useSelector } from 'react-redux';
import { createCommentAction, likePostAction } from '../../redux/Post/post.action';
import { store } from '../../redux/store';
import { isLikedByReqUser } from '../../utils/isLikedByReqUser';
const PostCard = ({ item }) => {
    const [showComments, setShowComments] = useState(false)
    const dispatch = useDispatch()
    const handleShowComments = () => setShowComments(!showComments);
    const {post, auth} = useSelector(store => store)
    const handleCreateComment = (content) => {
        const reqData = {
            post_id: item.post_id,
            data: {
                content
            }
        }
        dispatch(createCommentAction(reqData))
    }

    const handleLikePost = () =>{
        dispatch(likePostAction(item.post_id))
    }

    console.log("is like ", isLikedByReqUser(auth.user.user_id))
    return (
        <Card className=''>

            <CardHeader
                avatar={
                    <Avatar sx={{ bgcolor: red[500] }} aria-label="recipe" src={auth.user?.imgAvatar}>
                    </Avatar>
                }
                action={
                    <IconButton aria-label="settings">
                        <MoreVertIcon />
                    </IconButton>
                }
                title={item.user.firstName + " " + item.user.lastName}
                subheader={"@" + item.user.firstName.toLowerCase().replace(/\s/g, '') + "" + item.user.lastName.toLowerCase().replace(/\s/g, '')} />

            {/* <CardMedia
                component="img"
                height="100"
                image={item.image}
                alt="Paella dish" /> */}
                <img className='w-full max-h-[40rem] object-cover' src={item.image} alt=''/>

            <CardContent>
                <Typography variant="body2" color="text.secondary">
                    {item.caption}
                </Typography>
            </CardContent>

            <CardActions className='flex justify-between' disableSpacing>
                <div>
                    <IconButton onClick={handleLikePost}>
                        {isLikedByReqUser(auth.user.user_id, item) ? <FavoriteIcon /> : <FavoriteBorderIcon />}
                    </IconButton>

                    <IconButton onClick={handleShowComments}>
                        {<ChatBubbleIcon />}
                    </IconButton>

                    <IconButton>
                        {<ShareIcon />}
                    </IconButton>
                </div>
                <div>
                    <IconButton>
                        {true ? <BookmarkIcon /> : <BookmarkBorderIcon />}
                    </IconButton>
                </div>
            </CardActions>
            {showComments && <section>
                <div className='flex items-center space-x-5 mx-3 my-5'>
                    <Avatar sx={{}} />
                    <input onKeyPress={(e) => {
                        if (e.key == "Enter") {
                            handleCreateComment(e.target.value)
                            console.log("enter pressed ---" + e.target.value)
                        }
                    }} className="w-full outline-none bg-transparent border border-[#3b4054] 
                    rounded-full px-5 py-2" type="text"
                        placeholder='Write your comment...' />
                </div>
                <Divider />
                <div className='mx-3 space-y-2 my-5 text-xs'>
                    {item.comments?.map((comment) => <div className='flex items-center space-x-5'>
                        <Avatar sx={{ height: "2rem", width: "2rem", fontSize: ".8rem" }}>
                            {comment.user.firstName[0]}
                        </Avatar>
                        <p>{comment.content}</p>
                    </div>)}
                </div>
            </section>}
        </Card>
    )
}

export default PostCard