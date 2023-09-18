import java.util.ArrayList;
import java.util.List;

public class Individuo {
    //cromossomo 1 = item na mochila, cromossomo 0 = item fora da mochila
    List<Integer> cromossomo;

    //valorTotal total de itens 1 no cromossomo
    int valorTotal;

    int pesoTotal;

    public Individuo(int tamanhoCromossomo) {
        cromossomo = new ArrayList<>(tamanhoCromossomo);
        for (int i = 0; i < tamanhoCromossomo; i++) {
            cromossomo.add(0);
        }
        valorTotal = 0;
    }
}
