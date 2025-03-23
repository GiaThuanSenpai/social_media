import { Avatar, Card, IconButton } from '@mui/material'
import React, { useEffect, useState } from 'react'
import AddIcon from '@mui/icons-material/Add';
import StoryCircle from './StoryCircle';
import ImageIcon from '@mui/icons-material/Image';
import VideocamIcon from '@mui/icons-material/Videocam';
import ArticleIcon from '@mui/icons-material/Article';
import PostCard from '../Post/PostCard';
import CreatePostModal from '../CreatePost/CreatePostModal';
import { useDispatch, useSelector } from 'react-redux';
import { getAllPostAction } from '../../redux/Post/post.action';

const story = [11, 1, 1, 1, 1, 1]
const posts = [1, 1, 1, 1, 1]
const MiddlePart = () => {

    const dispatch = useDispatch()
    const {auth, post} = useSelector(store => store)
    const [openCreatePostModal, setOpenCreatePostModal] = useState(false)

    const handleCloseCreatePostModal = () => setOpenCreatePostModal(false)


    const handleOpenCreatePostModal = () => {
        setOpenCreatePostModal(true)

        console.log("open .....", openCreatePostModal)
    }

    useEffect(() => {
        dispatch(getAllPostAction())
    }, [post.newComment])
    return (
        <div className='px-20'>
            <section className='flex items-center p-5 rounded-b-md'>
                <div className='flex flex-col items-center mr-4 cursor-pointer'>
                    <Avatar
                        sx={{ width: "5rem", height: "5rem" }}
                    //src='https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTeNB8EktTK1tqEdVC_LNw7N4N9BILd98okMA&s'
                    >
                        <AddIcon sx={{ fontSize: "3rem" }} />

                    </Avatar >
                    <p>New</p>
                </div>
                {story.map((item) => <StoryCircle />)}
            </section>

            <Card className='p-5 mt-5'>
                <div className='flex justify-between'>
                    <Avatar src={auth.user?.imgAvatar} />
                    <input
                        onClick={handleOpenCreatePostModal}
                        placeholder='What are you thinking?'
                        className='outline-none w-[90%] rounded-full px-5  bg-transparent border-[#3b4054] border'
                        type='text' />
                </div>
                <div className='flex justify-center space-x-9 mt-5'>
                    <div className='flex items-center'>

                        <IconButton color="primary" onClick={handleOpenCreatePostModal}>
                            <ImageIcon />
                        </IconButton>
                        <span>Media</span>
                    </div>

                    <div className='flex items-center'>
                        <IconButton color="primary"
                            onClick={handleOpenCreatePostModal}>
                            <VideocamIcon />
                        </IconButton>
                        <span>Video</span>
                    </div>

                    <div className='flex items-center'>
                        <IconButton color="primary"
                            onClick={handleOpenCreatePostModal}>
                            <ArticleIcon />
                        </IconButton>
                        <span>Write Article</span>
                    </div>
                </div>
            </Card>
            <div className='mt-5 space-y-5'>
                {post.posts.map((item) => <PostCard  item = {item}/>)}
            </div>
            <div>
                <CreatePostModal handleClose={handleCloseCreatePostModal}
                    open={openCreatePostModal} />
            </div>
        </div>
    )
}

export default MiddlePart