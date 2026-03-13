# Automação de Testes E2E em Java e Selenium - Desafio Sicredi

## Informações do projeto

Projeto desenvolvido como parte de um desafio técnico de automação de testes E2E.

Este projeto contém uma suíte de testes automatizados end-to-end (E2E) desenvolvida para validar funcionalidades do site fictício Saucedemo.

A automação tem como objetivo validar os fluxos críticos, positivos e negativos da aplicação

Os testes foram desenvolvidos utilizando Java, Selenium WebDriver e JUnit 5, seguindo o padrão Page Object Model (POM) para garantir organização, reutilização de código e manutenção facilitada.

A automação cobre cenários de:

- autenticação
- navegação no inventário
- manipulação de carrinho
- fluxo de checkout
- ordenação de produtos
- persistência de sessão
- validação de inconsistências da aplicação

### URL da aplicação testada:

https://www.saucedemo.com/

---

## Tecnologias utilizadas

| Tecnologia               | Finalidade                                                |
|--------------------------|-----------------------------------------------------------|
| Java 21                  | Linguagem utilizada na automação                          |
| Selenium WebDriver       | Automação de navegador                                    |
| JUnit 5                  | Framework de testes                                       |
| Gradle                   | Gerenciamento de dependências e build                     |
| Allure Reports           | Relatórios de execução dos testes                         |
| Drivers locais (offline) | Execução determinística e independente de conexão na rede |
| JavaFaker                | Biblioteca para geração dinâmica de massa de dados        |

---

## Usuários utilizados nos testes

A aplicação SauceDemo disponibiliza diferentes perfis de usuários para simular comportamentos distintos.

Esse projeto limita-se aos perfis `standard_user` e `problem_user`.

| Usuário | Descrição |
|---|---|
| standard_user | Usuário com comportamento esperado da aplicação |
| problem_user | Usuário com comportamentos inconsistentes propositalmente |


---

## Estrutura do projeto

A automação segue uma arquitetura baseada em Page Object Model (POM).

```
src  
└── test  
└── java  
├── base  
│    └── BaseTest.java  
│  
├── core  
│    ├── PageManager.java  
│    └── DriverFactory.java 
├── data  
│    └── TestDataFactory.java  
│   
│  
├── listeners  
│    └── TestListener.java  
│  
├── pages  
│    ├── LoginPage.java  
│    ├── InventoryPage.java  
│    ├── CartPage.java  
│    └── CheckoutPage.java  
│  
├── tests
│    ├── BaseUserTest.java  
│    ├── StandardUserTest.java  
│    └── ProblemUserTest.java  
│  
└── utils
│    ├── FakerUtils.java    
│    ├── SortOption.java
│    └── UserType.java    
```

### Descrição dos diretórios de testes

| Diretório | Responsabilidade |
|---|---|
| base | Configuração base da execução dos testes e inicialização do WebDriver |
| core | Gerenciamento centralizado das Page Objects |
| data | Dados utilizados nos testes |
| listeners | Captura de screenshots e evidências em falhas |
| pages | Implementação das Page Objects |
| tests | Casos de teste automatizados |
| utils | Utilidades auxiliares |

---

## Estratégia de testes

A estratégia adotada foi baseada em testes E2E focados em fluxos positivos e negativos da aplicação.

Os cenários foram construídos para validar:

### Fluxos funcionais principais

- login de usuário
- visualização de produtos
- adição e remoção de itens no carrinho
- ordenação de produtos
- finalização de compra

### Validação de comportamento da aplicação

- atualização do badge do carrinho
- consistência entre inventário e página do produto
- persistência de sessão
- integridade do fluxo de checkout

### Testes exploratórios automatizados

Além dos fluxos principais, foram criados testes exploratórios automatizados para detectar inconsistências na aplicação.

Esses testes foram desenvolvidos para identificar:

- divergência de dados entre páginas
- falhas na ordenação de produtos
- inconsistências no carrinho
- problemas de navegação

Alguns testes da suíte tem bugs como resultado, pois validam comportamentos incorretos da aplicação. Esses cenários servem como evidência dos bugs identificados durante a execução da automação.

---

## Execução dos testes

### Pré-requisitos instalados

- Java 21
- Git
- IDE (IntelliJ IDEA - recomendado)
- Navegador (Chrome, Firefox ou Edge)

---
### Passo-a-passo de comandos via terminal/prompt
1. Clonar o repositório
```bash
git clone https://github.com/leandroalves011/desafio-e2e-selenium-sicredi.git
```
2. Acessar o diretório do projeto:

```bash
cd desafioE2ESicredi-Selenium
```
---

### Executar todos os testes

Windows PowerShell:

```bash
.\gradlew clean test
```
Observação: o projeto utiliza Gradle Wrapper, portanto não é necessário ter Gradle instalado globalmente na máquina.

---

## Execução em navegadores específicos

### Execução offline utilizando drivers locais

O framework foi configurado para permitir execução totalmente offline, sem dependência de download automático de WebDrivers.

