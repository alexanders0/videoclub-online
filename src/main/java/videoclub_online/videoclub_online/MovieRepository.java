package videoclub_online.videoclub_online;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long>{
	
	List<Movie> findByName(String name);
	List<Movie> findByNameContaining(String name);

}
