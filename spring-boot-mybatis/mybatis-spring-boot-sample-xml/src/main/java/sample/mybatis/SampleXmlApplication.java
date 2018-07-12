package sample.mybatis;

import sample.mybatis.dao.CityDao;
import sample.mybatis.mapper.HotelMapper;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleXmlApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SampleXmlApplication.class, args);
	}

	private final CityDao cityDao;

	private final HotelMapper hotelMapper;

	public SampleXmlApplication(CityDao cityDao, HotelMapper hotelMapper) {
		this.cityDao = cityDao;
		this.hotelMapper = hotelMapper;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.cityDao.selectCityById(1));
		System.out.println(this.hotelMapper.selectByCityId(1));
	}

}
