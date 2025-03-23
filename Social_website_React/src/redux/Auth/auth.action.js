import axios from "axios"
import { GET_PROFILE_FAILURE, GET_PROFILE_REQUEST, GET_PROFILE_SUCCESS, LOGIN_FAILURE, LOGIN_REQUEST, LOGIN_SUCCESS, REGISTER_FAILURE, REGISTER_REQUEST, REGISTER_SUCCESS, SEARCH_USER_FAILURE, SEARCH_USER_REQUEST, SEARCH_USER_SUCCESS, UPDATE_PROFILE_FAILURE, UPDATE_PROFILE_REQUEST, UPDATE_PROFILE_SUCCESS } from "./auth.actionType"
import { API_BASE_URL, api } from "../../config/api"


export const loginUserAction = (loginData) => async (dispatch) => {
    dispatch({ type: LOGIN_REQUEST });
    try {
        const { data } = await axios.post(`${API_BASE_URL}/auth/signin`, loginData.data);

        if (data.token) {
            localStorage.setItem("jwt", data.token);
        } 

        dispatch({ type: LOGIN_SUCCESS, payload: data.jwt });
    } catch (error) {
        console.error("Login Error: ", error);
        dispatch({ type: LOGIN_FAILURE, payload: error });
    }
};

export const registerUserAction = (loginData) => async (dispatch) => {
    dispatch({ type: REGISTER_REQUEST });
    try {
        const { data } = await axios.post(`${API_BASE_URL}/auth/signup`, loginData.data);

        console.log("Response data: ", data);

        if (data.token) {
            localStorage.setItem("jwt", data.token);
            console.log("JWT saved to localStorage: ", data.token);
        } else {
            console.error("JWT not found in response data: ", data);
        }

        dispatch({ type: REGISTER_SUCCESS, payload: data.jwt });
    } catch (error) {
        console.error("Registration Error: ", error);
        dispatch({ type: REGISTER_FAILURE, payload: error });
    }
};

export const getProfileAction = (jwt) => async (dispatch) => {
    dispatch({type: GET_PROFILE_REQUEST});
    try {
        const { data } = await axios.get(`${API_BASE_URL}/api/users/profile`, {
            headers: {
                Authorization: `Bearer ${jwt}`,
            },
        });
        console.log("Profile data: ", data);
        dispatch({ type: GET_PROFILE_SUCCESS, payload: data });
    } catch (error) {
        console.error("Profile Fetch Error: ", error);
        dispatch({ type: GET_PROFILE_FAILURE, payload: error });
    }
};


export const updateProfileAction = (userData) => async (dispatch) => {
    dispatch({ type: UPDATE_PROFILE_REQUEST });
    try {
        const { data } = await api.put("/api/users", userData);
        console.log("Profile updated: ", data);
        dispatch({ type: UPDATE_PROFILE_SUCCESS, payload: data });
        return data; // Trả về dữ liệu từ server sau khi cập nhật thành công
    } catch (error) {
        console.error("Profile Update Error: ", error);
        dispatch({ type: UPDATE_PROFILE_FAILURE, payload: error });
        throw error; // Ném lỗi để xử lý bên ngoài action nếu cần thiết
    }
};

export const searchUser = (query) => async (dispatch) => {
    dispatch({type: SEARCH_USER_REQUEST});
    try {
        const { data } = await api.get(`/api/users/search?query=${query}`)
        console.log("Search User: ", data);
        dispatch({ type: SEARCH_USER_SUCCESS, payload: data });
    } catch (error) {
        console.error("Search Error: ", error);
        dispatch({ type: SEARCH_USER_FAILURE, payload: error });
    }
};

