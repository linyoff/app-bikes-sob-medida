# Prova 02 - Jeff Bike
## Prova final na disciplina de Programação para Dispositivos Móveis

Jeff Bike é um aplicativo desenvolvido como parte da prova final na disciplina de Programação para Dispositivos Móveis. O objetivo é facilitar a vida da empresa Jeff Bike, que produz bicicletas sob medida exclusivamente para clientes VIPs. O aplicativo, desenvolvido em Kotlin utilizando Android JetPack Compose, possui uma interface atraente e está integrado com o banco de dados Firebase.

## Funcionalidades
O aplicativo oferece as seguintes funcionalidades para gerenciamento de bicicletas e clientes:

- Inserir Bicicleta e Cliente
- Mostrar todos os registros de Bicicleta e Cliente
- Buscar Bicicleta por modelo
- Buscar Cliente por nome
- Buscar Bicicleta por código
- Buscar Cliente por CPF
- Salvar todos os dados em arquivo (txt)
- Atualizar Bicicleta e Cliente
- Excluir Bicicleta e Cliente

## Estrutura de Dados

### Cliente
- **CPF**: Identificador único do cliente.
- **Nome**: Nome do cliente.
- **E-mail**: E-mail do cliente.
- **Instagram**: Conta de Instagram do cliente.

### Bicicleta
- **Código**: Identificador único da bicicleta.
- **Modelo**: Modelo da bicicleta.
- **MaterialDoChassi**: Material do chassi da bicicleta.
- **Aro**: Tamanho do aro da bicicleta.
- **Preço**: Preço da bicicleta.
- **QuantidadeDeMarchas**: Número de marchas da bicicleta.
- **CPF do Cliente**: Referência ao cliente (Chave estrangeira).
