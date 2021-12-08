package br.edu.iff.projetoClinicaReab.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class MyWebSecurity extends WebSecurityConfigurerAdapter {
  @Autowired
  private FuncionarioDetailsService service;

  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(service)
        .passwordEncoder(new BCryptPasswordEncoder());

  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().ignoringAntMatchers("/apirest/**").and().authorizeRequests().antMatchers("/apirest/**").permitAll()
        .antMatchers("/funcionarios/").hasAnyRole("ADMIN", "FUNC")
        .antMatchers("/funcionarios/**").hasAnyRole("ADMIN", "FUNC")
        .antMatchers("/**").hasAnyRole("ADMIN", "FUNC")
        .antMatchers("/formFuncionario").hasAnyRole("ADMIN", "FUNC")
        .antMatchers("/pacientes/**").hasAnyRole("ADMIN", "FUNC")
        .antMatchers("/formPaciente").hasAnyRole("ADMIN", "FUNC")
        .antMatchers("/files/**").hasAnyRole("ADMIN", "FUNC")
        .antMatchers("/funcionarios/meusdados/**").hasAnyRole("ADMIN", "FUNC")
        .anyRequest().authenticated().and()
        .formLogin().permitAll().defaultSuccessUrl("/", true);
  }

  /*
   * @Override
   * protected void configure(AuthenticationManagerBuilder auth) throws Exception
   * {
   * auth.userDetailsService(service);
   * }
   */
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/h2-console/**");

  }

}
