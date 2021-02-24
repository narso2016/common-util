package com.libs.response.rest;

public class ResponseLogin extends CommonStatus{
	//BOSNIAN;
	
	private String korisnik;
	private String uspomena;
	private String expired;
	
	public ResponseLogin(int status) {
		super(status);
	}

	public ResponseLogin(int status, String message) {
		super(status, message);
	}
	
	public ResponseLogin(int status, String code, String message, String korisnik, String uspomena) {
		super(status, code, message);
		this.korisnik = korisnik;
		this.uspomena = uspomena;
	}
	
	public ResponseLogin(int status, String code, String message, String korisnik, String uspomena, String expired) {
		super(status, code, message);
		this.korisnik = korisnik;
		this.uspomena = uspomena;
		this.expired = expired;
	}
	
	public ResponseLogin(int status, String message, String korisnik, String uspomena) {
		super(status, message);
		this.korisnik = korisnik;
		this.uspomena = uspomena;
	}

	/**
	 * @return the korisnik
	 */
	public String getKorisnik() {
		return korisnik;
	}

	/**
	 * @param korisnik the korisnik to set
	 */
	public void setKorisnik(String korisnik) {
		this.korisnik = korisnik;
	}

	/**
	 * @return the uspomena
	 */
	public String getUspomena() {
		return uspomena;
	}

	/**
	 * @param uspomena the uspomena to set
	 */
	public void setUspomena(String uspomena) {
		this.uspomena = uspomena;
	}

	public String getExpired() {
		return expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}
	


}
