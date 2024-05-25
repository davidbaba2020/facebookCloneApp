    package facebookCloneApp.demo.Controller;

    import facebookCloneApp.demo.DTO.RequestDTO.LoginDTOReq;
    import facebookCloneApp.demo.DTO.ResponseDTO.LoginDTORes;
    import facebookCloneApp.demo.Models.Comment;
    import facebookCloneApp.demo.Models.Like;
    import facebookCloneApp.demo.Models.Post;
    import facebookCloneApp.demo.Models.User;
    import facebookCloneApp.demo.Services.*;
    import jakarta.servlet.http.HttpSession;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.bind.support.SessionStatus;


    import java.util.List;

    @Controller
    @SessionAttributes("loggedInUser")
    public class UserController {

        private final UserService userService;
        private final PostService postService;
        private final CommentService commentService;
        private final LikeService likeService;

        public UserController(UserServiceImpl userService, PostServiceImpl postService,
                              CommentServiceImpl commentService, LikeService likeService) {
            this.userService = userService;
            this.postService = postService;
            this.commentService = commentService;
            this.likeService = likeService;
        }

        @GetMapping("/")
        public String viewHomePage(Model model){
            model.addAttribute("listOfUsers", userService.getAllUser());
            return "index";
        }

        @GetMapping("/signup")
        public String signupPage(Model model){
            User user = new User();
            model.addAttribute("user", user);
            return "/signup";
        }

        @PostMapping("/signup")
        public String saveUser(@ModelAttribute("user") User user){
            if (userService.userAlreadyExist(user.getEmail())){
                return "redirect:/signup";
            }else {
                userService.saveUser(user);
                return "redirect:/login";

            }
        }

        @GetMapping("/login")
        public String loginPage(Model model){
            LoginDTOReq loginDTOReq = new LoginDTOReq();
            model.addAttribute("loginDTOReq", loginDTOReq);
            return "/login";
        }

        @PostMapping("/login")
        public String login(@ModelAttribute LoginDTOReq loginDTOReq, Model model){
            LoginDTORes loginDTORes = userService.getUserByEmailAndPassword(loginDTOReq);
            if (loginDTORes == null) {
                return "redirect:/login";

            }else
            {
                model.addAttribute("loggedInUser", loginDTORes);
                return "redirect:/dashboard";
            }
        }

        @GetMapping("/dashboard")
        public String dashboard(@ModelAttribute ("loggedInUser") LoginDTORes loginDTORes, Model model){
            if (loginDTORes != null){
                List<Post> postList = postService.getAllPostById(loginDTORes.getId());
                List<Comment> commentList = commentService.commentListByUserId(loginDTORes.getId());
                model.addAttribute("user", loginDTORes);
                model.addAttribute("post", new Post());
                model.addAttribute("comment", new Comment());
                model.addAttribute("posts", postList);
                model.addAttribute("comments", commentList);
                model.addAttribute("like", new Like());
                model.addAttribute("allUsers", userService.getAllUser());



                return "/dashboard";
            }

            return "redirect:/login";
        }

//        CRUD ON POST


        @GetMapping("/post-edit/{id}")
        public String editPost(@PathVariable(value = "id") Long id, Model model, HttpSession session) {
            LoginDTORes loggedInUser = (LoginDTORes) session.getAttribute("loggedInUser");
            if (loggedInUser == null) {
                // Handle the case where the user is not logged in
                return "redirect:/login"; // Redirect to the login page
            }

            Post post = postService.getPostById(id);
            model.addAttribute("post", post);
            model.addAttribute("loggedInUser", loggedInUser);
            return "update_post";
        }




        @GetMapping("/comment-edit/{id}")
        public String editComment(@PathVariable(value = "id") Long id, Model model, HttpSession session) {
            LoginDTORes loggedInUser = (LoginDTORes) session.getAttribute("loggedInUser");
            if (loggedInUser == null) {
                // Handle the case where the user is not logged in
                return "redirect:/login"; // Redirect to the login page
            }

            Comment comment = commentService.getCommentById(id);
            model.addAttribute("comment", comment);
            model.addAttribute("loggedInUser", loggedInUser);
            return "update_comment";
        }






//        LIKE / UNLIKE OPERATIONS





        @GetMapping("/logout")
        public String logoutUser(SessionStatus sessionStatus) {
            sessionStatus.setComplete();
            return "redirect:/login";
        }


    }
