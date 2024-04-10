package facebookCloneApp.demo.Services;

import facebookCloneApp.demo.Models.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostService {
    void createPost(Post post);
    void updatePost(Post post);
   List<Post> getAllPostById(Long id);
   List<Post> getAllPost();

   List<Post> getAllPostsWithComments();
   Post getPostById(Long postId);
   void deletePost(Long postId);
}
