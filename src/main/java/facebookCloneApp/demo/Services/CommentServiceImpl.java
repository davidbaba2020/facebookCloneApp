package facebookCloneApp.demo.Services;

import facebookCloneApp.demo.Models.Comment;
import facebookCloneApp.demo.Models.User;
import facebookCloneApp.demo.Repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void createComment(Comment comment) {
        this.commentRepository.save(comment);
    }

    public List<Comment> commentListByUserId(Long userId){
      return commentRepository.findByUserId(userId);
    }

    @Override
    public void updateComment(Comment comment) {
        this.commentRepository.save(comment);
    }



    @Override
    public void deleteComment(Long commentId) {
            this.commentRepository.deleteById(commentId);
    }
}
