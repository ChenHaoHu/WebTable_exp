package top.hcy.webtable.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.hcy.webtable.annotation.WebMapper;
import top.hcy.webtable.entity.Data1;


@Component(value = "data1Mapper")
@WebMapper(Data1.class)
public interface Data1Mapper extends BaseMapper<Data1> {



    @Insert("${sql}")
    int insertSQL(@Param("sql")String sql);
}