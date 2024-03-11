package io.biker.management.biker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.biker.dto.BikerCreationDTO;
import io.biker.management.biker.dto.BikerReadingDTO;
import io.biker.management.biker.entity.Biker;
import io.biker.management.biker.mapper.BikerMapper;
import io.biker.management.biker.service.BikerService;
import io.biker.management.constants.Roles_Const;
import io.biker.management.constants.response.Responses;
import io.biker.management.errorHandling.responses.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@RestController
@Tag(name = "Bikers", description = "Controller for handling mappings for bikers")
@SecurityRequirement(name = "Authorization")
@RequestMapping("/bikers")
public class BikerController {
    private BikerService bikerService;
    private BikerMapper bikerMapper;

    public BikerController(BikerService bikerService, BikerMapper bikerMapper) {
        this.bikerService = bikerService;
        this.bikerMapper = bikerMapper;
    }

    @Operation(description = "POST endpoint for creating a bikers in the table of bikers." +
            "\n\n Can only be done by back office users." +
            "\n\n Returns the biker created as an instance of BikerReadingDTO.", summary = "Create a biker")
    @PostMapping()
    @PreAuthorize("hasAuthority('" + Roles_Const.BACK_OFFICE + "')")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of BikerCreationDTO")
    public BikerReadingDTO createBiker(@Valid @RequestBody BikerCreationDTO dto) {
        log.info("Recieved: POST request to /bikers");
        Biker biker = bikerService.createBiker(bikerMapper.toBiker(dto));

        return bikerMapper.toDto(biker);
    }

    @Operation(description = "GET endpoint for retrieving all bikers." +
            "\n\n Can only be done by back office users." +
            "\n\n Returns all bikers as an List of BikerReadingDTO.", summary = "Get all bikers")
    @GetMapping()
    @PreAuthorize("hasAuthority('" + Roles_Const.BACK_OFFICE + "')")
    public List<BikerReadingDTO> getAllBikers() {
        log.info("Recieved: GET request to /bikers");
        return bikerMapper.toDtos(bikerService.getAllBikers());
    }

    @Operation(description = "GET endpoint for retrieving a single biker given their id." +
            "\n\n Can only be done by back office users or the biker being retrieved." +
            "\n\n Returns the biker as an instance of BikerReadingDTO.", summary = "Get single biker")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('" + Roles_Const.BACK_OFFICE + "') or " +
            "(hasAuthority('" + Roles_Const.BIKER + "') and #id == authentication.principal.id)")
    public BikerReadingDTO getSingleBiker(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Biker ID") @PathVariable int id) {
        log.info("Recieved: GET request to /bikers/" + id);
        return bikerMapper.toDto(bikerService.getSingleBiker(id));
    }

    @Operation(description = "DELETE endpoint for deleting a biker from the biker table." +
            "\n\n Can only be done by admins." +
            "\n\n Returns a response as an instance of ResponseEntity<SuccessResponse>.", summary = "Delete a Biker")
    @Transactional
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('" + Roles_Const.ADMIN + "')")
    public ResponseEntity<SuccessResponse> deleteBiker(
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Biker ID") @PathVariable int id) {
        log.info("Recieved: DELETE request to /bikers/" + id);
        bikerService.deleteBiker(id);
        return new ResponseEntity<SuccessResponse>(new SuccessResponse(Responses.BIKER_DELETED),
                HttpStatus.ACCEPTED);
    }
}
