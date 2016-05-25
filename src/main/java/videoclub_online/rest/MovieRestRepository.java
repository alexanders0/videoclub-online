package videoclub_online.rest;

import retrofit.http.GET;
import retrofit.http.Path;

public interface MovieRestRepository {
	
	@GET("/{index}")
	MovieRest getMovieRest(@Path("index") String string);
	
}
