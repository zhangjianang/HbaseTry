package test.singleTest;

import com.alibaba.fastjson.JSONObject;
import com.company.SearchDetailInfo;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by adimn on 2017/6/5.
 */
public class SearchDetailInfoTest {
    SearchDetailInfo searchDetailInfo;

    @Before
    public void setUp() throws Exception {
        searchDetailInfo=new SearchDetailInfo();
    }

    @Test
    public void getDetailInfoByCompanyId() throws Exception {

       Map<String,Object> res= searchDetailInfo.getDetailInfoByCompanyId("92280000");
        String jres= JSONObject.toJSONString(res);
        System.out.println(jres);
    }

    @Test
    public void nullCompanyIdTest(){
        Map<String,Object> res= searchDetailInfo.getDetailInfoByCompanyId("92280000");
        String jres= JSONObject.toJSONString(res);
        System.out.println(jres);
    }

}