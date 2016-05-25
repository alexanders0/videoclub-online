package videoclub_online.rest;

import java.util.ArrayList;
import java.util.List;

public class Data {
	
	private String id;
	private String type;
	private String title;
	private String year;
	private String description;
	private String certificate;
	private String duration;
	private String released;
	private List<String> cast = new ArrayList<String>();
	private List<String> genres = new ArrayList<String>();
	private List<String> directors = new ArrayList<String>();
	private List<String> writers = new ArrayList<String>();
	private String image;
	private Review review;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getReleased() {
		return released;
	}
	public void setReleased(String released) {
		this.released = released;
	}
	public String getCast() {
		String all_actors = "";
		int count = 1;
		for (String actor : cast) {
			if (count == 1) {
				all_actors += actor; 
			} else{
				all_actors += ", " + actor; 
			}
			count++;
		}
		return all_actors;
	}
	public void setCast(List<String> cast) {
		this.cast = cast;
	}
	public String getGenres() {
		String all_genres = "";
		int count = 1;
		for (String genre : genres) {
			if (count == 1) {
				all_genres += genre; 
			} else{
				all_genres += ", " + genre; 
			}
			count++;
		}
		return all_genres;
	}
	public void setGenres(List<String> genres) {
		this.genres = genres;
	}
	public String getDirectors() {
		String all_directors = "";
		int count = 1;
		for (String director : directors) {
			if (count == 1) {
				all_directors += director; 
			} else{
				all_directors += ", " + director; 
			}
			count++;
		}
		return all_directors;
	}
	public void setDirectors(List<String> directors) {
		this.directors = directors;
	}
	public String getWriters() {
		String all_writers = "";
		int count = 1;
		for (String writer : writers) {
			if (count == 1) {
				all_writers += writer; 
			} else{
				all_writers += ", " + writer; 
			}
			count++;
		}
		return all_writers;
	}
	public void setWriters(List<String> writers) {
		this.writers = writers;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Review getReview() {
		return review;
	}
	public void setReview(Review review) {
		this.review = review;
	}
	
}
