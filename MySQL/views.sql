use sucos_vendas;
create or replace view vw_maiores_embalagens as 
SELECT EMBALAGEM, MAX(PRECO_DE_LISTA) AS PRECO_MAXIMO FROM tabela_de_produtos
GROUP BY EMBALAGEM;

select * from vw_maiores_embalagens;

select x.embalagem, x.preco_maximo from
vw_maiores_embalagens x where x.preco_maximo <=10;