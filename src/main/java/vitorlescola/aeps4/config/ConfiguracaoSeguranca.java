package vitorlescola.aeps4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ConfiguracaoSeguranca{
    @Bean
    public BCryptPasswordEncoder encoderSenha() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http)
                throws Exception {

            http
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/login", "/css/**", "/js/**", "/images/**")
                            .permitAll()
                            .anyRequest().authenticated()
                    )
                    .formLogin(form -> form
                            .loginPage("/login")
                            .loginProcessingUrl("/login")
                            .defaultSuccessUrl("/home", true)
                            .failureUrl("/login?error=true")
                            .permitAll()
                    )
                    .logout(logout -> logout
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/login?logout=true")
                            .permitAll()
                    );

            return http.build();
        }
    }
    
}