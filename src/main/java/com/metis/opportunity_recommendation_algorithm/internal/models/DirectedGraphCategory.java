package com.metis.opportunity_recommendation_algorithm.internal.models;

public enum DirectedGraphCategory {

    /**
     * C3 - Fortemente Conexo
     * <br><br>
     * Este é o nível mais alto de conexidade. Um grafo é fortemente conexo se, para todo par de vértices "(u, v)", existe um caminho de "u" para "v" e um caminho de "v" para "u"
     * <br><br>
     * Condição: Se o algoritmo encontrar exatamente uma única SCC, e essa componente contiver todos os vértices do grafo, então o grafo é Fortemente Conexo (C3). A classificação para aqui.
     */
    C3,

    /**
     * C2 - Simplesmente Conexo
     * <br><br>
     * Um grafo é simplesmente conexo se, para qualquer par de vértices "{u, v}", existe um caminho de "u" para "v" ou um caminho de "v" para "u" (mas não necessariamente ambos).
     * <br><br>
     * Condição: Se tal caminho existir no Grafo Reduzido, o grafo original é Simplesmente Conexo (C2). Se o grafo for C1 mas não C2, ele permanece classificado como C1.
     */
    C2,

    /**
     * C1 - Fracamente Conexo
     * <br><br>
     * Um grafo é fracamente conexo se o seu "grafo subjacente" não direcionado for conexo. Ou seja, se ignorarmos a direção de todas as setas e tratarmos as arestas como vias de mão dupla, o grafo resultante seria conexo.
     * <br><br>
     * Condição: Se a busca visitar todos os vértices, o grafo é Fracamente Conexo (C1). A partir daqui, seria necessário um teste mais complexo para diferenciá-lo de um C2. Se a busca não visitar todos os vértices, o grafo é C0.
     */
    C1,

    /**
     * C0 - Desconexo
     * <br><br>
     * Um grafo é desconexo se ele não for nem mesmo fracamente conexo. Se ignorarmos as setas, o grafo ainda assim estaria dividido em múltiplas partes.
     * <br><br>
     * Condição: Se um grafo falha no teste para C1, significa que mesmo ignorando as direções das arestas, ele ainda está em pedaços separados. Portanto, ele é classificado como Desconexo (C0).
     */
    C0

}
