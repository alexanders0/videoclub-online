package videoclub_online.videoclub_online;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	User user;
	
	@Autowired
	private UserRepository userRepository;
	
	GrantedAuthority[] userRoles = {
            new SimpleGrantedAuthority("ROLE_USER") };
	
	GrantedAuthority[] adminRoles = {
			new SimpleGrantedAuthority("ROLE_ADMIN") };
	
	public User getUser(Long id){
		return userRepository.findOne(id);	
	}
	
	public Iterable<User> getUserList(){
		return userRepository.findAll();
	}
	
	public void createUser(String username, String password, String email, String role){
    	if (role.equals("1")) {
    		userRepository.save(new User(username, password, email, Arrays.asList(userRoles)));
		} else {
			userRepository.save(new User(username, password, email, Arrays.asList(adminRoles)));
		}
	}
	
	public void updateUser(Long id, String username, String password, String email, String role){
		// fields updated
    	user = userRepository.findOne(id);
    	user.setUser(username);
    	user.setPasswordHash(password);
    	user.setEmail(email);
//    	if (role.equals("1")) {
//    		user.setRoles(Arrays.asList(userRoles));
//		} else {
//			user.setRoles(Arrays.asList(adminRoles));
//		}
    	
    	userRepository.save(user); //object saved
	}
	
	public void deleteUser(Long id){
		user = userRepository.findOne(id);
    	userRepository.delete(user);
	}

}
