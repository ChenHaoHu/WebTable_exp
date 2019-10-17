package top.hcy.webtable.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.hcy.webtable.annotation.WebMapper;
import top.hcy.webtable.entity.Data2;


@Component(value = "data2Mapper")
@WebMapper(Data2.class)
public interface Data2Mapper extends BaseMapper<Data2> {



    @Insert("${sql}")
    int insertSQL(@Param("sql") String sql);
}