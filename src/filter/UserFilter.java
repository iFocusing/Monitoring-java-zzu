package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by huojingjing on 16/4/16.
 */
@WebFilter(filterName = "UserFilter", urlPatterns = {"/jsp/*","/servlet/*"})
public class UserFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        if(request.getSession().getAttribute("user") == null){
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://"+request.getServerName() + ":" + request.getServerPort() + path + "/";
            response.sendRedirect(basePath+"login.jsp");
        }else{
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
