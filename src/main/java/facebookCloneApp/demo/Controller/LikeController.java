package facebookCloneApp.demo.Controller;

import facebookCloneApp.demo.Services.LikeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("like")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }


    @GetMapping("/likePost/{postId}")
    public String likePost(@PathVariable(value = "postId") Long postId) {
        likeService.likePost(postId);
        return "redirect:/dashboard";
    }



    @GetMapping("/unlikePost/{postId}")
    public String unlikePost(@PathVariable(value = "postId") Long postId) {
        likeService.unlikePost(postId);
        return "redirect:/dashboard";
    }
}
