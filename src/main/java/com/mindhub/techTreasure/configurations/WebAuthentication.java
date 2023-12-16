package com.mindhub.techTreasure.configurations;

import com.mindhub.techTreasure.models.Customer;
import com.mindhub.techTreasure.repositories.CustomerRepository;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private CustomerRepository customerRepository;
    @Override

    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(email -> {
            Customer customer = customerRepository.findByEmail(email);

            if (customer!= null) {
                if(customer.getEmail().contains("@admin")) {
                    return new User(
                           customer.getEmail(),
                            customer.getPassword(),
                            AuthorityUtils.createAuthorityList("ADMIN"));
                }else{
                    return new User(
                            customer.getEmail(),
                            customer.getPassword(),
                            AuthorityUtils.createAuthorityList("CUSTOMER"));
                }
//                new User(
//                        "visitor",
//                        "visitor",
//                        AuthorityUtils.createAuthorityList("VISITOR")
//                );
            }
            else {
                throw new UsernameNotFoundException("Unknown user: " + email);
            }
        });
    }
    @Bean
    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}