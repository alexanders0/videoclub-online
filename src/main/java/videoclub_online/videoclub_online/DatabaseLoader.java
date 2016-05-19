package videoclub_online.videoclub_online;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
	private MovieRepository movieRepository;

    @PostConstruct
    private void initDatabase() {
        // User #1: "user", with password "p1" and role "USER"
        GrantedAuthority[] userRoles = {
                new SimpleGrantedAuthority("ROLE_USER") };
        
        userRepository.save(new User("user", "p1", "user@upm.es", Arrays.asList(userRoles)));

        // User #2: "root", with password "p2" and roles "USER" and "ADMIN"
        GrantedAuthority[] adminRoles = {
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_ADMIN") };
        
        userRepository.save(new User("root", "p2", "root@upm.es", Arrays.asList(adminRoles)));
        
        // Example movie 1
        movieRepository.save( new Movie( 
        		"Interestelar",
        		"https://www.youtube.com/embed/qPBRz8LcWn0",
        		"Interestelar description",
        		2014, 
        		"Christopher Nolan",
        		"Matthew McConaughey, Anne Hathaway, Jessica Chastain", 
        		"http://ia.media-imdb.com/images/M/MV5BMjIxNTU4MzY4MF5BMl5BanBnXkFtZTgwMzM4ODI3MjE@._V1_UX182_CR0,0,182,268_AL_.jpg",
        		5
        		));
        
        // Example movie 2
        movieRepository.save( new Movie( 
        		"The revenant",
        		"https://www.youtube.com/embed/QRfj1VCg16Y",
        		"The Revenant description",
        		2015, 
        		"Alejandro G. Iñárritu",
        		"Leonardo DiCaprio, Tom Hardy, Will Poulter", 
        		"http://ia.media-imdb.com/images/M/MV5BMjU4NDExNDM1NF5BMl5BanBnXkFtZTgwMDIyMTgxNzE@._V1_UX182_CR0,0,182,268_AL_.jpg",
        		4
        		));
    }

}