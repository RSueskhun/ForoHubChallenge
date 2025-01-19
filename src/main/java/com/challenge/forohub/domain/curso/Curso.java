package com.challenge.forohub.domain.curso;


import com.challenge.forohub.domain.curso.dto.ActualizarCursoDTO;
import com.challenge.forohub.domain.curso.dto.CrearCursoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cursos")
@Entity(name = "Curso")
@EqualsAndHashCode(of ="Id")

public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    private Boolean activo;

    public Curso(CrearCursoDTO crearCursoDTO) {
        this.name = crearCursoDTO.name();
        this.categoria = crearCursoDTO.categoria();
        this.activo = true;
    }

    public void actualizarCurso(ActualizarCursoDTO actualizarCursoDTO) {

        if(actualizarCursoDTO.name() !=null){
            this.name = actualizarCursoDTO.name();
        }
        if(actualizarCursoDTO.categoria() !=null){
            this.categoria = actualizarCursoDTO.categoria();
        }
        if(actualizarCursoDTO.activo() !=null){
            this.activo = actualizarCursoDTO.activo();
        }
    }

    public void eliminarCurso () {
    this.activo = false;
    }
}