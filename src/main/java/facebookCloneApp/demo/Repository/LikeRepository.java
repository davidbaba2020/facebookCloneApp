package facebookCloneApp.demo.Repository;

import facebookCloneApp.demo.Models.Comment;
import facebookCloneApp.demo.Models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Integer countAllByPostId(Long postId);
    List<Like> findAllByPostId(Long postId);
    Like  findLikeByPostId(Long postId);

}