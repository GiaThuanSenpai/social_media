import { CREATE_COMMENT_SUCCESS, CREATE_POST_FAILURE, CREATE_POST_REQUEST, CREATE_POST_SUCCESS, GET_ALL_POST_FAILURE, GET_ALL_POST_REQUEST, GET_ALL_POST_SUCCESS, GET_USERS_POST_FAILURE, GET_USERS_POST_SUCCESS, LIKE_POST_REQUEST, LIKE_POST_SUCCESS } from "./post.actionType";

const initialState = {
    post: null,
    loading: false,
    error: null,
    posts: [],
    like: null,
    comment: [],
    newComment: null
}

export const postReducer = (state = initialState, action) => {
    switch (action.type) {
        case CREATE_POST_REQUEST:
        case GET_ALL_POST_REQUEST:
        case LIKE_POST_REQUEST:
            return { ...state, error: null, loading: false }

        case CREATE_POST_SUCCESS:
            return {
                ...state, post: action.payload,
                posts: [action.payload, ...state.posts],
                loading: false,
                error: null
            }
        case GET_ALL_POST_SUCCESS:
            return {
                ...state,
                posts: action.payload,
                comments: action.payload.comment,
                loading: false,
                error: null
            }
        case GET_USERS_POST_SUCCESS: // Thêm case này
            return {
                ...state,
                posts: action.payload,
                loading: false,
                error: null
            }
        case LIKE_POST_SUCCESS:
            return {
                ...state,
                like: action.payload,
                posts: state.posts.map((item) => item.post_id === action.payload.post_id ? action.payload : item),
                loading: false,
                error: null
            }
        case CREATE_COMMENT_SUCCESS:
            return {
                ...state,
                newComment: action.payload,
                loading: false,
                error: null
            }
        case CREATE_POST_FAILURE:
        case GET_ALL_POST_FAILURE:
        case GET_USERS_POST_FAILURE:
            return {
                ...state, error: action.payload,
                loading: false
            }
        default:
            return state
    }
}