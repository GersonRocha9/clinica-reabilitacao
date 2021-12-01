package br.edu.iff.projetoClinicaReab;

import br.edu.iff.projetoClinicaReab.model.Endereco;
import br.edu.iff.projetoClinicaReab.model.Funcionario;
import br.edu.iff.projetoClinicaReab.model.Paciente;
import br.edu.iff.projetoClinicaReab.model.Permissao;
import br.edu.iff.projetoClinicaReab.repository.FuncionarioRepository;
import br.edu.iff.projetoClinicaReab.repository.PacienteRepository;
import br.edu.iff.projetoClinicaReab.repository.PermissaoRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetoClinicaReabApplication implements CommandLineRunner {

    @Autowired
    private PacienteRepository pacienteRepo;
    @Autowired
    private FuncionarioRepository funcionarioRepo;
    @Autowired
    private PermissaoRepository permissaoRepo;

    public static void main(String[] args) {
        SpringApplication.run(ProjetoClinicaReabApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Permissão
        Permissao per1 = new Permissao();
        per1.setNome("ADMIN");
        Permissao per2 = new Permissao();
        per2.setNome("FUNC");
        permissaoRepo.saveAll(List.of(per1, per2));

        Endereco end = new Endereco();
        end.setRua("Rua Amaro Silveira");
        end.setNumero(24);
        end.setBairro("Parque Califórnia");
        end.setCidade("Campos");
        end.setCep("28015-490");

        Paciente p1 = new Paciente();
        p1.setNome("Gerson Rocha");
        p1.setCpf("130.678.117-55");
        p1.setSituacao("Encontra-se bem, porém precisando de acolhimento.");
        p1.setTelefone("(22)99952-4259");
        p1.setEndereco(end);

        Funcionario f1 = new Funcionario();
        f1.setPermissoes(List.of(per1));
        f1.setNome("Adilson");
        f1.setEmail("adilson@gmail.com");
        f1.setSenha("123456");
        f1.setCpf("052.813.417-58");
        f1.setTelefone("(22)99432-3823");
        f1.setEndereco(end);

        funcionarioRepo.save(f1);
        pacienteRepo.save(p1);

    }

}