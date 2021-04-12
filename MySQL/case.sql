USE sucos_vendas;

select nome,
CASE  
when year(data_de_nascimento) < 1990 then 'Velhos'
when year(data_de_nascimento) between 1990 and 1995 then 'Jovens'
else 'Crianças' end as Idade
from tabela_de_clientes;
