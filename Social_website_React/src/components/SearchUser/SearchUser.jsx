import { Avatar, Card, CardHeader } from '@mui/material'
import React, { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { searchUser } from '../../redux/Auth/auth.action'
import { createChat } from '../../redux/Message/message.action'

const SearchUser = () => {
  const [userName, setUserName] = useState("")
  const dispatch = useDispatch()
  const { message, auth } = useSelector(store => store)

  const handleSearchUser = (e) => {
    setUserName(e.target.value)
    console.log("Search user...", auth.searchUser)
    dispatch(searchUser(userName))
  }
  const handleClick = (id) => {
    dispatch(createChat({user_id: id}))
  }
  return (
    <div>
      <div className='py-5 relative'>
        <input className='bg-transparent border border-[#3b4054]
        outline-none w-full px-5 py-3 rounded-full' placeholder='Search user...'
          onChange={handleSearchUser} type='text' />
        {userName &&
          (auth.searchUser.map((item) =>
            <Card className='absolute w-full z-10 top-[4.5rem] cursor-pointer'>
              <CardHeader
                onClick={() => {
                  handleClick(item.user_id)
                  setUserName("")
                }}
                avatar={<Avatar src='https://bazaarvietnam.vn/wp-content/uploads/2023/04/HBVN-bi-quyet-lam-dep-duong-da-cua-kim-tae-hee-1.jpg' />
                }
                title={item.firstName + " " + item.lastName}
                subheader={item.firstName?.toLowerCase().replace(/\s/g, '') + "" + item.lastName?.toLowerCase().replace(/\s/g, '')}
              />
            </Card>)
          )}
      </div>

    </div>
  )
}

export default SearchUser