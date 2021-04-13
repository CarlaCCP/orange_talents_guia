use sucos_vendas;
select distinct bairro from tabela_de_clientes;

select * from tabela_de_clientes where bairro 
in (select distinct bairro from tabela_de_clientes);

-- outra forma
select x.embalagem, x.preco from
(select embalagem, max(preco_de_lista) as preco from tabela_de_produtos
GROUP BY embalagem) x where x.preco >= 10;

 SELECT CPF, COUNT(*) FROM notas_fiscais
  WHERE YEAR(DATA_VENDA) = 2016
  GROUP BY CPF
 HAVING COUNT(*) > 2000;
 
 
 -- mesma consulta
 SELECT X.CPF, X.CONTADOR FROM 
(SELECT CPF, COUNT(*) AS CONTADOR FROM notas_fiscais
WHERE YEAR(DATA_VENDA) = 2016
GROUP BY CPF) X WHERE X.CONTADOR > 2000;