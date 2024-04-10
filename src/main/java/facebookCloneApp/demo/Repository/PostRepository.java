package facebookCloneApp.demo.Repository;

import facebookCloneApp.demo.Models.Comment;
import facebookCloneApp.demo.Models.Post;
import facebookCloneApp.demo.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);
    Optional<Post> getPostById(Long postId);
    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.comment")
    List<Post> findAllWithComments();

}
