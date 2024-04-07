    package facebookCloneApp.demo.Controller;

    import facebookCloneApp.demo.DTO.RequestDTO.LoginDTOReq;
    import facebookCloneApp.demo.DTO.ResponseDTO.LoginDTORes;
    import facebookCloneApp.demo.Models.Post;
    import facebookCloneApp.demo.Models.User;
    import facebookCloneApp.demo.Services.PostServiceImpl;
    import facebookCloneApp.demo.Services.UserServiceImpl;
    import jakarta.servlet.http.HttpSession;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.bind.support.SessionStatus;

    import java.util.Objects;

    @Controller
    @SessionAttributes("loggedInUser")
    public class UserController {

        private final UserServiceImpl userServiceImpl;
        private final PostServiceImpl postServiceImpl;

        public UserController(UserServiceImpl userServiceImpl, PostServiceImpl postServiceImpl) {
            this.userServiceImpl = userServiceImpl;
            this.postServiceImpl = postServiceImpl;
        }

        @GetMapping("/")
        public String viewHomePage(Model model){
            model.addAttribute("listOfUsers", userServiceImpl.getAllUser());
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
            userServiceImpl.saveUser(user);
            return "redirect:/login";
        }
        @GetMapping("/login")
        public String loginPage(Model model){
            LoginDTOReq loginDTOReq = new LoginDTOReq();
            model.addAttribute("loginDTOReq", loginDTOReq);
            return "/login";
        }
        @PostMapping("/login")
        public String login(@ModelAttribute LoginDTOReq loginDTOReq, Model model){
            LoginDTORes loginDTORes = userServiceImpl.getUserByEmailAndPassword(loginDTOReq);
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
                model.addAttribute("user", loginDTORes);
                model.addAttribute("post", new Post());
                model.addAttribute("posts", postServiceImpl.getAllPostById(loginDTORes.getId()));
                return "/dashboard";
            }
            return "redirect:/login";
        }

        @PostMapping("/posts/create")
        public String createPost(@ModelAttribute("loggedInUser") LoginDTORes loginDTORes, @ModelAttribute Post post){

            User userLoggedIn = postServiceImpl.createUserTemplate(loginDTORes);
            post.setUser(userLoggedIn);

        postServiceImpl.savePost(post);
        return "redirect:/dashboard";
        }

        @GetMapping("/edit-post/{id}")
        public String editPost(@PathVariable(value = "id") Long id, Model model, HttpSession session) {
            LoginDTORes loggedInUser = (LoginDTORes) session.getAttribute("loggedInUser");
            if (loggedInUser == null) {
                // Handle the case where the user is not logged in
                return "redirect:/login"; // Redirect to the login page
            }

            Post post = postServiceImpl.getPostById(id);
            model.addAttribute("post", post);
            model.addAttribute("loggedInUser", loggedInUser);
            return "update_post";
        }

        @PostMapping("/update_post/{id}")
        public String updatePost(@PathVariable(value = "id") Long id, @ModelAttribute Post updatedPost, HttpSession session) {
            LoginDTORes loggedInUser = (LoginDTORes) session.getAttribute("loggedInUser");
            if (loggedInUser == null) {
                // Handle the case where the user is not logged in
                return "redirect:/login"; // Redirect to the login page
            }

            Post existingPost = postServiceImpl.getPostById(id);
            if (!Objects.equals(existingPost.getUser().getId(), loggedInUser.getId())) {
                // Handle unauthorized access (e.g., redirect to an login page)
                return "redirect:/login"; // Redirect to an error page
            }

            existingPost.setContent(updatedPost.getContent());
            this.postServiceImpl.savePost(existingPost);
            return "redirect:/dashboard";
        }



        @GetMapping("/delete-post/{id}")
        public String deletePost(@PathVariable (value = "id") Long id){
            this.postServiceImpl.deletePost(id);
            return "redirect:/dashboard";
        }


        @GetMapping("/logout")
        public String logoutUser(SessionStatus sessionStatus) {
            sessionStatus.setComplete();
            return "redirect:/login";
        }


    }
