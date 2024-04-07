package facebookCloneApp.demo.Services;

import facebookCloneApp.demo.DTO.ResponseDTO.LoginDTORes;
import facebookCloneApp.demo.Models.Post;
import facebookCloneApp.demo.Models.User;
import facebookCloneApp.demo.Repository.PostRepository;
import facebookCloneApp.demo.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void savePost(Post post) {
        postRepository.save(post);
    }

//    @Override
//    public void updatePost(Post post) {
//        postRepository.saveAndFlush(post);
//    }

    @Override
    public List<Post> getAllPostById(Long id) {
       return postRepository.findByUserId(id);
    }

    @Override
    public Post getPostById(Long postId) {
        Optional<Post> optionalPost= postRepository.getPostById(postId);
        Post post = null;
        if (optionalPost.isPresent()){
            post = optionalPost.get();
        }else {
            throw new RuntimeException("No post found for that id");
        }
        return post;
    }

    @Override
    public void deletePost(Long postId) {
        this.postRepository.deleteById(postId);
    }

    public User createUserTemplate(LoginDTORes loginDTORes){
        User user = new User();
        user.setId(loginDTORes.getId());
        user.setCreatedAt(loginDTORes.getCreatedAt());
        user.setUpdatedAt(loginDTORes.getUpdatedAt());
        user.setFirstname(loginDTORes.getFirstname());
        user.setLastname(loginDTORes.getLastname());
        user.setAge(loginDTORes.getAge());
        user.setEmail(loginDTORes.getEmail());
        user.setGender(loginDTORes.getGender());
        user.setPosts(loginDTORes.getPostList());
        user.setComments(loginDTORes.getCommentList());
        user.setPassword(loginDTORes.getPassword());

        return user;
    }
}
