use sucos_vendas;

-- Possibilidade de unir uma ou mais tabelas através de campos comuns

select * from  tabela_de_vendedores a
inner join notas_fiscais b
on a.MATRICULA = b.MATRICULA;

select  a.MATRICULA, a.nome, count(*)
from tabela_de_vendedores a
inner join notas_fiscais b
on a.MATRICULA = b.MATRICULAnotas_fiscais
group by a.MATRICULA, a.nome;

SELECT YEAR(DATA_VENDA), SUM(QUANTIDADE * PRECO) AS FATURAMENTO
FROM notas_fiscais NF INNER JOIN itens_notas_fiscais INF 
ON NF.NUMERO = INF.NUMERO
GROUP BY YEAR(DATA_VENDA);