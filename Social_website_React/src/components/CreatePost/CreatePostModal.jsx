import { Avatar, Backdrop, Box, Button, CircularProgress, IconButton, Modal } from '@mui/material';
import { useFormik } from 'formik';
import ImageIcon from '@mui/icons-material/Image';
import VideoCallIcon from '@mui/icons-material/VideoCall';
import React, { useState } from 'react';
import { uploadToCloudinary } from '../../utils/uploadToCloudinary';
import { useDispatch, useSelector } from 'react-redux';
import { createPostAction } from '../../redux/Post/post.action';

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
    borderRadius: ".6rem",
    outline: "none"
};

const CreatePostModal = ({ handleClose, open }) => {
    const [selectedImage, setSelectedImage] = useState();
    const [selectedVideo, setSelectedVideo] = useState();
    const [isLoading, setIsLoading] = useState(false);
    const { auth } = useSelector(store => store);
    const dispatch = useDispatch();

    const handleSelectImage = async (event) => {
        setIsLoading(true);
        const imageUrl = await uploadToCloudinary(event.target.files[0], "image");
        setSelectedImage(imageUrl);
        setIsLoading(false);
        formik.setFieldValue("image", imageUrl);
    };

    const handleSelectVideo = async (event) => {
        setIsLoading(true);
        const videoUrl = await uploadToCloudinary(event.target.files[0], "video");
        setSelectedVideo(videoUrl);
        setIsLoading(false);
        formik.setFieldValue("video", videoUrl);
    };

    const formik = useFormik({
        initialValues: {
            caption: "",
            image: "",
            video: ""
        },
        onSubmit: async (values) => {
            setIsLoading(true);
            await dispatch(createPostAction(values));
            setIsLoading(false);
            handleClose(); // Đóng modal sau khi gửi thành công
        }
    });

    return (
        <Modal
            open={open}
            onClose={handleClose}
            aria-labelledby="modal-modal-title"
            aria-describedby="modal-modal-description"
        >
            <Box sx={style}>
                <form onSubmit={formik.handleSubmit}>
                    <div>
                        <div className='flex space-x-4 items-center'>
                            <Avatar src={auth.user?.imgAvatar}/>
                            <div>
                                <p className='font-bold text-lg'>{auth.user?.firstName + " " + auth.user?.lastName}</p>
                                <p className='text-sm'>@{auth.user?.firstName?.toLowerCase().replace(/\s/g, '') + "" + auth.user?.lastName?.toLowerCase().replace(/\s/g, '')}</p>
                            </div>
                        </div>
                        <textarea
                            className='outline-none w-full mt-5 p-2 
                                bg-transparent border border-[#3b4054] rounded-sm'
                            placeholder="Write caption..."
                            value={formik.values.caption}
                            onChange={formik.handleChange} name="caption" id="" rows="4"></textarea>

                        <div className='flex space-x-5 items-center mt-5'>
                            <div>
                                <input type="file" accept='image/*'
                                    onChange={handleSelectImage}
                                    style={{ display: "none" }}
                                    id="image-input" />
                                <label htmlFor='image-input'>
                                    <IconButton color="primary" component="span">
                                        <ImageIcon />
                                    </IconButton>
                                </label>
                                <span>Image</span>
                            </div>
                            <div>
                                <input type="file" accept='video/*'
                                    onChange={handleSelectVideo}
                                    style={{ display: "none" }}
                                    id="video-input" />
                                <label htmlFor='video-input'>
                                    <IconButton>
                                        <VideoCallIcon color="primary" />
                                    </IconButton>
                                </label>
                                <span>Video</span>
                            </div>
                        </div>
                        {selectedImage && <div>
                            <img className="h-[10rem]" src={selectedImage} alt="" />
                        </div>}
                        <div className='flex w-full justify-end'>
                            <Button variant="contained" type="submit" sx={{ borderRadius: "1.5rem" }}>Post</Button>
                        </div>
                    </div>
                </form>
                <Backdrop
                    sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
                    open={isLoading}
                    onClick={handleClose}
                >
                    <CircularProgress color="inherit" />
                </Backdrop>
            </Box>
        </Modal>
    );
}

export default CreatePostModal;
