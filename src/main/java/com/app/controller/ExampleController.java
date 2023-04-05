package com.app.controller;

import com.app.core.exception.AppErrorResponse;
import com.app.model.dto.example.ExampleDto;
import com.app.model.dto.example.create.CreateExampleDto;
import com.app.model.enums.ExampleSourceEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequestMapping(path = "/api/v1/example", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.ALL_VALUE})
@Tag(name = "Example Controller", description = "Example Controller")
@RestController
public interface ExampleController {

    @GetMapping()
    @Operation(
            summary = "Get all examples"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of examples"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(schema = @Schema(implementation = AppErrorResponse.class))
            )

    })
    Flux<ExampleDto> getAllExample(
            @RequestParam(value = "age", required = false)
            @Min(1) Integer age,
            @RequestParam(value = "source")
            @Schema(enumAsRef = true)
            ExampleSourceEnum exampleSourceEnum);

    @GetMapping("{id}")
    @Operation(
            summary = "get example by id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Example response"
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No matching data",
                    content = @Content(schema = @Schema())
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(schema = @Schema(implementation = AppErrorResponse.class))
            )
    })
    Mono<ExampleDto> getExampleById(
            @PathVariable(value = "id") String id);

    @PostMapping()
    @Operation(
            summary = "create example"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Example response"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(schema = @Schema(implementation = AppErrorResponse.class))
            )
    })
    Mono<ExampleDto> createExample(
            @RequestBody
            @Valid CreateExampleDto createExampleDto);
}
