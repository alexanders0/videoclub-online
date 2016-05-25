package videoclub_online.rest_search_movie;

public class SearchMovieRest {

	private String status;
	private String code;
	private String message;
	private String term;
	private String search_url;
	private Data data;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getSearch_url() {
		return search_url;
	}
	public void setSearch_url(String search_url) {
		this.search_url = search_url;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	
	
}
