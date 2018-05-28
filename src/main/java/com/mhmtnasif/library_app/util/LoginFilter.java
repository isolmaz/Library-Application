package com.mhmtnasif.library_app.util;

import com.mhmtnasif.library_app.entities.Users;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {


    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI();
        Users user = (Users) request.getSession().getAttribute("valid_user");

        if (user == null) {
            if (!url.contains("login") && !url.contains("register")) {
                response.sendRedirect(request.getContextPath() + "login.xhtml?faces-redirect=true");
            }
        } else {
            if (user.isAdmin()) {
                if (url.contains("my-content") || url.contains("login") || url.contains("register") || url.contains("user") || url.contains("add-content")) {
                    response.sendRedirect(request.getContextPath() + "index.xhtml?faces-redirect=true");
                } else if (url.contains("logout")) {
                    request.getSession().invalidate();
                    response.sendRedirect(request.getContextPath() + "login.xhtml?faces-redirect=true");
                    return;
                }
            } else {
                if (url.contains("authors") ||
                        url.contains("authors-edit") ||
                        url.contains("books") ||
                        url.contains("books-edit") ||
                        url.contains("index") ||
                        url.contains("login") ||
                        url.contains("publishers") ||
                        url.contains("publishers-edit") ||
                        url.contains("register")) {
                    response.sendRedirect(request.getContextPath() + "user.xhtml?faces-redirect=true");
                } else if (url.contains("logout")) {
                    request.getSession().invalidate();
                    response.sendRedirect(request.getContextPath() + "login.xhtml?faces-redirect=true");
                    return;
                }
            }

        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }


}
