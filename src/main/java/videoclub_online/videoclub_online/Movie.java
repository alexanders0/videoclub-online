package videoclub_online.videoclub_online;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Movie {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	private String urlMovie;
	
	@Column(length = 1000)
	private String description;
	private int year;
	private String director;
	private String actors;
	private String urlCoverFilm;
	private int rating;
	
	public Movie(){
	}
	
	public Movie(String nombre, String urlPelicula){
		this.name = nombre;
		this.urlMovie = urlPelicula;
	}
	
	public Movie(String nombre, String urlPelicula, String descripcion, int anio, String director, String actores,
			String urlPortada, int valoracion) {
		this.name = nombre;
		this.urlMovie = urlPelicula;
		this.description = descripcion;
		this.year = anio;
		this.director = director;
		this.actors = actores;
		this.urlCoverFilm = urlPortada;
		this.rating = valoracion;
	}

	//	Getters and Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlMovie() {
		return urlMovie;
	}

	public void setUrlMovie(String urlMovie) {
		this.urlMovie = urlMovie;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getUrlCoverFilm() {
		return urlCoverFilm;
	}

	public void setUrlCoverFilm(String urlCoverFilm) {
		this.urlCoverFilm = urlCoverFilm;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
}
