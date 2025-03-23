import { Route, Routes } from 'react-router-dom';
import './App.css';
import Authentication from './pages/Authentication/Authentication';
import HomePage from './pages/HomePage/HomePage';
import Message from './pages/Message/Message';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import { getProfileAction } from './redux/Auth/auth.action';

function App() {
  const { auth } = useSelector(store => store);
  const dispatch = useDispatch();
  const jwt = localStorage.getItem("jwt");

  useEffect(() => {
    dispatch(getProfileAction(jwt));
  }, [jwt, dispatch]);

  const isAuthenticated = !!auth.user;

  
  return (
    <div className="">
      <Routes>
        <Route path="/*" element={isAuthenticated ? <HomePage /> : <Authentication />} />
        <Route path="/messages" element={isAuthenticated ? <Message /> : <Authentication />} />
        <Route path="*" element={<Authentication />} />
      </Routes>
    </div>
  );
}

export default App;
