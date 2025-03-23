import { Avatar, Button, CardHeader, IconButton } from '@mui/material'
import React from 'react'
import MoreVertIcon from '@mui/icons-material/MoreVert';
import { red } from '@mui/material/colors';

const PopularUserCard = () => {
  return (
    <div>
        <CardHeader
                avatar={
                    <Avatar sx={{ bgcolor: red[500] }} aria-label="recipe" 
                    src='https://images2.thanhnien.vn/528068263637045248/2024/4/10/kimjiwonanhnienthieuxinhdep1-1712790884640931309627.jpg'>
                    </Avatar>
                }
                action={
                    <Button size='small'>
                        Follow
                    </Button>
                }
                title="Kim Ji Won"
                subheader="@kimjiwon" />
    </div>
  )
}

export default PopularUserCard