package videoclub_online.rest_search_movie;

import java.util.ArrayList;
import java.util.List;

public class Results {

	private List<Name> names = new ArrayList<Name>();
	private List<Character> characters = new ArrayList<Character>();
	private List<Title> titles = new ArrayList<Title>();
	private List<Keyword> keywords = new ArrayList<Keyword>();
	private List<Company> companies = new ArrayList<Company>();
	
	public List<Name> getNames() {
		return names;
	}
	public void setNames(List<Name> names) {
		this.names = names;
	}
	public List<Character> getCharacters() {
		return characters;
	}
	public void setCharacters(List<Character> characters) {
		this.characters = characters;
	}
	public List<Title> getTitles() {
		return titles;
	}
	public void setTitles(List<Title> titles) {
		this.titles = titles;
	}
	public List<Keyword> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}
	public List<Company> getCompanies() {
		return companies;
	}
	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	
}
