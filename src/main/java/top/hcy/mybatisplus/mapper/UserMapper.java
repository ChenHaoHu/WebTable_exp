package top.hcy.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.hcy.mybatisplus.annotation.WebMapper;
import top.hcy.mybatisplus.entity.User;


@Component(value = "userMapper")
@WebMapper(User.class)
public interface UserMapper extends BaseMapper<User> {



    @Insert("${sql}")
    int insertSQL(@Param("sql")String sql);
}