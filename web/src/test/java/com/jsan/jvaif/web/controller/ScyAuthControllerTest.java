package com.jsan.jvaif.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsan.jvaif.inf.domain.ScyAuth;
import com.jsan.jvaif.web.WebApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * ScyAuthController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>9月 4, 2019</pre>
 */
@Slf4j
public class ScyAuthControllerTest extends WebApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    /**
     * Method: addScyAuth(@RequestBody
     * ScyAuth scyAuth)
     */
    @Test
    public void testAddScyAuth() throws Exception {
        String url = "/scy/auth";
        Map<String, Object> scyAuthMap = new HashMap<>();
        scyAuthMap.put("authName", "权限1");
        scyAuthMap.put("parentId", "0");
        scyAuthMap.put("authExp", "auth:add");
        scyAuthMap.put("authDesc", "权限1");
        scyAuthMap.put("status", 1);
        scyAuthMap.put("createdTime", new Date());

        log.info(JSONObject.toJSONString(scyAuthMap));
        MvcResult rs = mockMvc
            .perform(post(url).contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(scyAuthMap)))
            .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();

        log.info("testAddScyAuth() = {}", rs.getResponse().getContentAsString());

    }

    @Test
    public void testBatchAddScyAuth() throws Exception {
        String url = "/scy/auth/batch";
        List<ScyAuth> scyAuthList = new ArrayList<>();

        ScyAuth s1 = new ScyAuth();
        s1.setAuthName("权限b1");
        s1.setParentId("0");
        s1.setAuthExp("auth:batch_add");
        s1.setStatus("1");
        s1.setCreatedTime(new Date());
        scyAuthList.add(s1);

        ScyAuth s2 = new ScyAuth();
        s2.setAuthName("权限b1");
        s2.setParentId("0");
        s2.setAuthExp("auth:batch_add");
        s2.setStatus("1");
        s2.setCreatedTime(new Date());
        scyAuthList.add(s2);

        MvcResult rs = mockMvc
            .perform(post(url).contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(scyAuthList)))
            .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();

        log.info("testAddScyAuth() = {}", rs.getResponse().getContentAsString());

    }

    /**
     * Method: getScyAuthById(@PathVariable("scyAuthId")
     * String scyAuthId)
     */
    @Test
    public void testGetScyAuthById() throws Exception {
        String url = "/scy/auth";
        String scyAuthId = "1?_cols=id,authName";

        MvcResult rs = mockMvc.perform(get(url + "/" + scyAuthId))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();

        log.info("testGetScyAuthById() = {}", rs.getResponse().getContentAsString());
    }

    /**
     * Method: getScyAuth(@Valid ScyAuthVo vo)
     */
    @Test
    public void testGetScyAuth() throws Exception {
        String url = "/scy/auth";
        String params = "?_cols=id,authName";

        MvcResult rs =
            mockMvc.perform(get(url + params)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        log.info("testGetScyAuth() = {}", rs.getResponse().getContentAsString());
    }


    /**
     * Method: getByVoForPage(@Valid ScyAuthVo vo)
     */
    @Test
    public void testGetByVoForPage() throws Exception {
        String url = "/scy/auth/page";
        String params = "?page=3&limit=2&_cols=id,authName";

        MvcResult rs =
            mockMvc.perform(get(url + params)).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        log.info("testGetByVoForPage() = {}", rs.getResponse().getContentAsString());
    }

    /**
     * Method: updateScyAuth(@RequestBody
     * ScyAuth scyAuth)
     */
    @Test
    public void testUpdateScyAuthById() throws Exception {
        String url = "/scy/auth";
        Map<String, Object> scyAuthMap = new HashMap<>();
        scyAuthMap.put("id", "43eb8c954f5c938a3d4b436e7df66f5f");
        scyAuthMap.put("authName", "权限2");
        scyAuthMap.put("authDesc", "权限2");

        MvcResult rs = mockMvc
            .perform(put(url).contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(scyAuthMap)))
            .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();

        log.info("testUpdateScyAuthById() = {}", rs.getResponse().getContentAsString());
    }

    /**
     * Method: batchUpdateScyAuth(@RequestBody
     * List<ScyAuth> scyAuthList)
     */
    @Test
    public void testBatchUpdateScyAuth() throws Exception {
        String url = "/scy/auth/batch";
        List<ScyAuth> scyAuthList = new ArrayList<>();

        ScyAuth s1 = new ScyAuth();
        s1.setId("1b386bc8431a694b8762000cd2365c81");
        s1.setAuthName("权限b1");
        s1.setAuthDesc("权限b1");
        scyAuthList.add(s1);

        ScyAuth s2 = new ScyAuth();
        s2.setId("43eb8c954f5c938a3d4b436e7df66f5f");
        s2.setAuthName("权限b2");
        s2.setAuthDesc("权限b2");
        scyAuthList.add(s2);

        MvcResult rs = mockMvc
            .perform(put(url).contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(scyAuthList)))
            .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();

        log.info("testBatchUpdateScyAuth() = {}", rs.getResponse().getContentAsString());

    }

    /**
     * Method: delScyAuth(@PathVariable("scyAuthId")
     * String scyAuthId)
     */
    @Test
    public void testDelScyAuthById() throws Exception {
        String url = "/scy/auth";
        String scyAuthId = "232e85ee95f6b4a9f7610849ed936f94";

        MvcResult rs = mockMvc.perform(delete(url + "/" + scyAuthId))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();

        log.info("testDelScyAuthById() = {}", rs.getResponse().getContentAsString());
    }

    /**
     * Method: batchDelScyAuth(@RequestParam("ids")
     * List<String> ids)
     */
    @Test
    public void testBatchDelScyAuth() throws Exception {
        String url = "/scy/auth/batch";

        MvcResult rs = mockMvc
            .perform(delete(url).param("ids", "1b386bc8431a694b8762000cd2365c81,43eb8c954f5c938a3d4b436e7df66f5f"))
            .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();

        log.info("testBatchDelScyAuth() = {}", rs.getResponse().getContentAsString());
    }

}
