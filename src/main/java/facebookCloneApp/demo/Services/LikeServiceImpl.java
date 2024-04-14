package facebookCloneApp.demo.Services;

import facebookCloneApp.demo.Models.Like;
import facebookCloneApp.demo.Models.Post;
import facebookCloneApp.demo.Models.User;
import facebookCloneApp.demo.Repository.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImpl implements LikeService{
    private final LikeRepository likeRepository;
    private final PostService postService;

    public LikeServiceImpl(LikeRepository likeRepository, PostService postService) {
        this.likeRepository = likeRepository;
        this.postService = postService;
    }

    @Override
    public void likePost(Long postId) {
        Integer likeCount = likeRepository.countAllByPostId(postId);
        Post post = postService.getPostById(postId);
        post.setId(postId);
        if(likeCount == 0) {
            Like like = new Like();
            like.setPost(post);
            like.setLiked(true);

            this.likeRepository.save(like);
        }
    }

    @Override
    public void unlikePost(Long postId) {
        Like like = likeRepository.findLikeByPostId(postId);
        this.likeRepository.delete(like);
    }




}
