
package br.edu.iff.projetoClinicaReab.repository;

import br.edu.iff.projetoClinicaReab.model.Exame;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExameRepository extends JpaRepository<Exame, Long> {
  @Query("SELECT e FROM Exame e WHERE e.nomePaciente = ?1")
  List<Exame> findByNomePaciente(String nomePaciente);

}
