import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TelaRelatorio extends Application {
    private static ArrayList<Transacao>transacoes;
    private static ArrayList<Categoria> categorias  ;
    private static double totalEntrdas=0;
    private static double totalDespesa=0;


    @Override
    public void start(Stage stage) throws Exception {

        if (transacoes == null) {
            transacoes = new ArrayList<>();
            System.out.println("Aviso: Lista de transações estava nula, foi inicializada vazia.");
        }

        if (categorias == null) {
            categorias = new ArrayList<>();
            System.out.println("Aviso: Lista de categorias estava nula, foi inicializada vazia.");
        }

        StringBuilder relatorio = new StringBuilder("RELATÓRIO DE TRANSAÇÕES\n\n\n");

            for (Transacao transacao : transacoes) {
                relatorio.append(transacao.toString()).append("\n");
            }
            relatorio.append("\n\n");
            relatorio.append("Valor Total de Entradas: "+totalEntrdas+"\n");
            relatorio.append("Valor Total de Despesas: "+totalDespesa+"\n");
            // Criando o label para exibir o relatório
            Label labelRelatorio = new Label(relatorio.toString());



            // Criando o ScrollPane para a rolagem do relatório
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(labelRelatorio);
            scrollPane.setFitToWidth(true);

            PieChart pieChart = new PieChart();
            ArrayList<Transacao> transacoesCategoria = new ArrayList<>();
            for (Categoria categoria : categorias) {
                transacoesCategoria = ConexaoSQLite.filtrarTransacoesPorCategoria(categoria.getNome());
                PieChart.Data pedaço = new PieChart.Data(categoria.getNome(), transacoesCategoria.size());
                pieChart.getData().add(pedaço);
            }

            Button botaoRelatorio = new Button(" Biaxar Relatorio");
            botaoRelatorio.setOnAction(e -> {ArquivoRelatorio.downloadTransacoes(transacoes, totalEntrdas, totalDespesa);});

            VBox vbox = new VBox(50);
            vbox.getChildren().addAll(scrollPane, pieChart, botaoRelatorio);

            Scene scene = new Scene(vbox, 600, 600);

            stage.setScene(scene);

            // Definindo o título da janela
            stage.setTitle("Relatório de Transações");

            // Mostrando a janela
            stage.show();




    }
    public static void exibir(ArrayList<Transacao> listaTransacoes, ArrayList<Categoria> listaCategorias, double totalEntrdas, double totalDespesa) {
        setTransacoes(listaTransacoes,listaCategorias,totalEntrdas,totalDespesa);
        // Define os dados antes de iniciar o JavaFX
        new Thread(() -> Application.launch(TelaRelatorio.class)).start();
    }

    private static void setTransacoes(ArrayList<Transacao> listaTransacoes, ArrayList<Categoria> listaCategorias, double totalEntrdas, double totalDespesa) {
        transacoes= listaTransacoes;
        categorias = listaCategorias;
        totalEntrdas = totalEntrdas;
        totalDespesa = totalDespesa;
    }
}
