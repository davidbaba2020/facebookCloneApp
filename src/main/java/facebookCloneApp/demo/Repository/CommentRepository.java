package facebookCloneApp.demo.Repository;

import facebookCloneApp.demo.Models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUserId(Long userId);
    Optional<Comment> getCommentById(Long commentId);

    Optional<Comment> getCommentByIdAndUserId(Long commentId, Long userId);
}
