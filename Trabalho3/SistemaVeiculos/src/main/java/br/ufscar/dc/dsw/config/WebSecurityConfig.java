package br.ufscar.dc.dsw.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http)
            throws Exception {
        http.csrf().disable().authorizeRequests()
                // Controladores REST
                .antMatchers("/clientes", "/lojas").permitAll()
                .antMatchers("/clientes/{\\d+}", "/lojas/{\\d+}").permitAll()
                .antMatchers("/propostas/veiculos/{\\d+}").permitAll()
                .antMatchers("/propostas/clientes/{\\d+}").permitAll()
                .antMatchers("/veiculos/lojas/{\\d+}").permitAll()
                .antMatchers("/veiculos/modelos/{\\w+}").permitAll()
                // Demais linhas
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().logoutSuccessUrl("/").permitAll();
    }
}
