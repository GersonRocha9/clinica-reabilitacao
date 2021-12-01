package br.edu.iff.projetoClinicaReab.services;

import br.edu.iff.projetoClinicaReab.exception.NotFoundException;
import br.edu.iff.projetoClinicaReab.model.Paciente;
import br.edu.iff.projetoClinicaReab.repository.PacienteRepository;
import org.springframework.data.domain.Pageable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repo;

    public List<Paciente> findAll(int page, int size) {
        Pageable p = (Pageable) PageRequest.of(page, size);
        return repo.findAll(p).toList();

    }

    public List<Paciente> findAll() {
        return repo.findAll();
    }

    public Paciente findById(Long id) {
        Optional<Paciente> result = repo.findById(id);
        if (result == null) {
            throw new NotFoundException("Paciente não encontrado");
        } else {
        }
        return result.get();
    }

    public Paciente save(Paciente p) {

        try {
            return repo.save(p);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao salvar o Paciente");
        }
    }

    public Paciente update(Paciente p) {
        // Paciente já existe
        Paciente obj = findById(p.getId());
        try {
            p.setCpf(obj.getCpf());
            return repo.save(p);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao atualizar o Paciente");
        }

    }

    public void delete(Long id) {
        Paciente obj = findById(id);
        try {
            repo.delete(obj);
            if (obj.getSituacao() != null) {
                Path caminho = Paths.get("uploads", obj.getSituacao());
                Files.deleteIfExists(caminho);
            }
        } catch (Exception e) {
            throw new RuntimeException("Falha ao excluir o Paciente");
        }
    }

    private void verificaCPF(String cpf) {
        List<Paciente> result = repo.findByCpf(cpf);
        if (result.isEmpty()) {
            throw new RuntimeException("CPF já cadastrado");
        }
    }

}
