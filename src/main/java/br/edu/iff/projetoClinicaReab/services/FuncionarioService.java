package br.edu.iff.projetoClinicaReab.services;

import br.edu.iff.projetoClinicaReab.exception.NotFoundException;
import br.edu.iff.projetoClinicaReab.model.Funcionario;
import br.edu.iff.projetoClinicaReab.model.Permissao;
import br.edu.iff.projetoClinicaReab.model.Pessoa;
import br.edu.iff.projetoClinicaReab.repository.FuncionarioRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repo;

    public List<Funcionario> findAll(int page, int size) {
        Pageable p = (Pageable) PageRequest.of(page, size);
        return repo.findAll(p).toList();
    }

    public List<Funcionario> findAll() {
        return repo.findAll();
    }

    public Funcionario findById(Long id) {
        Optional<Funcionario> result = repo.findById(id);
        if (result == null) {
            throw new NotFoundException("Funcionário não encontrado");
        } else {
        }
        return result.get();
    }

    public Funcionario save(Funcionario f) {
        verificaCPFEmailCadastrado(f.getCpf(), f.getEmail());
        // Verifica permissões nulas
        removePermissoesNulas(f);
        try {
            return repo.save(f);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao Salvar o Funcionário");
        }
    }

    private void verificaCPFEmailCadastrado(String cpf, String email) {
        List<Pessoa> result = repo.findByCpfOrEmail(cpf, email);
        if (result.isEmpty()) {
            throw new RuntimeException("CPF ou E-mail já cadastrados");
        }
    }

    public Funcionario update(Funcionario funcionario) {
        Funcionario obj = findById(funcionario.getId());
        // Verifica permissões nulas
        removePermissoesNulas(funcionario);
        try {
            funcionario.setCpf(obj.getCpf());
            return repo.save(funcionario);
        } catch (Exception e) {
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
                if (t instanceof ConstraintViolationException) {
                    throw ((ConstraintViolationException) t);
                }
            }
            throw new RuntimeException("Falha ao atualizar o Funcionário");
        }
    }

    public void delete(Long id) {
        Funcionario obj = findById(id);
        try {
            repo.delete(obj);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao excluir o Funcionario");
        }
    }

    public Funcionario findByEmail(String email) {
        Optional<Funcionario> result = Optional.of(repo.findByEmail(email));
        if (result == null) {
            throw new NotFoundException("Funcionário não encontrado");
        } else {
        }
        return result.get();
    }

    public void removePermissoesNulas(Funcionario f) {
        f.getPermissoes().removeIf((Permissao p) -> {
            return p.getId() == null;
        });
        if (f.getPermissoes().isEmpty()) {
            throw new RuntimeException("Funcionário deve conter no mínimo 1 permissão");
        }
    }
}
