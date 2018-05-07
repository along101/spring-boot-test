package com.yzl.spring.archaius;

import com.netflix.config.PollResult;
import com.netflix.config.PolledConfigurationSource;
import org.springframework.core.env.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by yinzuolong on 2017/9/24.
 */
public class SpringEnvConfigurationSource implements PolledConfigurationSource {

    private ConfigurableEnvironment environment;
    private PropertySourcesPropertyResolver resolver;

    SpringEnvConfigurationSource(ConfigurableEnvironment environment) {
        this.environment = environment;
        this.resolver = new PropertySourcesPropertyResolver(this.environment.getPropertySources());
        this.resolver.setIgnoreUnresolvableNestedPlaceholders(true);

    }

    @Override
    public PollResult poll(boolean initial, Object checkPoint) throws Exception {
        return PollResult.createFull(getProperties());
    }

    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new LinkedHashMap<>();
        for (Map.Entry<String, PropertySource<?>> entry : getPropertySources().entrySet()) {
            PropertySource<?> source = entry.getValue();
            if (source instanceof EnumerablePropertySource) {
                EnumerablePropertySource<?> enumerable = (EnumerablePropertySource<?>) source;
                for (String name : enumerable.getPropertyNames()) {
                    if (!properties.containsKey(name)) {
                        properties.put(name, resolver.getProperty(name));
                    }
                }
            }
        }
        return properties;
    }

    private Map<String, PropertySource<?>> getPropertySources() {
        Map<String, PropertySource<?>> map = new LinkedHashMap<String, PropertySource<?>>();
        MutablePropertySources sources = null;
        if (environment != null) {
            sources = environment.getPropertySources();
        } else {
            sources = new StandardEnvironment().getPropertySources();
        }
        for (PropertySource<?> source : sources) {
            extract("", map, source);
        }
        return map;
    }

    private void extract(String root, Map<String, PropertySource<?>> map,
                         PropertySource<?> source) {
        if (source instanceof CompositePropertySource) {
            for (PropertySource<?> nest : ((CompositePropertySource) source)
                    .getPropertySources()) {
                extract(source.getName() + ":", map, nest);
            }
        } else {
            map.put(root + source.getName(), source);
        }
    }
}
