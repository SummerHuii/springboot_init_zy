//package top.qtcc.qiutuanallpowerfulspringboot.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeRequests(authorizeRequests ->
//                authorizeRequests
//                    .antMatchers("/test/**").permitAll()
//                    .anyRequest().authenticated()
//            )
//            .formLogin(withDefaults());
//        return http.build();
//    }
//}
