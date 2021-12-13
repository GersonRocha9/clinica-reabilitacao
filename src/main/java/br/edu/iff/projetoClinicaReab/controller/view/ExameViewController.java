package br.edu.iff.projetoClinicaReab.controller.view;

import br.edu.iff.projetoClinicaReab.model.Exame;
import br.edu.iff.projetoClinicaReab.services.ExameService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/exames")
public class ExameViewController {

  @Autowired
  private ExameService service;

  @GetMapping
  public String getAll(Model model) {
    model.addAttribute("exames", service.findAll());
    return "exames";
  }

  @GetMapping(path = "/exame")
  public String cadastro(Model model) {
    model.addAttribute("exame", new Exame());
    return "formExame";
  }

  @PostMapping(path = "/exame")
  public String salvar(@Valid @ModelAttribute Exame exame, BindingResult result, Model model) {

    if (result.hasErrors()) {
      model.addAttribute("msgErros", result.getAllErrors());
      return "formExame";
    }
    exame.setId(null);
    try {
      service.save(exame);
      model.addAttribute("msgSucesso", "Exame cadastrado com sucesso.");
      model.addAttribute("exame", new Exame());
      return "formExame";
    } catch (Exception e) {
      model.addAttribute("msgErros", new ObjectError("exame", e.getMessage()));
      return "formExame";
    }
  }

  @GetMapping(path = "/exame/{id}")
  public String editar(@PathVariable("id") Long id, Model model) {
    model.addAttribute("exame", service.findById(id));
    return "formExame";
  }

  @PostMapping(path = "/exame/{id}")
  public String atualizar(@Valid @ModelAttribute Exame exame, BindingResult result,
      @PathVariable("id") Long id, Model model) {

    if (result.hasErrors()) {
      model.addAttribute("msgErros", result.getAllErrors());
      return "formExame";
    }
    exame.setId(id);
    try {
      service.update(exame);
      model.addAttribute("msgSucesso", "Exame cadastrado com sucesso.");
      model.addAttribute("exame", exame);
      return "formExame";
    } catch (Exception e) {
      model.addAttribute("msgErros", new ObjectError("exame", e.getMessage()));
      return "formExame";
    }
  }

  @GetMapping(path = "{id}/deletar")
  public String deletar(@PathVariable("id") Long id) {
    service.delete(id);
    return "redirect:/exames";
  }
}
