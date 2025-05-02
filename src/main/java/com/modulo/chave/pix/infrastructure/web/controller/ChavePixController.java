package com.modulo.chave.pix.infrastructure.web.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.modulo.chave.pix.application.dto.request.AlteracaoChavePixRequest;
import com.modulo.chave.pix.application.dto.request.AlteracaoContaPixRequest;
import com.modulo.chave.pix.application.dto.request.ConsultaChavePixRequest;
import com.modulo.chave.pix.application.dto.request.InclusaoChavePixRequest;
import com.modulo.chave.pix.application.dto.response.AlteracaoChavePixResponse;
import com.modulo.chave.pix.application.dto.response.ConsultaChavePixResponse;
import com.modulo.chave.pix.application.dto.response.InclusaoChavePixResponse;
import com.modulo.chave.pix.application.mappers.ChavePixMapper;
import com.modulo.chave.pix.application.usecase.AlteracaoChavePixUseCase;
import com.modulo.chave.pix.application.usecase.AlteracaoContaPixUseCase;
import com.modulo.chave.pix.application.usecase.ConsultaChavePixUseCase;
import com.modulo.chave.pix.application.usecase.InclusaoChavePixUseCase;
import com.modulo.chave.pix.domain.exception.BusinessValidationException;
import com.modulo.chave.pix.domain.exception.ValidationException;
import com.modulo.chave.pix.domain.model.ChavePix;
import com.modulo.chave.pix.domain.model.enums.TipoChaveEnum;
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
    private final ConsultaChavePixUseCase consultaChavePixUseCase;
    private final ChavePixMapper chavePixMapper;

    @Operation(summary = "Cria nova chave PIX")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Chave criada com sucesso"),
        @ApiResponse(responseCode = "422", description = "Erro de validação"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    public ResponseEntity<InclusaoChavePixResponse> incluirChave(@Valid @RequestBody InclusaoChavePixRequest request)
            throws BusinessValidationException, ValidationException {

        log.info("Iniciando processo de criação de chave PIX");

        var chavePix = chavePixMapper.toCriarDomain(request);
        var chavePixCriada = inclusaoChavePixUseCase.execute(chavePix);
        var chavePixResponse = chavePixMapper.toCriarResponse(chavePixCriada);

        log.info("Chave PIX criada com sucesso");
        return ResponseEntity.ok(chavePixResponse);
    }

    @Operation(summary = "Altera chave PIX existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Chave alterada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Chave não encontrada"),
        @ApiResponse(responseCode = "422", description = "Erro de validação"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/alterar-chave")
    public ResponseEntity<AlteracaoChavePixResponse> atualizarChave(@Valid @RequestBody AlteracaoChavePixRequest request) {
        log.info("Iniciando processo de alteração de chave PIX");

        var chavePix = chavePixMapper.toAlterarDomain(request);
        var chavePixAtualizada = alteracaoChavePixUseCase.execute(chavePix);
        AlteracaoChavePixResponse chavePixResponse = chavePixMapper.toAlterarResponse(chavePixAtualizada);

        log.info("Chave PIX atualizada com sucesso");
        return ResponseEntity.ok(chavePixResponse);
    }
    
    @Operation(summary = "Altera conta PIX existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Chave encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Chave não encontrada"),
        @ApiResponse(responseCode = "422", description = "Erro de validação"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/alterar-conta")
    public ResponseEntity<AlteracaoChavePixResponse> atualizarConta(@Valid @RequestBody AlteracaoContaPixRequest request) {
        log.info("Iniciando processo de alteração de conta PIX");

        var chavePix = chavePixMapper.toAlterarDomain(request);
        var chavePixAtualizada = alteracaoContaPixUseCase.execute(chavePix);
        AlteracaoChavePixResponse chavePixResponse = chavePixMapper.toAlterarResponse(chavePixAtualizada);

        log.info("Conta PIX atualizada com sucesso");
        return ResponseEntity.ok(chavePixResponse);
    }


    @Operation(summary = "Consulta chave PIX com filtros")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Chave encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Chave não encontrada"),
        @ApiResponse(responseCode = "422", description = "Erro de validação"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping
    public ResponseEntity<List<ConsultaChavePixResponse>> consultarChavesPix(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) TipoChaveEnum tipoChave,
            @RequestParam(required = false) String numeroAgencia,
            @RequestParam(required = false) String numeroConta,
            @RequestParam(required = false) String nomeCorrentista,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy[ HH:mm:ss]")  LocalDateTime dataInclusao,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy[ HH:mm:ss]") LocalDateTime dataInativacao) {
        
        log.info("Recebendo requisição de consulta de chaves PIX");
        
        // Construir a requisição usando padrão builder
        ConsultaChavePixRequest chavePixRequest = ConsultaChavePixRequest.builder()
                .id(id != null ? java.util.UUID.fromString(id) : null)
                .tipoChave(tipoChave)
                .numeroAgencia(numeroAgencia != null ? numeroAgencia.toString() : null)
                .numeroConta(numeroConta != null ? numeroConta.toString() : null)
                .nomeCorrentista(nomeCorrentista)
                .dataInclusao(dataInclusao != null ? dataInclusao : null)
                .dataInativacao(dataInativacao != null ? dataInativacao : null)
                .build();

        log.info("Iniciando processo consulta de chave PIX");

        var chavePix = chavePixMapper.toAlterarDomain(chavePixRequest);
        List<ChavePix> chavesPixEncontradas = consultaChavePixUseCase.execute(chavePix);
        List<ConsultaChavePixResponse> chavePixResponse = chavePixMapper.toConsultaResponse(chavesPixEncontradas);

        log.info("Consulta de chaves PIX concluída. Encontradas {} chaves", chavePixResponse.size());
        
        if (chavePixResponse.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(chavePixResponse);
    }
}
