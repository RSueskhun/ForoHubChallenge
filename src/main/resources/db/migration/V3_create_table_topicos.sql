CREATE TABLE topicos (
                         id bigint auto_increment primary key,
                         estado enum('OPEN', 'CLOSED', 'DELETED') not null,
                         fecha_creacion datetime not null,
                         mensaje varchar(255) not null,
                         titulo varchar(255) not null,
                         ultima_actualizacion datetime not null,
                         curso_id bigint not null,
                         usuario_id bigint not null,
                         foreign key (curso_id) references cursos(id),
                         foreign key (usuario_id) references usuarios(id)
);