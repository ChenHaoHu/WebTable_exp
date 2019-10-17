package top.hcy.mybatisplus.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.hcy.mybatisplus.annotation.WebField;
import top.hcy.mybatisplus.annotation.WebTable;
import top.hcy.mybatisplus.entity.User;
import top.hcy.mybatisplus.mapper.UserMapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

@Controller
public class testController {


    @Autowired
    UserMapper userMapper;


    @Autowired
    Set<Class<?>> webTableClass;


    @Autowired
    AutowireCapableBeanFactory auto;

    @Autowired
    HashMap<String,Class> webMapperClass;


    @RequestMapping("/aa")
    @ResponseBody
    public String test1(Model model , HttpServletRequest request, HttpSession httpSession){

        Set<String> entries = webMapperClass.keySet();
        Iterator<String> iterator = entries.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            System.out.println(next+"--------"+webMapperClass.get(next));
        }
        return "aa";
    }


    @RequestMapping("/bb")
    public String test2(Model model , HttpServletRequest request,HttpSession httpSession){

        return "bb";
    }

    @RequestMapping("/")
    public  String index(Model model,HttpServletRequest httpServletRequest){
        String table = null;
        BaseMapper mapper = null;
        List<HashMap<String,String>> tables = new ArrayList<>();
        HashMap<String,String> map = null;
        Iterator<Class<?>> iterator = webTableClass.iterator();
        while (iterator.hasNext()){
            Class<?> next = iterator.next();
            if (table == null){
                table = next.getSimpleName();
            }
            WebTable declaredAnnotation = next.getDeclaredAnnotation(WebTable.class);
            String value = declaredAnnotation.value();
            map = new HashMap<>();
            if (value.length() == 0){
                map.put("name",next.getSimpleName());
                map.put("table",next.getSimpleName());
                tables.add(map);

            }else{
                map.put("name",value);
                map.put("table",next.getSimpleName());
                tables.add(map);

            }
        }
        if (tables.size()<0){
            //没有表字段
            return "index.html";
        }
       // System.out.println(table);
        Class mapperClass = webMapperClass.get(table);
        //System.out.println(mapperClass);
        mapper = (BaseMapper)auto.getBean(mapperClass);
        model.addAttribute("tablename",table);
        model.addAttribute("tables",tables);
        Page<User> page = new Page<>(1,6);
        IPage<User> mapIPage = mapper.selectPage(page, null);
        model.addAttribute("data",mapIPage);
        List<Map<String,String>> titles = new ArrayList<>();
        Field[] declaredFields = User.class.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            WebField webField = declaredFields[i].getAnnotation(WebField.class);
            if (webField != null){
                String title = webField.value();
               // System.out.println(declaredFields[i].getName()+"-----"+title+"----");
                if (title.length() == 0){
                    map  = new HashMap<>();
                    map.put("name",declaredFields[i].getName());
                    map.put("field",declaredFields[i].getName());
                    titles.add(map);
                }else{
                    map  = new HashMap<>();
                    map.put("name",title);
                    map.put("field",declaredFields[i].getName());
                    titles.add(map);
                }
            }

        }
        model.addAttribute("title",titles);
        return "index.html";
    }

    @RequestMapping("/data/dele/{id}")
    @ResponseBody
    public  int deleteData(@PathVariable("id")Integer id){
        int i = userMapper.deleteById(id);
        return i;
    }

    @RequestMapping("/data/get/{page}/{size}")
    @ResponseBody
    public IPage deleteData(@PathVariable("page")Integer page,
                            @PathVariable("size")Integer size){
        Page<User> userPage = new Page<>(page,size);
        IPage<User> mapIPage = userMapper.selectPage(userPage, null);
        return mapIPage;
    }


    @RequestMapping("/data/sear/{text}")
    @ResponseBody
    public List searData(@PathVariable("text")String text){
        QueryWrapper<User>  queryWrapper = new QueryWrapper();
        Consumer<QueryWrapper<User>> query = userQueryWrapper -> {
            Field[] declaredFields = User.class.getDeclaredFields();
            for (int i = 0; i < declaredFields.length; i++) {
                userQueryWrapper.like(declaredFields[i].getName(), text).or();
            }
        };
        queryWrapper.and(query);
        List<User> users = userMapper.selectList(queryWrapper);
        return users;
    }
}
