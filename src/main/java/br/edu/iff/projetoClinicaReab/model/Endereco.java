
package br.edu.iff.projetoClinicaReab.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Embeddable
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(length = 200, nullable = false)
    @NotBlank(message = "Rua é obrigatório")
    @Length(max = 200, message = "Campo Rua deve ter no máximo 200 caracteres")
    private String rua;

    @Column()
    @Digits(integer = 4, fraction = 0, message = "Número deve ser inteiro e ter até 4 digitos")
    @Min(value = 0L, message = "O número precisa ser positivo")
    private int numero;

    @Column(length = 50, nullable = false)
    @NotBlank(message = "Bairro é obrigatório")
    @Length(max = 50, message = "Campo Bairro deve ter no máximo 50 caracteres")
    private String bairro;

    @Column(length = 50, nullable = false)
    @NotBlank(message = "Cidade é obrigatório")
    @Length(max = 50, message = "Campo Cidade deve ter no máximo 50 caracteres")
    private String cidade;

    @Column(length = 9, nullable = false)
    @NotBlank(message = "CEP é obrigatório")
    @Length(min = 9, max = 9, message = "CEP deve ter o formato XXXXX-XXX")
    private String cep;

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

}
