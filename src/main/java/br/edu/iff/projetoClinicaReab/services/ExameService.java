package br.edu.iff.projetoClinicaReab.services;

import br.edu.iff.projetoClinicaReab.exception.NotFoundException;
import br.edu.iff.projetoClinicaReab.model.Exame;
import br.edu.iff.projetoClinicaReab.repository.ExameRepository;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ExameService {

  @Autowired
  private ExameRepository repo;

  public List<Exame> findAll(int page, int size) {
    Pageable p = (Pageable) PageRequest.of(page, size);
    return repo.findAll(p).toList();

  }

  public List<Exame> findAll() {
    return repo.findAll();
  }

  public List<Exame> findByNomePaciente(String nomePaciente) {
    List<Exame> result = new ArrayList<Exame>();
    result.addAll(repo.findByNomePaciente(nomePaciente));
    if (result == null) {
      throw new NotFoundException("Exame não encontrado");
    }
    return result;
  }

  public Exame findById(Long id) {
    Optional<Exame> result = repo.findById(id);
    if (result == null) {
      throw new NotFoundException("Exame não encontrado");
    } else {
    }
    return result.get();
  }

  public Exame save(Exame p) {

    try {
      return repo.save(p);
    } catch (Exception e) {
      throw new RuntimeException("Falha ao salvar o Exame");
    }
  }

  public Exame update(Exame p) {
    // Exame já existe
    Exame obj = findById(p.getId());
    try {
      return repo.save(p);
    } catch (Exception e) {
      throw new RuntimeException("Falha ao atualizar o Exame");
    }

  }

  public void delete(Long id) {
    Exame obj = findById(id);
    try {
      repo.delete(obj);
    } catch (Exception e) {
      throw new RuntimeException("Falha ao excluir o Exame");
    }
  }

}
