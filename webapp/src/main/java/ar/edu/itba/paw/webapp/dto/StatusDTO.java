package ar.edu.itba.paw.webapp.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StatusDTO {

	private String status;

	/* default */ StatusDTO() {}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
