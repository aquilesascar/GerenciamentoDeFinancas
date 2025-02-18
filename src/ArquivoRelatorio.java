import javafx.embed.swing.SwingFXUtils;
import javafx.scene.chart.PieChart;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
// Classe responsável pela manipulação de arquivos CSV
public class ArquivoRelatorio {
    public static void downloadTransacoes(List<Transacao> transacoes, double valorEntrada, double valorSaida) {
        // Implementação para salvar transações em arquivo CSV
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Escolha onde salvar o arquivo");

        // Define um nome padrão para o arquivo
        fileChooser.setSelectedFile(new File("RelatórioDeTransacoes.csv"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            // Garante que a extensão .txt seja adicionada, caso o usuário não a tenha colocado
            if (!fileToSave.getName().toLowerCase().endsWith(".csv")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                writer.write("RELATÓRIO DE TRANSAÇÃO");
                writer.newLine();
               for (Transacao transacao : transacoes) {
                   writer.write(transacao.toString());
                   writer.newLine();
               }
                writer.newLine();
                writer.newLine();
                writer.write("VALOR TOTAL DE ENTRADAS: " + valorEntrada);
                writer.newLine();
                writer.write("VALOR TOTAL DE DESPESAS: " + valorSaida);
                System.out.println("Arquivo salvo com sucesso em: " + fileToSave.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Erro ao salvar o arquivo: " + e.getMessage());
            }
        } else {
            System.out.println("Ação de salvar cancelada pelo usuário.");
        }
    }


        public static void salvarGraficoImagem(PieChart pieChart, Stage stage, String nomeArquivo) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Escolha onde salvar o arquivo");

            // Define a extensão padrão como PNG
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Imagem PNG (*.png)", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);

            // Sugere um nome inicial
            fileChooser.setInitialFileName(nomeArquivo+".png");

            // Abre a janela para o usuário escolher onde salvar
            File fileToSave = fileChooser.showSaveDialog(stage);
            if (fileToSave != null) {
                try {
                    WritableImage image = pieChart.snapshot(null, null);
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", fileToSave);
                    System.out.println("✅ Gráfico salvo com sucesso em: " + fileToSave.getAbsolutePath());
                } catch (IOException e) {
                    System.out.println("❌ Erro ao salvar o gráfico: " + e.getMessage());
                }
            } else {
                System.out.println("⚠️ Salvamento cancelado pelo usuário.");
            }
        }





}
