import { api } from "../../config/api"
import { CREATE_COMMENT_FAILURE, CREATE_COMMENT_REQUEST, CREATE_COMMENT_SUCCESS, CREATE_POST_FAILURE, CREATE_POST_REQUEST, CREATE_POST_SUCCESS, GET_ALL_POST_FAILURE, GET_ALL_POST_REQUEST, GET_ALL_POST_SUCCESS, GET_USERS_POST_FAILURE, GET_USERS_POST_REQUEST, GET_USERS_POST_SUCCESS, LIKE_POST_FAILURE, LIKE_POST_REQUEST, LIKE_POST_SUCCESS } from "./post.actionType"

export const createPostAction = (postData) => async(dispatch) =>{
    dispatch({type:CREATE_POST_REQUEST})
    
    try {
        const {data} = await api.post(`/api/posts`, postData)
        dispatch({type: CREATE_POST_SUCCESS , payload: data})
        console.log("create post ", data)
    } catch (error) {
        console.log("error ", error)
        dispatch({type: CREATE_POST_FAILURE, payload: error})
    }
}

export const getAllPostAction = () => async(dispatch) =>{
    dispatch({type:GET_ALL_POST_REQUEST})
    
    try {
        const {data} = await api.get(`/api/posts`)
        dispatch({type: GET_ALL_POST_SUCCESS , payload: data})
        console.log("get all post ", data)
    } catch (error) {
        console.log("error ", error)
        dispatch({type: GET_ALL_POST_FAILURE, payload: error})
    }
}

export const getUsersPostAction = (user_id) => async(dispatch) =>{
    dispatch({type: GET_USERS_POST_REQUEST})
    console.log("user_id type:", typeof user_id); 
    try {
        const {data} = await api.get(`/api/posts/user/${user_id}`)
        dispatch({type: GET_USERS_POST_SUCCESS, payload: data})
        console.log("get users post", data)
    } catch (error) {
        console.error("Error fetching user's posts: ", error);
        dispatch({type: GET_USERS_POST_FAILURE, payload: error.message || 'Something went wrong'})
    }
}



export const likePostAction = (post_id) => async(dispatch) =>{
    dispatch({type:LIKE_POST_REQUEST})
    
    try {
        const {data} = await api.put(`/api/posts/like/${post_id}`)
        dispatch({type: LIKE_POST_SUCCESS , payload: data})
        console.log("like post", data)
    } catch (error) {
        console.log("error ", error)
        dispatch({type: LIKE_POST_FAILURE, payload: error})
    }
}

export const createCommentAction = (reqData) => async(dispatch) =>{
    dispatch({type: CREATE_COMMENT_REQUEST})
    try {
        const {data} = await api.post(`/api/comments/post/${reqData.post_id}`, reqData.data)
        dispatch({type: CREATE_COMMENT_SUCCESS , payload: data})
        console.log("create comment ", data)
    } catch (error) {
        console.log("error ", error)
        dispatch({type: CREATE_COMMENT_FAILURE, payload: error})
    }
}


