package videoclub_online.rest_search_movie;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

public interface SearchMovieRestService {

	 @GET("/searchMovieRest")
	 List<SearchMovieRest> getSearchMovieRest();
	 
	 @GET("/searchMovieRest/{index}")
	 SearchMovieRest getgetSearchMovieRest(@Path("index") String string);
}
