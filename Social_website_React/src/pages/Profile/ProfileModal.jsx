import React, { useState } from "react";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Modal from "@mui/material/Modal";
import { Avatar, IconButton, TextField } from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import ImageIcon from '@mui/icons-material/Image';
import { useFormik } from "formik";
import { useDispatch, useSelector } from "react-redux";
import { updateProfileAction } from "../../redux/Auth/auth.action";
import { uploadToCloudinary } from "../../utils/uploadToCloudinary";

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 600,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 2,
    outline: "none",
    overFlow: "scroll-y",
    borderRadius: 3,
};

export default function ProfileModal({ open, handleClose }) {
    const [selectedAvatar, setSelectedAvatar] = useState();
    const [selectedBackground, setSelectedBackground] = useState();
    const [isLoading, setIsLoading] = useState(false);

    const handleSelectImage = async (event) => {
        setIsLoading(true);
        const imageUrl = await uploadToCloudinary(event.target.files[0], "image");
        setSelectedAvatar(imageUrl);
        setIsLoading(false);
        formik.setFieldValue("imgAvatar", imageUrl);
    };
    const handleSelectBackground = async (event) => {
        setIsLoading(true);
        const imageUrl = await uploadToCloudinary(event.target.files[0], "image");
        setSelectedBackground(imageUrl);
        setIsLoading(false);
        formik.setFieldValue("imgBackground", imageUrl);
    };

    const dispatch = useDispatch();
    const { firstName, lastName, imgAvatar, imgBackground } = useSelector(state => state.auth.user);

    const formik = useFormik({
        initialValues: {
            firstName: firstName || "",
            lastName: lastName || "",
            imgAvatar: imgAvatar,
            imgBackground: imgBackground
        },
        onSubmit: (values) => {
            setIsLoading(true)
            dispatch(updateProfileAction(values));
            setIsLoading(false)
            handleClose();
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
                    <div className="flex items-center justify-between">
                        <div className="flex items-center space-x-3">
                            <IconButton onClick={handleClose}>
                                <CloseIcon />
                            </IconButton>
                            <p>Edit Profile</p>
                        </div>
                        <Button type="submit">Save</Button>
                    </div>
                    <div>
                        <div className="h-[15rem] relative">
                            <img
                                className="w-full h-full rounded-t-md"
                                src={selectedBackground || imgBackground}
                                alt=""
                            />
                            <div className="absolute bottom-0 right-0 p-2 bg-white bg-opacity-0 rounded-sm flex items-center">
                                <input
                                    type="file"
                                    accept="image/*"    
                                    onChange={handleSelectBackground}
                                    style={{ display: "none" }}
                                    id="background-image-input"
                                />
                                <label htmlFor="background-image-input">
                                    < Button sx={{ borderRadius: "15px", color: "white"}} variant='outlined' startIcon={<ImageIcon />} component="span">
                                        Chỉnh sửa ảnh bìa
                                    </Button>
                                </label>
                            </div>
                        </div>

                        <div className="pl-5 flex">
                            <Avatar
                                className="transform -translate-y-24"
                                sx={{ width: "10rem", height: "10rem" }}
                                src={selectedAvatar || imgAvatar}
                            />
                            <div>
                                <input
                                    type="file"
                                    accept="image/*"
                                    onChange={handleSelectImage}
                                    style={{ display: "none" }}
                                    id="image-input"
                                />
                                <label htmlFor="image-input">
                                    <IconButton color="primary" component="span">
                                        <ImageIcon />
                                    </IconButton>
                                </label>
                            </div>
                        </div>
                    </div>
                    <div className="space-y-3">
                        <TextField
                            fullWidth
                            id="firstName"
                            name="firstName"
                            label="First Name"
                            value={formik.values.firstName}
                            onChange={formik.handleChange}
                        />
                        <TextField
                            fullWidth
                            id="lastName"
                            name="lastName"
                            label="Last name"
                            value={formik.values.lastName}
                            onChange={formik.handleChange}
                        />
                    </div>
                </form>
            </Box>
        </Modal>
    );
}