Os binários dos drivers (Chrome, Firefox e Edge) já estão versionados dentro do projeto.

`OBS: Caso ocorra falha na inicialização do navegador, verifique se a versão do driver é compatível com a versão do navegador instalada na máquina.`

Chrome
```bash
.\gradlew clean test -Dbrowser=chrome
```

Firefox
```bash
.\gradlew clean test -Dbrowser=firefox
```

Edge
```bash
.\gradlew clean test -Dbrowser=edge
```

---

## Execução em modo headless
```bash
.\gradlew clean test -Dheadless=true
```
---

## Relatórios de execução

Após a execução dos testes, os relatórios podem ser gerados utilizando Allure Reports.

Gerar relatório:
```bash
.\gradlew allureReport
```

Abrir relatório:
```bash
.\gradlew allureServe
```
Durante os testes com falhas devido a bugs, são automaticamente anexados ao relatório:

- screenshot da aplicação
- URL da página no momento da falha
- código fonte da página

---

## Plano de testes

O plano de testes utilizado no projeto está disponível em:

`docs/plano_de_testes_E2E_Saucedemo.xlsx`

Esse documento contém:

- identificação dos cenários testados
- resultado esperado
- status da automação
- observações sobre bugs encontrados

---

## Bugs identificados

Durante a execução da automação foram identificadas inconsistências na aplicação Saucedemo.

### Carrinho não é resetado após logout

Usuário realiza logout com itens no carrinho e, ao realizar novo login, os itens continuam presentes.

Impacto: estado da sessão não é resetado corretamente.

### Badge do carrinho não atualiza corretamente

Após remover um item do carrinho, o contador exibido no ícone do carrinho não é atualizado.

Impacto: inconsistência visual na interface.

### Ordenação de produtos não funciona corretamente

Ao selecionar ordenação Z-A, os produtos continuam na ordem original.

Impacto: funcionalidade de ordenação não aplicada.

### Página de produto exibe item incorreto

Ao acessar a página de um produto específico, a aplicação apresenta informações de outro produto.

Impacto: inconsistência entre inventário e página de produto.

### Itens não são adicionados corretamente ao carrinho

Determinados produtos não são adicionados ao carrinho quando selecionados.

Impacto: falha funcional no fluxo de compra.

### Fluxo de checkout não avança para o resumo

Após preencher os dados de checkout, a aplicação não avança corretamente para o passo seguinte.

Impacto: fluxo de compra interrompido.

### Campo firstName é apagado ao preencher lastName

Durante o preenchimento do formulário de checkout, ao digitar no campo lastName o campo firstName perde o valor digitado.

Impacto: erro de manipulação de campos no formulário.

---

## Decisões Técnicas do Projeto

Durante o desenvolvimento da automação foram tomadas algumas decisões técnicas para manter o projeto organizado e de fácil manutenção.

### Uso do Page Object Model

O padrão Page Object Model foi adotado para separar a lógica de interação com a interface da lógica de validação dos testes.

Essa abordagem permite centralizar os seletores da interface, reutilizar métodos de interação e facilitar manutenção caso a interface da aplicação sofra alterações.

### Separação de cenários por tipo de usuário

Os testes foram divididos em duas classes principais: StandardUserTest e ProblemUserTest.

Essa separação permite validar tanto os fluxos esperados da aplicação quanto os comportamentos inconsistentes presentes no usuário `problem_user`.

### Uso de geração dinâmica de dados

Foi utilizada a biblioteca JavaFaker para geração de dados dinâmicos durante os testes, principalmente no fluxo de checkout.

Isso evita dependência de dados fixos e torna os testes mais próximos de cenários reais de utilização.


### Captura automática de evidências

Foi implementado um TestListener responsável por capturar screenshots automaticamente quando ocorre falha em algum teste.

Essas evidências são anexadas aos relatórios gerados pelo Allure, facilitando a análise de falhas.

### Considerações sobre execução cross-browser

Durante a execução da suíte foi possível observar pequenas variações de comportamento entre navegadores.

Alguns cenários de testes apresentaram falhas apenas em navegadores específicos, principalmente relacionadas a timing de renderização de elementos ou diferenças de interação com componentes da interface.

Esses comportamentos foram mantidos intencionalmente sem tratamento adicional para preservar a evidência real do funcionamento da aplicação em diferentes engines de navegador.

---

## Conclusão

A automação desenvolvida valida os fluxos principais da aplicação SauceDemo e identifica inconsistências presentes no sistema.

O projeto foi estruturado utilizando boas práticas de automação de testes, com foco em organização, reutilização de código e clareza na execução dos cenários.

A arquitetura também foi pensada com o intuito de criar um framework de automação de testes de forma que seja fácil de adaptar e implementar para os casos de novos tipos de usuários, novos cenários de testes e futuras alterações no CSS e HTML do site.

A execução multi-browser e em modo headless permitiu validar não apenas o comportamento funcional da aplicação, mas também a robustez e portabilidade do framework de automação.