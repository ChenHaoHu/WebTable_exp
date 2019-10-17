package top.hcy.mybatisplus.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.hcy.mybatisplus.annotation.WebField;
import top.hcy.mybatisplus.annotation.WebTable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("data")
@WebTable("测试表1")
public class User {

    @TableId(type = IdType.AUTO)
    @WebField("序号")
    private Long id;
    @WebField
    private String name;
    @WebField
    private Integer age;
    @WebField
    private String passwd;
    private String data1;
    private String data2;
    private String data3;
    private String data4;


}