package main.java.grafo.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;

public class Grafo {
	private int qtdMaximaVertices;
	private boolean isQtdMaximaDefinida;
	private int qtdAtualVertices = 0;
	private Map<String, Integer> rotulosEmIndices = new HashMap<String, Integer>();
	private List<Vertice> vertices = new ArrayList<Vertice>();
	private MatrizAdjacencia matrizAdjacencia;

	public Grafo() {
		qtdMaximaVertices = 10;
	}

	public Grafo(int qtdVertices) {
		if (qtdVertices <= 0) {
			throw new IllegalArgumentException("A quantidade máxima de vértices deve ser maior ou igual à 1");
		}
		qtdMaximaVertices = qtdVertices;
		isQtdMaximaDefinida = true;
	}

	public void adicionarVertice(String rotulo) throws Exception {
		if (qtdAtualVertices <= qtdMaximaVertices - 1) {
			Vertice novoVertice = new Vertice(rotulo);
			this.vertices.add(novoVertice);
			this.rotulosEmIndices.put(rotulo, qtdAtualVertices);
			qtdAtualVertices++;
		} else {
			throw new Exception("A quantidade de vértices permitida (" + qtdMaximaVertices + ")	foi	excedida.");
		}
	}
	
	public List<Vertice> getVertices(){
		return this.vertices;
	}
	
	public Vertice getVertice(String rotulo) {
		this.existeVerticeOrThrow(rotulo);
		int indice = this.rotulosEmIndices.get(rotulo);
		return this.vertices.get(indice);
	}
	
	public void conectarVertices(String rotuloVerticeInicial, String rotuloVerticeFinal, Integer peso) throws Exception{
		if(!this.existeVertice(rotuloVerticeInicial) || !this.existeVertice(rotuloVerticeFinal)) {
    		throw new Exception("Para adicionar uma aresta ambos os vértices devem existir.");
    	}
	    criarMatrizAdjacencia();
	    int indiceVerticeFinal = this.rotulosEmIndices.get(rotuloVerticeInicial);
		int indiceVerticeInicial = this.rotulosEmIndices.get(rotuloVerticeFinal);
		this.matrizAdjacencia.adicionarAresta(indiceVerticeInicial, indiceVerticeFinal, peso);
	}
	
	public List<Vertice> getAdjacencias(String vertice) {
		this.existeVerticeOrThrow(vertice);
		int indiceVertice = this.rotulosEmIndices.get(vertice);
		return this.matrizAdjacencia != null ? this.matrizAdjacencia.getAdjacencias(indiceVertice) : Collections.emptyList();
	}
	
	private boolean existeVerticeOrThrow(String vertice) {
		if(!existeVertice(vertice)) {
			throw new IllegalArgumentException("O vértice não existe.");
		}
		return true;
	}
	
	boolean existeVertice(String rotuloVertice) {
		return this.rotulosEmIndices.get(rotuloVertice) != null ? true : false;
	}

	void criarMatrizAdjacencia() throws Exception {
	    if(this.matrizAdjacencia == null){
	        this.matrizAdjacencia = new MatrizAdjacencia(new ArrayList<Vertice>(this.vertices));
	    } else {
	    	int qtdVerticesNaMatriz = this.matrizAdjacencia.getQtdVertices();
	    	if(this.vertices.size() != qtdVerticesNaMatriz) {
	    		MatrizAdjacencia matrizAdjacenciaTemp = new MatrizAdjacencia(this.vertices);
	    		this.matrizAdjacencia.copiaValoresPara(matrizAdjacenciaTemp);
	    		this.matrizAdjacencia = matrizAdjacenciaTemp;
	    	}
	    }
	}
	
