package test.singleTest;

import com.alibaba.fastjson.JSONObject;
import com.company.MD5;
import com.company.SearchDetailInfo;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import static com.company.Main.searchDetailInfo;
import static org.junit.Assert.*;

/**
 * Created by adimn on 2017/6/5.
 */
public class SingleMainTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void dealOneTest() throws Exception {
//431
//        Map<String, Object> res = searchDetailInfo.getDetailInfoByCompanyId("");
        Map<String, Object> res2 = searchDetailInfo.getDetailInfoByCompanyId("103553136");
        String jres= JSONObject.toJSONString(res2);
        System.out.println(jres);
//        com.company.Main.dealOne("60004000");
    }

    @Test
    public void fileDealOneTest() throws  Exception{
        com.company.Main.readFileDealOne(SingleMainTest.class.getClassLoader().getResource("data/special_final").getPath());
    }

    //之前进行测试的
    public static void oldFunc(){
        SearchDetailInfo searchDetailInfo=new SearchDetailInfo();
        Map<String, Object> res = searchDetailInfo.getDetailInfoByCompanyId("1");
        String jres=JSONObject.toJSONString(res);
//        readFileByLines("D:///sth.txt");
//	    write your code here
        String encryptedPwd = null;


        try {
            encryptedPwd = MD5.MD5_32bit("1");
//            System.out.println(encryptedPwd);
//            addData(encryptedPwd,"company","data",jres);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}