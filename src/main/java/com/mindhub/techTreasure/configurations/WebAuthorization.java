package com.mindhub.techTreasure.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity

@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/api/products").permitAll()//traer todos los productos
//                .antMatchers(HttpMethod.GET, "/api/products/{id}").permitAll()//traer un producto por id
//                .antMatchers(HttpMethod.POST, "/api/products/create").hasAuthority("ADMIN") //Crear un producto
//                .antMatchers(HttpMethod.POST, "/api/products/{id}/update").hasAuthority("ADMIN")//Actualizar un producto
//                .antMatchers(HttpMethod.PATCH, "/api/products/{id}/deactivate").hasAuthority("ADMIN")//Desactivar un producto
//                .antMatchers(HttpMethod.GET, "/api/brands").permitAll()//traer marcas
//                .antMatchers(HttpMethod.GET, "/api//brands/{brand}/products").permitAll()//Trae todos los productos por marca
//                .antMatchers(HttpMethod.GET, "/api/categories/{categoryId}/products").permitAll()//Trae todos los productos por categoría
//                .antMatchers(HttpMethod.GET, "/api/categories").permitAll()//traer todas las categorias
//                .antMatchers(HttpMethod.POST, "/api/categories/create").hasAuthority("ADMIN")//crear categoria
//                .antMatchers(HttpMethod.POST, "/api/categories/{id}/update").hasAuthority("ADMIN")//Actualizar una categoría
//                .antMatchers(HttpMethod.PATCH, "/api/categories/{id}/deactivate").hasAuthority("ADMIN")//Desactivar una categoria
//                .antMatchers(HttpMethod.PATCH, "/api/modify/review").hasAuthority("ADMIN")//elimiar una reseña
//                .antMatchers(HttpMethod.GET,"/api/get/customers/{id}").hasAnyAuthority("CUSTOMER","ADMIN")
//                .antMatchers(HttpMethod.GET,"/api/get/customers").hasAnyAuthority("ADMIN")
//                .antMatchers(HttpMethod.GET,"/api/customer/current").hasAnyAuthority("ADMIN,CUSTOMER")
//                .antMatchers(HttpMethod.GET,"/api/get/customerByEmail").hasAnyAuthority("ADMIN")
//                .antMatchers(HttpMethod.PATCH,"/api/modify/password/customer").hasAnyAuthority("CUSTOMER,ADMIN")
//                .antMatchers(HttpMethod.PATCH,"/api/modify/name/customer").hasAnyAuthority("ADMIN","CUSTOMER")
//                .antMatchers(HttpMethod.PATCH,"/api/modify/lastName/customer").hasAnyAuthority("ADMIN","CUSTOMER")
//                .antMatchers(HttpMethod.PATCH,"/api/modify/email/customer").hasAnyAuthority("ADMIN","CUSTOMER")
//                .antMatchers(HttpMethod.DELETE,"/api/delete/customer").hasAnyAuthority("ADMIN","CUSTOMER")
//                .antMatchers(HttpMethod.POST,"/api/add/telephone").hasAnyAuthority("ADMIN","CUSTOMER")
//                .antMatchers(HttpMethod.DELETE,"/api/delete/telephone").hasAnyAuthority("ADMIN","CUSTOMER")
//                .antMatchers(HttpMethod.POST,"/api/add/address").hasAnyAuthority("ADMIN","CUSTOMER")
//                .antMatchers(HttpMethod.DELETE,"/api/delete/address").hasAnyAuthority("ADMIN","CUSTOMER")
//                .antMatchers(HttpMethod.POST, "/api/new/customer").hasAnyAuthority("ADMIN","CUSTOMER")
//                .antMatchers(HttpMethod.POST, "/api/new/favorite").hasAuthority("CUSTOMER")
//                .antMatchers(HttpMethod.DELETE, "/api/delete/favorite").hasAuthority("CUSTOMER")
//                .antMatchers(HttpMethod.POST, "/api/new/review").hasAuthority("CUSTOMER")
//                .antMatchers(HttpMethod.PATCH,"/api/modify/review").hasAuthority("ADMIN")
//                .antMatchers(HttpMethod.POST,"/api/new/buy").hasAuthority("CUSTOMER");
//                .antMatchers(HttpMethod.GET,"/api/brands/{brand}/products").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/products/{productId}/reviews").permitAll()
//                .antMatchers(HttpMethod.PATCH,"/api/categories/{id}/deactive").hasAuthority("ADMIN")

        .antMatchers("**").permitAll();

        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout()
                .logoutUrl("/api/logout").deleteCookies("JESSIONID");


        http.csrf()
                .disable();


        http.headers()
                .frameOptions().disable();


        http.exceptionHandling()
                .authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));


        http.formLogin()
                .successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        http.formLogin()
                .failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));


        http.logout()
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

}
