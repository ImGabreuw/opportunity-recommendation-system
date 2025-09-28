## Sistema de recomendação de oportunidades no ensino superior
### Integrantes

- Enzo B. Proença - 10418579 - 06D
- Gabriel Ken Kazama Geronazzo - 10418247 - 06D
- Lucas Pires de Camargo Sarai - 10418013 - 06D

### Problema abordado
O ambiente universitário é um ecossistema rico em oportunidades de desenvolvimento profissional e pessoal, como projetos de iniciação científica, equipes de competição, empresas juniores e workshops. 
Contudo, a conexão entre o corpo discente e essas oportunidades é frequentemente ineficaz, marcada por uma assimetria de informação: os alunos não têm conhecimento de vagas que se alinham perfeitamente aos seus interesses e competências, enquanto os responsáveis pelas oportunidades lutam para atrair os talentos mais adequados. 
Os métodos atuais de divulgação, como murais, e-mails em massa e portais baseados em buscas por palavras-chave, não capturam a compatibilidade real entre perfil e vaga.

Para trabalhar nesse problema, o seguinte grafo com 60 vértices e 150 arestas foi modelado:
<img width="933" height="561" alt="{4331D2AC-CB38-4170-9F1B-D8E65EE9AA04}" src="https://github.com/user-attachments/assets/ca9d0d97-1a20-410d-bc57-964fc4f54ce4" />

Nele, existem 4 tipos de vértices: aluno, tema, habilidade e oportunidade. 

E, entre eles, existem 4 tipos de relações possíveis, além de seus respectivos pesos:
- POSSUI_HABILIDADE: Relaciona alunos com as habilidades que ele possui (Peso 1.0);
- REQUER_HABILIDADE: Relaciona oportunidade com habilidades pré-requeridas (Peso 2.0);
- INTERESSADO_EM: Relaciona aluno com temas de interesse (Peso 1.0);
- RELACIONADO_A_TEMA: Relaciona oportunidade com tema (Peso 1.5);

**OBS: Essas nomenclaturas e pesos foram omitidas no grafo anterior para melhor visualização das conexões.** 

Os pesos foram distribuídos dessa forma, visto que é mais importante ser recomendado ao aluno oportunidades que correspondam com as habilidades e seus temas de interesse em conjunto, em seguida aquelas que o aluno atenda aos pré-requisitos de habildades e, após ambas, as relacionadas aos seus temas de interesse. <br>
Isso porque, as oportunidades ideais são as que ele atenda aos pré-requisitos e sejam de seu interesse. No entanto, para as que só correspondam a um desses dois, devem ser priorizadas as que o aluno possua as habilidades necessárias para que ele possa aproveitar a oportunidade, evitando, por exemplo, que se frustre por não entender o que lhe é solicitado e/ou ensinado. <br>
Após passar por ambas, as de tema relacionado seriam as próximas recomendações, pois nem todas possuem pré-requisitos específicos, dado que podem ser de interesse geral.

Abaixo, segue um grafo reduzido onde é possível visualizar melhor as relações e seus pesos:
<img width="1050" height="539" alt="{2BC06CC9-8623-41F0-B432-16CA1FFED3B1}" src="https://github.com/user-attachments/assets/895a92e1-2412-4ccf-9da6-e984cd32bce1" />


**OBS: Nesse exemplo, também foram omitidas algumas labels, sendo exibidas apenas uma vez, buscando melhor visualização do todo.**


### ODS

Baseado no contexto desse projeto, pode-se afirmar que ele está de acordo com os objetivos:

- 4: Educação de Qualidade, por conta do seguinte tópico:

> 4.4 - Até 2030, aumentar substancialmente o número de jovens e adultos que tenham habilidades relevantes, inclusive competências técnicas e profissionais, para emprego, trabalho decente e empreendedorismo
<img width="131" height="135" alt="image" src="https://github.com/user-attachments/assets/ae47faa7-e2e8-4b5f-aa29-ad3a92182993" />

