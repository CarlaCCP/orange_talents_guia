use sucos_vendas;

-- FUNÇÕES DE STRING
select ltrim("		ola") as resultado;
select rtrim("ola		") as resultado;
select trim("		ola		") as resultado;
select concat("ola" , " teste") AS resultado;
select concat(nome, " ", "Endereço: ", endereco_1) as Clientes from tabela_de_clientes;

-- FUNCOES DE DATAS
SELECT curdate();
SELECT current_date();
SELECT current_timestamp();
SELECT current_user();
SELECT current_time();
SELECT datediff(CURRENT_TIMESTAMP(), '1996-01-19') AS DIAS_VIVIDOS;

SELECT DISTINCT DATA_VENDA, 
dayname(data_venda) as DIAS, monthname(data_venda) as Mês, year(data_venda) as ano
from notas_fiscais;

select nome, 
concat((year(current_date()) - year (data_de_nascimento) - 1) , ' anos') as Idade
from tabela_de_clientes;  

SELECT NOME, TIMESTAMPDIFF (YEAR, DATA_DE_NASCIMENTO, CURDATE()) AS    IDADE
FROM  tabela_de_clientes;



-- Funções matemáticas 
select ceiling(12.54658987754); -- sempre pra cima
select round(13.4555474,2); -- regra da casa = ou acima de 5
select rand() as aleatório;

select * from notas_fiscais;
select * from itens_notas_fiscais;

-- Na tabela de notas fiscais temos o valor do imposto. Já na tabela de itens temos a 
-- quantidade e o faturamento. 
-- Calcule o valor do imposto pago no ano de 2016 arredondando para o menor inteiro.

select year(data_venda) as Ano, FLOOR(SUM(IMPOSTO * (QUANTIDADE * PRECO))) as Valor 
from itens_notas_fiscais x 
inner join notas_fiscais b
on b.numero = x.numero
where year(data_venda) = 2016
group by year(data_venda);

-- FUNÇÕES DE CONVERSÃO 

SELECT CONCAT('O cliente ', TC.NOME, ' faturou ', 
CAST(SUM(INF.QUANTIDADE * INF.preco) AS char (20))
 , ' no ano ', CAST(YEAR(NF.DATA_VENDA) AS char (20))) AS SENTENCA FROM notas_fiscais NF
INNER JOIN itens_notas_fiscais INF ON NF.NUMERO = INF.NUMERO
INNER JOIN tabela_de_clientes TC ON NF.CPF = TC.CPF
WHERE YEAR(DATA_VENDA) = 2016
GROUP BY TC.NOME, YEAR(DATA_VENDA);
