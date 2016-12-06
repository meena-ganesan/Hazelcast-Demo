package com.test.config;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.cache.impl.HazelcastClientCachingProvider;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;

@Configuration
@Profile("hazelcast-client")
@EnableCaching
public class HazelcastClientConfig extends CachingConfigurerSupport {
	
	private final static Logger log = LoggerFactory.getLogger(HazelcastClientConfig.class);
	
	@Value("${servers.hazelcast}")
	private String SERVER_LIST;
	
	@Bean
	@Override
	public CacheManager cacheManager() {
		CachingProvider cachingProvider = HazelcastClientCachingProvider.createCachingProvider(instance());
		javax.cache.CacheManager cacheManager = cachingProvider.getCacheManager();
		cacheManager.createCache("satCache", cacheConfig());
		log.info(cacheManager.getCacheNames().toString());
		return new JCacheCacheManager(cacheManager);
	}

	public HazelcastInstance instance() {
		log.info("SERVER_LIST: " + SERVER_LIST);
		ClientConfig clientConfig = new ClientConfig();
		ClientNetworkConfig clientNetworkConfig = new ClientNetworkConfig();
		clientNetworkConfig.addAddress(SERVER_LIST.split(","));
		clientConfig.setNetworkConfig(clientNetworkConfig);
		clientConfig.setClassLoader(getClass().getClassLoader());
		HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
		return client;
	}
	
	private MutableConfiguration<String, Object> cacheConfig() {
		MutableConfiguration<String, Object> config = new MutableConfiguration<>();
		config.setTypes(String.class, Object.class);
        config.setStoreByValue(true);
        config.setStoreByValue(true);
        config.setStatisticsEnabled(true);
        config.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(
                Duration.ONE_MINUTE));
        return config;
	}

}
