package main.java.grafo.util;

import main.java.grafo.core.Digrafo;
import main.java.grafo.core.Grafo;
import main.java.grafo.core.Vertice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AlgoritmoPrim {
    private static AlgoritmoPrim algoritmoPrim;
    private Map<String, String> candidatos;
    private Set<String> aConectar;

    private AlgoritmoPrim(){}

    public static AlgoritmoPrim getInstance(){
        if(algoritmoPrim == null){
            return new AlgoritmoPrim();
        }
        return algoritmoPrim;
    }

    public Digrafo processar(String raiz, Grafo grafo) throws Exception {
        aConectar = new HashSet<>();
        for(Vertice vertice : grafo.getVertices()){
            aConectar.add(vertice.getRotulo());
        }

        Digrafo mst = new Digrafo();
        mst.adicionarVertice(raiz);
        aConectar.remove(raiz);

        candidatos = new HashMap();
        atualizarCandidatos(grafo, raiz);

        while(aConectar.size() > 0){
            String melhorU = null;
            String melhorV = null;
            int menorDistancia = Integer.MAX_VALUE;
            for(String u : candidatos.keySet()){
                String v = candidatos.get(u);
                int peso = grafo.getPeso(u, v);
                if(peso < menorDistancia){
                    melhorU = u;
                    melhorV = v;
                    menorDistancia = peso;
                }
            }

            if(menorDistancia == Integer.MAX_VALUE)
                break;

            mst.adicionarVertice(melhorV);
            mst.conectarVertices(melhorU, melhorV, menorDistancia);
            aConectar.remove(melhorV);
            atualizarCandidatos(grafo, melhorU);
            atualizarCandidatos(grafo, melhorV);
        }

        return mst;
    }

    private void atualizarCandidatos(Grafo grafo, String vertice){
        int menorDistancia = Integer.MAX_VALUE;
        String maisProximo = null;
        for(Vertice adj : grafo.getAdjacencias(vertice)){
            int peso = grafo.getPeso(vertice, adj.getRotulo());
            if(aConectar.contains(adj.getRotulo()) && peso < menorDistancia){
                menorDistancia = peso;
                maisProximo = adj.getRotulo();
            }
        }
        if(maisProximo != null){
            candidatos.put(vertice, maisProximo);
        } else {
            candidatos.remove(vertice);
        }
    }
}
