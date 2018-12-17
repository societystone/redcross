package com.app.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.alibaba.fastjson.JSONObject;
import com.app.util.PageUtils;
import com.app.util.StringUtils;

/**
 * 分页过滤器-处理pageNum和pageSize的入参 . <br>
 * 
 * @author wangtw <br>
 */
@WebFilter(filterName = "pageFilter", urlPatterns = "/*")
public class PageFilter implements Filter {
	private static final int page = 1;
	private static final int limit = 10;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @SuppressWarnings("unused")
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        PageUtils.setPageNum(getPageNum(httpRequest));
//        PageUtils.setPageSize(getPageSize(httpRequest));
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
        }
        try {
//            chain.doFilter(request, response);
            if (null == requestWrapper) {
            	chain.doFilter(request, response);
            } else {
            	JSONObject jsonObject = JSONObject.parseObject(new String(((BodyReaderHttpServletRequestWrapper)requestWrapper).body));
            	PageUtils.setPageNum(getPageNum(jsonObject));
            	PageUtils.setPageSize(getPageSize(jsonObject));
            	chain.doFilter(requestWrapper, response);
            }
        } finally {
            // 由于tomcat线程重用,记得清空
            PageUtils.removePageNum();
            PageUtils.removePageSize();
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 获取前台传过来的页数-默认1
     * 
     * @param httpRequest
     * @return
     */
    @SuppressWarnings("unused")
	private int getPageNum(HttpServletRequest httpRequest) {
        int pageNum = page;
        String param = httpRequest.getParameter("page");
        Integer checkPageNum = StringUtils.stringToInteger(param);
        if (checkPageNum != null) {
            return checkPageNum;
        }
        return pageNum;
    }

    /**
     * 获取前台传过来的每页记录数-默认10
     * 
     * @param httpRequest
     * @return
     */
    @SuppressWarnings("unused")
	private int getPageSize(HttpServletRequest httpRequest) {
        int pageSize = limit;
        String param = httpRequest.getParameter("limit");
        Integer checkPageSize = StringUtils.stringToInteger(param);
        if (checkPageSize != null) {
            return checkPageSize;
        }
        return pageSize;
    }

    /**
     * 获取前台传过来的页数-默认1
     * 
     * @param jsonObject
     * @return
     */
    private int getPageNum(JSONObject jsonObject) {
        int pageNum = page;
        if(jsonObject!=null) {
	        String param = jsonObject.getString("page");
	        Integer checkPageNum = StringUtils.stringToInteger(param);
	        if (checkPageNum != null) {
	            return checkPageNum;
	        }
        }
        return pageNum;
    }

    /**
     * 获取前台传过来的每页记录数-默认10
     * 
     * @param jsonObject
     * @return
     */
    private int getPageSize(JSONObject jsonObject) {
        int pageSize = limit;
        if(jsonObject!=null) {
	        String param = jsonObject.getString("limit");
	        Integer checkPageSize = StringUtils.stringToInteger(param);
	        if (checkPageSize != null) {
	            return checkPageSize;
	        }
        }
        return pageSize;
    }
    
    static class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {
		private final byte[] body;
        public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
            super(request);
            body = toByteArray(request.getInputStream());
        }
        private byte[] toByteArray(InputStream in) throws IOException {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 4];
            int n = 0;
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            return out.toByteArray();
        }
        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            final ByteArrayInputStream bais = new ByteArrayInputStream(body);
            return new ServletInputStream() {
                @Override
                public int read() throws IOException {
                    return bais.read();
                }

				@Override
				public boolean isFinished() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean isReady() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public void setReadListener(ReadListener readListener) {
					// TODO Auto-generated method stub
					
				}
            };
        }
    }

}
