package facebookCloneApp.demo.Services;

import facebookCloneApp.demo.Models.Like;
import facebookCloneApp.demo.Models.Post;
import facebookCloneApp.demo.Models.User;

import java.util.List;

public interface LikeService {

    void likePost(Long postId);
    void unlikePost(Long postId);

    List<Like> findAllLike();
}
