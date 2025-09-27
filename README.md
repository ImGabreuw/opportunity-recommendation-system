## Sistema de recomendação de oportunidades no ensino superior
### Integrantes

- Enzo B. Proença - 10418579
- Gabriel Ken Kazama Geronazzo - 10418247
- Lucas Pires de Camargo Sarai - 10418013

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

#### Opção a)
TODO
#### Opção b)
TODO
#### Opção c)
TODO
#### Opção d)
TODO
#### Opção e)
TODO
#### Opção f)
TODO
#### Opção g)
TODO
#### Opção h)
TODO
#### Opção i)
TODO
#### Opção j)
TODO

### Repositório
Clique <a href="https://github.com/ImGabreuw/opportunity-recommendation-system"> Aqui </a> para ver o repositório.







