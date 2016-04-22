package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by root on 3/29/16.
 */
@WebFilter(filterName = "EncodingFilter", urlPatterns = "/*", initParams = {@WebInitParam(name="encoding", value = "UTF-8")})
public class EncodingFilter implements Filter {
    private String encoding;
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
    }
    /**
     * @return
     * @param
     * @author 黄诗鹤
     */
    public void doFilter(ServletRequest arg0, ServletResponse arg1,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) arg0;
        HttpServletResponse response = (HttpServletResponse) arg1;
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        response.setContentType("text/html;charset=" + encoding);
        //避免调用缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"+request.getServerName() + ":" + request.getServerPort() + path + "/";
        //将basePath放入session
        request.getSession().setAttribute("basePath",basePath);

        chain.doFilter(request, response);
    }

    public void destroy(){

    }

}
