USE sucos_vendas;
SELECT * FROM itens_notas_fiscais;

select * from tabela_de_clientes where cidade in ("Rio de Janeiro", "São Paulo") 
and idade >18;

select * from tabela_de_clientes where nome like '%Mattos';

select distinct embalagem as emba, sabor  as sa from tabela_de_produtos;

SELECT DISTINCT BAIRRO FROM tabela_de_clientes WHERE CIDADE = 'Rio de Janeiro';

-- limit sempre fica no final 

select * from notas_fiscais where data_venda = '2017-01-01' limit 10;

SELECT * FROM itens_notas_fiscais WHERE 
codigo_do_produto = '1101035' ORDER BY QUANTIDADE desc limit 10;

-- group by

SELECT MAX(`QUANTIDADE`) as 'MAIOR QUANTIDADE' 
FROM itens_notas_fiscais WHERE `CODIGO_DO_PRODUTO` = '1101035' ;

SELECT COUNT(*) FROM itens_notas_fiscais 
WHERE codigo_do_produto = '1101035' AND QUANTIDADE = 99;

-- o having é uma condição filtro que se aplica ao resultado de um agregação tabela_de_clientes
-- Quais foram os clientes que fizeram mais de 2000 compras em 2016?itens_notas_fiscaisnotas_fiscais
SELECT CPF, COUNT(*) FROM notas_fiscais
WHERE YEAR(DATA_VENDA) = 2016
GROUP BY CPF
HAVING COUNT(*) > 2000;