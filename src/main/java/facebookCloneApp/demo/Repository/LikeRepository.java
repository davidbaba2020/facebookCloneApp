package facebookCloneApp.demo.Repository;

import facebookCloneApp.demo.Models.Comment;
import facebookCloneApp.demo.Models.Like;
import facebookCloneApp.demo.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByUserIdAndPostId(Long userId, Long postId);

    Long countByPostId(Long postId);

    Integer countAllByPostId(Long postId);

    Like findLikeByPostId(Long postId);
}