create table tb_user(
    sus_card varchar(15) unique primary key,
    name varchar(255) not null,
    cpf varchar(11) unique not null,
    rg varchar(20) unique not null,
    phone varchar(20) not null,
    insulin_posology varchar(255) not null
)