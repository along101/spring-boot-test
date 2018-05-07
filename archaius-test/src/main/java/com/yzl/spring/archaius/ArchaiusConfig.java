package com.yzl.spring.archaius;

import com.netflix.config.ConcurrentCompositeConfiguration;
import com.netflix.config.ConcurrentMapConfiguration;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.FixedDelayPollingScheduler;
import lombok.Setter;
import org.apache.commons.configuration.AbstractConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

/**
 * Created by yinzuolong on 2017/9/24.
 */
@Configuration
@ConfigurationProperties("archaius")
public class ArchaiusConfig implements EnvironmentAware, InitializingBean {
    private static final String CONFIG_NAME = "springEnv";
    @Setter
    private int pollDelayMillis = 5000;
    @Setter
    private int poolInitialDelayMillis = 5000;
    @Setter
    private boolean pollIgnoreDeletesFromSource = false;

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        SpringEnvConfigurationSource springEnvConfigurationSource = new SpringEnvConfigurationSource((ConfigurableEnvironment) this.environment);
        FixedDelayPollingScheduler scheduler = new FixedDelayPollingScheduler(poolInitialDelayMillis, pollDelayMillis, pollIgnoreDeletesFromSource);
        ConcurrentMapConfiguration configuration = new ConcurrentCompositeConfiguration();
        scheduler.startPolling(springEnvConfigurationSource, configuration);

        if (ConfigurationManager.isConfigurationInstalled()) {
            AbstractConfiguration installedConfiguration = ConfigurationManager.getConfigInstance();
            if (installedConfiguration instanceof ConcurrentCompositeConfiguration) {
                ConcurrentCompositeConfiguration configInstance = (ConcurrentCompositeConfiguration) installedConfiguration;
                if (configInstance.getConfiguration(CONFIG_NAME) == null)
                    configInstance.addConfigurationAtFront(configuration, CONFIG_NAME);
            }
        } else {
            ConcurrentCompositeConfiguration concurrentCompositeConfiguration = new ConcurrentCompositeConfiguration();
            concurrentCompositeConfiguration.addConfiguration(configuration, CONFIG_NAME);
            ConfigurationManager.install(concurrentCompositeConfiguration);
        }
    }
}
