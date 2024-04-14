package facebookCloneApp.demo.Services;

import facebookCloneApp.demo.Models.Comment;
import facebookCloneApp.demo.Models.Post;
import facebookCloneApp.demo.Models.User;
import facebookCloneApp.demo.Repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Comment getCommentById(Long commentId) {
        Optional<Comment> optionalComment= commentRepository.getCommentById(commentId);
        Comment comment = null;
        if (optionalComment.isPresent()){
            comment = optionalComment.get();
        }else {
            throw new RuntimeException("No comment found for that id");
        }
        return comment;
    }
    @Override
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
