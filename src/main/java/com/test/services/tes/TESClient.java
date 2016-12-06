package com.test.services.tes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.test.services.model.SatToken;

@Service(value = "tesClient")
public class TESClient {
	private final static Logger log = LoggerFactory.getLogger(TESClient.class);
	
	// This will actually make the REST call
	@Cacheable(value="satCache", 
			  key="{ #root.methodName, new String('test') }")
	public SatToken getSAT() {
		try {
			log.info("Sleeping for 500 ms");
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new SatToken("dummy token", 30);
	}

}
