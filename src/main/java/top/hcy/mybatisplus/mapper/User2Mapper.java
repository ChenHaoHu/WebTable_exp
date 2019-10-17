package top.hcy.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.hcy.mybatisplus.annotation.WebMapper;
import top.hcy.mybatisplus.entity.User;
import top.hcy.mybatisplus.entity.User2;


@Component(value = "user2Mapper")
@WebMapper(User2.class)
public interface User2Mapper extends BaseMapper<User2> {



    @Insert("${sql}")
    int insertSQL(@Param("sql") String sql);
}