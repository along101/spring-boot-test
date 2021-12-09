package sample.mybatis.dao;

import org.apache.ibatis.session.SqlSession;
import sample.mybatis.domain.City;

import org.springframework.stereotype.Component;

@Component
public class CityDao {

	private final SqlSession sqlSession;

	public CityDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public City selectCityById(long id) {
		return this.sqlSession.selectOne("selectCityById", id);
	}

}
