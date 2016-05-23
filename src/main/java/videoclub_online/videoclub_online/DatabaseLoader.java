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
        		"Inspirada en la teoría del experto en relatividad Kip Stepehen Thorne sobre la existencia de "
        		+ "los agujeros de gusano, y su función como canal para llevar a cabo los viajes en el tiempo. "
        		+ "La historia gira en torno a un grupo de intrépidos exploradores que se adentran por uno de esos "
        		+ "agujeros y viajan a través del mismo, encontrándose en otra dimensión. Un mundo desconocido se abre "
        		+ "ante ellos y deberán luchar por mantenerse unidos si quieren volver de una pieza.",
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
        		"Año 1823. En las profundidades de la América salvaje, el explorador Hugh Glass (Leonardo DiCaprio) "
        		+ "participa junto a su hijo mestizo Hawk en una expedición de tramperos que recolecta pieles. Glass "
        		+ "resulta gravemente herido por el ataque de un oso y es abandonado a su suerte por un traicionero "
        		+ "miembro de su equipo, John Fitzgerald (Tom Hardy). Con la fuerza de voluntad como su única arma, "
        		+ "Glass deberá enfrentarse a un territorio hostil, a un invierno brutal y a la guerra constante entre "
        		+ "las tribus de nativos americanos, en una búsqueda implacable para conseguir vengarse.",
        		2015, 
        		"Alejandro G. Iñárritu",
        		"Leonardo DiCaprio, Tom Hardy, Will Poulter", 
        		"http://ia.media-imdb.com/images/M/MV5BMjU4NDExNDM1NF5BMl5BanBnXkFtZTgwMDIyMTgxNzE@._V1_UX182_CR0,0,182,268_AL_.jpg",
        		4
        		));
    }

}