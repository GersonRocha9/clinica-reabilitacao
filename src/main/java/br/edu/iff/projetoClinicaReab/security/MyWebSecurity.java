package br.edu.iff.projetoClinicaReab.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class MyWebSecurity extends WebSecurityConfigurerAdapter {
  @Autowired
  private FuncionarioDetailsService service;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().ignoringAntMatchers("/apirest/**").and().authorizeRequests().antMatchers("/apirest/**").permitAll()
        .antMatchers("/funcionarios/").permitAll().antMatchers("/funcionarios/**").permitAll().antMatchers("/**")
        .permitAll().antMatchers("/formFuncionario").permitAll().antMatchers("/pacientes/**").permitAll()
        .antMatchers("/formPaciente").permitAll().antMatchers("/files/**").hasAnyRole("ADMIN", "FUNC")
        .antMatchers("/funcionarios/meusdados/**").hasAnyRole("ADMIN", "FUNC").anyRequest().authenticated().and()
        .formLogin().permitAll().defaultSuccessUrl("/funcionarios/meusdados/**", true);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(service);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/h2-console/**");

  }

}
