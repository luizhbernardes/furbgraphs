package base;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe Vertice
 *
 * @author Maicon Rafael Zatelli
 *
 */
public class Vertice implements Cloneable {

    private final int id;
    private Object dado;
    private Color cor = new Color(0, 0, 0);
    private final ArrayList<Aresta> arestas = new ArrayList<Aresta>();
    private final HashMap<Integer, Aresta> idArestas = new HashMap<Integer, Aresta>();
    public Vertice vertice_pai = null;
    public Aresta aresta_pai = null;
    //Custo total até o vertice
    public Double custo_total = 0.0;

    /**
     * Construtor da classe Vertice
     *
     * @param id Identificador único do vértice
     * @throws Exception
     */
    public Vertice(int id) {
        this.id = id;
        dado = id;
    }

    /**
     * Retorna o identificador único do vértice
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o dado do vértice
     *
     * @return dado
     */
    public Object getDado() {
        return dado;
    }

    /**
     * Atribui um dado qualquer ao vértice
     *
     * @param dado
     */
    public void setDado(Object dado) {
        this.dado = dado;
    }

    /**
     * Retorna a cor do vértice
     *
     * @return cor
     */
    public Color getCor() {
        return cor;
    }

    /**
     * Atribui uma cor ao vértice
     *
     * @param cor
     */
    public void setCor(Color cor) {
        this.cor = cor;
    }

    /**
     * Adiciona uma aresta ao vértice
     *
     * @param a
     *            Aresta
     */
    public void addAresta(Aresta a) {
        idArestas.put(a.getId(), a);
        arestas.add(a);
    }

    /**
     * Apaga uma aresta do vértice
     *
     * @param a
     *            Aresta
     */
    public void delAresta(Aresta a) {
        while (arestas.contains(a)) {
            arestas.remove(a);
        }
        idArestas.remove(a.getId());
    }

    /**
     * Retorna uma aresta pela sua posição
     *
     * @param i
     *            Posição
     * @return Aresta
     */
    public Aresta getAresta(int i) {
        return arestas.get(i);
    }

    /**
     * Retorna a quantidade de arestas
     *
     * @return grau
     */
    public int getQtdeArestas() {
        return arestas.size();
    }

    /**
     * @author Luiz Henrique bernardes
     *         Retorna uma lista das arestas ligadas ao vertice
     *
     * @return temp_list
     */
    public List<Aresta> getArestaList() {
        return arestas;
        /*List<Aresta> temp_list = new ArrayList<>();
        int qtd_aresta = this.getQtdeArestas();
        for (int x = 0; x < qtd_aresta; x++) {
            temp_list.add(this.getAresta(x));
        }
        return temp_list;*/
    }

    /**
     * @author Luiz Henrique Bernardes
     * @param custo
     */
    public void set_custo(Double custo) {
        this.custo_total = custo;
    }

    /**
     * @author Luiz Henrique Bernardes
     * @return custo_total
     */
    public Double get_custo() {
        return this.custo_total;
    }

    /**
     * @author Luiz Henrique Bernardes
     * @return
     */
    public List<Vertice> get_caminho() {
        List<Vertice> temp_list = new ArrayList();
        Vertice temp_v = this.vertice_pai;

        while (temp_v != null) {
            temp_list.add(temp_v);
            temp_v = temp_v.vertice_pai;
        }
        return temp_list;
    }

    /**
     * @author Luiz Henrique Bernardes
     * @param v
     * @param a
     */
    public void setPai(Vertice v, Aresta a) {
        this.vertice_pai = v;
        this.aresta_pai = a;
    }

    /**
     * Limpa as arestas do vértice
     */
    public void limpaArestas() {
        arestas.clear();
        idArestas.clear();
    }

    /**
     * Compara o vértice
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return getId() == ((Vertice) obj).getId();
    }

    /**
     * Retorna uma String descrevendo o objeto vértice
     */
    @Override
    public String toString() {
        return id + " D: " + dado;
    }

    /**
     * Retorna um clone do vertice
     *
     * @return Vertice
     */
    @Override
    public Vertice clone() {
        Vertice v = null;
        try {
            v = new Vertice(id);
        } catch (Exception e) {} // FIXME
        v.setCor(cor);
        v.setDado(dado);

        return v;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
