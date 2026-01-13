package mode;

public class Paciente implements Runnable {

    private final int id;
    private Prioridade prioridade;

    // Cria o paciente com ID
    public Paciente(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    // Define a prioridade após a triagem
    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    @Override
    public void run() {
        // comportamento controlado pelo serviço
    }
}