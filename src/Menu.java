public abstract class Menu {
    Usuario usuario;

    public Menu(Usuario usuario) {
        this.usuario = usuario;
    }

    public abstract void exibir();
}
