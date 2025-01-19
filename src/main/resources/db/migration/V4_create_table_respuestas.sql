CREATE TABLE respuestas (
                            id bigint auto_increment primary key,
                            borrado bit(1) not null,
                            fecha_creacion datetime not null,
                            mensaje varchar(255) not null,
                            solucion bit(1) not null,
                            ultima_actualizacion datetime not null,
                            topico_id bigint not null ,
                            usuario_id bigint not null,
                            foreign key (topico_id) references topicos(id),
                            foreign key (usuario_id) references usuarios(id)
);