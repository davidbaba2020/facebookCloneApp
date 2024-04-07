package facebookCloneApp.demo.Services;

import facebookCloneApp.demo.DTO.RequestDTO.LoginDTOReq;
import facebookCloneApp.demo.DTO.ResponseDTO.LoginDTORes;
import facebookCloneApp.demo.Models.Post;
import facebookCloneApp.demo.Models.User;
import facebookCloneApp.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new RuntimeException("User not found for id :: " + id);
        }
        return user;
    }

    @Override
    public LoginDTORes getUserByEmailAndPassword(LoginDTOReq loginDTOReq) {
        List<User> users = userRepository.findAll();
        LoginDTORes loginDTORes = new LoginDTORes();
        for (User user : users) {
            if ((Objects.equals(user.getEmail(), loginDTOReq.getEmail())) && (Objects.equals(user.getPassword(), loginDTOReq.getPassword()))) {

                return  createUserTemplate(user, loginDTORes);
            }
        }
        return null;
    }

    @Override
    public User findByPost(Post post) {
        return userRepository.findByPostsContaining(post);
    }

    LoginDTORes createUserTemplate (User user, LoginDTORes loginDTORes){
        loginDTORes.setId(user.getId());
        loginDTORes.setCreatedAt(user.getCreatedAt());
        loginDTORes.setUpdatedAt(user.getUpdatedAt());
        loginDTORes.setFirstname(user.getFirstname());
        loginDTORes.setLastname(user.getLastname());
        loginDTORes.setAge(user.getAge());
        loginDTORes.setEmail(user.getEmail());
        loginDTORes.setGender(user.getGender());
        loginDTORes.setPassword(user.getPassword());
        loginDTORes.setPostList(user.getPosts());
        loginDTORes.setCommentList(user.getComments());

        return loginDTORes;
    }




}