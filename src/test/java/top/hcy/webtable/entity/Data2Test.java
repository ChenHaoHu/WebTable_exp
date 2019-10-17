package top.hcy.webtable.entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.hcy.webtable.mapper.Data1Mapper;

import javax.sql.DataSource;
import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Data2Test {


    @Autowired
    DataSource dataSource;


    @Autowired
    private Data1Mapper data1Mapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        data1Mapper.deleteById(1);
//        List<Data2> userList = data1Mapper.selectList(null);
//
//        Assert.assertEquals(4, userList.size());
//        userList.forEach(System.out::println);
    }


    @Test
    public void insertData() {

        Data2 user = null;
        for (int i = 0; i < 10000; i++) {
//            data1Mapper.insert(new Data2("hcy"+i,i%100,"775656"+i+"@qq.com"));
            System.out.println(i);
        }

    }

    @Test
    public void insertData1() {
        //    Data2 user = data1Mapper.selectById(10);
//        System.out.println(user);
//        System.out.println(user.getData1());
    }


    @Test
    public void page() {
//        Page <Data2> page = new Page<>(1,10);
//        IPage<Data2> userIPage = data1Mapper.selectPage(page,null);
//
//        System.out.println(userIPage.toString());
    }

    @Test
    public void test() throws InterruptedException {

        System.out.println(dataSource.getClass());

        final  int thread_count  = 3000;

        long start = System.currentTimeMillis();
        // int i = data1Mapper.insertSQL("insert into my(name,age,email) VALUES('hcy0',0,'77565674@qq.com'),('hcy0',0,'77565670@qq.com'),('hcy1',1,'77565671@qq.com');");
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
                int i = data1Mapper.insertSQL(sql.toString());
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