package br.com.test.simplesdental.controllers;


import br.com.test.simplesdental.exceptions.ApiException;
import br.com.test.simplesdental.model.dto.ContatoRequestDTO;
import br.com.test.simplesdental.model.dto.ErrorResponse;
import br.com.test.simplesdental.model.entity.ContatoEntity;
import br.com.test.simplesdental.service.ContatoService;
import br.com.test.simplesdental.service.FilterFieldsService;
import br.com.test.simplesdental.swagger.ExamplesDocs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contatos")
@RequiredArgsConstructor
public class ContatoController {

    private final ContatoService contatoService;
    private final FilterFieldsService filterFieldsService;

    @Operation(summary = "Lista de contato com base nos critérios definidos nos filtros")
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Encontrou os registros",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ContatoEntity.class)))})
    })
    public ResponseEntity<MappingJacksonValue> list(
            @RequestParam(value = "filter")
            @Schema(example = ExamplesDocs.CONTATO_FILTER, description = ExamplesDocs.CONTATO_FILTER_DESC)
            final String filter,

            @RequestParam(value = "fields", required = false)
            @Schema(example = ExamplesDocs.CONTATO_FIELDS, description = ExamplesDocs.CONTATO_FIELDS_DESC)
            List<String> fields
    ) {
        List<ContatoEntity> contatosEntities = this.contatoService.listarContatos(filter);

        MappingJacksonValue objetoComOsCamposFiltrados = this.filterFieldsService.getObjetoComOsCamposFiltrados(fields, contatosEntities, ContatoEntity.class);

        return new ResponseEntity<>(
                objetoComOsCamposFiltrados, HttpStatus.OK);
    }


    @Operation(summary = "Retorna todos os dados do contato que possui o ID passado na URL")
    @GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Encontrou o registro",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ContatoEntity.class)))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Registro não encontrado",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    public ResponseEntity<ContatoEntity> list(
            @RequestParam(value = "id", required = false)
            @Schema(example = ExamplesDocs.PROFISSIONAL_ID, description = ExamplesDocs.PROFISSIONAL_ID_DESC)
            String id
    ) throws ApiException {
        return new ResponseEntity<>(this.contatoService.getById(id), HttpStatus.OK);
    }


    @Operation(summary = "Cria o registro do contato")
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Criou o contato",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ContatoEntity.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erros de validação nos dados de entrada do contato",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))})

    })
    public ResponseEntity<ContatoEntity> create(@Valid @RequestBody ContatoRequestDTO contato
    ) throws Exception {
        ContatoEntity contatos = this.contatoService.create(contato);
        return new ResponseEntity<>(contatos, HttpStatus.CREATED);
    }

    @Operation(summary = "Ataualiza os dados do contato que possui o ID passado na URL")
    @PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Atualizou o contato",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ContatoEntity.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erros de validação nos dados de entrada do contato",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))})

    })
    public ResponseEntity<ContatoEntity> create(@Valid @RequestBody ContatoRequestDTO contato,
                                                     @RequestParam(value = "id", required = false)
                                                     @Schema(example = ExamplesDocs.CONTATO_ID, description = ExamplesDocs.CONTATO_ID_DESC)
                                                     String id
    ) throws Exception {
        ContatoEntity contatos = this.contatoService.update(contato,id);
        return new ResponseEntity<>(contatos, HttpStatus.OK);
    }


    @Operation(summary = "Ataualiza os dados do contato que possui o ID passado na URL")
    @DeleteMapping(path = "/{id}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Exclusão do contato",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = String.class))})

    })
    public ResponseEntity<String> delete(@RequestParam(value = "id", required = false)
                                                     @Schema(example = ExamplesDocs.CONTATO_ID, description = ExamplesDocs.CONTATO_ID_DESC)
                                                     String id
    ) throws Exception {
        this.contatoService.delete(id);
        return new ResponseEntity<>("Sucesso contato excluído", HttpStatus.OK);
    }

}
