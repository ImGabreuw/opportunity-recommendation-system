# Sistema de Recomendação de Oportunidades Acadêmicas

## Integrantes

- Enzo B. Proença - 10418579 - 06D
- Gabriel Ken Kazama Geronazzo - 10418247 - 06D
- Lucas Pires de Camargo Sarai - 10418013 - 06D

## Problema abordado

O ambiente universitário é um ecossistema rico em oportunidades de desenvolvimento profissional e pessoal, como projetos
de iniciação científica, equipes de competição, empresas juniores e workshops.
Contudo, a conexão entre o corpo discente e essas oportunidades é frequentemente ineficaz, marcada por uma assimetria de
informação: os alunos não têm conhecimento de vagas que se alinham perfeitamente aos seus interesses e competências,
enquanto os responsáveis pelas oportunidades lutam para atrair os talentos mais adequados.
Os métodos atuais de divulgação, como murais, e-mails em massa e portais baseados em buscas por palavras-chave, não
capturam a compatibilidade real entre perfil e vaga.

Para trabalhar nesse problema, o seguinte grafo com 60 vértices e 150 arestas foi modelado:

![](https://github.com/user-attachments/assets/ca9d0d97-1a20-410d-bc57-964fc4f54ce4)

Nele, existem 4 tipos de vértices: aluno, tema, habilidade e oportunidade.

E, entre eles, existem 4 tipos de relações possíveis, além de seus respectivos pesos:

- POSSUI_HABILIDADE: Relaciona alunos com as habilidades que ele possui (Peso 1.0);
- REQUER_HABILIDADE: Relaciona oportunidade com habilidades pré-requeridas (Peso 2.0);
- INTERESSADO_EM: Relaciona aluno com temas de interesse (Peso 1.0);
- RELACIONADO_A_TEMA: Relaciona oportunidade com tema (Peso 1.5);

**OBS: Essas nomenclaturas e pesos foram omitidas no grafo anterior para melhor visualização das conexões.**

A distribuição dos pesos foi feita considerando que é mais importante ser recomendado ao aluno oportunidades que
correspondam com as habilidades e seus temas de interesse em conjunto, em seguida aquelas que o aluno atenda aos
pré-requisitos de habildades e, após ambas, as relacionadas aos seus temas de interesse. <br>
Isso porque, as oportunidades ideais são as que ele atenda aos pré-requisitos e sejam de seu interesse. No entanto, para
as que só correspondam a um desses dois, devem ser priorizadas as que o aluno possua as habilidades necessárias para que
ele possa aproveitar a oportunidade, evitando, por exemplo, que se frustre por não entender o que lhe é solicitado e/ou
ensinado. <br>
Após passar por ambas, as de tema relacionado seriam as próximas recomendações, pois nem todas possuem pré-requisitos
específicos, dado que podem ser de interesse geral.

Abaixo, segue um grafo reduzido onde é possível visualizar melhor as relações e seus pesos:

![](https://github.com/user-attachments/assets/895a92e1-2412-4ccf-9da6-e984cd32bce1)


> **OBS: Nesse exemplo, também foram omitidas algumas labels, sendo exibidas apenas uma vez, buscando melhor
visualização
do todo.**

Para esse grafo reduzido, um exemplo de recomendação pode ser feito para o aluno 2. Para isso, o algoritmo vai olhar
cada habilidade e tema de interesse do aluno e buscar as oportunidades conectadas a elas. No caso, ele possui habilidade
em Python e SQL e interesse na área de dados. Para essas, a oportunidade 2 está conectado ao tema de dados (1,5) e à
habilidade com SQL (2.0), somando 3,5 pontos. Já a oportunidade 1 está conectada à habilidade com Python (2.0) apenas,
somando 2,0 pontos. Com isso, o rank gerado é:

1. Op2 (3.5)
2. Op1 (2.0)

Note que os pesos das conexões dos alunos não é contabilizado, tendo importância reduzida no processo.

## ODS

Baseado no contexto desse projeto, pode-se afirmar que ele está de acordo com os objetivos:

4: Educação de Qualidade, por conta do seguinte tópico:

> 4.4 - Até 2030, aumentar substancialmente o número de jovens e adultos que tenham habilidades relevantes, inclusive
> competências técnicas e profissionais, para emprego, trabalho decente e empreendedorismo

![](https://github.com/user-attachments/assets/ae47faa7-e2e8-4b5f-aa29-ad3a92182993)

8: Trabalho decente e crescimento econômico, por conta do seguinte tópico:

> 8.6 - Até 2030, reduzir substancialmente a proporção de jovens sem emprego, educação ou formação

![](https://github.com/user-attachments/assets/b19010b3-c82a-4c1d-9888-fa8577170877)

## Testes

### Opção 1. Ler dados do arquivo grafo.txt;

#### Arquivo seguindo padrões exigidos

![](https://github.com/user-attachments/assets/032f654a-6f30-4297-bd90-41feb29ee645)

#### Arquivo fora dos padrões

Exemplo: Número encontrado no meio dos nomes dos vértices

```txt
...
aluno8 STUDENT
10
aluno9 STUDENT
```

![](https://github.com/user-attachments/assets/6d6f3a62-47d8-40a9-acb2-aed3b10e4ca4)


> **OBS: A exceção foi montada no código, e não lançada inesperadamente.**

### Opção 2. Gravar dados no arquivo grafo.txt;

#### Com inserção de arestas e atualização no original

![](https://github.com/user-attachments/assets/47b399d5-4892-4041-b3c2-04c4c4149590)<br>
![](https://github.com/user-attachments/assets/c224d17c-ae1a-4930-b1ed-fc0e6ad2ec4b)<br>
![](https://github.com/user-attachments/assets/d1fdfa1b-2537-42a4-af9a-82795e918dc4)<br>
![](https://github.com/user-attachments/assets/8018d826-6459-487e-b8d1-51555c170561)<br>
![](https://github.com/user-attachments/assets/537cad3d-36c6-4749-b536-9eaf355570e6)



#### Sem ter grafo base gerado (sem ler arquivo ou montar manualmente)

![](https://github.com/user-attachments/assets/4bbab25d-17d9-4257-a3d2-b0894cbef9c1)

### Opção 3. Inserir vértice;

#### Inserção válida

![](https://github.com/user-attachments/assets/dd9b09e3-bb86-46a0-b259-56f1ca3c6d22)

#### Inserção fora dos padrões

![](https://github.com/user-attachments/assets/75ea4f0d-efed-4f42-a7af-f1152dc609d4)

### Opção 4. Inserir aresta;

#### Inserção válida

![](https://github.com/user-attachments/assets/b468e38c-a25d-41b5-b5fa-8c262cd609ee)

#### Inserção com nó inexistente

![](https://github.com/user-attachments/assets/294b81c5-23bd-4570-b7eb-6ee57b74b483)


> **OBS: A exceção foi montada no código, e não lançada inesperadamente.**

### Opção 5. Remove vértice;

#### Nó existente

![](https://github.com/user-attachments/assets/47d03b79-4eb4-484d-82c5-09ced8fca210)

#### Nó inexistente

![](https://github.com/user-attachments/assets/3237f8dc-797a-4418-9f1f-989052a939ac)


> **OBS: A exceção foi montada no código, não lançada inesperadamente.**

### Opção 6. Remove aresta;

#### Aresta existente

![](https://github.com/user-attachments/assets/0713a71e-6d59-4ce7-abad-e8a96b691166)

#### Aresta inexistente

![](https://github.com/user-attachments/assets/d066f86a-3fb2-4b0c-9684-f0ef91cbfa9d)

### Opção 7. Mostrar conteúdo do arquivo;

#### Exibição esperada

![](https://github.com/user-attachments/assets/5fcfa83d-c5b6-4316-a4cb-b3b6bb91596e)

#### Arquivo não encontrado

![](https://github.com/user-attachments/assets/b2174fcc-57d8-4e4d-89ac-c8158cc37a14)

### Opção 8. Mostrar grafo;

#### Exibição esperada

![](https://github.com/user-attachments/assets/f2635055-887e-4f80-89d7-59e98afbfeb5)

#### Exibição de grafo ainda não processado

![](https://github.com/user-attachments/assets/8c2cbb02-1262-4988-a146-0b3d9210fb24)

### Opção 9. Apresentar a conexidade do grafo e o reduzido;

#### Redução de grafo original C1

![](https://github.com/user-attachments/assets/edaf48d9-b78d-4476-a61d-45916ad7425b)

#### Redução de grafo modificado C3

```txt
3
aluno1 STUDENT
hab_java SKILL
tema_ia THEME
3
aluno1 HAS_SKILL hab_java
hab_java RELATED_TO_THEME tema_ia
tema_ia INTERESTED_IN aluno1
```

![](https://github.com/user-attachments/assets/e94085c6-459f-478e-93a8-8bc02df2e1ae)


> **OBS: O grafo reduzido não pode ser gerado, pois o grafo do nosso problema não pode ser C3. <br>Para tal exemplo,
precisamos criar um grafo irreal, onde o "tema" é interessado em "aluno"**

### Opção 10. (Opção desenvolvida por nós) Apresentar recomendações para um aluno

#### Recomendação para aluno 3: Interessado em web

```txt
aluno3 HAS_SKILL hab_html
aluno3 HAS_SKILL hab_css
aluno3 HAS_SKILL hab_git
aluno3 INTERESTED_IN tema_web
...
op4 REQUIRES_SKILL hab_js
op4 REQUIRES_SKILL hab_html
op4 REQUIRES_SKILL hab_css
op4 REQUIRES_SKILL hab_communication
op4 RELATED_TO_THEME tema_web
op5 REQUIRES_SKILL hab_docker
op5 REQUIRES_SKILL hab_kubernetes
op5 REQUIRES_SKILL hab_aws
op5 REQUIRES_SKILL hab_git
op5 RELATED_TO_THEME tema_cloud
op6 REQUIRES_SKILL hab_c
op6 REQUIRES_SKILL hab_cpp
op6 REQUIRES_SKILL hab_git
op6 REQUIRES_SKILL hab_linux
op6 RELATED_TO_THEME tema_mobile
...
```

![](https://github.com/user-attachments/assets/a53e6fea-0dfc-40b8-9c63-1a1c9382025e)


> **OBS 1: Não foram encontradas mais que 5 oportunidades para o aluno. Portanto, só aparecem 4**

> **OBS 2: As descrições ainda não estão sendo salvas, vamos planejar como obtê-las no próximo bimestre**

#### Recomendação para aluno 14: Interessado em bigdata

```txt
aluno14 HAS_SKILL hab_spark
aluno14 HAS_SKILL hab_nosql
aluno14 HAS_SKILL hab_sql
aluno14 HAS_SKILL hab_data_viz
aluno14 INTERESTED_IN tema_bigdata
...
op2 REQUIRES_SKILL hab_python
op2 REQUIRES_SKILL hab_ml
op2 REQUIRES_SKILL hab_tensorflow
op2 REQUIRES_SKILL hab_data_viz
op2 RELATED_TO_THEME tema_ia
op3 REQUIRES_SKILL hab_sql
op3 REQUIRES_SKILL hab_nosql
op3 REQUIRES_SKILL hab_spark
op3 REQUIRES_SKILL hab_data_viz
op3 RELATED_TO_THEME tema_bigdata
...
op7 REQUIRES_SKILL hab_python
op7 REQUIRES_SKILL hab_sql
op7 REQUIRES_SKILL hab_communication
op7 RELATED_TO_THEME tema_seguranca
```

![](https://github.com/user-attachments/assets/a3a59a23-475f-4719-be96-8f1714f92a0e)

### Opção 11. Encerrar a aplicação.

#### Encerrando a aplicação

![](https://github.com/user-attachments/assets/aeee958d-2fcf-441a-930f-0e8a68200f9a)

## Repositório

Clique <a href="https://github.com/ImGabreuw/opportunity-recommendation-system"> Aqui </a> para ver o repositório.
