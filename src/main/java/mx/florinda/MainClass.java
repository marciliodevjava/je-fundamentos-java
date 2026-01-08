package mx.florinda;

import mx.florinda.cardapio.InMeroryDataBase;
import mx.florinda.cardapio.HistoricoVisualização;

public class MainClass {
    public static void main(String[] args) throws InterruptedException {
        HistoricoVisualização historicoVisualização = new HistoricoVisualização(new InMeroryDataBase());
        historicoVisualização.registrarVisualizacao(1);
        historicoVisualização.registrarVisualizacao(8);
        historicoVisualização.registrarVisualizacao(6);
        historicoVisualização.registrarVisualizacao(2);
        historicoVisualização.registrarVisualizacao(5);

        historicoVisualização.removerItemCardapio(1);
        System.out.println(historicoVisualização.getVisualizacao());

        System.out.println("Solicitando o gc");
        System.gc();
        Thread.sleep(500);
        System.out.println(historicoVisualização.getVisualizacao().size());
        System.out.println(historicoVisualização.getVisualizacao());

    }
}
