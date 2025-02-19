import java.time.LocalDate;
import java.util.ArrayList;

public class ArrayParaTeste {
    public static ArrayList<Transacao> arrayTransacoesTeste() {
        ArrayList<Transacao> transacoes = new ArrayList<>();

        // Criando categorias
        Categoria salario = new Categoria("Salário");
        Categoria alimentacao = new Categoria("Alimentação");
        Categoria transporte = new Categoria("Transporte");
        Categoria lazer = new Categoria("Lazer");
        Categoria saude = new Categoria("Saúde");
        Categoria educacao = new Categoria("Educação");
        Categoria moradia = new Categoria("Moradia");
        Categoria investimentos = new Categoria("Investimentos");

        // Adicionando transações (receitas e despesas)
        transacoes.add(new Transacao("receita", "Salário mensal", 5000.00, LocalDate.of(2025, 2, 1), salario));
        transacoes.add(new Transacao("despesa", "Almoço no restaurante", 45.50, LocalDate.of(2025, 2, 3), alimentacao));
        transacoes.add(new Transacao("despesa", "Passagem de ônibus", 4.80, LocalDate.of(2025, 2, 5), transporte));
        transacoes.add(new Transacao("receita", "Freelance de programação", 800.00, LocalDate.of(2025, 2, 7), investimentos));
        transacoes.add(new Transacao("despesa", "Cinema com amigos", 30.00, LocalDate.of(2025, 2, 10), lazer));
        transacoes.add(new Transacao("despesa", "Consulta médica", 150.00, LocalDate.of(2025, 2, 12), saude));
        transacoes.add(new Transacao("despesa", "Mensalidade da faculdade", 1200.00, LocalDate.of(2025, 2, 15), educacao));
        transacoes.add(new Transacao("receita", "Investimento rendimentos", 200.00, LocalDate.of(2025, 2, 18), investimentos));
        transacoes.add(new Transacao("despesa", "Aluguel do apartamento", 1800.00, LocalDate.of(2025, 2, 20), moradia));
        transacoes.add(new Transacao("despesa", "Supermercado", 350.00, LocalDate.of(2025, 2, 22), alimentacao));
        transacoes.add(new Transacao("despesa", "Combustível para o carro", 250.00, LocalDate.of(2025, 2, 25), transporte));
        transacoes.add(new Transacao("receita", "Venda de itens usados", 150.00, LocalDate.of(2025, 2, 26), investimentos));
        transacoes.add(new Transacao("despesa", "Plano de saúde", 400.00, LocalDate.of(2025, 2, 28), saude));
        transacoes.add(new Transacao("despesa", "Curso online de Java", 100.00, LocalDate.of(2025, 3, 2), educacao));
        transacoes.add(new Transacao("receita", "Bônus da empresa", 750.00, LocalDate.of(2025, 3, 5), salario));
        transacoes.add(new Transacao("despesa", "Jantar especial", 90.00, LocalDate.of(2025, 3, 7), alimentacao));
        transacoes.add(new Transacao("despesa", "Show de música", 200.00, LocalDate.of(2025, 3, 10), lazer));
        transacoes.add(new Transacao("despesa", "Manutenção do carro", 500.00, LocalDate.of(2025, 3, 12), transporte));
        transacoes.add(new Transacao("receita", "Dividendos de ações", 300.00, LocalDate.of(2025, 3, 15), investimentos));
        transacoes.add(new Transacao("despesa", "Conta de luz", 120.00, LocalDate.of(2025, 3, 18), moradia));

        return transacoes;
    }

}

