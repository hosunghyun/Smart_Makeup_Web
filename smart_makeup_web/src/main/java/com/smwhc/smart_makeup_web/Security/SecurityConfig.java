package com.smwhc.smart_makeup_web.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf((csrf)->csrf.disable());  // CSRF 보호를 비활성화합니다.

        // authorizeHttpRequests: HTTP 요청에 대한 권한을 설정
        // requestMatchers("/**"): 모든 경로(루트 / 아래의 모든 경로)에 대한 요청을 지정
        // permitAll(): 해당 경로에 대한 접근을 모든 사용자에게 허용합니다.
        httpSecurity.authorizeHttpRequests((authorize)-> authorize.requestMatchers("/**").permitAll());

        // 로그인을 FilterChain에서 관리한다.
        httpSecurity.formLogin((formLogin)->formLogin.loginPage("/sign") // 로그인페이지 URL
                                .defaultSuccessUrl("/") //성공했을때 가야하는 URL
                                .failureUrl("/sign?error"));  // 실패했을때. 만약이걸 안해놓으면 /login?error 로 이동
                                
        
        httpSecurity.logout((logout)-> logout.logoutUrl("/signout")    // 로그아웃페이지 URL
                            .logoutSuccessUrl("/index?signout=true")); // 로그아웃이 성공하면 첫페이지로.

        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {     // 암호화
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로 허용
                .allowedOrigins("http://localhost:8080") // 특정 도메인만 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true);
    }
}
