package sample.mybatis.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import sample.mybatis.domain.Hotel;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link HotelMapper}.
 * @author wonwoo
 * @since 1.2.1
 */
@RunWith(SpringRunner.class)
@MybatisTest
public class HotelMapperTest {

  @Autowired
  private HotelMapper hotelMapper;

  @Test
  public void selectByCityIdTest() {
    Hotel hotel = hotelMapper.selectByCityId(1);
    assertThat(hotel.getName()).isEqualTo("Conrad Treasury Place");
    assertThat(hotel.getAddress()).isEqualTo("William & George Streets");
    assertThat(hotel.getZip()).isEqualTo("4001");
  }

}
