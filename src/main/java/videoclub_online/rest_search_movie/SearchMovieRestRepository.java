package videoclub_online.rest_search_movie;

import org.springframework.stereotype.Repository;

import retrofit.http.GET;
import retrofit.http.Query;

@Repository
public interface SearchMovieRestRepository {

	 @GET("/search")
	 SearchMovieRest getSearchMovieRest(@Query("q") String string);
}
