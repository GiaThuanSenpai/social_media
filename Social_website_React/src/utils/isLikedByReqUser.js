export const isLikedByReqUser = (reqUser_id, post) => {
    if (post && Array.isArray(post.liked)) {
        for (let user of post.liked) {
            if (reqUser_id === user.user_id) return true;
        }
    }
    return false;
}
