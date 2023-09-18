import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class App {


    private static List<Item> items = inicializarItens();

    public static void main(String[] args) {

        List<Individuo> populacao = inicializarPopulacao();
        ArrayList<Integer> listaMelhoresResultados = new ArrayList<>();

        for(int metricas = 1; metricas <= 10 ; metricas++) {
            String result = new String();
            for(int execs = 1; execs <= 10 ; execs++) {
                for (int geracao = 0; geracao < Constantes.NUMERO_GERACOES; geracao++) {
                    avaliarPopulacao(populacao);

                    List<Individuo> pais = selecionaPais(populacao);

                    List<Individuo> filhos = realizaCruzamento(pais);

                    aplicaMutacao(filhos);

                    avaliarPopulacao(filhos);

                    populacao = filhos;
                }

                Individuo melhorLoop = Collections.max(populacao, (a, b) -> a.valorTotal - b.valorTotal);

                listaMelhoresResultados.add(melhorLoop.valorTotal);
            }
            for(Integer i : listaMelhoresResultados){
                result = result + "," + i;
            }
            System.out.println(result);
            listaMelhoresResultados.clear();
        }
    }

    public static List<Individuo> realizaCruzamento(List<Individuo> pais) {
        List<Individuo> filhos = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < pais.size(); i += 2) {

            if (random.nextDouble() <= Constantes.TAXA_CRUZAMENTO) {
                Individuo pai1 = pais.get(i);
                Individuo pai2 = pais.get(i + 1);

                Individuo filho1 = new Individuo(pai1.cromossomo.size());
                Individuo filho2 = new Individuo(pai2.cromossomo.size());

                int crossoverPoint = random.nextInt(pai1.cromossomo.size());

                for (int j = 0; j < crossoverPoint; j++) {
                    filho1.cromossomo.set(j, pai1.cromossomo.get(j));
                    filho2.cromossomo.set(j, pai2.cromossomo.get(j));
                }

                for (int j = crossoverPoint; j < pai1.cromossomo.size(); j++) {
                    filho1.cromossomo.set(j, pai2.cromossomo.get(j));
                    filho2.cromossomo.set(j, pai1.cromossomo.get(j));
                }

                filhos.add(filho1);
                filhos.add(filho2);
            } else {
                filhos.add(pais.get(i));
                filhos.add(pais.get(i + 1));
            }
        }

        return filhos;
    }

    public static void aplicaMutacao(List<Individuo> offspring) {
        Random random = new Random();
        for (Individuo Individuo : offspring) {
            for (int i = 0; i < Individuo.cromossomo.size(); i++) {
                if (random.nextDouble() <= Constantes.TAXA_MUTACAO) {
                    int gene = Individuo.cromossomo.get(i);
                    Individuo.cromossomo.set(i, gene == 0 ? 1 : 0);
                }
            }
        }
    }

    public static void avaliarPopulacao(List<Individuo> populacao) {
        for (Individuo Individuo : populacao) {
            int totalpeso = 0;
            int totalvalor = 0;

            for (int i = 0; i < Individuo.cromossomo.size(); i++) {
                if (Individuo.cromossomo.get(i) == 1) {
                    totalpeso += items.get(i).peso;
                    totalvalor += items.get(i).valor;
                }
            }

            if (totalpeso <= Constantes.TAMANHO_MOCHILA) {
                Individuo.valorTotal = totalvalor;
                Individuo.pesoTotal = totalpeso;
            } else {
                Individuo.valorTotal = 0;
            }
        }
    }

    public static List<Individuo> selecionaPais(List<Individuo> populacao) {
        List<Individuo> pais = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < populacao.size(); i++) {
            Individuo pai = selecaoPorTorneio(populacao, random);
            pais.add(pai);
        }

        return pais;
    }

    private static Individuo selecaoPorTorneio(List<Individuo> populacao, Random random) {
        List<Individuo> tournament = new ArrayList<>();

        for (int i = 0; i < Constantes.VALOR_TORNEIO; i++) {
            int randomIndex = random.nextInt(populacao.size());
            tournament.add(populacao.get(randomIndex));
        }

        Individuo bestIndividuo = tournament.get(0);

        for (Individuo Individuo : tournament) {
            if (Individuo.valorTotal > bestIndividuo.valorTotal) {
                bestIndividuo = Individuo;
            }
        }

        return bestIndividuo;
    }

    private static List<Individuo> inicializarPopulacao() {
        List<Individuo> populacao = new ArrayList<>();
        for (int i = 0; i < Constantes.TAMANHO_POPULACAO; i++) {
            populacao.add(new Individuo(items.size()));
        }
        return populacao;
    }
    private static List<Item> inicializarItens() {
        List<Item> itens = new ArrayList<>();
        itens.add(new Item(100, 10));
        itens.add(new Item(135, 5));
        itens.add(new Item(150, 20));
        itens.add(new Item(300, 8));
        itens.add(new Item(45, 12));
        itens.add(new Item(200, 10));
        itens.add(new Item(80, 30));
        itens.add(new Item(90, 19));
        itens.add(new Item(50, 27));
        itens.add(new Item(300, 14));
        return itens;
    }
}