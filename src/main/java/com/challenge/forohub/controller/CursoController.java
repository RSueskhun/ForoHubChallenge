package com.challenge.forohub.controller;

import com.challenge.forohub.domain.curso.Curso;
import com.challenge.forohub.domain.curso.dto.ActualizarCursoDTO;
import com.challenge.forohub.domain.curso.dto.CrearCursoDTO;
import com.challenge.forohub.domain.curso.dto.DetalleCursoDTO;
import com.challenge.forohub.domain.curso.repository.CursoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Curso", description = "PUEDE ENCONTRARSE EN LAS CATEGORIAS DEFINIDAS")
public class CursoController {

    @Autowired
    private CursoRepository repository;

//POST
    @PostMapping
    @Transactional
    @Operation(summary = "REGISTRAR NUEVO CURSO EN LA BASE DE DATOS")
    public ResponseEntity<DetalleCursoDTO> crearTopico(@RequestBody @Valid CrearCursoDTO crearCursoDTO,
                                                       UriComponentsBuilder uriBuilder) {

        Curso curso = new Curso(crearCursoDTO);
        repository.save(curso);
        var uri = uriBuilder.path("/cursos/{i}").buildAndExpand(curso.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetalleCursoDTO(curso));

    }

    @GetMapping("/all")
    @Operation(summary = "LEER TODOS LOS CURSOS")
    public ResponseEntity<Page<DetalleCursoDTO>> listarCursos(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        var pagina = repository.findAll(pageable).map(DetalleCursoDTO::new);
        return ResponseEntity.ok(pagina);
    }
//GET
    @GetMapping
    @Operation(summary = "LISTA DE CURSOS ACTIVOS")
    public ResponseEntity<Page<DetalleCursoDTO>> listarCursosActivos(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        var pagina = repository.findAllByActivoTrue(pageable).map(DetalleCursoDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    @Operation(summary = "FILTRA CURSO POR ID")
    public ResponseEntity<DetalleCursoDTO> ListarUnCurso(@PathVariable Long id) {
        Curso curso = repository.getReferenceById(id);
        var datosDelCurso = new DetalleCursoDTO(
                curso.getId(),
                curso.getName(),
                curso.getCategoria(),
                curso.getActivo()
        );
        return ResponseEntity.ok(datosDelCurso);
    }

// PUT
    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "ACTUALIZA PARA UN CURSO: NOMBRE - ESTADO - CATEGORIA")
    public ResponseEntity<DetalleCursoDTO> actualizarCurso(@RequestBody @Valid ActualizarCursoDTO actualizarCursoDTO, @PathVariable Long id) {

        Curso curso = repository.getReferenceById(id);

        curso.actualizarCurso(actualizarCursoDTO);

        var datosDelCurso = new DetalleCursoDTO(
                curso.getId(),
                curso.getName(),
                curso.getCategoria(),
                curso.getActivo()
        );
        return ResponseEntity.ok(datosDelCurso);
    }

//DELETE
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "ELIMINA CURSO")
    public ResponseEntity<?> eliminarCurso(@PathVariable Long id) {
        Curso curso = repository.getReferenceById(id);
        curso.eliminarCurso();
        return ResponseEntity.noContent().build();
    }
}
