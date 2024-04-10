package facebookCloneApp.demo.Repository;

import facebookCloneApp.demo.Models.Post;
import facebookCloneApp.demo.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByPostsContaining(Post post);
    Boolean existsByEmail(String email);
}
