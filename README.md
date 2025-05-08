# 💳 Sistema de Controle Financeiro em Java

Este é um projeto de sistema de controle financeiro pessoal, desenvolvido em Java. Ele permite o gerenciamento de cartões de crédito, registro de diferentes tipos de compras (à vista, parceladas e recorrentes), geração de faturas e alertas de limite de crédito.

## 📌 Funcionalidades

- ✅ Registro de compras à vista
- ✅ Registro de compras parceladas
- ✅ Registro de compras recorrentes (assinaturas)
- ✅ Geração de fatura mensal detalhada
- ✅ Definição de data de fechamento da fatura
- ✅ Alerta de limite do cartão
- ✅ Menu interativo com retorno automático após cada operação

## 🛠 Tecnologias Utilizadas

- Java (JDK 8+)
- Paradigma de Programação Orientada a Objetos
- Console (modo texto)

## 🧩 Estrutura do Projeto


Cada classe tem uma responsabilidade específica:

- `Main.java`: Ponto de entrada da aplicação.
- `CartaoCredito.java`: Responsável por armazenar informações do cartão, limite, faturas e compras.
- `Compra.java`: Classe abstrata base para as diferentes formas de compra.
- `CompraAVista.java`: Implementa uma compra feita em uma única parcela.
- `CompraParcelada.java`: Gerencia compras divididas em múltiplas parcelas.
- `CompraRecorrente.java`: Representa serviços com cobrança mensal fixa.
- `Fatura.java`: Gera o resumo mensal das compras com base na data de fechamento.
- `Menu.java`: Interface de interação com o usuário via terminal.

## 🚀 Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/sistema-financeiro-java.git

## Exemplo de Uso
======= MENU =======
1. Registrar compra à vista
2. Registrar compra parcelada
3. Registrar compra recorrente
4. Gerar fatura atual
5. Ver limite disponível
6. Sair
=====================
Escolha uma opção:

