package br.edu.iff.projetoClinicaReab.controller.view;

import br.edu.iff.projetoClinicaReab.model.Paciente;
import br.edu.iff.projetoClinicaReab.services.PacienteService;
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
@RequestMapping(path = "/pacientes")
public class PacienteViewController {

    @Autowired
    private PacienteService service;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("pacientes", service.findAll());
        return "pacientes";
    }

    @GetMapping(path = "/paciente")
    public String cadastro(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "formPaciente";
    }

    @PostMapping(path = "/paciente")
    public String salvar(@Valid @ModelAttribute Paciente paciente, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("msgErros", result.getAllErrors());
            return "formPaciente";
        }
        paciente.setId(null);
        try {
            service.save(paciente);
            model.addAttribute("msgSucesso", "Paciente cadastrado com sucesso.");
            model.addAttribute("paciente", new Paciente());
            return "formPaciente";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("paciente", e.getMessage()));
            return "formPaciente";
        }
    }

    @GetMapping(path = "/paciente/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {
        model.addAttribute("paciente", service.findById(id));
        return "formPaciente";
    }

    @PostMapping(path = "/paciente/{id}")
    public String atualizar(@Valid @ModelAttribute Paciente paciente, BindingResult result,
            @PathVariable("id") Long id, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("msgErros", result.getAllErrors());
            return "formPaciente";
        }
        paciente.setId(id);
        try {
            service.update(paciente);
            model.addAttribute("msgSucesso", "Paciente cadastrado com sucesso.");
            model.addAttribute("paciente", paciente);
            return "formPaciente";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("paciente", e.getMessage()));
            return "formPaciente";
        }
    }

    @GetMapping(path = "{id}/deletar")
    public String deletar(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/pacientes";
    }
}
