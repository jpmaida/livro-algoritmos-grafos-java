package main.java.grafo.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import main.java.grafo.core.Grafo;
import main.java.grafo.core.Vertice;

public class AlgoritmoDijkstra {

	private static AlgoritmoDijkstra algoritmoDijkstra;

	private AlgoritmoDijkstra(){}

	public static AlgoritmoDijkstra getInstance(){
		if(algoritmoDijkstra == null){
			return new AlgoritmoDijkstra();
		}
		return algoritmoDijkstra;
	}

	public Map<String, Info> processar(String origem, String destino, Grafo grafo) {
		try {
			grafo.getVertice(origem);
			grafo.getVertice(destino);
		} catch (Exception e) {
			throw e;
		}
		
		Map<String, Info> infoVertice = new HashMap<>();
		infoVertice.put(origem, new Info(0, null));

		Set<String> aVisitar = new HashSet<>();
		aVisitar.add(origem);

		while (aVisitar.size() > 0) {
			String melhorVertice = null;
			int menorDistancia = Integer.MAX_VALUE;
			for (String v : aVisitar) {
				Info info = infoVertice.get(v);
				if (info.distancia < menorDistancia) {
					melhorVertice = v;
					menorDistancia = info.distancia;
				}
			}

			if (melhorVertice.equals(destino))
				break;
			
			aVisitar.remove(melhorVertice);
			
			for(Vertice vizinho : grafo.getAdjacencias(melhorVertice)) {
				String rotulo = vizinho.getRotulo();
				int distancia = menorDistancia + grafo.getPeso(melhorVertice, rotulo);
				if(infoVertice.containsKey(rotulo)) {
					Info info = infoVertice.get(rotulo);
					if(distancia < info.distancia) {
						info.distancia = distancia;
						info.predecessor = grafo.getVertice(melhorVertice);
					}
				} else {
					infoVertice.put(rotulo, new Info(distancia, grafo.getVertice(melhorVertice)));
					aVisitar.add(rotulo);
				}
			}
		}
		
		return infoVertice;
	}
	
	public class Info {
		public int distancia;
		public Vertice predecessor;

		Info(int distancia, Vertice predecessor) {
			this.distancia = distancia;
			this.predecessor = predecessor;
		}
	}
}