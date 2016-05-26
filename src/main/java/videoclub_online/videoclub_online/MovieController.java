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
public class MovieController {
	
	Movie movie;

	@Autowired
	private MovieService movieService;
	
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
    		listMovies = movieService.getMovieListBySearch(search); //movie list by parameter
    	} else{
    		listMovies = movieService.getMovieList(); //all movie list
    	}
    	
        return new ModelAndView("home").addObject("movies", listMovies);
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/new_movie")
    public ModelAndView newMovie() {
        return new ModelAndView("new_movie");
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/insert_movie", method = { RequestMethod.GET, RequestMethod.POST })
    public String insertMovie(
    		@RequestParam String movie_name, 
    		@RequestParam String url_movie,
    		@RequestParam String description,
    		@RequestParam(required = false) String year,
    		@RequestParam String director,
    		@RequestParam String actors,
    		@RequestParam String writer,
    		@RequestParam String duration,
    		@RequestParam String genre,
    		@RequestParam String url_cover_film,
    		@RequestParam(required = false) String rating,
    		RedirectAttributes redirectAttributes
    		) {
    	
    	movieService.createMovie(movie_name, url_movie, description, year, director, actors, writer, duration, genre, url_cover_film, rating);
    	
    	redirectAttributes.addFlashAttribute("message", "Película ingresada correctamene!");
    	redirectAttributes.addFlashAttribute("type_message", "create");
    	return "redirect:/home";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/edit_movie")
    public ModelAndView editMovie(@RequestParam long id) {
    	movie = movieService.getMovie(id);
        return new ModelAndView("edit_movie").addObject("movie", movie);
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/update_movie", method = { RequestMethod.GET, RequestMethod.POST })
    public String updateMovie(
    		@RequestParam long id,
    		@RequestParam String movie_name, 
    		@RequestParam String url_movie,
    		@RequestParam String description,
    		@RequestParam(required = false) String year,
    		@RequestParam String director,
    		@RequestParam String actors,
    		@RequestParam String url_cover_film,
    		@RequestParam(required = false) String rating,
    		RedirectAttributes redirectAttributes
    		) {
    	
    	movieService.updateMovie(id, movie_name, url_movie, description, year, director, actors, url_cover_film, rating);
    	
    	redirectAttributes.addFlashAttribute("message", "Película actualizada correctamene!");
    	redirectAttributes.addFlashAttribute("type_message", "update");
    	return "redirect:/home";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/warning_movie")
    public ModelAndView warningMovie(@RequestParam long id) {
    	movie = movieService.getMovie(id);
        return new ModelAndView("warning_movie").addObject("movie", movie);
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/delete_movie", method = { RequestMethod.GET, RequestMethod.POST })
    public String deleteMovie(@RequestParam long id, RedirectAttributes redirectAttributes) {
    	
    	movieService.deleteMovie(id);
    	
    	redirectAttributes.addFlashAttribute("message", "Película eliminada correctamene!");
    	redirectAttributes.addFlashAttribute("type_message", "delete");
    	return "redirect:/home";
    }
    
    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    @RequestMapping("/watch_movie")
    public ModelAndView watchMovie(@RequestParam long id) {
    	movie = movieService.getMovie(id);
        return new ModelAndView("watch_movie").addObject("movie", movie);
    }
}
