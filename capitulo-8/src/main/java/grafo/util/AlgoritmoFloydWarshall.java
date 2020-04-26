package main.java.grafo.util;

import main.java.grafo.core.Digrafo;
import main.java.grafo.core.Vertice;

import java.util.HashMap;
import java.util.Map;

public class AlgoritmoFloydWarshall {
    private static AlgoritmoFloydWarshall algoritmoFloydWarshall;

    private AlgoritmoFloydWarshall(){}

    public static AlgoritmoFloydWarshall getInstance(){
        if(algoritmoFloydWarshall == null){
            return new AlgoritmoFloydWarshall();
        }
        return algoritmoFloydWarshall;
    }

    public Map<String, Map<String, Info>> processar(Digrafo digrafo){
        Map<String, Map<String, Info>> matriz = new HashMap<>();
        for(Vertice u : digrafo.getVertices()){
            Map<String, Info> linha = new HashMap<>();
            matriz.put(u.getRotulo(), linha);
            for(Vertice v : digrafo.getVertices()){
                Integer peso = digrafo.getPeso(u.getRotulo(), v.getRotulo());
                int valor = peso == null || peso != null && peso == 0 ? Integer.MAX_VALUE : peso;
                Info info = new Info();
                info.porQualVertice = v;
                info.distancia = u.getRotulo().equalsIgnoreCase(v.getRotulo()) ? 0 : valor;
                linha.put(v.getRotulo(), info);
            }
        }

        for(Vertice k : digrafo.getVertices()){
            Map<String, Info> linhaK = matriz.get(k.getRotulo());
            for(Vertice u : digrafo.getVertices()){
                Map<String, Info> linhaU = matriz.get(u.getRotulo());
                Info uk = linhaU.get(k.getRotulo());
                for(Vertice v : digrafo.getVertices()){
                    Info kv = linhaK.get(v.getRotulo());
                    /*
                    Essa verificação é necessária pois operações que envolvam o valor Integer.MAX_VALUE resultam em valores negativos
                    devido a extrapolação do valor que um dado do tipo inteiro pode assumir.
                    Veja o link abaixo para maiores detalhes:
                    https://softwareengineering.stackexchange.com/questions/323292/why-adding-positive-values-in-java-or-c-sometimes-result-in-a-negative-value
                     */
                    int soma = uk.distancia == Integer.MAX_VALUE || kv.distancia == Integer.MAX_VALUE ? Integer.MAX_VALUE : uk.distancia + kv.distancia;
                    if(soma < linhaU.get(v.getRotulo()).distancia){
                        Info info = new Info();
                        info.porQualVertice = uk.porQualVertice;
                        info.distancia = soma;
                        linhaU.put(v.getRotulo(), info);
                    }
                }
            }
        }

        return matriz;
    }

    public class Info {
        public int distancia;
        public Vertice porQualVertice;
    }
}
