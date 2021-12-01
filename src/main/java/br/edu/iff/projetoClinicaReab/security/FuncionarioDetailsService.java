package br.edu.iff.projetoClinicaReab.security;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import br.edu.iff.projetoClinicaReab.model.Funcionario;
import br.edu.iff.projetoClinicaReab.model.Permissao;
import br.edu.iff.projetoClinicaReab.repository.FuncionarioRepository;

@Service
public class FuncionarioDetailsService implements UserDetailsService {

  @Autowired
  private FuncionarioRepository repo;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Funcionario funcionario = repo.findByEmail(email);
    if (funcionario == null) {
      throw new UsernameNotFoundException("Funcionário não encontrado");
    }
    return new User(funcionario.getEmail(), funcionario.getSenha(), getAuthorities(funcionario.getPermissoes()));
  }

  private List<GrantedAuthority> getAuthorities(List<Permissao> lista) {
    List<GrantedAuthority> l = new ArrayList<>();
    for (Permissao p : lista) {
      l.add(new SimpleGrantedAuthority("ROLE_" + p.getNome()));
    }
    return l;
  }

}
