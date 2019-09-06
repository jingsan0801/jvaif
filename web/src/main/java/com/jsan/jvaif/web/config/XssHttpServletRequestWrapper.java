package com.jsan.jvaif.web.config;

import io.micrometer.core.instrument.util.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.jsan.jvaif.inf.constant.PublicConstant.PARAM_NAME_NO_XSS_TAIL;

/**
 * @description: xss过滤实现
 * @author: jcwang
 * @create: 2019-08-27 22:59
 **/
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private HttpServletRequest orgRequest;

    /**
     * 配置可以通过过滤的白名单
     */
    private static final Whitelist WHITELIST = new Whitelist();
    /**
     * 配置过滤化参数,不对代码进行格式化
     */
    private static final Document.OutputSettings OUTPUT_SETTINGS = new Document.OutputSettings().prettyPrint(false);

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.orgRequest = request;
    }

    @Override
    public String getParameter(String name) {
        // 例外
        if (name.endsWith(PARAM_NAME_NO_XSS_TAIL)) {
            return super.getParameter(name);
        }
        name = clean(name);
        String value = super.getParameter(name);
        if (StringUtils.isNotBlank(value)) {
            value = clean(value);
        }
        return value;
    }

    @Override
    public Map getParameterMap() {
        Map map = super.getParameterMap();
        Map<String, String> returnMap = new HashMap<>(16);
        Iterator entries = map.entrySet().iterator();
        Map.Entry entry;
        String name;
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry)entries.next();
            name = (String)entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[])valueObj;
                for (String s : values) {
                    value = s + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, clean(value).trim());
        }
        return returnMap;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] arr = super.getParameterValues(name);
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = clean(arr[i]);
            }
        }
        return arr;
    }

    @Override
    public String getHeader(String name) {
        name = clean(name);
        String value = super.getHeader(name);
        if (StringUtils.isNotBlank(value)) {
            value = clean(value);
        }
        return value;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        StringBuilder newRs;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(orgRequest.getInputStream()))) {
            String line;
            newRs = new StringBuilder();
            while ((line = br.readLine()) != null) {
                newRs.append(clean(line));
            }
        }

        return new WrappedServletInputStream(new ByteArrayInputStream(newRs.toString().getBytes()));
    }

    private String clean(String content) {
        // 对参数值的前后空格做处理
        content = content.trim();
        // 避免处理掉json格式的数据
        WHITELIST.addTags("{", "}", ":");
        return Jsoup.clean(content, "", WHITELIST, OUTPUT_SETTINGS);
    }

    private static class WrappedServletInputStream extends ServletInputStream {

        private InputStream stream;

        WrappedServletInputStream(InputStream stream) {
            this.stream = stream;
        }

        @Override
        public int read() throws IOException {
            return stream.read();
        }

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }
    }

    private HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper)req).getOrgRequest();
        }

        return req;
    }

}
