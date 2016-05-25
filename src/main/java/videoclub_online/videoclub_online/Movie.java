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
	private String year;
	private String director;
	@Column(length = 1000)
	private String actors;
	private String urlCoverFilm;
	private String rating;
	
	private String duration;
	private String writer;
	private String genre;
	
	public Movie(){
	}
	
	public Movie(String nombre, String urlPelicula){
		this.name = nombre;
		this.urlMovie = urlPelicula;
	}
	
	public Movie(String nombre, String urlPelicula, String descripcion, String anio, String director, String actores,
			String urlPortada, String valoracion, String duration, String writer, String genre) {
		this.name = nombre;
		this.urlMovie = urlPelicula;
		this.description = descripcion;
		this.year = anio;
		this.director = director;
		this.actors = actores;
		this.urlCoverFilm = urlPortada;
		this.rating = valoracion;
		this.duration = duration;
		this.writer = writer;
		this.genre = genre;
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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
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

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
}
