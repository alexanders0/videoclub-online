package videoclub_online.rest;

import org.springframework.stereotype.Repository;

import retrofit.http.GET;
import retrofit.http.Path;

@Repository
public interface MovieRestRepository {
	
	@GET("/{index}")
	MovieRest getMovieRest(@Path("index") String string);
	
}
