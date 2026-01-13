package service;

import mode.Paciente;
import mode.Prioridade;
import java.util.Random;

public class TriagemService {

    // Gera prioridades aleatória
    private final Random random = new Random();

    public void triar(Paciente paciente) {
        // Escolhe uma prioridade aleatória
        Prioridade prioridade = Prioridade.values()[random.nextInt(3)];
        // Define a prioridade no paciente
        paciente.setPrioridade(prioridade);

        System.out.println(
            "Paciente " + paciente.getId() +
            " triado como " + prioridade
        );
    }
}