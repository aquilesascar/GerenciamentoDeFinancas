import java.util.List;

public class RelatorioFinanceiro {

        public static void gerarRelatorio(List<Categoria> categorias) {
            System.out.println("--- Relatório Financeiro ---");
            for (Categoria categoria : categorias) {
                categoria.exibirStatus();
            }
        }
}
