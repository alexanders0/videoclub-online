package videoclub_online.videoclub_online;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
	
	User user;
	
	@Autowired
	private UserService userService;
	
    @Secured("ROLE_ADMIN")
    @RequestMapping("/manage_users")
    public ModelAndView manageUsers() {
    	Iterable<User> listUsers = userService.getUserList(); //list of users
        return new ModelAndView("manage_users").addObject("users", listUsers);
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/new_user")
    public ModelAndView newUser() {
        return new ModelAndView("new_user");
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/insert_user", method = { RequestMethod.GET, RequestMethod.POST })
    public String insertUser(@RequestParam String username, 
    		@RequestParam String password, 
    		@RequestParam String email,
    		@RequestParam String role,
    		RedirectAttributes redirectAttributes
    		) {
    	
    	userService.createUser(username, password, email, role);
    	
    	redirectAttributes.addFlashAttribute("message", "Usuario ingresado correctamene!");
    	redirectAttributes.addFlashAttribute("type_message", "create");
    	return "redirect:/manage_users";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/edit_user")
    public ModelAndView editUser(@RequestParam long id) {
    	user = userService.getUser(id);
        return new ModelAndView("edit_user").addObject("user", user);
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/update_user", method = { RequestMethod.GET, RequestMethod.POST })
    public String updateUser(
    		@RequestParam long id,
    		@RequestParam String username, 
    		@RequestParam String password,
    		@RequestParam String email,
    		RedirectAttributes redirectAttributes
    		) {
    	
    	userService.updateUser(id, username, password, email);
    	
    	redirectAttributes.addFlashAttribute("message", "Usuario actualizado correctamene!");
    	redirectAttributes.addFlashAttribute("type_message", "update");
    	return "redirect:/manage_users";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/warning_user")
    public ModelAndView warningUser(@RequestParam long id) {
    	user = userService.getUser(id);
        return new ModelAndView("warning_user").addObject("user", user);
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/delete_user", method = { RequestMethod.GET, RequestMethod.POST })
    public String deleteUser(@RequestParam long id, RedirectAttributes redirectAttributes) {
    	
    	userService.deleteUser(id);
    	
    	redirectAttributes.addFlashAttribute("message", "Usuario eliminado correctamene!");
    	redirectAttributes.addFlashAttribute("type_message", "delete");
    	return "redirect:/manage_users";
    }
    
    @Secured({ "ROLE_ADMIN" })
    @RequestMapping("/user")
    public ModelAndView user(@RequestParam long id) {
    	user = userService.getUser(id);
        return new ModelAndView("user").addObject("user", user);
    }
}
