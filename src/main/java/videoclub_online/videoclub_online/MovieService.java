package videoclub_online.videoclub_online;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.debatty.java.stringsimilarity.JaroWinkler;
import retrofit.RestAdapter;
import videoclub_online.rest.MovieRest;
import videoclub_online.rest.MovieRestRepository;
import videoclub_online.rest_search_movie.SearchMovieRest;
import videoclub_online.rest_search_movie.SearchMovieRestRepository;
import videoclub_online.rest_search_movie.Title;

@Service
public class MovieService {
	
	Movie movie;
	
	@Autowired
	private MovieRepository movieRepository;

	public void createMovie(
			String movie_name,
			String url_movie,
			String description,
			String year,
			String director,
			String actors,
			String url_cover_film,
			String rating){
		
    	// Search Movie Rest Service
    	RestAdapter search_restadapter = new RestAdapter.Builder().setEndpoint("http://imdb.wemakesites.net/api").build();
    	SearchMovieRestRepository search_service = search_restadapter.create(SearchMovieRestRepository.class);
    	SearchMovieRest search_movie = search_service.getSearchMovieRest(movie_name.replace(" ", "%20"));
    	System.out.println("URL search" +search_movie.getSearch_url());
    	
    	String id = null;
    	List<Title> movies_rest;
    	movies_rest = search_movie.getData().getResults().getTitles();
    	
    	// creas tu objeto de la clase JaroWinkler y llamas a su metodo similarity
    	JaroWinkler comparison = new JaroWinkler();
    	double val_simil;
    	val_simil = comparison.similarity("hola", "hola1");
    	
    	for (Title title : movies_rest) {
    		if (title.getTitle().equals(movie_name)) {
				id = title.getId();
				break;
			}
    		val_simil = comparison.similarity(title.getTitle(), movie_name);
    		System.out.println("Similitud: "+ val_simil);
    		System.out.print(" Titulos: "+title.getTitle()+" - "+title.getId());
		}
    	
    	System.out.println("ID_URL: "+id);
  
    	// Rest Service
    	RestAdapter restadapter = new RestAdapter.Builder().setEndpoint("http://imdb.wemakesites.net/api/").build();
    	MovieRestRepository service = restadapter.create(MovieRestRepository.class);
    	MovieRest rest_movie = service.getMovieRest(id);
    	
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
	}
	
	public void updateMovie(
			Long id,
			String movie_name,
			String url_movie,
			String description,
			String year,
			String director,
			String actors,
			String url_cover_film,
			String rating){
		
    	// fields updated
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
		
	}
	
	public void deleteMovie(Long id){
		movie = movieRepository.findOne(id);
    	movieRepository.delete(movie);
	}

	public Movie getMovie(Long id){
		return movieRepository.findOne(id);	
	}
	
	public Iterable<Movie> getMovieList(){
		return movieRepository.findAll();
	}
	
	public Iterable<Movie> getMovieListBySearch(String search_value){
		return movieRepository.findByNameContaining(search_value);
	}
	
}