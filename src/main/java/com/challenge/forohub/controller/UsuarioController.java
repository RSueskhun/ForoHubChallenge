package com.challenge.forohub.controller;

import com.challenge.forohub.domain.usuarios.Usuario;
import com.challenge.forohub.domain.usuarios.dto.ActualizarUsuarioDTO;
import com.challenge.forohub.domain.usuarios.dto.CrearUsuarioDTO;
import com.challenge.forohub.domain.usuarios.dto.DetallesUsuarioDTO;
import com.challenge.forohub.domain.usuarios.repository.UsuarioRepository;
import com.challenge.forohub.domain.usuarios.validations.create.ValidarCrearUsuario;
import com.challenge.forohub.domain.usuarios.validations.update.ValidarActualizarUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Usuario", description = "CREAR TOPICOS Y CREAR RESPUESTAS")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    List<ValidarCrearUsuario> crearValidador;

    @Autowired
    List<ValidarActualizarUsuario> actualizarValidador;

    //POST
    @PostMapping
    @Transactional
    @Operation(summary = "REGISTRA UN NUEVO USAURIO EN LA BASE DE DATOS")
    public ResponseEntity<DetallesUsuarioDTO> crearUsuario(@RequestBody @Valid CrearUsuarioDTO crearUsuarioDTO, UriComponentsBuilder uriBuilder){
        crearValidador.forEach(v -> v.validate(crearUsuarioDTO));

        String hashedPassword = passwordEncoder.encode(crearUsuarioDTO.password());
        Usuario usuario = new Usuario(crearUsuarioDTO, hashedPassword);

        repository.save(usuario);
        var uri = uriBuilder.path("/usuarios/{username}").buildAndExpand(usuario.getUsername()).toUri();
        return ResponseEntity.created(uri).body(new DetallesUsuarioDTO(usuario));
    }

    //GET
    @GetMapping("/all")
    @Operation(summary = "LISTA A TODOS LOS USUARIOS")
    public ResponseEntity<Page<DetallesUsuarioDTO>> leerTodosUsuarios(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable){
        var pagina = repository.findAll(pageable).map(DetallesUsuarioDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping
    @Operation(summary = "LISTA USUARIOS HABILITADOS")
    public ResponseEntity<Page<DetallesUsuarioDTO>> leerUsuariosActivos(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable){
        var pagina = repository.findAllByEnabledTrue(pageable).map(DetallesUsuarioDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "FILTRA USUARIO POR NOMBRE")
    public ResponseEntity<DetallesUsuarioDTO> leerUnUsuario(@PathVariable String username){
        Usuario usuario = (Usuario) repository.findByUsername(username);
        var datosUsuario = new DetallesUsuarioDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRole(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getEnabled()
        );
        return ResponseEntity.ok(datosUsuario);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "FILTRA USUARIO POR ID")
    public ResponseEntity<DetallesUsuarioDTO>leerUnUsuario(@PathVariable Long id){
        Usuario usuario = repository.getReferenceById(id);
        var datosUsuario = new DetallesUsuarioDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRole(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getEnabled()
        );
        return ResponseEntity.ok(datosUsuario);
    }

    //PUT
    @PutMapping("/{username}")
    @Transactional
    @Operation(summary = "ACTUALIZA DE UN USUARIO: ROL - CONTRASEÑA - NOMBRE - APELLIDO - EMAIL - ESTADO")
    public ResponseEntity<DetallesUsuarioDTO> actualizarUsuario(@RequestBody @Valid ActualizarUsuarioDTO actualizarUsuarioDTO, @PathVariable String username){
        actualizarValidador.forEach(v -> v.validate(actualizarUsuarioDTO));

        Usuario usuario = (Usuario) repository.findByUsername(username);

        if (actualizarUsuarioDTO.password() != null){
            String hashedPassword = passwordEncoder.encode(actualizarUsuarioDTO.password());
            usuario.actualizarUsuarioConPassword(actualizarUsuarioDTO, hashedPassword);

        }else {
            usuario.actualizarUsuario(actualizarUsuarioDTO);
        }

        var datosUsuario = new DetallesUsuarioDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRole(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getEnabled()
        );
        return ResponseEntity.ok(datosUsuario);
    }

    //DELETE
    @DeleteMapping("/{username}")
    @Transactional
    @Operation(summary = "DESHABILITA UN USUARIO")
    public ResponseEntity<?> eliminarUsuario(@PathVariable String username){
        Usuario usuario = (Usuario) repository.findByUsername(username);
        usuario.eliminarUsuario();
        return ResponseEntity.noContent().build();
    }


}