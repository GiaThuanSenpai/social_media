import React from 'react';
import { Avatar, Button, Card, Divider, Menu, MenuItem } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import { navigationMenu } from './SidebarNavigation';

const Sidebar = () => {
  const navigate = useNavigate();
  const { auth } = useSelector(store => store);
  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleNavigate = (item) => {
    if (item.title === "Profile") {
      navigate(`/profile/${auth.user?.user_id}`);
    }
    else {
      navigate(item.path);
    }
  }

  return (
    <Card className="card h-screen flex flex-col justify-between py-5">
      <div className="space-y-8 pl-5">
        <div>
          <span className="logo font-bold text-xl">Pentimento Social</span>
        </div>
        <div className="space-y-8">
          {navigationMenu.map((item, index) => (
            <div key={index} onClick={() => handleNavigate(item)} className="cursor-pointer flex space-x-3 items-center">
              {item.icon}
              <p className="text-xl">{item.title}</p>
            </div>
          ))}
        </div>
      </div>
      <div>
        <Divider />
        <div className="pl-5 flex items-center justify-between pt-5">
          <div className="flex items-center space-x-3">
            <Avatar src={auth.user?.imgAvatar} />
            <div>
              <p className="font-bold">{auth.user?.firstName + " " + auth.user?.lastName}</p>
              <p className="opacity-70">@{auth.user?.firstName?.toLowerCase().replace(/\s/g, '') + "" + auth.user?.lastName?.toLowerCase().replace(/\s/g, '')}</p>
            </div>
          </div>
          <Button
            id="basic-button"
            aria-controls={open ? 'basic-menu' : undefined}
            aria-haspopup="true"
            aria-expanded={open ? 'true' : undefined}
            onClick={handleClick}
          >
            <MoreHorizIcon />
          </Button>
          <Menu
            id="basic-menu"
            anchorEl={anchorEl}
            open={open}
            onClose={handleClose}
            MenuListProps={{
              'aria-labelledby': 'basic-button',
            }}
          >
            <MenuItem onClick={() => handleNavigate({ title: "Profile" })}>Profile</MenuItem>
            <MenuItem onClick={() => handleNavigate({ title: "My account", path: "/my-account" })}>My account</MenuItem>
            <MenuItem onClick={() => handleNavigate({ title: "Logout", path: "/logout" })}>Logout</MenuItem>
          </Menu>
        </div>
      </div>
    </Card>
  );
};

export default Sidebar;
