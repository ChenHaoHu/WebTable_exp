package top.hcy.mybatisplus.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.hcy.mybatisplus.mapper.UserMapper;

import javax.sql.DataSource;
import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {


    @Autowired
    DataSource dataSource;


    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        userMapper.deleteById(1);
//        List<User> userList = userMapper.selectList(null);
//
//        Assert.assertEquals(4, userList.size());
//        userList.forEach(System.out::println);
    }


    @Test
    public void insertData() {

        User user = null;
        for (int i = 0; i < 10000; i++) {
//            userMapper.insert(new User("hcy"+i,i%100,"775656"+i+"@qq.com"));
            System.out.println(i);
        }

    }

    @Test
    public void insertData1() {
        //    User user = userMapper.selectById(10);
//        System.out.println(user);
//        System.out.println(user.getData1());
    }


    @Test
    public void page() {
        Page <User> page = new Page<>(1,10);
        IPage<User> userIPage = userMapper.selectPage(page,null);

        System.out.println(userIPage.toString());
    }

    @Test
    public void test() throws InterruptedException {

        System.out.println(dataSource.getClass());

        final  int thread_count  = 3000;

        long start = System.currentTimeMillis();
        // int i = userMapper.insertSQL("insert into my(name,age,email) VALUES('hcy0',0,'77565674@qq.com'),('hcy0',0,'77565670@qq.com'),('hcy1',1,'77565671@qq.com');");
        // System.out.println(i);
        CountDownLatch  countDownLatch = new CountDownLatch(thread_count);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                StringBuffer sql  = new StringBuffer("INSERT INTO my ( NAME, age, passwd, data1, data2, data3, data4 ) SELECT b.a,20,b.a,b.a,b.a,b.a,b.a FROM (SELECT SUBSTRING( MD5( rand( )),1,4) AS a) b");
                for (int j = 1; j < 2000; j++) {
                    sql = sql.append(" union all SELECT b.a,20,b.a,b.a,b.a,b.a,b.a FROM (SELECT SUBSTRING( MD5( rand( )),1,4) AS a) b");
                }
                sql = sql.append(";") ;
                System.out.println("sql ok "+Thread.currentThread().getName());
                int i = userMapper.insertSQL(sql.toString());
                countDownLatch.countDown();
                System.out.println(System.currentTimeMillis()+"-----------"+i+"---------"+Thread.currentThread());
            }
        };


        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(5000);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(100, 1000, 1000, TimeUnit.SECONDS, workQueue);
        for (int i = 0; i < thread_count; i++) {
            threadPoolExecutor.execute(runnable);
        }
        countDownLatch.await();
        long end = System.currentTimeMillis();
        System.out.println((long)(end-start)/1000/60);

    }
}