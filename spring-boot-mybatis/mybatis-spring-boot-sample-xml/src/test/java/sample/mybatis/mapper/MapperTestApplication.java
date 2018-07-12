package sample.mybatis.mapper;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Spring Boot Application for testing {@link org.mybatis.spring.boot.test.autoconfigure.MybatisTest @MybatisTest}.
 * <p>
 * This class has role for prevent to run the {@link sample.mybatis.SampleXmlApplication}.
 * For more detail information, please refer <a href="http://stackoverflow.com/questions/42722480/jdbctest-detect-class-annotated-springbootapplication">Here</a>.
 *
 * @author Kazuki Shimizu
 * @since 1.2.1
 */
@SpringBootApplication
public class MapperTestApplication {

}
