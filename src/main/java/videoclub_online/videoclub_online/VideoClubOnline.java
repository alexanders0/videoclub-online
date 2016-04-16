package videoclub_online.videoclub_online;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class VideoClubOnline extends WebMvcConfigurerAdapter {
	
	public static void main(String[] args) { 
		SpringApplication.run(VideoClubOnline.class, args);
	} 
	
}
