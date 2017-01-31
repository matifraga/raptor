package ar.edu.itba.paw.webapp.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class SearchDTO<T> {

	protected String type;
	private T results;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public T getResults() {
		return results;
	}

	public void setResults(T results) {
		this.results = results;
	}
	
}
