package mode;

public enum Prioridade {
    VERMELHO(3), AMARELO(2), VERDE(1);

    private final int nivel;

    Prioridade(int nivel) {
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }
}