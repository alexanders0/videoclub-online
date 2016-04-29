package videoclub_online.videoclub_online;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUser(String user);

}
