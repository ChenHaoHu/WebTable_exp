package top.hcy.webtable.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.hcy.webtable.annotation.WebField;
import top.hcy.webtable.annotation.WebTable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("data2")
@WebTable("测试表-副")
public class Data2 {

    @TableId(type = IdType.AUTO)
    @WebField("序号--")
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