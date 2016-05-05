package videoclub_online.videoclub_online;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VideoClubController {
	
	Movie movie;
	
	@Autowired
	private MovieRepository repository;

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
    public ModelAndView home() {
    	Iterable<Movie> listMovies = repository.findAll(); //list of movies
        return new ModelAndView("home").addObject("movies", listMovies);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/root")
    public ModelAndView root() {
        return new ModelAndView("root");
    }
    
    @RequestMapping("/admin_panel")
    public ModelAndView adminPanel() {
        return new ModelAndView("admin_panel");
    }
    
    @RequestMapping("/manage_users")
    public ModelAndView manageUsers() {
        return new ModelAndView("manage_users");
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/new_movie")
    public ModelAndView newMovie() {
        return new ModelAndView("new_movie");
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/insert_movie")
    public ModelAndView insertMovie(@RequestParam String movie_name, 
    		@RequestParam String url_movie) {
    	
    	repository.save(new Movie(movie_name, url_movie));
        return new ModelAndView("insert_movie");
    }
    
    @RequestMapping("/search_movies")
    public ModelAndView searchMovies() {
        return new ModelAndView("search_movies");
    }
    
    @RequestMapping("/search_result")
    public ModelAndView searchResult(@RequestParam String movie_name) {
    	
    	Iterable<Movie> listMovies = repository.findByNameContaining(movie_name);
        return new ModelAndView("search_result").addObject("movies", listMovies);
    }
    
    @RequestMapping("/watch_movie")
    public ModelAndView watchMovie(@RequestParam long id) {
    	
    	movie = repository.findOne(id);
        return new ModelAndView("watch_movie").addObject("movie", movie);
    }
}
