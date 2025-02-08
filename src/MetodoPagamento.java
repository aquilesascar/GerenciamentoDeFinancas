public abstract class MetodoPagamento {
    private String nome;

    public MetodoPagamento(String nome){
        this.nome = nome;

    }

    public String getNome() {
        return nome;
    }
}
