package com.blogger.config;



//package com.blogger.config;
//
//import com.blogger.service.impl.CustomUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import
//        org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private CustomUserDetailsService userDetailsService;
//
////    @Bean
////    public CustomUserDetailsService userDetailsService(){
////
////
////    }
//@Bean
//public AuthenticationManager authenticationManager(AuthenticationManagerBuilder builder) throws Exception {
//    return builder.build();
//}
//
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws
//            Exception {
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder());
//    }
//}

import com.blogger.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    //    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception
//    {
//        return super.authenticationManager();
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // permissions for comments
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/comment/test/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/comment/test/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/comment/test/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/comment/test/**").hasRole("USER")
                //permission for posts
                .antMatchers(HttpMethod.DELETE, "/comment/test/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/comment/test/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/comment/test/**").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/comment/**").hasRole("ANALYST")
                // permissions for users
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    protected UserDetailsService userDetailsService() {
        UserDetails admin = User.builder().username("raghav").password(passwordEncoder.encode("Admin123")).roles("ADMIN").build();
        UserDetails user = User.builder().username("shyam").password(passwordEncoder.encode("Mylog123")).roles("USER").build();
        UserDetails analyst = User.builder().username("david").password(passwordEncoder.encode("comments123")).roles("ANALYST").build();
        UserDetails commentor = User.builder().username("jack").password(passwordEncoder.encode("rider123")).roles("COMMENTOR").build();
        return new InMemoryUserDetailsManager(user, admin, analyst, commentor);
    }
//    @Configuration
//    @EnableSwagger2WebMvc
//    public class SwaggerConfig {
//
//        @Bean
//        public Docket api() {
//            return new Docket(DocumentationType.SWAGGER_2)
//                    .select()
//                    .apis(RequestHandlerSelectors.basePackage("com.blogger.controller"))
//                    .paths(PathSelectors.any())
//                    .build();
//        }
    }



//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws
//            Exception {
//        auth.userDetailsService(userDetailsService)
//
//                .passwordEncoder(passwordEncoder());
//
//    }}
