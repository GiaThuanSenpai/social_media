import { Avatar, Backdrop, CircularProgress, Grid, IconButton } from '@mui/material'
import React, { useEffect, useRef, useState } from 'react'
import WestIcon from '@mui/icons-material/West';
import CallIcon from '@mui/icons-material/Call';
import VideocamIcon from '@mui/icons-material/Videocam';
import AddPhotoAlternateIcon from '@mui/icons-material/AddPhotoAlternate';
import SearchUser from '../../components/SearchUser/SearchUser';
import UserChatCard from './UserChatCard';
import ChatMessage from './ChatMessage';
import { useDispatch, useSelector } from 'react-redux';
import { createMessage, getAllChats } from '../../redux/Message/message.action';
import ChatBubbleOutlineIcon from '@mui/icons-material/ChatBubbleOutline';
import { uploadToCloudinary } from '../../utils/uploadToCloudinary';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
const Message = () => {
  const dispatch = useDispatch()
  const { message, auth } = useSelector(store => store)
  const [currentChat, setCurrentChat] = useState(null)
  const [messages, setMessages] = useState([])
  const [selectedImage, setSelectedImage] = useState(null)
  const [loading, setLoading] = useState(false)
  const chatContainerRef = useRef(null)
  const [messageInput, setMessageInput] = useState('');


  useEffect(() => {
    dispatch(getAllChats())
  }, [])

  useEffect(() => {
    console.log("Chat--: ", message.chats)
    console.log("C: ", messages)
  })

  const handleSelectImage = async (e) => {
    setLoading(true)
    console.log("Select Image...")
    const imgUrl = await uploadToCloudinary(e.target.files[0], "image")
    setSelectedImage(imgUrl)
    setLoading(false)
  }

  const handleCreateMessage = (value) => {
    const message = {
      chat_id: currentChat?.chat_id,
      content: value,
      image: selectedImage
    };
    dispatch(createMessage({ message, sendMessageToServer }));
    setMessageInput(''); // Clear the message input after sending
    setSelectedImage(null); // Clear the selected image if any
  };
  

  // useEffect(() => {
  //   setMessages([
  //     ...messages, message.message]
  //   )
  // }, [message.message])

  const [stompClient, setStompClient] = useState(null)
  useEffect(() => {
    const sock = new SockJS("http://localhost:8080/ws")
    const stomp = Stomp.over(sock)
    setStompClient(stomp)

    stomp.connect({}, onConnect, onErr)
  }, [])

  const onConnect = () => {
    console.log("WebSocket connected...")
  }

  const onErr = (error) => {
    console.log("Err", error)
  }

  useEffect(() => {
    if (stompClient && auth.user && currentChat) {
      const subScription = stompClient.subscribe(`/user/${currentChat.chat_id}/private`,
        onMessageReceive)
    }
  })

  const sendMessageToServer = (newMessage) => {
    if (stompClient && newMessage) {
      stompClient.send(`/app/chat/${currentChat?.chat_id.toString()}`, {}, JSON.stringify(newMessage))
    }
  }
  const onMessageReceive = (payload) => {
    const receivedMessage = JSON.parse(payload.body)
    console.log("Message Receive from WebSocket", receivedMessage)
    setMessages([
      ...messages,
      receivedMessage
    ])
  }

  useEffect(() => {
    if (chatContainerRef.current) {
      chatContainerRef.current.scrollTop = chatContainerRef.current.scrollHeight
    }
  }, [messages])
  return (
    <div>
      <Grid container className='h-screen overflow-y-hidden'>
        <Grid className='px-5' item xs={3}>
          <div className='flex h-full justify-between space-x-2'>
            <div className='w-full'>
              <div className='flex space-x-4 items-center py-5'>
                <WestIcon />
                <h1 className='text-xl font-bold'>Home</h1>
              </div>
              <div className='h-[83vh]'>
                <div>
                  <SearchUser />
                </div>
                <div className='h-full space-y-4 mt-5 overflow-y-scroll hideScrollbar'>
                  {
                    message.chats ? (
                      message.chats.map((item) => (
                        <div key={item.chat_id} onClick={() => {
                          setCurrentChat(item)
                          setMessages(item.messages)
                        }}>
                          <UserChatCard chat={item} />
                        </div>
                      ))
                    ) : (
                      <p>Loading chats...</p>
                    )
                  }
                </div>
              </div>
            </div>
          </div>
        </Grid>
        <Grid className='h-full' item xs={9}>
          {currentChat ? (
            <div>
              <div className='flex justify-between items-center border-l p-5'>
                <div className='flex items-center space-x-3'>
                  <Avatar src={auth.user.img} />
                  <p>{auth.user?.user_id === currentChat.users[0]?.user_id ?
                    currentChat.users[1].firstName + " " + currentChat.users[1].lastName :
                    currentChat.users[0].firstName + " " + currentChat.users[0].lastName
                  }</p>
                </div>
                <div className='flex space-x-3'>
                  <IconButton>
                    <CallIcon />
                  </IconButton>

                  <IconButton>
                    <VideocamIcon />
                  </IconButton>
                </div>
              </div>
              <div ref={chatContainerRef} className='hideScrollbar overflow-y-scroll h-[82vh] 
              px-2 space-y-5 py-5'>
                {messages.map((item, index) => <ChatMessage key={index} item={item} />)}
              </div>
              <div className='sticky bottom-0 border-l'>
                {selectedImage && <img className='w-[5rem] h-[5rem] object-cover px-2' src={selectedImage} alt='' />}
                <div className='py-5 flex items-center justify-center space-x-5'>
                  <input
                    value={messageInput}
                    onChange={(e) => setMessageInput(e.target.value)}
                    onKeyPress={(e) => {
                      if (e.key === "Enter" && messageInput.trim()) {
                        handleCreateMessage(messageInput);
                      }
                    }}
                    className='bg-transparent border border-[#3b4054] rounded-full w-[90%] py-3 px-5'
                    placeholder='Type message...'
                    type='text'
                  />

                  <div>
                    <input type='file' accept='image/*' onChange={handleSelectImage}
                      className='hidden' id='image-input' />
                    <label htmlFor='image-input'>
                      <AddPhotoAlternateIcon />
                    </label>
                  </div>
                </div>
              </div>
            </div>
          ) : (
            <div className='h-full space-y-5 flex flex-col justify-center items-center'>
              <ChatBubbleOutlineIcon sx={{ fontSize: "15rem" }} />
              <p className='text-xl font-semibold'>No Chat Selected</p>
            </div>
          )}
        </Grid>
      </Grid>

      <Backdrop
        sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
        open={loading}
      >
        <CircularProgress color="inherit" />
      </Backdrop>
    </div>
  )
}

export default Message
