public abstract class MetodoPagamento {
    protected String nome;

    public MetodoPagamento(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
