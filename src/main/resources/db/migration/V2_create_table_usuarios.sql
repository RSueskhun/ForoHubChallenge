CREATE TABLE usuarios (
                          id bigint auto_increment primary key,
                          apellido varchar(255) not null,
                          email varchar(255) not null unique,
                          enabled bit(1) not null,
                          nombre varchar(255) not null,
                          password varchar(255) not null,
                          role enum('ADMINISTRADOR', 'USUARIO', 'EXPECTADOR') not null,
                          username varchar(255) not null unique
);