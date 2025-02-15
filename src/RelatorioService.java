import java.time.LocalDate;
import java.util.ArrayList;

// Classe responsável pela geração de relatórios
public class RelatorioService extends ControleFinanceiro {
    //calcular gastos com cada metodo de pagamento
    //calacular gastos com cada categoria
    //Gerar relatorio com gastos gerais
    public void gerarRelatorioSaldo(){


    }

    public void gerarRelatorioDespesasPorMes(){
        // Implementação
    }
    public void gerarGastosPorCategoria(){

    }

    public double calacularPorcentagemCategoria(int numMes, Categoria categoria){
        ArrayList<Transacao> transacaoes= (ArrayList<Transacao>) super.usuario.getTransacoes();
            LocalDate mes;
            int quantidade =0;
            for(Transacao transacao: transacaoes){
                mes=transacao.getData();
                if (mes.getMonthValue()==numMes && transacao.getCategoria().equals(categoria)){
                    quantidade++;
                }
            }
            return (double)(quantidade*100)/transacaoes.size();

    }

}
