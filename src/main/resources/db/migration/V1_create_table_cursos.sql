CREATE TABLE cursos (
                        id bigint auto_increment primary key,
                        activo bit(1) not null,
                        categoria enum('FRONTEND', 'BACKEND', 'DEVOPS', 'ROBOTICS', 'IA') not null,
                        name varchar(255) not null
);