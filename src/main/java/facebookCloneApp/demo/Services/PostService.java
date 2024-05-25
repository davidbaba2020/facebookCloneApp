package facebookCloneApp.demo.Services;

import facebookCloneApp.demo.DTO.ResponseDTO.LoginDTORes;
import facebookCloneApp.demo.Models.Post;
import facebookCloneApp.demo.Models.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostService {
    void createPost(Long userId, Post post);
    void save(Post post);
   List<Post> getAllPostById(Long id);
   List<Post> getAllPost();

   List<Post> getAllPostsWithComments();
   Post getPostById(Long postId);
   void deletePost(Long postId);

    User createUserTemplate(LoginDTORes loginDTORes);

    void updatePost(Long postId, Long userId, Post updatedPost);
}
