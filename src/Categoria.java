// Classe que representa uma categoria
public class Categoria {
    private String nome; //Nome da categoria ex: transporte, lazer, comida
    private String descricao; //Descriação d que represententa cada categoria caso o usuário fique em dúvida
    private boolean eGanho; //Diz se é gato ou ganho, ganho==true, gasto==false
    //Possivelmente adicionar um arrayList de transacoes

    public Categoria(String nome) {
        this.nome = nome;
    }
}
