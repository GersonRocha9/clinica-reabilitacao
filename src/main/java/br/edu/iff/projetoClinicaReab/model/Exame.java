package br.edu.iff.projetoClinicaReab.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Length;

@Entity
public class Exame {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 500)
  @Length(max = 200, message = "Campo Nome do Paciente deve ter no máximo 500 caracteres")
  public String nomePaciente;

  @Column(length = 500)
  @Length(max = 200, message = "Campo Descrição deve ter no máximo 500 caracteres")
  public String descricaoExame;

  @Column(length = 500)
  @Length(max = 200, message = "Campo Data do Exame deve ter no máximo 500 caracteres")
  public String dataExame;

  @Column(length = 500)
  @Length(max = 200, message = "Campo Horário do Exame deve ter no máximo 500 caracteres")
  public String horarioExame;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNomePaciente() {
    return nomePaciente;
  }

  public void setNomePaciente(String nomePaciente) {
    this.nomePaciente = nomePaciente;
  }

  public String getDescricaoExame() {
    return descricaoExame;
  }

  public void setDescricaoExame(String descricaoExame) {
    this.descricaoExame = descricaoExame;
  }

  public String getDataExame() {
    return dataExame;
  }

  public void setDataExame(String dataExame) {
    this.dataExame = dataExame;
  }

  public String getHorarioExame() {
    return horarioExame;
  }

  public void setHorarioExame(String horarioExame) {
    this.horarioExame = horarioExame;
  }

  public Exame() {
  }

  public Exame(String nomePaciente, Long id, String descricaoExame, String horarioExame, String dataExame) {
    this.id = id;
    this.nomePaciente = nomePaciente;
    this.descricaoExame = descricaoExame;
    this.horarioExame = horarioExame;
    this.dataExame = dataExame;
  }

}