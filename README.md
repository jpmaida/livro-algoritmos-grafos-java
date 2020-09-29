# livro-algoritmos-grafos-java

Este repositório possui todo o código-fonte da aplicação desenvolvida no livro __Teoria dos Grafos: Uma abordagem prática em Java__. Foi utilizada uma divisão por capítulos entre os diretórios para que o leitor possa analisar separadamente as alterações feitas entre capítulos, logo:
```
├── README.md ~> Você está lendo este arquivo :D
├── capitulo-2 ~> Aplicação desenvolvida no capítulo 2
│   └── src
│       └── main
│           └── java
│               └── grafo
│                   └── core
│                       ├── Grafo.java
│                       └── Vertice.java
├── capitulo-3 ~> Aplicação desenvolvida no capítulo 3
│   └── src
│       └── main
│           └── java
│               └── grafo
│                   └── core
│                       ├── Grafo.java
│                       ├── MatrizAdjacencia.java
│                       └── Vertice.java
├── capitulo-4 ~> Aplicação desenvolvida no capítulo 4
│   └── src
│       └── main
│           └── java
│               └── grafo
│                   ├── core
│                   │   ├── Grafo.java
│                   │   ├── MatrizAdjacencia.java
│                   │   └── Vertice.java
│                   └── search
│                       ├── BuscaEmLargura.java
│                       ├── BuscaEmProfundidade.java
│                       └── Caminho.java
├── capitulo-5 ~> Aplicação desenvolvida no capítulo 5
│   └── src
│       └── main
│           └── java
│               └── grafo
│                   ├── core
│                   │   ├── Grafo.java
│                   │   ├── MatrizAdjacencia.java
│                   │   └── Vertice.java
│                   └── search
│                       ├── BuscaEmLargura.java
│                       ├── BuscaEmProfundidade.java
│                       └── Caminho.java
├── capitulo-6 ~> Aplicação desenvolvida no capítulo 6
│   └── src
│       └── main
│           └── java
│               └── grafo
│                   ├── core
│                   │   ├── Digrafo.java
│                   │   ├── Grafo.java
│                   │   ├── MatrizAdjacencia.java
│                   │   └── Vertice.java
│                   └── search
│                       ├── BuscaEmLargura.java
│                       ├── BuscaEmProfundidade.java
│                       └── Caminho.java
├── capitulo-7 ~> Aplicação desenvolvida no capítulo 7
│   └── src
│       └── main
│           └── java
│               └── grafo
│                   ├── core
│                   │   ├── Digrafo.java
│                   │   ├── Grafo.java
│                   │   ├── MatrizAdjacencia.java
│                   │   └── Vertice.java
│                   ├── search
│                   │   ├── BuscaEmLargura.java
│                   │   ├── BuscaEmProfundidade.java
│                   │   └── Caminho.java
│                   └── util
│                       ├── AlgoritmoDijkstra.java
│                       ├── AlgoritmoFloydWarshall.java
│                       └── AlgoritmoPrim.java
└── capitulo-8 ~> Aplicação desenvolvida no capítulo 8, versão completa
    └── src
        └── main
            └── java
                ├── aplicacao
                │   └── Aplicacao.java
                └── grafo
                    ├── core
                    │   ├── Digrafo.java
                    │   ├── Grafo.java
                    │   ├── MatrizAdjacencia.java
                    │   └── Vertice.java
                    ├── search
                    │   ├── BuscaEmLargura.java
                    │   ├── BuscaEmProfundidade.java
                    │   └── Caminho.java
                    └── util
                        ├── AlgoritmoDijkstra.java
                        ├── AlgoritmoFloydWarshall.java
                        └── AlgoritmoPrim.java
```

O diretório `capitulo-8` concentra a aplicação por completo, ou seja, todo o código desenvolvido no livro está nele.

Foi utilizada a JDK 1.8, independente do fornecedor (Oracle, OpenJDK, etc.), para o desenvolvimento deste projeto, mas é possível reescrevê-lo em qualquer versão. Arquivos oriundos de ferramentas de desenvolvimento (IDE) não estão presentes neste repositório.

Boa leitura e bom estudo.

Dúvidas, críticas ou sugestões ? Abra uma issue :D
