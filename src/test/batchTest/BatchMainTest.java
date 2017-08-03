package test.batchTest;

import com.company.MD5;
import com.company.batchDetail.BatchMain;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by adimn on 2017/6/1.
 */
public class BatchMainTest {

    private BatchMain batchMain ;
    @Before
    public void setUp() throws Exception {
        batchMain=new BatchMain();
    }

    @Test
    public void batchMainTest() throws Exception {
//        batchMain.main();
        String res= MD5.MD5_32bit("深圳市堃鹏文化发展有限公司");
        System.out.println(res);
    }

    @Test
    public void batchInsertTest() throws  Exception{
        batchMain.main(new String[]{"60000000","60001000","1"});
    }

}