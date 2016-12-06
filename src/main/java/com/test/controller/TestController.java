package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.services.model.SatToken;
import com.test.services.tes.TESClient;

@RestController
@RequestMapping(value = "/testService", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {
	
	@Autowired
	private TESClient tesClient;
	
	@RequestMapping(value = "/testSAT", method = RequestMethod.GET)
	public SatToken testSAT() {
		return tesClient.getSAT();
	}
}
