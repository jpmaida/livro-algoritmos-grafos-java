package main.java.aplicacao;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import main.java.grafo.core.Digrafo;
import main.java.grafo.core.Grafo;
import main.java.grafo.core.Vertice;
import main.java.grafo.search.BuscaEmLargura;
import main.java.grafo.search.BuscaEmProfundidade;
import main.java.grafo.util.AlgoritmoDijkstra;
import main.java.grafo.util.AlgoritmoDijkstra.Info;
import main.java.grafo.util.AlgoritmoFloydWarshall;
import main.java.grafo.util.AlgoritmoPrim;

public class Aplicacao {
	
	private Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
	    Aplicacao app = new Aplicacao();
	    Grafo grafo = null;
	   
	    app.menu();
	    
	    while(true) {
	    	System.out.print("Digite a opção desejada: ");
	    	String opcao = app.ler().toUpperCase();
	    	try {
		    	switch (opcao) {
					case "G":
						grafo = app.novoGrafo();
						break;
					case "D":
						grafo = app.novoDigrafo();
						break;
					case "V":
						app.novoVertice(grafo);
						break;
					case "A":
						app.novaAresta(grafo);
						break;
					case "PE":
						app.novaArestaPonderada(grafo);
						break;
					case "P":
						app.buscarPorProfundidade(grafo);
						break;
					case "L":
						app.buscarPorLargura(grafo);
						break;
					case "AG":
						app.arvoreGeradora(grafo);
						break;
					case "PR":
						app.arvoreGeradoraMinimaPorPrim(grafo);
						break;
					case "DIJ":
						app.algoritmoDijkstra(grafo);
						break;
					case "FL":
						app.algoritmoFloydWarshall(grafo);
						break;
					case "VIZ":
						app.imprimir(grafo);
						break;
					case "S":
						System.out.println("Encerrando...");
						app.input.close();
						app.sair();
					default:
						System.out.println("Opção não reconhecida.");
						app.input.close();
						app.sair();
				}
	    	} catch (Exception e) {
	    		System.out.println("Erro: " + e.getMessage());
	    		System.out.println("Abortando ...");
	    		System.out.println("Programa finalizado.");
	    		System.exit(1);
	    	}
	    }
	}
	
	private void menu() {
		StringBuilder texto = new StringBuilder();
		texto   .append("********** Bem vindo *********************\n")
				.append("* Escolha uma das opções abaixo:         *\n")
				.append("* [G]rafo                                *\n")
				.append("* [D]igrafo                              *\n")
				.append("* [V]értice                              *\n")
				.append("* [A]resta                               *\n")
				.append("* Aresta com [Pe]so                      *\n")
				.append("* Busca por [P]rofundidade               *\n")
				.append("* Busca por [L]argura                    *\n")
				.append("* [AG] - Árvore Geradora                 *\n")
				.append("* Árvore Geradora Mínima por [Pr]im      *\n")
				.append("* Algoritmo de [Dij]kstra                *\n")
				.append("* Algoritmo de [Fl]oydWarshall           *\n")
				.append("* Gerar representação Graph[Viz]         *\n")
				.append("* [S]air                                 *\n")
				.append("******************************************\n");
		System.out.print(texto);
	}
	
	private String ler() {
		return input.next();
	}
	
	private Grafo novoGrafo() {
		Grafo grafo = new Grafo();
		System.out.println("Novo grafo criado.");
		return grafo;
	}
	
	private Digrafo novoDigrafo() {
		Digrafo digrafo = new Digrafo();
		System.out.println("Novo dígrafo criado.");
		return digrafo;
	}
	
	private void novoVertice(Grafo grafo) throws Exception {
		System.out.print("Defina o nome do vértice ? ");
		String nome = ler();
		grafo.adicionarVertice(nome);
		System.out.println("Novo vértice " + nome + " criado.");
	}
	
	private void novaAresta(Grafo grafo) throws Exception {
		System.out.print("Qual o vértice de origem ? ");
		String vOrigem = ler();
		System.out.print("Qual o vértice de destino ? ");
		String vDestino = ler();
		grafo.conectarVertices(vOrigem, vDestino, null);
		System.out.println("Nova aresta criada.");
	}
	
	private void novaArestaPonderada(Grafo grafo) throws Exception {
		System.out.print("Qual o vértice de origem ? ");
		String vOrigem = ler();
		System.out.print("Qual o vértice de destino ? ");
		String vDestino = ler();
		System.out.print("Qual o peso da aresta ? ");
		String peso = ler();
		grafo.conectarVertices(vOrigem, vDestino, Integer.valueOf(peso));
		System.out.println("Nova aresta ponderada criada.");
	}
	
	private void buscarPorProfundidade(Grafo grafo) {
		System.out.println("Busca por Profundidade");
		System.out.print("Qual o vértice de origem ? ");
		String inicio = ler();
		System.out.print("Qual o vértice de destino ? ");
		String fim = ler();
		List<String> caminhoPercorrido = BuscaEmProfundidade.getInstance().buscar(grafo, inicio, fim);
		graphVizParaBuscas(grafo, caminhoPercorrido);
	}

	private void buscarPorLargura(Grafo grafo) {
		System.out.println("Busca por Largura");
		System.out.print("Qual o vértice de origem ? ");
		String inicio = ler();
		System.out.print("Qual o vértice de destino ? ");
		String fim = ler();
		List<String> caminhoPercorrido = BuscaEmLargura.getInstance().buscar(grafo, inicio, fim);
		graphVizParaBuscas(grafo, caminhoPercorrido);
	}
	
	private void arvoreGeradora(Grafo grafo) throws Exception {
		System.out.println("Árvore Geradora");
		Grafo arvore = grafo.arvoreGeradoraPorProfundidade();
		String graphViz = this.graphViz(grafo, false);
		boolean isDigrafo = grafo instanceof Digrafo ? true : false;
		graphVizParaArvores(isDigrafo, arvore, graphViz, false);
	}

	private void arvoreGeradoraMinimaPorPrim(Grafo grafo) throws Exception {
		System.out.println("Árvore Geradora por Prim");
		System.out.print("Qual a raiz ? ");
		String raiz = ler();
		Digrafo arvore = AlgoritmoPrim.getInstance().processar(raiz, grafo);
		String graphViz = this.graphViz(arvore, true);
		boolean isDigrafo = grafo instanceof Digrafo ? true : false;
		graphVizParaArvores(isDigrafo, arvore, graphViz, true);
	}

	private void algoritmoDijkstra(Grafo grafo) {
		System.out.println("Algoritmo de Dijkstra");
		System.out.print("Qual o vértice de origem ? ");
		String origem = ler();
		System.out.print("Qual o vértice de destino ? ");
		String destino = ler();
		Map<String, Info> menorCaminho = AlgoritmoDijkstra.getInstance().processar(origem, destino, grafo);
		String graphViz = this.graphViz(grafo, true);
		for(String key : menorCaminho.keySet()) {
			Info info = menorCaminho.get(key);
			if(info.predecessor != null) {
				String conexaoAsString = info.predecessor.getRotulo() + " -> " + key;
				int peso = grafo.getPeso(info.predecessor.getRotulo(), key);
				String regex = conexaoAsString + "\\[.*\\]";
				String replacement = conexaoAsString + "[color=red,label=" + peso + "]";
				graphViz = graphViz.replaceAll(regex, replacement);
			}
		}
		System.out.println(graphViz);
	}
	
	private void algoritmoFloydWarshall(Grafo grafo) {
		System.out.println("Algoritmo de Floyd Warshall");
		if(grafo instanceof Digrafo) {
			Digrafo digrafo = (Digrafo) grafo;
			Map<String, Map<String, AlgoritmoFloydWarshall.Info>> matriz = AlgoritmoFloydWarshall.getInstance().processar(digrafo);
			this.graphVizParaFloydWarshall(matriz);
		} else {
			System.out.println("Operação não permitida para grafos somente para dígrafos.");
		}
	}
	
	private void imprimir(Grafo grafo) {
		System.out.println("Imprimindo grafo...");
		System.out.print("O grafo é ponderado (s/n) ? ");
		String resposta = ler();
		String graphViz = this.graphViz(grafo, "s".equalsIgnoreCase(resposta));
		System.out.println(graphViz);
	}
	
	private String graphViz(Grafo grafo, boolean isPonderado) {
		System.out.println("Para visualizar o grafo acesse o site http://magjac.com/graphviz-visual-editor/ e cole o conteúdo gerado no painel da esquerda OU http://www.webgraphviz.com/ e cole o conteúdo gerado na textarea e clique em \"Generate Graph\".");
		return grafo.graphViz(isPonderado);
	}
	
	private void graphVizParaBuscas(Grafo grafo, List<String> caminhoPercorrido) {
		boolean isDigrafo = grafo instanceof Digrafo ? true : false;
		String graphViz = this.graphViz(grafo,false);
		String anterior = null;
		String proximo = null;
		for(String v : caminhoPercorrido) {
			if(anterior == null) {
				anterior = v;
			} else {
				proximo = v;
				String conexao = anterior + " -> " + proximo;
				String regex = conexao + "\\[.*\\]";
				if(!Pattern.compile(regex).matcher(graphViz.toString()).find()) {
					conexao = proximo + " -> " + anterior;
					regex = conexao + "\\[.*\\]";
				}
				String replacement = conexao + "[";
				replacement = replacement.concat(!isDigrafo ? "arrowhead=none,color=red]" : "color=red]");
				graphViz = graphViz.replaceAll(regex, replacement);
				anterior = proximo;
				proximo = null;
			}
		}
		System.out.println(graphViz);
	}
	
	private void graphVizParaArvores(boolean isDigrafo, Grafo arvore, String graphViz, boolean isPonderado) {
		for(Vertice v : arvore.getVertices()) {
			for(Vertice adj : arvore.getAdjacencias(v.getRotulo())) {
				String conexaoAsString = v.getRotulo() + " -> " + adj.getRotulo();
				String regex = conexaoAsString + "\\[.*\\]";
				String replacement = conexaoAsString + "[";
				if(isPonderado) {
					int peso = arvore.getPeso(v.getRotulo(), adj.getRotulo());
					replacement = replacement.concat("label="+peso).concat(",");
				}
				replacement = replacement.concat(!isDigrafo ? "arrowhead=none,color=red]" : "color=red]");
				graphViz = graphViz.replaceAll(regex , replacement);
			}
		}
		System.out.println(graphViz);
	}
	
	private void graphVizParaFloydWarshall(Map<String, Map<String, AlgoritmoFloydWarshall.Info>> matriz) {
		System.out.println("Para visualizar o resultado do algoritmo acesse o site http://magjac.com/graphviz-visual-editor/ e "
				+ "cole o conteúdo gerado no painel da esquerda.");
		StringBuilder graphViz = new StringBuilder();
		graphViz.append("digraph D {\n")				
				.append("\taHtmlTable [\n")
					.append("\t\tshape=plaintext\n")
					.append("\t\tcolor=black\n")
					.append("\t\tlabel=<\n")
						.append("\t\t\t<table style='border: 1px solid black; text-align: center;' cellspacing='0'>\n");
		
		graphViz.append("\t\t\t\t<tr>\n\t\t\t\t\t<td></td>\n");
		for(String v : matriz.keySet()) {
			graphViz.append("\t\t\t\t\t<td style='font-weight: bold;'>").append(v).append("</td>\n");
		}
		graphViz.append("\t\t\t\t</tr>\n");
		
		for(String v : matriz.keySet()) {
			graphViz.append("\t\t\t\t<tr>\n")
					.append("\t\t\t\t\t<td style='font-weight: bold;'>").append(v).append("</td>\n");
			Map<String, AlgoritmoFloydWarshall.Info> celulas = matriz.get(v);
			for(String u : celulas.keySet()) {
				AlgoritmoFloydWarshall.Info info = celulas.get(u);
				graphViz.append("\t\t\t\t\t<td>").append(info.porQualVertice.getRotulo()).append(" (");
				if(info.distancia == Integer.MAX_VALUE) {
					graphViz.append("∞");
				} else {
					graphViz.append(info.distancia);
				}
				graphViz.append(")").append("</td>\n");
			}
			graphViz.append("\t\t\t\t</tr>\n");
		}
		
		graphViz.append("\t\t\t</table>").append("\n\t>];").append("\n}");
		
		System.out.println(graphViz.toString());
	}
	
	private void sair() {
		System.out.println("Programa finalizado.");
		System.exit(0);
	}
}
