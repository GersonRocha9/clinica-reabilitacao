package br.edu.iff.projetoClinicaReab.controller.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.edu.iff.projetoClinicaReab.model.Funcionario;
import br.edu.iff.projetoClinicaReab.repository.PermissaoRepository;
import br.edu.iff.projetoClinicaReab.services.FuncionarioService;

@RestController
@RequestMapping(path = "/meusdados")
public class MeusDadosViewController {

  @Autowired
  private FuncionarioService service;

  @Autowired
  private PermissaoRepository permissaoRepo;
  /*
   * @PutMapping(path = "/{id}")
   * public ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody
   * Funcionario funcionario) {
   * funcionario.setId(id);
   * service.update(funcionario);
   * return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
   * }
   */

  @GetMapping
  public ModelAndView getAll( Model model) {
	  ModelAndView modelAndView = new ModelAndView();
  	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
  	String email = auth.getName();
  	
      model.addAttribute("funcionarios",service.findByEmail(email));
      model.addAttribute("/meusdados");
      modelAndView.addObject(model);
      System.out.println( email);
      return modelAndView ;
  }
  
  
  
  @PostMapping(path = "/{id}")
  public String update( Funcionario funcionario, BindingResult result,
       Model model) {
	  
	  ModelAndView modelAndView = new ModelAndView();
	  	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  	String email = auth.getName();
	  	

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
      return "formMeusDados";
    }

    funcionario.setEmail(email);
    try {
    	String senhaCriptografada = new BCryptPasswordEncoder().encode(funcionario.getSenha());
		funcionario.setSenha(senhaCriptografada);
      service.update(funcionario);
      model.addAttribute("msgSucesso", "Funcionário atualizado com sucesso.");
      model.addAttribute("funcionario", funcionario);
      return "formMeusDados";
    } catch (Exception e) {
      model.addAttribute("msgErros", new ObjectError("funcionario", e.getMessage()));
      return "formMeusDados";
    }
  }
  

}