- 8: Trabalho decente e crescimento econômico, por conta do seguinte tópico:
> 8.6 - Até 2030, reduzir substancialmente a proporção de jovens sem emprego, educação ou formação
<img width="131" height="131" alt="image" src="https://github.com/user-attachments/assets/b19010b3-c82a-4c1d-9888-fa8577170877" />

### Testes

#### Opção 1. Ler dados do arquivo grafo.txt;
##### Arquivo seguindo padrões exigidos
<img width="336" height="101" alt="{17823D55-A549-4497-9C8C-9CBB002EE7AC}" src="https://github.com/user-attachments/assets/032f654a-6f30-4297-bd90-41feb29ee645" />

##### Arquivo fora dos padrões
Exemplo: Número encontrado no meio dos nomes dos vértices
```txt
...
aluno8 STUDENT
10
aluno9 STUDENT
```
<img width="1094" height="125" alt="{D61D7227-21C3-42EE-9402-A6A74876ABC5}" src="https://github.com/user-attachments/assets/6d6f3a62-47d8-40a9-acb2-aed3b10e4ca4" />

**OBS: A exceção foi montada no código, e não lançada inesperadamente.**


#### Opção 2. Gravar dados no arquivo grafo.txt;

##### Com inserção de arestas e atualização no original

<img width="1063" height="108" alt="{77BE1F96-D631-4F7E-9B65-26288DF0A71B}" src="https://github.com/user-attachments/assets/f4117c15-f1b2-49de-b4f1-6b81a294ea47" /><br>
<img width="506" height="102" alt="{440E9152-00AD-46F4-BC79-2F4539FF9CB6}" src="https://github.com/user-attachments/assets/d1fdfa1b-2537-42a4-af9a-82795e918dc4" /><br>
<img width="386" height="137" alt="{D61F5485-93DF-421B-AF21-5462E5747289}" src="https://github.com/user-attachments/assets/f5657f9d-5926-4405-96db-ffdea52ff375" />

##### Sem ter grafo base gerado (sem ler arquivo ou montar manualmente)
<img width="642" height="108" alt="{9BC96A82-4910-4ECD-A0CC-261FFEDCEF05}" src="https://github.com/user-attachments/assets/4bbab25d-17d9-4257-a3d2-b0894cbef9c1" />



#### Opção 3. Inserir vértice;

##### Inserção válida
<img width="584" height="97" alt="{6D707747-E7CC-445A-BCF3-999CBF381147}" src="https://github.com/user-attachments/assets/dd9b09e3-bb86-46a0-b259-56f1ca3c6d22" />

##### Inserção fora dos padrões
<img width="649" height="81" alt="{870DC359-59DE-4995-9C62-E8E9E1237A3C}" src="https://github.com/user-attachments/assets/75ea4f0d-efed-4f42-a7af-f1152dc609d4" />

#### Opção 4. Inserir aresta;
##### Inserção válida
<img width="1060" height="69" alt="{7F26F3AE-83EA-4EF8-8712-350261F81FD2}" src="https://github.com/user-attachments/assets/b468e38c-a25d-41b5-b5fa-8c262cd609ee" />

##### Inserção com nó inexistente
<img width="1058" height="184" alt="{AAC76EC9-D0FE-4BAF-8A26-9C621FB23B64}" src="https://github.com/user-attachments/assets/294b81c5-23bd-4570-b7eb-6ee57b74b483" />

**OBS: A exceção foi montada no código, e não lançada inesperadamente.**

#### Opção 5. Remove vértice;
##### Nó existente

<img width="422" height="80" alt="{7CDC7C2F-DDDE-45C3-8051-9D2619EAF9AB}" src="https://github.com/user-attachments/assets/47d03b79-4eb4-484d-82c5-09ced8fca210" />

##### Nó inexistente

<img width="966" height="172" alt="{7FEA8670-AD9A-48CF-ABAD-0CB76084EB9D}" src="https://github.com/user-attachments/assets/3237f8dc-797a-4418-9f1f-989052a939ac" />

