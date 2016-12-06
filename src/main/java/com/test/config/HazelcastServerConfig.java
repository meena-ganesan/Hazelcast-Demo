package com.test.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

import com.hazelcast.cache.impl.HazelcastServerCachingProvider;
import com.hazelcast.config.CacheSimpleConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@Configuration
@Profile("hazelcast-server")
@EnableCaching
public class HazelcastServerConfig extends CachingConfigurerSupport {
	
	private final static Logger log = LoggerFactory.getLogger(HazelcastServerConfig.class);
	
	@Value("${servers.hazelcast}")
    private String SERVER_LIST;
	
	private int PORT_NUMBER = 5701;
	
	@Bean
	@Override
    public CacheManager cacheManager() {
		CachingProvider cachingProvider = HazelcastServerCachingProvider.createCachingProvider(instance());
        javax.cache.CacheManager cacheManager = cachingProvider.getCacheManager();
        log.info(cacheManager.getCacheNames().toString());
        return new JCacheCacheManager(cacheManager);
	}
	
	@Bean
    public HazelcastInstance instance() {
        return Hazelcast.newHazelcastInstance(config());
    }
	
	public Config config() {
		Config config = new Config();
		NetworkConfig network = config.getNetworkConfig();
		network.setPort(PORT_NUMBER);
		JoinConfig join = network.getJoin();
		join.getMulticastConfig().setEnabled(false);
		join.getTcpIpConfig().addMember(SERVER_LIST);
		Map<String, CacheSimpleConfig> cacheConfigs = new HashMap<String, CacheSimpleConfig>();
		cacheConfigs.put("satCache", satCache());
		config.setCacheConfigs(cacheConfigs);
		return config;
	}
	
	public CacheSimpleConfig satCache() {
		CacheSimpleConfig config = new CacheSimpleConfig();
		config.setName("satCache");
		config.setExpiryPolicyFactoryConfig(new CacheSimpleConfig.ExpiryPolicyFactoryConfig(
				new CacheSimpleConfig.ExpiryPolicyFactoryConfig.TimedExpiryPolicyFactoryConfig(
		          CacheSimpleConfig.ExpiryPolicyFactoryConfig.TimedExpiryPolicyFactoryConfig.ExpiryPolicyType.CREATED, 
		          new CacheSimpleConfig.ExpiryPolicyFactoryConfig.DurationConfig(1, TimeUnit.MINUTES))));
		return config;
	}
}
