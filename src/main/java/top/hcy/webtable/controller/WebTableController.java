package top.hcy.webtable.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.hcy.webtable.annotation.WebField;
import top.hcy.webtable.annotation.WebTable;
import top.hcy.webtable.entity.Data2;
import top.hcy.webtable.mapper.Data1Mapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

@Controller
public class WebTableController {


    @Autowired
    Data1Mapper data1Mapper;


    @Autowired
    Set<Class<?>> webTableClass;

    @Autowired
    HashMap<String,Class> webEntityClass;


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


    @RequestMapping("/out")
    public String loginOut(Model model , HttpServletRequest request,HttpSession httpSession){
        httpSession.removeAttribute("login");
        return "login";
    }

    @RequestMapping("/login")
    @PostMapping
    public String loginIn(Model model , HttpServletRequest request,HttpSession httpSession){
        String user = request.getParameter("name");
        String passwd = request.getParameter("passwd");
        if (user.equals("admin")&&passwd.equals("admin")){
            httpSession.setAttribute("login","true");
            return  "redirect:/";
        }
        model.addAttribute("msg","登录失败");
        return "login";
    }

    @RequestMapping(value = {"/{table}","/"})
    public  String index(Model model,@Nullable @PathVariable("table")String table,HttpSession session){
        String login = (String)session.getAttribute("login");
        if (login!="true"){
            return "/login";
        }
        if (" ".equals(table)){
            table = null;
        }
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
        if (tables.size()<0 || table == null){
            //没有表字段
            return "index.html";
        }
        Class mapperClass = webMapperClass.get(table);
        Class entity = webEntityClass.get(table);
        //System.out.println(mapperClass);
        mapper = (BaseMapper)auto.getBean(mapperClass);
        model.addAttribute("tablename",table);
        model.addAttribute("tables",tables);
        Page<Object> page = new Page<>(1,6);
        IPage<Object> mapIPage = mapper.selectPage(page, null);
        model.addAttribute("data",mapIPage);
        List<Map<String,String>> titles = new ArrayList<>();
        Field[] declaredFields = entity.getDeclaredFields();
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

    @RequestMapping("/data/dele/{table}/{id}")
    @ResponseBody
    public  int deleteData(@PathVariable("table")String table,
                           @PathVariable("id")Integer id){
        BaseMapper mapper = null;
        Class mapperClass = webMapperClass.get(table);
        mapper = (BaseMapper)auto.getBean(mapperClass);
        int i = mapper.deleteById(id);
        return i;
    }

    @RequestMapping("/data/get/{table}/{page}/{size}")
    @ResponseBody
    public IPage deleteData(@PathVariable("table")String table,
                            @PathVariable("page")Integer page,
                            @PathVariable("size")Integer size){
        BaseMapper mapper = null;
        Class mapperClass = webMapperClass.get(table);
        Class entity = webEntityClass.get(table);
        //System.out.println(mapperClass);
        mapper = (BaseMapper)auto.getBean(mapperClass);
        Page<Object> objectPage = new Page<>(page,size);
        IPage<Object> mapIPage = mapper.selectPage(objectPage, null);
        return mapIPage;
    }


    @RequestMapping("/data/sear/{table}/{text}")
    @ResponseBody
    public List searData( @PathVariable("table")String table,
                          @PathVariable("text")String text){
        BaseMapper mapper = null;
        Class mapperClass = webMapperClass.get(table);
        Class entity = webEntityClass.get(table);
        //System.out.println(mapperClass);
        mapper = (BaseMapper)auto.getBean(mapperClass);
        QueryWrapper<Object>  queryWrapper = new QueryWrapper();
        Consumer<QueryWrapper<Object>> query = userQueryWrapper -> {
            Field[] declaredFields = Data2.class.getDeclaredFields();
            for (int i = 0; i < declaredFields.length; i++) {
                userQueryWrapper.like(declaredFields[i].getName(), text).or();
            }
        };
        queryWrapper.and(query);
        List data = mapper.selectList(queryWrapper);
        return data;
    }
}