**OBS: A exceção foi montada no código, não lançada inesperadamente.**


#### Opção 6. Remove aresta;
##### Aresta existente
<img width="1186" height="84" alt="{1A154EF6-A92B-4799-97E6-958D335F9149}" src="https://github.com/user-attachments/assets/0713a71e-6d59-4ce7-abad-e8a96b691166" />

##### Aresta inexistente
<img width="1204" height="87" alt="{C0993403-ABAD-4474-A57D-4FA163C69226}" src="https://github.com/user-attachments/assets/d066f86a-3fb2-4b0c-9684-f0ef91cbfa9d" />


#### Opção 7. Mostrar conteúdo do arquivo;

##### Exibição esperada
<img width="403" height="668" alt="{53DAAF54-96B2-40F2-A5DA-7F56476D0FBF}" src="https://github.com/user-attachments/assets/5fcfa83d-c5b6-4316-a4cb-b3b6bb91596e" />

##### Arquivo não encontrado
<img width="312" height="103" alt="{37C2E569-352D-47F3-97CF-BDACDB059515}" src="https://github.com/user-attachments/assets/b2174fcc-57d8-4e4d-89ac-c8158cc37a14" />

#### Opção 8. Mostrar grafo;

##### Exibição esperada
<img width="587" height="719" alt="{B619BBD9-BFCF-4B01-9AFE-D1FDBB656D90}" src="https://github.com/user-attachments/assets/f2635055-887e-4f80-89d7-59e98afbfeb5" />

##### Exibição de grafo ainda não processado
<img width="660" height="93" alt="{A0191419-D478-4755-9A75-186E4CDC87BC}" src="https://github.com/user-attachments/assets/8c2cbb02-1262-4988-a146-0b3d9210fb24" />

#### Opção 9. Apresentar a conexidade do grafo e o reduzido;
##### Redução para grafo original C1
<img width="501" height="240" alt="{7EB8E9ED-984C-407E-8FAF-FEBA08165850}" src="https://github.com/user-attachments/assets/edaf48d9-b78d-4476-a61d-45916ad7425b" />

##### Exibição de grafo modificado para C3
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
<img width="302" height="104" alt="{5DB424DA-ECA5-402B-A444-1ACC236CD730}" src="https://github.com/user-attachments/assets/e94085c6-459f-478e-93a8-8bc02df2e1ae" />

**OBS: O grafo reduzido não pode ser gerado, pois o grafo do nosso problema não pode ser C3. <br>Para tal exemplo, precisamos criar um grafo irreal, onde o "tema" é interessado em "aluno"** 

#### Opção 10. (Opção desenvolvida por nós) Apresentar recomendações para um aluno
##### Recomendação para aluno 3: Interessado em web
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
<img width="779" height="231" alt="{E6D4845F-F7AE-4061-8372-998678869270}" src="https://github.com/user-attachments/assets/a53e6fea-0dfc-40b8-9c63-1a1c9382025e" />

**OBS: Não foram encontradas mais que 5 oportunidades para o aluno. Portanto, só aparecem 4** <br>
**OBS2: As descrições ainda não estão sendo salvas, vamos planejar como obtê-las no próximo bimestre** 

##### Recomendação para aluno 14: Interessado em bigdata
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
<img width="762" height="209" alt="{5C0F5776-1FC8-4C61-840E-461516BEAE8C}" src="https://github.com/user-attachments/assets/a3a59a23-475f-4719-be96-8f1714f92a0e" />

#### Opção 11. Encerrar a aplicação.

##### Encerrando a aplicação
<img width="345" height="124" alt="{D7D46555-56A9-4399-A848-349AEDEB207E}" src="https://github.com/user-attachments/assets/aeee958d-2fcf-441a-930f-0e8a68200f9a" />


### Repositório
Clique <a href="https://github.com/ImGabreuw/opportunity-recommendation-system"> Aqui </a> para ver o repositório.







