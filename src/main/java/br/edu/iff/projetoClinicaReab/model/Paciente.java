package br.edu.iff.projetoClinicaReab.model;

// import javax.persistence.Column;
import javax.persistence.Entity;
// import org.hibernate.validator.constraints.Length;

@Entity
public class Paciente extends Pessoa {

    private static final long serialVersionUID = 1L;
    // @Column(length = 500)
    // @Length(max = 200, message = "Campo Situacao deve ter no máximo 500
    // caracteres")
    public String situacao;

    public Paciente() {
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Paciente(String situacao, Long id, String nome, String cpf, String telefone, Endereco endereco) {
        super(id, nome, cpf, telefone, endereco);
        this.situacao = situacao;
    }

}
