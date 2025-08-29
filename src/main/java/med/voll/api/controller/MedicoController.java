package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

// aplicando boas praticas usando metodo responsentity
// passando mapeamento api
@RestController
@RequestMapping("medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
            var medico = new Medico(dados);
            repository.save(medico);

            // criando variavel da uri
            var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
            return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    @Transactional
    public ResponseEntity<Page<DadosListagemMedico>>listar(@PageableDefault(sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar( @RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.AtualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity remover(@PathVariable long id){
        var medico = repository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();
    }

    // detalhamento de cadastro
    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable long id){
        var medico = repository.getReferenceById(id);


        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));

    }

}
