create database zup;
use zup;
drop database zup;
create table alunosZup (
	idAluno int auto_increment,
	email varchar(30),
	nome varchar (30),
	idade tinyint,
    primary key (idAluno)
); 

create table avaliacao (
	idAvaliacao int auto_increment,
    titulo varchar(50),
    descricao text,
    primary key (idAvaliacao)
);

create table respostaAvaliacao (
	idResposta int auto_increment,
	resposta text, -- text
    idAluno int,
    idAvaliacao int,
    primary key (idResposta),
    foreign key (idAluno) references alunosZup (idAluno),
    foreign key (idAvaliacao) references avaliacao (idAvaliacao)
);

create table notaAvaliacao (
	idNota int auto_increment,
    nota tinyint,
    idResposta int,
    idAluno int,
    primary key (idNota),
	foreign key (idAluno) references alunosZup (idAluno), -- não precisa 
	foreign key (idResposta) references respostaAvaliacao (idResposta)
); 

insert into alunosZup 
values
(null, 'carla@carla.com', 'Carla Cristina', 20),
(null, 'jose@jose.com', "Jose Paulo", 30),
(null, 'paula@paula.com', "Paula Conceição", 25),
(null, 'Victor@victor.com', "Victor Saade", 27),
(null, 'Joao@joao.com', "Joao Victor", 18);

insert into avaliacao values
(null, "Spring", "testando testando testando testandot estando testando"),
(null, "Java", "testando testando testando testandot estando testando"),
(null, "Teste", "testando testando testando testandot estando testando"),
(null, "MYSQL", "testando testando testando testandot estando testando"),
(null, "Kotlin", "testando testando testando testandot estando testando");

insert into avaliacao values
(null, "HIBERNETE", "testando testando testando testandot estando testando");


-- aluno e avaliacao
insert into respostaAvaliacao values 
(null, "resposta respósta resposta", 1, 1), -- 1
(null, "resposta respósta resposta", 2, 1), -- 2
(null, "resposta respósta resposta", 3, 2), -- 3
(null, "resposta respósta resposta", 4, 3), -- 4
(null, "resposta respósta resposta", 5, 5), -- 5
(null, "resposta respósta resposta", 5, 1), -- 6
(null, "resposta respósta resposta", 5, 2), -- 7 
(null, "resposta respósta resposta", 5, 3), -- 8
(null, "resposta respósta resposta", 3, 4); -- 9

-- nota, idResposta, idAluno
insert into notaAvaliacao values 
(null,10,1,1),
(null,7,2, 2),
(null,5,3 ,3),
(null,10,4,4),
(null,9,5,5 ),
(null,5,6,5 ),
(null,5,7,5 ),
(null,10,8,5 ),
(null,9,9,3 );

select * from alunosZup;
select * from avaliacao;
select * from respostaAvaliacao;
select * from notaAvaliacao;

-- Resposta A
select a.nome, b.titulo
from alunoszup a
inner join respostaavaliacao c on a.idAluno = c.idAluno
inner join avaliacao b on b.idAvaliacao = c.idAvaliacao
where titulo like '%spring%'
group by nome;

-- RESPOSTA B
-- Precisamos saber quantas respostas foram dadas por avaliação
select count(resposta) as quantidade_Resposta from respostaAvaliacao 
where idAvaliacao = 4;

select x.idAvaliacao, x.titulo, count(c.resposta) as Quantidade
from avaliacao x
inner join respostaavaliacao c
on x.idAvaliacao = c.idAvaliacao
group by idAvaliacao;

-- Precisamos da nota média da autocorreção por avaliação
select b.idAvaliacao, avg(c.nota) as Média, d.titulo 
from notaavaliacao c
right join respostaavaliacao b on b.idResposta = c.idResposta
right join avaliacao d on d.idAvaliacao = b.idAvaliacao
group by idAvaliacao;
