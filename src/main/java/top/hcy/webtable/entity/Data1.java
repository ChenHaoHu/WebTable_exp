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
@TableName("data1")
@WebTable("测试表-主")
public class Data1 {

    @TableId(type = IdType.AUTO)
    @WebField("序号--")
    private Long id;
    @WebField("名字")
    private String name;
    @WebField("年龄")
    private Integer age;
    @WebField("passwd")
    private String passwd;
    private String data1;
    private String data2;
    private String data3;
    private String data4;


}