package videoclub_online.rest;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

public interface MovieRestService {
	 
	@GET("/movieRest")
	 List<MovieRest> getMovieRest();
	
	@GET("/movieRest/{index}")
	MovieRest getMovieRest(@Path("index") String string);
	
}
