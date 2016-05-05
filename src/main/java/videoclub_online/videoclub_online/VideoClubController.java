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
    public ModelAndView insertMovie(
    		@RequestParam String movie_name, 
    		@RequestParam String url_movie,
    		@RequestParam String description,
    		@RequestParam String year,
    		@RequestParam String director,
    		@RequestParam String actors,
    		@RequestParam String url_cover_film,
    		@RequestParam int rating
    		) {
    	
    	repository.save(new Movie(movie_name, url_movie, description, 
    			year, director, actors, url_cover_film, rating));
    	
        return new ModelAndView("insert_movie");
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/edit_movie")
    public ModelAndView editMovie(@RequestParam long id) {
    	movie = repository.findOne(id);
        return new ModelAndView("edit_movie").addObject("movie", movie);
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/update_movie")
    public ModelAndView updateMovie(
    		@RequestParam long id,
    		@RequestParam String movie_name, 
    		@RequestParam String url_movie,
    		@RequestParam String description,
    		@RequestParam String year,
    		@RequestParam String director,
    		@RequestParam String actors,
    		@RequestParam String url_cover_film,
    		@RequestParam int rating
    		) {
    	
//    	fields updated
    	movie = repository.findOne(id);
    	movie.setName(movie_name);
    	movie.setUrlMovie(url_movie);
    	movie.setDescription(description);
    	movie.setYear(year);
    	movie.setDirector(director);
    	movie.setActors(actors);
    	movie.setUrlCoverFilm(url_cover_film);
    	movie.setRating(rating);
    	
    	repository.save(movie); //object saved
    	
        return new ModelAndView("update_movie");
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/delete_movie")
    public ModelAndView deleteMovie(@RequestParam long id) {
    	movie = repository.findOne(id);
    	repository.delete(movie);
        return new ModelAndView("delete_movie");
    }
    
    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    @RequestMapping("/search_result")
    public ModelAndView searchResult(@RequestParam String movie_name) {
    	
    	Iterable<Movie> listMovies = repository.findByNameContaining(movie_name);
        return new ModelAndView("search_result").addObject("movies", listMovies);
    }
    
    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    @RequestMapping("/watch_movie")
    public ModelAndView watchMovie(@RequestParam long id) {
    	movie = repository.findOne(id);
        return new ModelAndView("watch_movie").addObject("movie", movie);
    }
}
