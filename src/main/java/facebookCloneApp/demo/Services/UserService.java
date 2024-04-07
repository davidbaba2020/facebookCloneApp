package facebookCloneApp.demo.Services;

import facebookCloneApp.demo.DTO.RequestDTO.LoginDTOReq;
import facebookCloneApp.demo.DTO.ResponseDTO.LoginDTORes;
import facebookCloneApp.demo.Models.Post;
import facebookCloneApp.demo.Models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUser();
    void saveUser(User user);
    User getUserById(Long id);
    LoginDTORes getUserByEmailAndPassword(LoginDTOReq loginDTOReq);
    User findByPost(Post post);
}
