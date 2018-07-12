package sample.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

import sample.mybatis.domain.Hotel;

/**
 * @author Eduardo Macarron
 */
@Mapper
public interface HotelMapper {

	Hotel selectByCityId(int city_id);

}
