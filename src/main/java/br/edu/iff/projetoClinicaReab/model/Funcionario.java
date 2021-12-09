
package br.edu.iff.projetoClinicaReab.model;

import br.edu.iff.projetoClinicaReab.annotation.EmailValidation;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Funcionario extends Pessoa {

    private static final long serialVersionUID = 1L;
   
    @NotBlank(message = "E-mail obrigatório")
    @EmailValidation(message = "E-mail inválido")
    private String email;
    @NotBlank(message = "Senha obrigatória")
    private String senha;

    @ManyToMany(fetch = FetchType.EAGER)
    @OrderColumn
    @Size(min = 1, message = "Funcionário deve ter no mínimo 1 permissão")
    private List<Permissao> permissoes = new ArrayList<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((permissoes == null) ? 0 : permissoes.hashCode());
        result = prime * result + ((senha == null) ? 0 : senha.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Funcionario other = (Funcionario) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (permissoes == null) {
            if (other.permissoes != null)
                return false;
        } else if (!permissoes.equals(other.permissoes))
            return false;
        if (senha == null) {
            if (other.senha != null)
                return false;
        } else if (!senha.equals(other.senha))
            return false;
        return true;
    }

}
