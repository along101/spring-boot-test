package sample.mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sample.mybatis.domain.City;
import sample.mybatis.mapper.CityMapper;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleAnnotationApplication.class)
public class CityMapperTest {

    @Autowired
    private CityMapper cityMapper;

    @Test
    public void findByStateTest() {
        City city = cityMapper.findByState("CA");
        assertThat(city.getName()).isEqualTo("San Francisco");
        assertThat(city.getState()).isEqualTo("CA");
        assertThat(city.getCountry()).isEqualTo("US");
    }

    @Test
    public void test() {
        //插入
        City city = new City();
        city.setName("杭州");
        city.setState("浙江");
        city.setCountry("中国");
        int count = cityMapper.insert(city);
        assertThat(count).isEqualTo(1);
        assertThat(city.getId()).isGreaterThan(1);
        //查询
        City queryCity = cityMapper.selectById(city.getId());
        System.out.println(queryCity);
        assertThat(queryCity.getName()).isEqualTo("杭州");

        //修改
        queryCity.setName("宁波");
        queryCity.setCountry(null);
        count = cityMapper.updateById(queryCity);
        assertThat(count).isEqualTo(1);
        //查询
        queryCity = cityMapper.selectById(city.getId());
        System.out.println(queryCity);
        assertThat(queryCity.getName()).isEqualTo("宁波");
        assertThat(queryCity.getCountry()).isEqualTo("中国");

        //删除
        count = cityMapper.deleteById(queryCity.getId());
        assertThat(count).isEqualTo(1);
        //查询
        queryCity = cityMapper.selectById(city.getId());
        assertThat(queryCity).isNull();
    }
}
