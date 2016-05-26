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
			String writer,
    		String duration,
    		String genre,
			String url_cover_film,
			String rating){
		
    	// Search Movie Rest Service
    	RestAdapter search_restadapter = new RestAdapter.Builder().setEndpoint("http://imdb.wemakesites.net/api").build();
    	SearchMovieRestRepository search_service = search_restadapter.create(SearchMovieRestRepository.class);
    	SearchMovieRest search_movie = search_service.getSearchMovieRest(movie_name.replace(" ", "%20"));
    	
    	
    	List<Title> movies_rest = search_movie.getData().getResults().getTitles(); //list of movies
    	
    	movie = new Movie();
    	movie.setName(movie_name);
    	movie.setUrlMovie(url_movie);
    	
    	// If list of movies is not empty, we search an IMDB movie ID
    	
		String id = getRestId(movie_name, movies_rest); //IMDB movie id with greater similarity
		
		if (id != null) {
			
        	// Movie Rest Service
        	RestAdapter restadapter = new RestAdapter.Builder().setEndpoint("http://imdb.wemakesites.net/api/").build();
        	MovieRestRepository service = restadapter.create(MovieRestRepository.class);
        	MovieRest rest_movie = service.getMovieRest(id);
        	
        	// Assignment of data
        	
        	// description
        	if (description.equals("") && rest_movie.getData().getDescription() != null) {
        		movie.setDescription(rest_movie.getData().getDescription());
    		} else {
    			movie.setDescription(description);
    		}
        	
        	// year
        	if (year.equals("") && rest_movie.getData().getYear() != null) {
        		movie.setYear(rest_movie.getData().getYear());
    		} else {
    			movie.setYear(year);
    		}
        	
        	// director
        	if (director.equals("") && !rest_movie.getData().getDirectors().isEmpty()) {
        		movie.setDirector(rest_movie.getData().getDirectors());
    		} else {
    			movie.setDirector(director);
    		}
        	
        	// actors
        	if (actors.equals("") && !rest_movie.getData().getCast().isEmpty()) {
        		movie.setActors(rest_movie.getData().getCast());
    		} else {
    			movie.setActors(actors);
    		}
        	
        	// writers
        	if (writer.equals("") && !rest_movie.getData().getWriters().isEmpty()) {
        		movie.setWriter(rest_movie.getData().getWriters());
    		} else {
    			movie.setWriter(writer);
    		}
        	
        	// duration
        	if (duration.equals("") && rest_movie.getData().getDuration() != null) {
        		movie.setDuration(rest_movie.getData().getDuration());
    		} else {
    			movie.setDuration(duration);
    		}

        	// genres
        	if (genre.equals("") && !rest_movie.getData().getGenres().isEmpty()) {
        		movie.setGenre(rest_movie.getData().getGenres());
    		} else {
    			movie.setGenre(genre);
    		}
        	
        	// url_cover_film
        	if (url_cover_film.equals("") && rest_movie.getData().getImage() != null) {
        		movie.setUrlCoverFilm(rest_movie.getData().getImage());
    		} else {
    			movie.setUrlCoverFilm(url_cover_film);
    		}
        	
        	// rating
        	if (rating.equals("") && rest_movie.getData().getReview().getRating() != null) {
        		movie.setRating(rest_movie.getData().getReview().getRating());
    		} else {
    			movie.setRating(rating+"/10");
    		}
        	
        	//Movie Saved
		}
    	
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
			String writer,
			String duration,
			String genre,
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
    	movie.setWriter(writer);
    	movie.setDuration(duration);
    	movie.setGenre(genre);
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
	
	public String getRestId(String movie_name, List<Title> movies_rest){
		// Comparison between movie_name and rest list of movies with JaroWinkler library
    	JaroWinkler comparison = new JaroWinkler();
    	double val_simil;
    	double greater_simil = 0;
    	String id = null;
    	
    	for (Title title : movies_rest) {
    		val_simil = comparison.similarity(movie_name, title.getTitle());
    		System.out.print("Similitud: "+ val_simil);
    		System.out.println(" Titulos: "+title.getTitle()+" - "+title.getId());
    		if (val_simil > greater_simil) {
				greater_simil = val_simil;
				id = title.getId();
			}
		}
    	
    	System.out.println("ID_IMDB: "+id);
		return id;
	}
	
}