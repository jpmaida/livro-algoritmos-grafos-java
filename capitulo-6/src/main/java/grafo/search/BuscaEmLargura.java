package main.java.grafo.search;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import main.java.grafo.core.Grafo;
import main.java.grafo.core.Vertice;

public class BuscaEmLargura {

	private static BuscaEmLargura instance;
	
	public static BuscaEmLargura getInstance() {
    	if(instance == null) {
    		return new BuscaEmLargura();
    	}
    	return instance;
    }
	
	public List<String> buscar(Grafo grafo, String origem, String destino) {
		Queue<String> roloDeBarbante = new LinkedList<String>();
		LinkedHashSet<String> verticesVisitados = new LinkedHashSet<String>();
        Caminho caminho = new Caminho();
        
        roloDeBarbante.add(origem);

        while(!roloDeBarbante.isEmpty()){
        	String v = roloDeBarbante.poll();
        	if(v.equals(destino)) {
            	break;
            }
        	for(Vertice u : grafo.getAdjacencias(v)) {
        		String rotulo = u.getRotulo();
        		if(!verticesVisitados.contains(rotulo)) {
					verticesVisitados.add(rotulo);
        			caminho.ligar(rotulo, v);
        			roloDeBarbante.add(rotulo);
        		}
        	}
        }

        return caminho.gerar(origem, destino);
	}
}