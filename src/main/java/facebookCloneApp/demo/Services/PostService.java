package facebookCloneApp.demo.Services;

import facebookCloneApp.demo.Models.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostService {
    void savePost(Post post);
//    void updatePost(Post post);
   List<Post> getAllPostById(Long id);
   Post getPostById(Long postId);
   void deletePost(Long postId);
}
