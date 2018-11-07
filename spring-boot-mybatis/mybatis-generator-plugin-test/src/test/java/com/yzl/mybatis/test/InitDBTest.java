package com.yzl.mybatis.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

/**
 * @author yutu
 * @date 2018/11/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@EnableConfigurationProperties(DataSourceProperties.class)
public class InitDBTest {

    @Autowired
    private DataSourceProperties properties;

    @Autowired
    private DataSource dataSource;


    @Test
    public void testInit() {
        String classPathResource = "import.sql";
        runDataScripts(classPathResource);
    }

    private void runDataScripts(String classPathResource) {
        Resource script = new ClassPathResource("import.sql");
        String username = this.properties.getUsername();
        String password = this.properties.getPassword();
        runScripts(Collections.singletonList(script), username, password);
    }


    private void runScripts(List<Resource> resources, String username, String password) {
        if (resources.isEmpty()) {
            return;
        }
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(this.properties.isContinueOnError());
        populator.setSeparator(this.properties.getSeparator());
        if (this.properties.getSqlScriptEncoding() != null) {
            populator.setSqlScriptEncoding(this.properties.getSqlScriptEncoding().name());
        }
        for (Resource resource : resources) {
            populator.addScript(resource);
        }
        DataSource dataSource = this.dataSource;
        if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
            dataSource = DataSourceBuilder.create(this.properties.getClassLoader())
                    .driverClassName(this.properties.determineDriverClassName())
                    .url(this.properties.determineUrl()).username(username)
                    .password(password).build();
        }
        DatabasePopulatorUtils.execute(populator, dataSource);
    }

}
