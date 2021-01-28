package org.hazelcast.zerodowntime.customer;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class EShopWebConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomerCheckHandlerInterceptor())
                .excludePathPatterns(
                        "/",
                        "/favicon.ico",
                        "/webjars/**",
                        "/customer/**",
                        "/rest/**",
                        "/image/**",
                        "/script/**");
    }

    private static final class CustomerCheckHandlerInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
            var session = req.getSession();
            var customer = session.getAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME);
            if (customer == null) {
                resp.sendRedirect("/");
                return false;
            }
            return true;
        }
    }
}