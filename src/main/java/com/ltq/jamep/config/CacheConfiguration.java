package com.ltq.jamep.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.ltq.jamep.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.ltq.jamep.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.ltq.jamep.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.ltq.jamep.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.ltq.jamep.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.ltq.jamep.domain.VCenter.class.getName(), jcacheConfiguration);
            cm.createCache(com.ltq.jamep.domain.PhysicalDatacenter.class.getName(), jcacheConfiguration);
            cm.createCache(com.ltq.jamep.domain.VMHostCluster.class.getName(), jcacheConfiguration);
            cm.createCache(com.ltq.jamep.domain.DatastoreCluster.class.getName(), jcacheConfiguration);
            cm.createCache(com.ltq.jamep.domain.DatastoreCluster.class.getName() + ".vmhosts", jcacheConfiguration);
            cm.createCache(com.ltq.jamep.domain.VMHost.class.getName(), jcacheConfiguration);
            cm.createCache(com.ltq.jamep.domain.VMHost.class.getName() + ".datastores", jcacheConfiguration);
            cm.createCache(com.ltq.jamep.domain.VMHost.class.getName() + ".datastoreclusters", jcacheConfiguration);
            cm.createCache(com.ltq.jamep.domain.Datastore.class.getName(), jcacheConfiguration);
            cm.createCache(com.ltq.jamep.domain.Datastore.class.getName() + ".vmhosts", jcacheConfiguration);
            cm.createCache(com.ltq.jamep.domain.StorageArray.class.getName(), jcacheConfiguration);
            cm.createCache(com.ltq.jamep.domain.VirtualVolume.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
