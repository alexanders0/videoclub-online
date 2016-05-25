package videoclub_online.videoclub_online;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long>{
	
	Movie findByName(String name);
	List<Movie> findByNameContaining(String name);

}
