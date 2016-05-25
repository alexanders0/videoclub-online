package videoclub_online.videoclub_online;

import java.util.Arrays;
import java.util.List;

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

import retrofit.RestAdapter;
import videoclub_online.rest.MovieRest;
import videoclub_online.rest.MovieRestService;
import videoclub_online.rest_search_movie.SearchMovieRest;
import videoclub_online.rest_search_movie.SearchMovieRestService;

import videoclub_online.rest_search_movie.*;

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
    @RequestMapping(value = "/insert_user", method = { RequestMethod.GET, RequestMethod.POST })
    public String insertUser(@RequestParam String username, 
    		@RequestParam String password, 
    		@RequestParam String email,
    		@RequestParam String role,
    		RedirectAttributes redirectAttributes
    		) {
    	
    	if (role.equals("1")) {
    		userRepository.save(new User(username, password, email, Arrays.asList(userRoles)));
		} else {
			userRepository.save(new User(username, password, email, Arrays.asList(adminRoles)));
		}
    	
    	redirectAttributes.addFlashAttribute("message", "Usuario ingresado correctamene!");
    	redirectAttributes.addFlashAttribute("type_message", "create");
    	return "redirect:/manage_users";
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
    	redirectAttributes.addFlashAttribute("message", "Usuario actualizado correctamene!");
    	redirectAttributes.addFlashAttribute("type_message", "update");
    	return "redirect:/manage_users";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/warning_user")
    public ModelAndView warningUser(@RequestParam long id) {
    	user = userRepository.findOne(id);
        return new ModelAndView("warning_user").addObject("user", user);
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/delete_user", method = { RequestMethod.GET, RequestMethod.POST })
    public String deleteUser(@RequestParam long id, RedirectAttributes redirectAttributes) {
    	user = userRepository.findOne(id);
    	userRepository.delete(user);
    	redirectAttributes.addFlashAttribute("message", "Usuario eliminado correctamene!");
    	redirectAttributes.addFlashAttribute("type_message", "delete");
    	return "redirect:/manage_users";
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
    @RequestMapping(value = "/insert_movie", method = { RequestMethod.GET, RequestMethod.POST })
    public String insertMovie(
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
    	
    	// Search Movie Rest Service
    	RestAdapter search_restadapter = new RestAdapter.Builder().setEndpoint("http://imdb.wemakesites.net/api/").build();
    	SearchMovieRestService search_service = search_restadapter.create(SearchMovieRestService.class);
    	SearchMovieRest search_movie = search_service.getgetSearchMovieRest("search?q=Star%20Wars");
    	
//    	System.out.println("URL de pelicula: "+search_movie.getData().getResults().getTitles().get(1).getTitle());
    	List<Title> pelis;
    	pelis = search_movie.getData().getResults().getTitles();
    	for (Title title : pelis) {
    		System.out.println("Titulos: "+title.getUrl());
		}
  
    	
    	// Rest Service
    	RestAdapter restadapter = new RestAdapter.Builder().setEndpoint("http://imdb.wemakesites.net/api/").build();
    	MovieRestService service = restadapter.create(MovieRestService.class);
    	MovieRest rest_movie = service.getMovieRest("tt1663202");
    	
    	Movie movie = new Movie();
    	movie.setName(movie_name);
    	movie.setUrlMovie(url_movie);
    	
    	// Rest Data
    	movie.setDescription(rest_movie.getData().getDescription());
    	if (rest_movie.getData().getYear() != null) {
    		movie.setYear(rest_movie.getData().getYear());
		}
    	if (!rest_movie.getData().getDirectors().isEmpty()) {
    		movie.setDirector(rest_movie.getData().getDirectors());
		}
    	movie.setActors(rest_movie.getData().getCast());
    	movie.setUrlCoverFilm(rest_movie.getData().getImage());
    	movie.setRating(rest_movie.getData().getReview().getRating());
    	movie.setDuration(rest_movie.getData().getDuration());
    	movie.setWriter(rest_movie.getData().getWriters());
    	movie.setGenre(rest_movie.getData().getGenres());
    	
    	//Movie Saved
    	movieRepository.save(movie);
    	
    	
//    	movieRepository.save(new Movie(movie_name, url_movie));
    	
    	redirectAttributes.addFlashAttribute("message", "Película ingresada correctamene!");
    	redirectAttributes.addFlashAttribute("type_message", "create");
    	return "redirect:/home";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/edit_movie")
    public ModelAndView editMovie(@RequestParam long id) {
    	movie = movieRepository.findOne(id);
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
    	
    	redirectAttributes.addFlashAttribute("message", "Película actualizada correctamene!");
    	redirectAttributes.addFlashAttribute("type_message", "update");
    	return "redirect:/home";
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping("/warning_movie")
    public ModelAndView warningMovie(@RequestParam long id) {
    	movie = movieRepository.findOne(id);
        return new ModelAndView("warning_movie").addObject("movie", movie);
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/delete_movie", method = { RequestMethod.GET, RequestMethod.POST })
    public String deleteMovie(@RequestParam long id, RedirectAttributes redirectAttributes) {
    	movie = movieRepository.findOne(id);
    	movieRepository.delete(movie);
    	redirectAttributes.addFlashAttribute("message", "Película eliminada correctamene!");
    	redirectAttributes.addFlashAttribute("type_message", "delete");
    	return "redirect:/home";
    }
    
    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    @RequestMapping("/watch_movie")
    public ModelAndView watchMovie(@RequestParam long id) {
    	movie = movieRepository.findOne(id);
        return new ModelAndView("watch_movie").addObject("movie", movie);
    }
}
