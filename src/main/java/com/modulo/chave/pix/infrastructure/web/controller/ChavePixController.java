package com.modulo.chave.pix.infrastructure.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modulo.chave.pix.application.dto.request.CriarChavePixRequest;
import com.modulo.chave.pix.application.dto.response.CriarChavePixResponse;
import com.modulo.chave.pix.application.mappers.ChavePixMapper;
import com.modulo.chave.pix.application.usecase.InclusaoChavePixUseCase;
import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.exception.ValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@RestController
@Slf4j
//@RequestMapping("/chave-pix")
@RequestMapping("/api/modulo-chave-pix/chave-pix")
public class ChavePixController {

    private final InclusaoChavePixUseCase inclusaoChavePixUseCase;
    private final ChavePixMapper chavePixMapper;

    @Operation(summary = "Cria nova chave PIX")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chave criada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    public ResponseEntity<CriarChavePixResponse> create(@Valid @RequestBody CriarChavePixRequest request)
            throws BusinessValidationException, ValidationException {

        log.info("Iniciando processo de criação de chave PIX");
        
        ChavePix chavePix = chavePixMapper.requestToDomain(request);
        ChavePix chavePixCriada = inclusaoChavePixUseCase.execute(chavePix);
        CriarChavePixResponse response = chavePixMapper.domainToResponse(chavePixCriada);

        log.info("Chave PIX criada com sucesso");
        return ResponseEntity.ok(response);
    }

    /*
     * @PutMapping("/{id}")
     * public ResponseEntity<CriarChavePixResponse> update(@PathVariable UUID
     * id, @Valid @RequestBody CriarChavePixRequest request) {
     * try {
     * UUID id = inclusaoChavePixUseCase.execute(request);
     * return ResponseEntity.ok(new CriarChavePixResponse(id));
     * }
     * }
     * 
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
