import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

// Classe responsável pela geração de relatórios
public  class RelatorioService  {
    public static void gerarRelatorioMesAno(ArrayList<Categoria> categorias){
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o ano do relatorio");
        int ano= sc.nextInt();
        System.out.println("Digite o mês do relatorio");
        int mes= sc.nextInt();
        ArrayList<Transacao> transacoes = ConexaoSQLite.filtrarTransacoesPorMesAno(mes,ano);
        ordenarTranscoes(transacoes);
        double valorTotalEntradas= calcularEntradas(transacoes);
        double valortotalSaidas =calcularGastos(transacoes);

            TelaRelatorio.exibir(transacoes, categorias,valorTotalEntradas,valortotalSaidas);


    }

    private static void ordenarTranscoes(ArrayList<Transacao> transacoes){
        //Ordenando Transações da ultima para a primeira
        transacoes.sort(Comparator.comparing(Transacao::getData).reversed());

    }
    private static double calcularEntradas(ArrayList<Transacao> transacoes){
        double total = 0;
        for(Transacao transacao : transacoes){
            if(transacao.getTipo().equalsIgnoreCase("Receita")){
                total += transacao.getValor();
            }
        }
        return total;

    }
    private static double calcularGastos(ArrayList<Transacao> transacoes){
        double total = 0;
        for(Transacao transacao : transacoes){
            if(transacao.getTipo().equalsIgnoreCase("Despesa")){
                total += transacao.getValor();
            }
        }
        return total;
    }



}
