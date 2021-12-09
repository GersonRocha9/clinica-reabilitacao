package br.edu.iff.projetoClinicaReab.controller.view;

import br.edu.iff.projetoClinicaReab.model.Funcionario;
import br.edu.iff.projetoClinicaReab.repository.PermissaoRepository;
import br.edu.iff.projetoClinicaReab.services.FuncionarioService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path = "/funcionarios")
public class FuncionarioViewController {

    @Autowired
    private FuncionarioService service;
    @Autowired
    private PermissaoRepository permissaoRepo;
    
    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("funcionarios", service.findAll());
        return "funcionarios";
    }
    
    /*
    //exibe somente 1 dado, caso não queira mostrar todos
    @GetMapping
    public String getAll( Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String email = auth.getName();
    	
        model.addAttribute("funcionarios",service.findByEmail(email));
        System.out.println( email);
        return "funcionarios";
    }
    */
    
    
    

    @GetMapping(path = "/funcionario")
    public String cadastro(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        model.addAttribute("permissoes", permissaoRepo.findAll());
        return "formFuncionario";
    }

    @PostMapping(path = "/funcionario")
    public String save(@Valid @ModelAttribute Funcionario funcionario, BindingResult result,
            // @RequestParam("confirmarSenha") String confirmarSenha,
            Model model) {

        // Valores a serem retornados:
        model.addAttribute("permissoes", permissaoRepo.findAll());

        if (result.hasErrors()) {
            model.addAttribute("msgErros", result.getAllErrors());
            return "formFuncionario";
        }

        funcionario.setId(null);
        try {
        	String senhaCriptografada = new BCryptPasswordEncoder().encode(funcionario.getSenha());
    		funcionario.setSenha(senhaCriptografada);
            service.save(funcionario);
            model.addAttribute("msgSucesso", "Funcionário cadastrado com sucesso.");
            model.addAttribute("funcionario", new Funcionario());
            return "formFuncionario";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("funcionario", e.getMessage()));
            return "formFuncionario";
        }
    }

    @GetMapping(path = "/funcionario/{id}")
    public String atualizacao(@PathVariable("id") Long id, Model model) {
        model.addAttribute("funcionario", service.findById(id));
        model.addAttribute("permissoes", permissaoRepo.findAll());
        return "formFuncionario";
    }

    @PostMapping(path = "/funcionario/{id}")
    public String update(@Valid @ModelAttribute Funcionario funcionario, BindingResult result,
            @PathVariable("id") Long id, Model model) {

        // Valores a serem retornados:
        model.addAttribute("permissoes", permissaoRepo.findAll());

        List<FieldError> list = new ArrayList<>();
        for (FieldError fe : result.getFieldErrors()) {
            if (!fe.getField().equals("senha")) {
                list.add(fe);
            }
        }
        if (!list.isEmpty()) {
            model.addAttribute("msgErros", list);
            return "formFuncionario";
        }

        funcionario.setId(id);
        try {
        	String senhaCriptografada = new BCryptPasswordEncoder().encode(funcionario.getSenha());
    		funcionario.setSenha(senhaCriptografada);
    		service.update(funcionario);
            model.addAttribute("msgSucesso", "Funcionário atualizado com sucesso.");
            model.addAttribute("funcionario", funcionario);
            return "formFuncionario";
        } catch (Exception e) {
            model.addAttribute("msgErros", new ObjectError("funcionario", e.getMessage()));
            return "formFuncionario";
        }
    }

    @GetMapping(path = "/{id}/deletar")
    public String deletar(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/funcionarios";
    }

    /*
    //teste ver meus dados
    @GetMapping(path = "/meusdados/{id}")
    public String getOne(@AuthenticationPrincipal Funcionario funcionario, RedirectAttributes model, @PathVariable("id") Long id) {
        model.addAttribute("funcionario", service.findById(funcionario.getId()));
        model.addAttribute("funcionario", funcionario);
        return "meusdados";
    }
    */
}