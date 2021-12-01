package br.edu.iff.projetoClinicaReab.repository;

import br.edu.iff.projetoClinicaReab.model.Funcionario;
import br.edu.iff.projetoClinicaReab.model.Pessoa;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    @Query("SELECT p FROM Pessoa p WHERE p.cpf = :cpf OR p.nome = :nome")
    public List<Pessoa> findByCpfOrName(@Param("cpf") String cpf, @Param("nome") String nome);

    public Funcionario findByEmail(String email);

    public List<Pessoa> findByCpf(String cpf);

    public List<Pessoa> findByCpfOrEmail(String cpf, String email);
}
