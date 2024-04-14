package facebookCloneApp.demo.Services;

import facebookCloneApp.demo.Models.Like;
import facebookCloneApp.demo.Models.Post;
import facebookCloneApp.demo.Models.User;

import java.util.List;

public interface LikeService {

    void unlikePost(Long postId);
    void likePost(Long postId);
}
