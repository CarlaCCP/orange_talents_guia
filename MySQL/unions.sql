use sucos_vendas;

-- o uninon já vem com distinct
-- precisa ter o mesmo número de tabelas nas duas consultas 
select bairro from tabela_de_clientes
union
select bairro from tabela_de_vendedores;

-- força a inclusão de todos os dados
select bairro from tabela_de_clientes
union all
select bairro from tabela_de_vendedores;