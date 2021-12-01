
package br.edu.iff.projetoClinicaReab.repository;

import br.edu.iff.projetoClinicaReab.model.Paciente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    @Query("SELECT p FROM Paciente p WHERE p.cpf = :cpf OR p.nome = :nome")
    public List<Paciente> findByCpfOrName(@Param("cpf") String cpf, @Param("nome") String nome);

    public List<Paciente> findByCpf(String cpf);

}
