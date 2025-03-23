import { Avatar, Box, Button, Card, Tab, Tabs } from '@mui/material'
import React, { useEffect, useState } from 'react'
import EditIcon from '@mui/icons-material/Edit';
import PersonAddAltIcon from '@mui/icons-material/PersonAddAlt';
import PostCard from '../../components/Post/PostCard';
import UserReelCard from '../../components/Reels/UserReelCard';
import { useDispatch, useSelector } from 'react-redux';
import ProfileModal from './ProfileModal';
import { getUsersPostAction } from '../../redux/Post/post.action';

const tabs = [
    { value: "post", name: "Post" },
    { value: "reels", name: "Reels" },
    { value: "saved", name: "Saved" },
    { value: "repost", name: "Repost" }
];


const reels = []
const savedPost = []

const Profile = () => {
    const dispatch = useDispatch()

    const [open, setOpen] = useState(false)
    const handleOpenProfileModal = () => setOpen(true)
    const handleClose = () => setOpen(false)
    const [value, setValue] = React.useState('post');
    const { auth, post } = useSelector(store => store)

    const posts = post.posts
    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    useEffect(() => {
        const user_id = auth.user?.user_id;


        if (user_id) {
            dispatch(getUsersPostAction(user_id));
        }
    }, [dispatch, auth]);

    console.log("aaaa" + auth.user.firstName)
    return (
        <Card className='my-10 w-[70%]'>
            <div className='rounded-md'>
                <div className='h-[15rem]'>
                    <img
                        className='w-full h-full rounded-t-md'
                        src={auth.user?.imgBackground} 
                        alt='' />
                </div>
                <div className='px-5 flex justify-between items-start
                mt-5 h-[5rem]'>
                    <Avatar
                        className='transform -translate-y-24'
                        sx={{ width: "10rem", height: "10rem" }}
                        src={auth.user?.imgAvatar} />

                    {true ?
                        (<Button sx={{ borderRadius: "20px" }} variant='outlined' startIcon={<EditIcon />} onClick={handleOpenProfileModal}>Edit Profile</Button>)
                        :
                        (<Button variant='outlined' sx={{ borderRadius: "20px" }} startIcon={<PersonAddAltIcon />}>Follow</Button>)}
                </div>
                <div className='py-5 px-10'>
                    <div>
                        <h1 className='py-1 font-bold text-xl'>{auth.user?.firstName + " " + auth.user?.lastName}</h1>
                        <p>@{auth.user?.firstName?.toLowerCase() + "" + auth.user?.lastName?.toLowerCase().replace(/\s/g, '')}</p>
                    </div>
                    <div className='opacity-70 flex gap-2 items-center py-3'>
                        <span>{posts.length} posts</span>
                        <span>{auth.user.followers.length} followers</span>
                        <span>{auth.user.followings.length} followings</span>

                    </div>

                    <div>
                        <p className='flex justify-center font-semibold'>Beauty is only skin deep</p>
                    </div>
                </div>
                <section>
                    <Box sx={{ width: '100%', borderBottom: 1, borderColor: "divider" }}>
                        <Tabs
                            value={value}
                            onChange={handleChange}
                            aria-label="wrapped label tabs example"
                        >
                            {tabs.map((item) => <Tab value={item.value} label={item.name} />)}
                        </Tabs>
                    </Box>
                    <div className='flex justify-center'>
                        {value === "post"
                            ?
                            <div className='space-y-5 w-[70%] my-10'>
                                {posts.map((item) => <div className='border border-slate-100 rounded-md'><PostCard item={item} /></div>)}
                            </div>
                            : value === "reels" ?
                                <div className='flex flex-wrap justify-center gap-2 my-10'>
                                    {reels.map((item) => <UserReelCard />)}
                                </div>
                                : value === "saved" ?
                                    <div className='space-y-5 w-[70%] my-10'>
                                        {savedPost.map((item) => <div className='border border-slate-100 rounded-md'><PostCard /></div>)}
                                    </div>
                                    : <div className='flex justify-center items-center h-[200px]'> Repost</div>}
                    </div>
                </section>
            </div>
            <section>
                <ProfileModal open={open} handleClose={handleClose} />
            </section>
        </Card>
    )
}

export default Profile