package facebookCloneApp.demo.Repository;

import facebookCloneApp.demo.Models.Post;
import facebookCloneApp.demo.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByPostsContaining(Post post);
}
