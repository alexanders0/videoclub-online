package videoclub_online.videoclub_online;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VideoClubController {
	
	Movie movie;
	User user;
	
	GrantedAuthority[] userRoles = {
            new SimpleGrantedAuthority("ROLE_USER") };
	
	GrantedAuthority[] adminRoles = {
			new SimpleGrantedAuthority("ROLE_USER"),
			new SimpleGrantedAuthority("ROLE_ADMIN") };
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
    private UserRepository userRepository;

	@RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }
    
    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    @RequestMapping("/home")
    public ModelAndView home(@RequestParam(required = false) String search) {
    	Iterable<Movie> listMovies;
    	if (search != null){
    		listMovies = movieRepository.findByNameContaining(search); //movie list by parameter
    	} else{
    		listMovies = movieRepository.findAll(); //all movie list
    	}
    	
        return new ModelAndView("home").addObject("movies", listMovies);
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/manage_users")
    public ModelAndView manageUsers() {
    	Iterable<User> listUsers = userRepository.findAll(); //list of users
        return new ModelAndView("manage_users").addObject("users", listUsers);
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/new_user")
    public ModelAndView newUser() {
        return new ModelAndView("new_user");
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/insert_user")
    public ModelAndView insertUser(@RequestParam String username, 
    		@RequestParam String password, 
    		@RequestParam String email,
    		@RequestParam String role
    		) {
    	
    	if (role.equals("1")) {
    		userRepository.save(new User(username, password, email, Arrays.asList(userRoles)));
		} else {
			userRepository.save(new User(username, password, email, Arrays.asList(adminRoles)));
		}
    	
        return new ModelAndView("insert_user");
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/edit_user")
    public ModelAndView editUser(@RequestParam long id) {
    	user = userRepository.findOne(id);
        return new ModelAndView("edit_user").addObject("user", user);
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/update_user", method = { RequestMethod.GET, RequestMethod.POST })
    public String updateUser(
    		@RequestParam long id,
    		@RequestParam String username, 
    		@RequestParam String password,
    		@RequestParam String email,
    		@RequestParam String role,
    		RedirectAttributes redirectAttributes
    		) {
    	
//    	fields updated
    	user = userRepository.findOne(id);
    	user.setUser(username);
    	user.setPasswordHash(password);
    	user.setEmail(email);
    	
//    	if (role.equals("1")) {
//    		user.setRoles(Arrays.asList(userRoles));
//		} else {
//			user.setRoles(Arrays.asList(adminRoles));
//		}
//    	
    	userRepository.save(user); //object saved
    	String message = "Usuario actualizado correctamene!";
    	redirectAttributes.addFlashAttribute("message", message);
    	return "redirect:/manage_users";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/warning_user")
    public ModelAndView warningUser(@RequestParam long id) {
    	user = userRepository.findOne(id);
        return new ModelAndView("warning_user").addObject("user", user);
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/delete_user")
    public ModelAndView deleteUser(@RequestParam long id) {
    	user = userRepository.findOne(id);
    	userRepository.delete(user);
        return new ModelAndView("delete_user");
    }
    
    @Secured({ "ROLE_ADMIN" })
    @RequestMapping("/user")
    public ModelAndView user(@RequestParam long id) {
    	user = userRepository.findOne(id);
        return new ModelAndView("user").addObject("user", user);
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/new_movie")
    public ModelAndView newMovie() {
        return new ModelAndView("new_movie");
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/insert_movie")
    public ModelAndView insertMovie(
    		@RequestParam String movie_name, 
    		@RequestParam String url_movie,
    		@RequestParam String description,
    		@RequestParam(required = false) int year,
    		@RequestParam String director,
    		@RequestParam String actors,
    		@RequestParam String url_cover_film,
    		@RequestParam(required = false) int rating
    		) {
    	
    	movieRepository.save(new Movie(movie_name, url_movie));
    	
        return new ModelAndView("insert_movie");
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/edit_movie")
    public ModelAndView editMovie(@RequestParam long id) {
    	movie = movieRepository.findOne(id);
        return new ModelAndView("edit_movie").addObject("movie", movie);
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/update_movie")
    public ModelAndView updateMovie(
    		@RequestParam long id,
    		@RequestParam String movie_name, 
    		@RequestParam String url_movie,
    		@RequestParam String description,
    		@RequestParam(required = false) int year,
    		@RequestParam String director,
    		@RequestParam String actors,
    		@RequestParam String url_cover_film,
    		@RequestParam(required = false) int rating
    		) {
    	
//    	fields updated
    	movie = movieRepository.findOne(id);
    	movie.setName(movie_name);
    	movie.setUrlMovie(url_movie);
    	movie.setDescription(description);
    	movie.setYear(year);
    	movie.setDirector(director);
    	movie.setActors(actors);
    	movie.setUrlCoverFilm(url_cover_film);
    	movie.setRating(rating);
    	
    	movieRepository.save(movie); //object saved
    	
        return new ModelAndView("update_movie");
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/warning_movie")
    public ModelAndView warningMovie(@RequestParam long id) {
    	movie = movieRepository.findOne(id);
        return new ModelAndView("warning_movie").addObject("movie", movie);
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/delete_movie")
    public ModelAndView deleteMovie(@RequestParam long id) {
    	movie = movieRepository.findOne(id);
    	movieRepository.delete(movie);
        return new ModelAndView("delete_movie");
    }
    
    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    @RequestMapping("/watch_movie")
    public ModelAndView watchMovie(@RequestParam long id) {
    	movie = movieRepository.findOne(id);
        return new ModelAndView("watch_movie").addObject("movie", movie);
    }
}
