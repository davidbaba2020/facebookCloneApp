package facebookCloneApp.demo.Services;

import facebookCloneApp.demo.DTO.ResponseDTO.LoginDTORes;
import facebookCloneApp.demo.Models.Post;
import facebookCloneApp.demo.Models.User;
import facebookCloneApp.demo.Repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private final UserService userService;

    public PostServiceImpl(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Override
    public void createPost(Long userId, Post post) {
        User user = userService.getUserById(userId);
        if (user != null) {
            post.setUser(user);
            postRepository.save(post);
        }
    }

    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @Override
    public void updatePost(Long postId, Long userId, Post updatedPost) {
        Post existingPost = null;
            Optional<Post> post = postRepository.findByIdAndUserId(postId, userId);
            if (post.isPresent()) {
                existingPost = post.get();
                existingPost.setContent(updatedPost.getContent());
                postRepository.save(existingPost);
            }
    }

    @Override
    public List<Post> getAllPostById(Long id) {
       return postRepository.findByUserId(id);
    }

    @Override
    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getAllPostsWithComments() {
       return postRepository.findAllWithComments();
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
