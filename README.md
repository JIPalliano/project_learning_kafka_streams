# Processamento de Ocorrências em Tempo Real

Este projeto é uma aplicação de **Big Data e Processamento de Eventos** desenvolvida com **Spring Boot 3.4** e **Project Reactor (WebFlux)**, seguindo os princípios da **Arquitetura Hexagonal (Ports and Adapters)**.

O sistema é responsável por ingerir ocorrências (com geolocalização e fotos), processá-las de forma assíncrona via **Kafka** e **Kafka Streams**, armazenar as imagens em um Object Storage (**MinIO/S3**) e persistir os dados estruturados em um banco de dados relacional (**PostgreSQL**) de forma totalmente não-bloqueante (**R2DBC**).

---

## 🚀 Tecnologias Utilizadas

*   **Java 21**
*   **Spring Boot 3.4** (WebFlux, Validation, Actuator)
*   **Spring Cloud Stream** (Kafka Binder & Kafka Streams Binder)
*   **Apache Kafka** (Mensageria e Stream Processing)
*   **PostgreSQL** (Banco de Dados Relacional)
*   **Spring Data R2DBC** (Persistência Reativa)
*   **MinIO** (Object Storage compatível com S3)
*   **AWS SDK v2** (Cliente S3 Assíncrono)
*   **MapStruct** (Mapeamento de Objetos)
*   **Docker & Docker Compose** (Infraestrutura)
*   **Testcontainers** (Testes de Integração)
*   **Gradle** (Gerenciador de Dependência)

---

## 🏛️ Arquitetura

O projeto segue a **Arquitetura Hexagonal**, dividido em módulos para garantir desacoplamento:

*   **contract-producer**: Controladores REST e DTOs de entrada.
*   **domain-producer**: Regras de negócio e Portas de saída da ingestão.
*   **infrastructure-producer**: Adaptadores de saída (Kafka Producer).
*   **contract-consumer**: Listeners do Kafka (Kafka Streams).
*   **domain-consumer**: Regras de negócio do processamento.
*   **infrastructure-consumer**: Adaptadores de infraestrutura (R2DBC, S3/MinIO).

### Fluxo de Dados

1.  **Ingestão (REST):** O cliente envia um POST com dados da ocorrência e imagem em Base64.
2.  **Producer (Kafka):** A aplicação valida os dados e publica uma mensagem no tópico `ocorrencias-topic`.
3.  **Processamento (Kafka Streams):** O Consumer lê a mensagem do tópico.
4.  **Armazenamento (S3):** A imagem é decodificada e enviada para o bucket `ocorrencias-bucket` no MinIO.
5.  **Persistência (R2DBC):**
    *   Verifica/Cria o Cliente.
    *   Salva o Endereço.
    *   Salva a Ocorrência vinculada.
    *   Salva a referência da Foto.

---

## 🛠️ Como Rodar o Projeto

### Pré-requisitos

*   Java 21+
*   Docker e Docker Compose

### 1. Subir a Infraestrutura (Docker)

Na raiz do projeto, execute:

```bash
docker-compose up -d
```

Isso irá subir:
*   **PostgreSQL** (Porta 5432)
*   **Kafka** (Porta 9092 - Externa / 29092 - Interna)
*   **MinIO** (Porta 9000 - API / 9001 - Console)
*   **Kafka UI** (Porta 8090 - http://localhost:8090)
*   **PgAdmin** (Porta 5050 - http://localhost:5050)

O script de inicialização (`createbuckets`) criará automaticamente o bucket `ocorrencias-bucket` no MinIO.

### 2. Rodar a Aplicação

Com a infraestrutura de pé, execute:

```bash
./gradlew bootRun
```

A aplicação iniciará na porta **8080**.

---

## 🧪 Como Testar

### Endpoint de Ingestão

**POST** `/v1/ocorrencias`

**Body (JSON):**

```json
{
  "codCliente": 1050,
  "cpf": "12345678909",
  "logradouro": "Av. Paulista",
  "bairro": "Bela Vista",
  "cep": "01310-100",
  "cidade": "São Paulo",
  "estado": "SP",
  "latitude": -23.561414,
  "longitude": -46.6558819,
  "imagemBase64": "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8z8BQDwAEhQGAhKmMIQAAAABJRU5ErkJggg=="
}
```

**Resposta Esperada:** `201 Created`

### Verificando o Processamento

1.  **Kafka UI:** Acesse http://localhost:8090 e verifique se a mensagem chegou no tópico `ocorrencias-topic`.
2.  **MinIO:** Acesse http://localhost:9001 (User: `minio_admin`, Pass: `minio_password`) e veja se a imagem foi salva no bucket.
3.  **Banco de Dados:** Verifique as tabelas `ocorrencia`, `endereco`, `cliente` e `foto_ocorrencia` no PostgreSQL.

---

## ✅ Testes Automatizados

O projeto possui uma suíte robusta de testes:

*   **Testes de Integração:** Usam **Testcontainers** para subir Kafka e Postgres reais e testar o fluxo ponta a ponta.
*   **Testes Unitários:** Usam **Mockito** e **H2 Database** para testar a lógica de negócio e adaptadores isoladamente.

Para rodar todos os testes:

```bash
./gradlew test
```

---

## 📝 Estrutura do Banco de Dados

```sql
CREATE TABLE cliente (...);
CREATE TABLE endereco (...);
CREATE TABLE ocorrencia (
    ...
    FOREIGN KEY (cod_cliente) REFERENCES cliente(cod_cliente),
    FOREIGN KEY (cod_endereco) REFERENCES endereco(cod_endereco)
);
CREATE TABLE foto_ocorrencia (...);
```

---


**Desenvolvido por jose ismael palliano evaldt**
