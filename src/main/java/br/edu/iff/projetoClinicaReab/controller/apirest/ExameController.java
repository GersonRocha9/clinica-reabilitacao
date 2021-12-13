
package br.edu.iff.projetoClinicaReab.controller.apirest;

import br.edu.iff.projetoClinicaReab.model.Exame;
import br.edu.iff.projetoClinicaReab.services.ExameService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/apirest/exames")
public class ExameController {
  @Autowired
  private ExameService service;

  @GetMapping
  public ResponseEntity getAll(
      @RequestParam(name = "page", defaultValue = "0", required = false) int page,
      @RequestParam(name = "size", defaultValue = "10", required = false) int size) {

    return ResponseEntity.ok(service.findAll(page, size));
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity getOne(@PathVariable("id") Long id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @PostMapping
  public ResponseEntity save(@Valid @RequestBody Exame exame) {
    exame.setId(null);
    service.save(exame);
    return ResponseEntity.status(HttpStatus.CREATED).body(exame);
  }

  @PutMapping(path = "/{id}")
  public ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody Exame exame) {
    exame.setId(id);
    service.update(exame);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity delete(@PathVariable("id") Long id) {
    service.delete(id);
    ResponseEntity.ok();
    return ResponseEntity.ok().build();
  }
}
