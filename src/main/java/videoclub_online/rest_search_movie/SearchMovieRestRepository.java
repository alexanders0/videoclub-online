package videoclub_online.rest_search_movie;

import retrofit.http.GET;
import retrofit.http.Query;

public interface SearchMovieRestRepository {

	 @GET("/search")
	 SearchMovieRest getSearchMovieRest(@Query("q") String string);
}
