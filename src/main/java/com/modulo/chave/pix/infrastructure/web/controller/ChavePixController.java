package com.modulo.chave.pix.infrastructure.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modulo.chave.pix.application.dto.request.AlterarChavePixRequest;
import com.modulo.chave.pix.application.dto.request.CriarChavePixRequest;
import com.modulo.chave.pix.application.dto.response.AlterarChavePixResponse;
import com.modulo.chave.pix.application.dto.response.CriarChavePixResponse;
import com.modulo.chave.pix.application.mappers.ChavePixMapper;
import com.modulo.chave.pix.application.usecase.AlteracaoChavePixUseCase;
import com.modulo.chave.pix.application.usecase.AlteracaoContaPixUseCase;
import com.modulo.chave.pix.application.usecase.InclusaoChavePixUseCase;
import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.exception.ValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/chave-pix")
public class ChavePixController {

    private final InclusaoChavePixUseCase inclusaoChavePixUseCase;
    private final AlteracaoChavePixUseCase alteracaoChavePixUseCase;
    private final AlteracaoContaPixUseCase alteracaoContaPixUseCase;
    private final ChavePixMapper chavePixMapper;

    @Operation(summary = "Cria nova chave PIX")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chave criada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping("/criar")
    public ResponseEntity<CriarChavePixResponse> create(@Valid @RequestBody CriarChavePixRequest request)
            throws BusinessValidationException, ValidationException {

        log.info("Iniciando processo de criação de chave PIX");

        ChavePix chavePix = chavePixMapper.toCriarDomain(request);
        ChavePix chavePixCriada = inclusaoChavePixUseCase.execute(chavePix);
        CriarChavePixResponse response = chavePixMapper.toCriarResponse(chavePixCriada);

        log.info("Chave PIX criada com sucesso");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Altera chave PIX existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chave alterada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Chave não encontrada"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/alterar-chave")
    public ResponseEntity<AlterarChavePixResponse> updateChave(@Valid @RequestBody AlterarChavePixRequest request) {
            log.info("Iniciando processo de alteração de chave PIX");

            ChavePix chavePix = chavePixMapper.toAlterarDomain(request);
            ChavePix chavePixAtualizada = alteracaoChavePixUseCase.execute(chavePix);
            AlterarChavePixResponse response = chavePixMapper.toAlterarResponse(chavePixAtualizada);

            log.info("Chave PIX atualizada com sucesso");
            return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Altera conta PIX existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chave alterada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Chave não encontrada"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/alterar-conta")
    public ResponseEntity<AlterarChavePixResponse> updateConta(@Valid @RequestBody AlterarChavePixRequest request) {
            log.info("Iniciando processo de alteração de chave PIX");

            ChavePix chavePix = chavePixMapper.toAlterarDomain(request);
            ChavePix chavePixAtualizada = alteracaoContaPixUseCase.execute(chavePix);
            AlterarChavePixResponse response = chavePixMapper.toAlterarResponse(chavePixAtualizada);

            log.info("Chave PIX atualizada com sucesso");
            return ResponseEntity.ok(response);
    }

    /*
     * @DeleteMapping("/{id}")
     * public ResponseEntity<CriarChavePixResponse> delete(@PathVariable UUID id) {
     * try {
     * inclusaoChavePixUseCase.execute(id);
     * return ResponseEntity.ok(new CriarChavePixResponse(id));
     * }
     * }
     * 
     * @GetMapping("/{id}")
     * public ResponseEntity<CriarChavePixResponse> get(@PathVariable UUID id) {
     * try {
     * return ResponseEntity.ok(inclusaoChavePixUseCase.execute(id));
     * }
     * }
     * 
     * @GetMapping("/{tipo-chave}")
     * public ResponseEntity<CriarChavePixResponse> get(@PathVariable String
     * tipoChave) {
     * try {
     * return ResponseEntity.ok(inclusaoChavePixUseCase.execute(tipoChave));
     * }
     * }
     * 
     * @GetMapping("/{nome-correntista}")
     * public ResponseEntity<CriarChavePixResponse> get(@PathVariable String
     * nomeCorrentista) {
     * try {
     * return ResponseEntity.ok(inclusaoChavePixUseCase.execute(nomeCorrentista));
     * }
     * }
     * 
     * @GetMapping("/{data-inclusao}")
     * public ResponseEntity<CriarChavePixResponse> get(@PathVariable LocalDateTime
     * dataInclusao) {
     * try {
     * return ResponseEntity.ok(inclusaoChavePixUseCase.execute(dataInclusao));
     * }
     * }
     */
}
