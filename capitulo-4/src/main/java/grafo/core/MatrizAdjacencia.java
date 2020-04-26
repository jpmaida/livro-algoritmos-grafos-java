package main.java.grafo.core;

import java.util.ArrayList;
import java.util.List;

class MatrizAdjacencia {
	private int[][] matriz;
	private List<Vertice> vertices;
	private int qtdVertices;
    
    public MatrizAdjacencia(List<Vertice> vertices){
        this.vertices = vertices;
		this.qtdVertices = vertices.size();
		matriz = new int[qtdVertices][qtdVertices];
		inicializarMatriz();
    }
    
    private void inicializarMatriz(){
        for(int i=0; i<matriz.length; i++){
            for(int j=0; j<matriz[i].length; j++){
                matriz[i][j] = 0;
            }
        }
    }
    
    public void adicionarAresta(int indiceVerticeInicial, int indiceVerticeFinal) {
		Vertice verticeInicial = vertices.get(indiceVerticeInicial);
		Vertice verticeFinal = vertices.get(indiceVerticeFinal);
		if(indiceVerticeInicial == indiceVerticeFinal) {
    		matriz[indiceVerticeInicial][indiceVerticeInicial] = 1;
    		verticeInicial.addGrau();
    	} else {
			matriz[indiceVerticeInicial][indiceVerticeFinal] = 1;
			verticeInicial.addGrau();
	        matriz[indiceVerticeFinal][indiceVerticeInicial] = 1;
			verticeFinal.addGrau();
    	}
    }
    
    public List<Vertice> getAdjacencias(int indiceVertice) {
    	int linha = indiceVertice;
    	List<Vertice> adjacencias = new ArrayList<>();
    	for(int j=0; j<vertices.size(); j++) {
    		if(matriz[linha][j] == 1) {
				Vertice vertice = vertices.get(j);
    			adjacencias.add(vertice);
    		}
    	}
    	return adjacencias;
    }
}
