package sample.mybatis.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import sample.mybatis.domain.City;

/**
 * @author yzl
 */
@Mapper
public interface CityMapper {
    @Results(id = "BaseResultMap", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "state", property = "state", jdbcType = JdbcType.VARCHAR),
            @Result(column = "country", property = "country", jdbcType = JdbcType.VARCHAR)
    })
    @Select("select * from city where id=#{id}")
    City selectById(Long id);

    @ResultMap("BaseResultMap")
    @Select("select * from city where state = #{state}")
    City findByState(@Param("state") String state);

    @Insert("insert into city(name,state,country)" +
            "values(#{name},#{state},#{country})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    int insert(City city);

    @Delete("delete from city where id = #{id}")
    int deleteById(long id);

    @Update("<script>" +
            "update city <set> " +
            "<if test='name != null'> name = #{name} , </if>" +
            "<if test='state != null'> state = #{state} , </if>" +
            "<if test='country != null'> country = #{country} , </if>" +
            "</set> " +
            "where id = #{id}" +
            "</script>")
    int updateById(City city);

}
