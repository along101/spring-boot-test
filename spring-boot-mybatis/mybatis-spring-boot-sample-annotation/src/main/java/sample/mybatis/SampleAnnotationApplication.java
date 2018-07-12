package sample.mybatis;

import sample.mybatis.mapper.CityMapper;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleAnnotationApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SampleAnnotationApplication.class, args);
	}

	final private CityMapper cityMapper;

	public SampleAnnotationApplication(CityMapper cityMapper) {
		this.cityMapper = cityMapper;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.cityMapper.findByState("CA"));
	}

}
