# Fundamentação Tecnica

Codigo base faltando, documentação completa javaDoc e Swagger

É boa pratica separar as duas documentações pois ambas tem funcionalidades diferentes:

Swagger: documentação para quem irá consumir a api.

JavaDoc: documentação para quem irá manter/fazer alterações diretamente dentro do codigo

# justificativas Tecnicas

Metodos de Create retornando cabeçalho com locataion e status HTTP 201
Outros metodos de listar retornando status 200

Metodos que necessitam de Request com @valid para validação dos dados e @RequetBody exigindo corpo na requisição do metodo

Dois metodos de alertaMotor (buscarPorMotor e listarPorStatusResolucao) possuem o filtro da
ordem como logica propria, sendo assim não será implementado nas controller a ordenaçãos das destes 
metodos, seguindo com a ordenação pedida na capacidade C3 (a segunda), somente no metodo de listar todas
e como não foi especificado por qual dado precisaria ser listado, optei por listar pelo id em ordem crescente.

Nesse sistema foi usado como base para estruturação a arquitetura MVC, com algumas implementações a mais como:
DTO para requisição dos dados e Response sem dados sensíveis
Mapper para fazer o mapeamento das entidades para dto dto para entidade, utilizando
Repository para comunicação com o banco (gerencia todas as ações com ele)
Exception que faz o tratamento global das exceções lançadas
Entity para a criação das entidades (molde/planta)