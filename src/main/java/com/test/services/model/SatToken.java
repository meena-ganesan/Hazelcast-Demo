package com.test.services.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SAT Token model returned in the Token Exchange Service response
 * 
 * @author mganes004c
 * @since 2016-11-07
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SatToken implements java.io.Serializable {

	private static final long serialVersionUID = 373288294867241910L;

	private String token;
	private long expiresIn;

	@JsonCreator
	public SatToken(@JsonProperty("token") String token, @JsonProperty("expiresIn") long expiresIn) {
		this.token = token;
		this.expiresIn = expiresIn;
	}

	/**
	 * 
	 * @return The token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * 
	 * @param token
	 *            The serviceAccessToken
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * 
	 * @return The expiresIn
	 */
	public long getExpiresIn() {
		return expiresIn;
	}

	/**
	 * 
	 * @param expiresIn
	 *            The expires_in
	 */
	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}
}