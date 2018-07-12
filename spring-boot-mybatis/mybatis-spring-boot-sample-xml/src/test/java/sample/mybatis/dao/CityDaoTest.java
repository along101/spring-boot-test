package sample.mybatis.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import sample.mybatis.domain.City;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CityDao}.
 * @author wonwoo
 * @since 1.2.1
 */
@RunWith(SpringRunner.class)
@MybatisTest
@Import(CityDao.class)
public class CityDaoTest {

  @Autowired
  private CityDao cityDao;

  @Test
  public void selectCityByIdTest() {
    City city = cityDao.selectCityById(1);
    assertThat(city.getName()).isEqualTo("San Francisco");
    assertThat(city.getState()).isEqualTo("CA");
    assertThat(city.getCountry()).isEqualTo("US");
  }

}
