package main.java.grafo.core;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class Digrafo extends Grafo {
	public void conectarVertices(String rotuloVerticeInicial, String rotuloVerticeFinal, Integer peso) throws Exception{
		if(!super.existeVertice(rotuloVerticeInicial) || !super.existeVertice(rotuloVerticeFinal)) {
    		throw new Exception("Para adicionar uma aresta ambos os vértices devem existir.");
    	}
		Map<String, Integer> rotulosEmIndices = super.getRotulosEmIndices();
		super.criarMatrizAdjacencia();
		MatrizAdjacencia matrizAdjacencia = getMatrizAdjacencia();
		int indiceVerticeInicial = rotulosEmIndices.get(rotuloVerticeInicial);
	    int indiceVerticeFinal = rotulosEmIndices.get(rotuloVerticeFinal);
		matrizAdjacencia.adicionarArestaDirecionada(indiceVerticeInicial, indiceVerticeFinal, peso);
	}
	
	public Grafo arvoreGeradoraPorProfundidade() throws Exception {
		String raiz = super.getVertices().get(0).getRotulo();
		return this.arvoreGeradoraPorProfundidade(raiz);
	}
	
	public Grafo arvoreGeradoraPorProfundidade(String raiz) throws Exception {
		LinkedHashSet<String> aVisitar = new LinkedHashSet<String>();
		Digrafo arvore = new Digrafo();
		List<Vertice> vertices = super.getVertices();
		MatrizAdjacencia matrizAdjacencia = super.getMatrizAdjacencia();
		Map<String, Integer> rotulosEmIndices = super.getRotulosEmIndices();
		int indiceRaiz = rotulosEmIndices.get(raiz);

		for(Vertice v : vertices) {
			aVisitar.add(v.getRotulo());
		}
		
		if (raiz == null) {
			raiz = vertices.get(0).getRotulo();
		}
		
		aVisitar.remove(raiz);
		arvore.adicionarVertice(raiz);
		this.visitar(raiz, aVisitar, arvore);
		
		while(aVisitar.size() > 0) {
			if(!matrizAdjacencia.hasAncestrais(indiceRaiz)) {
				break;
			}
			String ancestral = null;
			for(Vertice a : matrizAdjacencia.getAncestrais(indiceRaiz)) {
				if(aVisitar.contains(a.getRotulo())) {
					ancestral = a.getRotulo();
					break;
				}
			}
			if(ancestral == null) {
				throw new Exception("Todos os ancestrais da raiz já foram visitados. Dígrafo não conexo.");
			}
			
			aVisitar.remove(ancestral);
			arvore.adicionarVertice(ancestral);
			arvore.conectarVertices(ancestral, raiz, null);
			raiz = ancestral;
			indiceRaiz = rotulosEmIndices.get(raiz);
			this.visitar(raiz, aVisitar, arvore);
		}
		
		return arvore;
	}
	
	private void visitar(String corrente, LinkedHashSet<String> aVisitar, Digrafo arvore) throws Exception {
		for(Vertice vizinho : super.getAdjacencias(corrente)) {
			String rotulo = vizinho.getRotulo();
			if(!aVisitar.contains(rotulo)) {
				continue;
			}
			arvore.adicionarVertice(rotulo);
			arvore.conectarVertices(corrente, rotulo, null);
			aVisitar.remove(rotulo);
			visitar(rotulo, aVisitar, arvore);
		}
	}
}	