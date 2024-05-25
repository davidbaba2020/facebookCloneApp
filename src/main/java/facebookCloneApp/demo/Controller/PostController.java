package facebookCloneApp.demo.Controller;

import facebookCloneApp.demo.DTO.ResponseDTO.LoginDTORes;
import facebookCloneApp.demo.Models.Post;
import facebookCloneApp.demo.Models.User;
import facebookCloneApp.demo.Services.PostService;
import facebookCloneApp.demo.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/createPost")
    public String createPost(@RequestParam Long userId, @ModelAttribute Post post){
        postService.createPost(userId, post);
        return "redirect:/dashboard";
    }


    @PostMapping("/updatePost")
    public String updatePost(@RequestParam Long postId, @ModelAttribute Post updatedPost, @RequestParam Long userId) {

        postService.updatePost(postId, userId, updatedPost);
        return "redirect:/dashboard";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable (value = "id") Long id){
        postService.deletePost(id);
        return "redirect:/dashboard";
    }
}