	public Grafo arvoreGeradoraPorProfundidade() throws Exception {
		Grafo arvore = new Grafo();
		Stack<Vertice> roloDeBarbante = new Stack<Vertice>();
		LinkedHashSet<String> verticesVisitados = new LinkedHashSet<String>();
		List<Vertice> vertices = getVertices();

		for(Vertice v : vertices) {
			arvore.adicionarVertice(v.getRotulo());
		}
		
		Vertice verticePontoDePartida = vertices.get(0);
        verticesVisitados.add(verticePontoDePartida.getRotulo());
        roloDeBarbante.push(verticePontoDePartida);

        while(!roloDeBarbante.empty()){
        	Vertice verticeAnalisado = roloDeBarbante.peek();
            Vertice proximoVertice = obterProximoVertice(verticeAnalisado, verticesVisitados);
            if(proximoVertice == null){
                roloDeBarbante.pop();
            } else {
            	String rotulo = proximoVertice.getRotulo();
            	verticesVisitados.add(rotulo);
                roloDeBarbante.push(proximoVertice);
                arvore.conectarVertices(verticeAnalisado.getRotulo(), proximoVertice.getRotulo(), null);
            }
        }

		return arvore;
	}
	
	Vertice obterProximoVertice(Vertice vertice, LinkedHashSet<String> verticesVisitados){
    	List<Vertice> adjacencias = getAdjacencias(vertice.getRotulo());
        for(int i=0; i<adjacencias.size(); i++){
        	Vertice adjacencia = adjacencias.get(i);
        	boolean naoVisitadoAinda = !verticesVisitados.contains(adjacencia.getRotulo());
            if(naoVisitadoAinda){
                return adjacencia;
            }
        }
        return null;
    }
	
	Map<String, Integer> getRotulosEmIndices(){
		return rotulosEmIndices;
	}

	MatrizAdjacencia getMatrizAdjacencia() {
		return matrizAdjacencia;
	}
	
	public Integer getPeso(String rotuloVerticeInicial, String rotuloVerticeFinal) {
		int indiceVerticeInicial = rotulosEmIndices.get(rotuloVerticeInicial);
		int indiceVerticeFinal = rotulosEmIndices.get(rotuloVerticeFinal);
		if(matrizAdjacencia == null) {
			return null;
		}
		return matrizAdjacencia.getPeso(indiceVerticeInicial, indiceVerticeFinal);
	}

	public String graphViz(boolean isGrafoPonderado) {
		boolean isDigrafo = this instanceof Digrafo ? true : false;
		StringBuilder graphViz = new StringBuilder();
		graphViz.append("digraph D {\n");
		
		for(Vertice v : getVertices()) {
			String rotulo = v.getRotulo();
			graphViz.append("\t").append(rotulo).append(" [shape=circle]\n");
			for(Vertice adj : getAdjacencias(rotulo)) {
				String rotuloAdj = adj.getRotulo();
				if(isDigrafo) {
					graphVizConnection(isGrafoPonderado, isDigrafo, graphViz, rotulo, rotuloAdj);
				} else {
					String regex = ".*" + rotuloAdj + " -> " + rotulo + ".*";
					boolean hasConexaoRedundante = !Pattern.compile(regex).matcher(graphViz.toString()).find();
					if (hasConexaoRedundante) {
						graphVizConnection(isGrafoPonderado, isDigrafo, graphViz, rotulo, rotuloAdj);
					}
				}
			}
		}
		
		graphViz.append("}");
		return graphViz.toString();
	}

	private void graphVizConnection(boolean isGrafoPonderado, boolean isDigrafo, StringBuilder graphViz, String rotulo,
			String rotuloAdj) {
		graphViz.append("\t\t")
				.append(rotulo).append(" -> ").append(rotuloAdj)
				.append("[");
		if(!isDigrafo) {
			graphViz.append("arrowhead=none");
		}
		if(!isDigrafo && isGrafoPonderado) {
			graphViz.append(",");
		}
		if(isGrafoPonderado) {
			graphViz.append("label=").append(getPeso(rotulo, rotuloAdj));
		}
		graphViz.append("]\n");
	}
}
