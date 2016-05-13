package aplicacao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import algoritmos.AlgoritmoBellmanFord;
import algoritmos.AlgoritmoBellmanFordResultado;
import algoritmos.AlgoritmoBuscaLargura;
import algoritmos.AlgoritmoBuscaLarguraResultado;
import algoritmos.AlgoritmoBuscaProfundidade;
import algoritmos.AlgoritmoBuscaProfundidadeResultado;
import algoritmos.AlgoritmoCaminhoEuleriano;
import algoritmos.AlgoritmoCaminhoEulerianoResultado;
import algoritmos.AlgoritmoCicloHamiltoniano;
import algoritmos.AlgoritmoCicloHamiltonianoResultado;
import algoritmos.AlgoritmoDijkstra;
import algoritmos.AlgoritmoDijkstraResultado;
import algoritmos.AlgoritmoFloydWarshall;
import algoritmos.AlgoritmoFloydWarshallResultado;
import algoritmos.AlgoritmoFordFulkerson;
import algoritmos.AlgoritmoFordFulkersonResultado;
import algoritmos.AlgoritmoHopcroftTarjan;
import algoritmos.AlgoritmoHopcroftTarjanResultado;
import algoritmos.AlgoritmoKruskal;
import algoritmos.AlgoritmoKruskalResultado;
import algoritmos.AlgoritmoOrdenacaoTopologica;
import algoritmos.AlgoritmoOrdenacaoTopologicaResultado;
import algoritmos.AlgoritmoPontes;
import algoritmos.AlgoritmoPontesResultado;
import algoritmos.AlgoritmoPrim;
import algoritmos.AlgoritmoPrimResultado;
import base.Aresta;
import base.ArestaDirigida;
import base.ArestaNaoDirigida;
import base.Grafo;
import base.GrafoDirigido;
import base.GrafoNaoDirigido;
import base.GrafoNaoDirigido.PropriedadeIsomorfismoResultado;
import base.Vertice;
import persistencia.DefinicaoGrafoVisual;
import persistencia.PersistenciaVisual;

public class GraphViewer extends JComponent {

    private static final int WIDTH = 900;
    private static final int HIGH = 480;
    private static final int RADIUS = 18;
    private final ControlPanel control = new ControlPanel();
    private int radius = RADIUS;
    private List<VerticeVisual> vertexs = new ArrayList<VerticeVisual>();
    private final List<VerticeVisual> selected = new ArrayList<VerticeVisual>();
    private List<ArestaVisual> edges = new ArrayList<ArestaVisual>();
    private Point mousePoint = new Point(WIDTH / 2, HIGH / 2); // FIXME desnecessário inicializar?
    private final Rectangle mouseRect = new Rectangle();
    private boolean selecting = false;

    private JButton btnDeleteEdge;
    private JButton btnDeleteVertex;
    private JButton btnConectar;
    private JLabel labelValorVertice;
    private JLabel labelValorAresta;
    private JLabel labelDirecao;
    private JScrollPane scrollPane, scrollPane2;
    private JLabel vertexNameLabel;
    private JLabel labelNameAresta;
    private JTextField vertexValueField;
    private JTextField vertexNameField;
    private JTextField nameArestaField;
    private JTextField edgeValueField;
    private JComboBox<String> direcaoComboField;
    private JLabel statusLabel, debugLabel, espacoLabel, espacoLabel2;
    private JSpinner jspinner;
    public JTextArea txtAlg;
    public JTextArea txtLog;
    public ResultadoFrame algoritmoFrame = null;
    public ResultadoFrame algoritmoLog = null;

    private static AlgoritmoDesenho algoritmoDesenho = new AlgoritmoDesenho();
    private static IAlgoritmoExecutor algoritmoExecutor;

    public List<Vertice> vertice_aux_list = new ArrayList();
    public List<Aresta> aresta_aux_list = new ArrayList();

    public List<Vertice> vertice_list = new ArrayList();
    public List<Aresta> aresta_list = new ArrayList();

    public List<Vertice> vertice_menor_caminho_list = new ArrayList();
    public List<Aresta> aresta_menor_caminho_list = new ArrayList();

    public List<Vertice> vertice_proc_list = new ArrayList();
    public List<Aresta> aresta_proc_list = new ArrayList();

    public List<Vertice> vertice_cmp_pai_atual_list = new ArrayList();
    public List<Aresta> aresta_cmp_pai_atual_list = new ArrayList();

    public List<Vertice> vertice_cmp_pai_novo_list = new ArrayList();
    public List<Aresta> aresta_cmp_pai_novo_list = new ArrayList();

    public List<Vertice> vertice_restricao_list = new ArrayList();
    public List<Aresta> aresta_restricao_list = new ArrayList();

    public List<Vertice> vertice_ordem_list = new ArrayList();

    public List<Vertice> vertice_black_list = new ArrayList();

    public List<String> fila_bfs_list = new ArrayList();

    public Stack<Vertice> vertice_aux_stack = new Stack();
    public Stack<Vertice> vertice_proc_stack = new Stack();

    public Stack<String> empilhamento_dfs_stack = new Stack();

    public Stack<Vertice> vertice_ordem_visita_stack = new Stack();

    public Stack<Vertice> vertice_remove_stack = new Stack();

    public Vertice vertice_aux = null;
    public Vertice vertice_anterior = null;
    public Vertice vertice_remove = null;
    public Vertice vertice_origem = null;
    public Vertice vertice_destino = null;

    public int index_aresta, index_vertice = 0;
    public boolean BFS = false;
    public boolean DFS = false;
    public boolean DIJKSTRA = false;
    public boolean debugAtivo = false;
    protected JLabel processadoLabel;
    protected JLabel caminhoNovoLabel;
    protected JLabel caminhoAntigoLabel;
    protected JLabel naoUtilizadoLabel;
    protected JLabel caminhoMinimoLabel;
    private boolean check_menor_caminho;
    public String algoritmoStr;

    private static class AlgoritmoDesenho {

        Set<VerticeVisual> verticesMarcados = new LinkedHashSet<VerticeVisual>();
        Color corVerticesMarcados;
        Map<VerticeVisual, Color> coresVertices = new HashMap<VerticeVisual, Color>();

        Set<ArestaVisual> arestasMarcadas = new LinkedHashSet<ArestaVisual>();
        Color corArestasMarcadas;
        Map<ArestaVisual, Color> coresArestas = new HashMap<ArestaVisual, Color>();

        public AlgoritmoDesenho() {
            reset();
        }

        public void reset() {
            verticesMarcados.clear();
            coresVertices.clear();
            arestasMarcadas.clear();
            coresArestas.clear();
            corVerticesMarcados = Color.green;
            corArestasMarcadas = Color.green;
        }

    }

    private static class ResultadoFrame extends JFrame {

        public String valor = "";
        public JTextArea txtArea;

        public ResultadoFrame(String titulo, String resultado) {
            setTitle(titulo);
            setSize(500, 350);
            valor = resultado;
            txtArea = new JTextArea();
            txtArea.setText(valor);
            add(new JScrollPane(txtArea));
            //  pack();
            setLocationRelativeTo(null);
            setVisible(true);
        }

    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame f = new JFrame("FURB Graphs: uma aplicação para teoria dos grafos");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                final GraphViewer gp = new GraphViewer();
                f.add(gp.control, BorderLayout.NORTH);
                gp.scrollPane = new JScrollPane(gp);

                JPanel principal = new JPanel(new GridLayout(1, 2));
                JPanel logPrincipal = new JPanel(new GridLayout(2, 1));

                JPanel logPane = new JPanel();
                JPanel algPane = new JPanel();

                principal.add(gp.scrollPane);

                gp.txtLog = new JTextArea(20, 100);
                gp.txtAlg = new JTextArea(20, 100);

                JScrollPane scrollLog = new JScrollPane(gp.txtLog);
                JScrollPane scrollAlg = new JScrollPane(gp.txtAlg);

                logPane.add(scrollLog);
                algPane.add(scrollAlg);

                logPrincipal.add(logPane);
                logPrincipal.add(algPane);

                principal.add(logPrincipal);

                gp.addKeyShortcut();

                f.add(principal);

                JPanel statusPanel = new JPanel();
                statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
                f.add(statusPanel, BorderLayout.SOUTH);

                statusPanel.setPreferredSize(new Dimension(f.getWidth(), 30));
                statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));

                //LUIZ - Criação das labels e botões para controle do DEBUG!!
                gp.espacoLabel = new JLabel("     |     ");
                gp.espacoLabel.setHorizontalAlignment(SwingConstants.LEFT);

                gp.debugLabel = new JLabel("");
                gp.debugLabel.setHorizontalAlignment(SwingConstants.LEFT);

                gp.espacoLabel2 = new JLabel("     |     ");
                gp.espacoLabel2.setHorizontalAlignment(SwingConstants.LEFT);

                gp.statusLabel = new JLabel("                                                                       ");
                gp.statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                gp.updateFooter();
                StringBuilder teste = new StringBuilder();
                teste.append("teste");

                for (String str : " << , < , > ".split(",")) {
                    JButton bt = new JButton(str);
                    bt.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JButton btCaminhar = (JButton) e.getSource();
                            gp.debug_caminhar(btCaminhar);
                        }
                    });
                    statusPanel.add(bt);
                }

                statusPanel.add(gp.espacoLabel);
                statusPanel.add(gp.debugLabel);
                statusPanel.add(gp.espacoLabel2);
                statusPanel.add(gp.statusLabel);

                JMenuBar menuBar = new JMenuBar();
                {
                    //Build the first menu.
                    JMenu menu = new JMenu("Arquivo");
                    //    menu.setMnemonic(KeyEvent.VK_A);
                    //    menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");

                    JMenuItem menuItemSalvarGrafo = new JMenuItem("Salvar grafo...", KeyEvent.VK_S);
                    //    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
                    //    menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
                    menuItemSalvarGrafo.addActionListener(gp.new SalvarGrafoAction());
                    JMenuItem menuItemCarregarGrafo = new JMenuItem("Carregar grafo...", KeyEvent.VK_C);
                    //    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
                    //    menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
                    menuItemCarregarGrafo.addActionListener(gp.new CarregarGrafoAction());

                    menu.add(menuItemSalvarGrafo);
                    menu.add(menuItemCarregarGrafo);

                    menuBar.add(menu);
                }
                {
                    gp.menuPropriedades(menuBar);
                    gp.menuInfoDidatico(menuBar);
                    gp.menuAlgoritmos(menuBar);
                    gp.menuSobre(menuBar);
                }
                f.setJMenuBar(menuBar);

                f.pack();
                // f.setLocationByPlatform(true);
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            }

        });
    }

    private void debug_caminhar(JButton btCaminhar) {
        if (btCaminhar.getText().equals(" << ")) {
            this.retorna();
        } else if (btCaminhar.getText().equals(" > ")) {
            this.debugLabel.setText("Atenção!! Avançou 1 passo do algoritmo >");
            if (this.DIJKSTRA) {
                this.avanca_dijkstra();
            } else if (this.BFS) {
                this.avanca_bfs();
            } else if (this.DFS) {
                this.avanca_dfs();
            }
        } else {
            if (this.DIJKSTRA) {
                this.retorna_dijkstra();
            } else if (this.BFS) {
                this.retorna_bfs();
            } else if (this.DFS) {
                this.retorna_dfs();
            }
        }
        this.repaint();
    }

    public void retorna() {
        this.debugLabel.setText("Atenção!! Voltou todos os passos do algoritmo <<");

        this.vertice_aux_list.clear();
        this.aresta_aux_list.clear();
        this.vertice_proc_list.clear();
        this.aresta_proc_list.clear();
        this.vertice_cmp_pai_atual_list.clear();
        this.aresta_cmp_pai_atual_list.clear();
        this.vertice_cmp_pai_novo_list.clear();
        this.aresta_cmp_pai_novo_list.clear();
        this.vertice_restricao_list.clear();
        this.aresta_restricao_list.clear();
        this.vertice_black_list.clear();
        this.vertice_ordem_list.clear();
        this.fila_bfs_list.clear();

        this.vertice_aux_stack.clear();
        this.vertice_ordem_visita_stack.clear();
        this.vertice_remove_stack.clear();
        this.empilhamento_dfs_stack.clear();

        for (Vertice v : this.g().getVerticeList()) {
            this.algoritmoDesenho.verticesMarcados.remove(findVerticeVisual(v));
            this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(v));
            if (v.aresta_pai != null) {
                this.algoritmoDesenho.arestasMarcadas.remove(findArestaVisual(v.aresta_pai));
                this.algoritmoDesenho.coresArestas.remove(findArestaVisual(v.aresta_pai));
                findArestaVisual(v.aresta_pai).name = "";
            }
            findVerticeVisual(v).value = "";
            v.setPai(null, null);
            v.set_custo(0.0);
            this.repaint();
        }

        this.algoritmoDesenho.verticesMarcados.clear();
        this.algoritmoDesenho.arestasMarcadas.clear();
        this.algoritmoDesenho.coresVertices.clear();
        this.algoritmoDesenho.coresArestas.clear();

        this.vertice_remove = null;
        this.vertice_anterior = null;
        this.check_menor_caminho = false;
        if (this.DIJKSTRA) {
            this.vertice_aux = null;
        } else {
            this.vertice_aux = this.vertice_origem;
        }
    }

    public void retorna_bfs() {
        this.debugLabel.setText("Atenção!! Voltou 1 passo do algoritmo <");

        if (this.vertice_remove != null) {
            Vertice dest = this.vertice_remove;

            if (dest.equals(this.vertice_origem)) {
                System.out.println("REMOVENDO > " + dest);
                this.algoritmoDesenho.verticesMarcados.remove(findVerticeVisual(dest));
                this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(dest));
            }

            int count = 0;
            for (Aresta a : dest.getArestaList()) {
                Vertice v_destino = (a.getVi() == dest ? a.getVj() : a.getVi());

                if (this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(v_destino)) && v_destino.vertice_pai == dest) {
                    System.out.println("REMOVENDO FILHOS > " + v_destino);
                    this.algoritmoDesenho.verticesMarcados.remove(findVerticeVisual(v_destino));
                    this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(v_destino));
                    if (v_destino.aresta_pai != null) {
                        this.algoritmoDesenho.arestasMarcadas.remove(findArestaVisual(v_destino.aresta_pai));
                        this.algoritmoDesenho.coresArestas.remove(findArestaVisual(v_destino.aresta_pai));
                        findArestaVisual(v_destino.aresta_pai).name = "";
                    }
                }
            }

            for (Aresta a : dest.getArestaList()) {
                Vertice v_destino = (a.getVi() == dest ? a.getVj() : a.getVi());

                if (this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(v_destino))) {
                    count += 1;
                }
            }
            if (count == dest.getQtdeArestas()) {
                this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(dest));
                this.algoritmoDesenho.coresVertices.put(findVerticeVisual(dest), Color.black);
            } else if (!dest.equals(this.vertice_origem)) {
                this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(dest));
                this.algoritmoDesenho.coresVertices.put(findVerticeVisual(dest), Color.gray);
            }

            count = 0;
            for (Aresta a : dest.getArestaList()) {
                count = 0;

                Vertice v_destino = (a.getVi() == dest ? a.getVj() : a.getVi());
                System.out.println("REPINTAR O VERTICE " + v_destino);
                if (this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(v_destino))) {
                    for (Aresta aux : v_destino.getArestaList()) {
                        Vertice v = (aux.getVi() == v_destino ? aux.getVj() : aux.getVi());
                        if (this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(v))) {
                            count += 1;
                        }
                    }
                    if (count != v_destino.getQtdeArestas() && !v_destino.equals(this.vertice_origem)) {
                        System.out.println("PINTOU DE CINZA");
                        this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(v_destino));
                        this.algoritmoDesenho.coresVertices.put(findVerticeVisual(v_destino), Color.gray);
                    }
                }
            }

            this.repaint();

            this.vertice_proc_list.clear();
            this.vertice_aux_list.clear();
            this.vertice_black_list.clear();
            this.vertice_restricao_list.clear();

            this.vertice_remove = null;

        } else if (!this.vertice_remove_stack.isEmpty()) {
            Vertice dest = this.vertice_remove_stack.pop();

            this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(dest));
            this.algoritmoDesenho.coresVertices.put(findVerticeVisual(dest), Color.orange);

            this.vertice_aux = dest.vertice_pai;

            this.vertice_remove = dest;
        }
    }

    public void retorna_dfs() {
        this.debugLabel.setText("Atenção!! Voltou 1 passo do algoritmo <");
        if (this.vertice_remove != null) {
            Vertice dest = this.vertice_remove;

            System.out.println("VOLTANDO > " + dest);
            if (this.vertice_black_list.contains(dest)) {
                System.out.println("COLORE DE CINZA >> " + dest);
                this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(dest));
                this.algoritmoDesenho.coresVertices.put(findVerticeVisual(dest), Color.gray);
                String dados_pai = dest.temp_abertura + "/null";
                findVerticeVisual(dest).value = dados_pai;

                this.vertice_black_list.remove(dest);
                this.empilhamento_dfs_stack.push((String) dest.getDado());
            } else {
                System.out.println("REMOVE >> " + dest);
                this.algoritmoDesenho.verticesMarcados.remove(findVerticeVisual(dest));
                this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(dest));

                findVerticeVisual(dest).value = "";
                if (dest.aresta_pai != null) {
                    this.algoritmoDesenho.arestasMarcadas.remove(findArestaVisual(dest.aresta_pai));
                    this.algoritmoDesenho.coresArestas.remove(findArestaVisual(dest.aresta_pai));
                    //this.vertice_aux = (dest.aresta_pai.getVi() == dest ? dest.aresta_pai.getVj() : dest.aresta_pai.getVi());
                }
                this.empilhamento_dfs_stack.remove(dest.getDado());
            }

            this.repaint();

            this.vertice_proc_list.clear();
            this.vertice_aux_list.clear();
            this.vertice_restricao_list.clear();

            this.vertice_remove = null;

        } else if (!this.vertice_ordem_visita_stack.isEmpty()) {
            Vertice dest = this.vertice_ordem_visita_stack.pop();

            this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(dest));
            this.algoritmoDesenho.coresVertices.put(findVerticeVisual(dest), Color.orange);

            this.vertice_remove = dest;

            if (dest.vertice_pai != null) {
                this.vertice_aux = dest.vertice_pai;
            } else {
                this.vertice_aux = this.vertice_origem;
            }
        }
    }

    public void retorna_dijkstra() {
        this.debugLabel.setText("Atenção!! Voltou 1 passo do algoritmo <");
        Vertice dest = this.vertice_aux;

        if (this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(this.vertice_aux))) {
            this.algoritmoDesenho.verticesMarcados.remove(findVerticeVisual(this.vertice_aux));
            this.algoritmoDesenho.arestasMarcadas.remove(findArestaVisual(this.vertice_aux.aresta_pai));
            this.algoritmoDesenho.coresArestas.remove(findArestaVisual(this.vertice_aux.aresta_pai));
            this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(this.vertice_aux));
        }

        this.repaint();

        this.vertice_proc_list.clear();
        this.aresta_proc_list.clear();
        this.vertice_cmp_pai_atual_list.clear();
        this.aresta_cmp_pai_atual_list.clear();
        this.vertice_cmp_pai_novo_list.clear();
        this.aresta_cmp_pai_novo_list.clear();

        this.vertice_aux.setPai(null, null);
        this.vertice_aux.set_custo(0.0);

        this.vertice_aux = dest.vertice_pai;
    }

    /**
     * @author Luiz Henrique Bernardes
     */
    public void avanca_dfs() {

        if (this.vertice_aux == null) {
            this.vertice_aux = this.vertice_origem;
            this.vertice_aux_stack.clear();
        } else {
            this.txtLog.append("\n");
        }

        if (!this.vertice_aux_stack.isEmpty() && this.vertice_destino == null && !this.vertice_black_list.contains(this.vertice_aux)) {
            Vertice v_destino = this.vertice_aux_stack.pop();

            this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(this.vertice_aux));
            this.algoritmoDesenho.coresVertices.put(findVerticeVisual(this.vertice_aux), Color.gray);

            if (this.vertice_ordem_visita_stack.isEmpty()) {
                this.vertice_ordem_visita_stack.push(this.vertice_aux);
            }

            int count = 0;
            for (Aresta a : this.vertice_aux.getArestaList()) {
                Vertice v_aux = (a.getVi() == this.vertice_aux ? a.getVj() : a.getVi());
                if (this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(v_aux))) {
                    count += 1;
                }
            }
            if (count == this.vertice_aux.getQtdeArestas()) {
                this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(this.vertice_aux));
                this.algoritmoDesenho.coresVertices.put(findVerticeVisual(this.vertice_aux), Color.black);

                this.txtLog.append("DESEMPILHA VERTICE > " + this.vertice_aux.getDado() + "\n");
                this.empilhamento_dfs_stack.remove(this.vertice_aux.getDado());

                String dados_pai = this.vertice_aux.temp_abertura + "/" + this.vertice_aux.temp_fechamento;
                findVerticeVisual(this.vertice_aux).value = dados_pai;
                this.vertice_destino = this.vertice_aux.vertice_pai;
                this.vertice_black_list.add(this.vertice_aux);

                this.vertice_ordem_visita_stack.push(this.vertice_aux);

                this.txtLog.append("EMPILHAMENTO > " + this.empilhamento_dfs_stack + "\n");
            } else {
                this.txtLog.append("VISITANDO VERTICE > " + v_destino.getDado() + "\n");

                String dados_pai = this.vertice_aux.temp_abertura + "/null";
                findVerticeVisual(this.vertice_aux).value = dados_pai;

                this.vertice_ordem_visita_stack.push(v_destino);
            }

            this.repaint();

            //Pega o próximo vertice
            if (this.vertice_destino == null && !this.vertice_black_list.contains(v_destino)) {
                this.algoritmoDesenho.verticesMarcados.add(findVerticeVisual(v_destino));
                this.algoritmoDesenho.coresVertices.put(findVerticeVisual(v_destino), Color.gray);

                if (v_destino.aresta_pai != null) {
                    this.algoritmoDesenho.arestasMarcadas.add(findArestaVisual(v_destino.aresta_pai));
                    this.algoritmoDesenho.coresArestas.put(findArestaVisual(v_destino.aresta_pai), Color.orange);

                    String dados_filho = v_destino.temp_abertura + "/null";
                    findVerticeVisual(v_destino).value = dados_filho;
                }

                this.vertice_aux = v_destino;
                this.vertice_destino = v_destino;
            } else {
                this.vertice_aux_stack.push(v_destino);
                this.vertice_aux = this.vertice_destino;
                this.vertice_destino = null;
            }
        } else if (!this.vertice_black_list.contains(this.vertice_aux)) {

            //Colore o vertice principal
            if (this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(this.vertice_aux))) {
                this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(this.vertice_aux));
                this.algoritmoDesenho.coresVertices.put(findVerticeVisual(this.vertice_aux), Color.orange);
            } else {
                this.algoritmoDesenho.verticesMarcados.add(findVerticeVisual(this.vertice_aux));
                this.algoritmoDesenho.coresVertices.put(findVerticeVisual(this.vertice_aux), Color.orange);
            }

            this.txtLog.append("PROCESSANDO VERTICE >>>> " + this.vertice_aux.getDado() + "\n");
            this.empilhamento_dfs_stack.push((String) this.vertice_aux.getDado());

            //Percorre as arestas do vertice principal
            for (Aresta a : this.vertice_aux.getArestaList()) {
                //Encontra o destino
                Vertice v_destino = (a.getVi() == this.vertice_aux ? a.getVj() : a.getVi());

                //Se o vertice destino não constar na lista de processamento
                if (!this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(v_destino))) {
                    //Adiciona o vertice a lista dos vertices processados
                    this.vertice_aux_list.add(v_destino);
                    //this.vertice_aux_stack.push(v_destino);
                    v_destino.setPai(this.vertice_aux, a);
                }
            }

            for (int x = this.vertice_aux_list.size(); x > 0; x--) {
                this.vertice_aux_stack.push(this.vertice_aux_list.get(x - 1));
            }
            if (!this.vertice_aux_list.isEmpty()) {
                this.txtLog.append("LISTA DE VERTICES:\n");
                for (Vertice v : this.vertice_aux_list) {
                    this.txtLog.append(">>> " + v.getDado() + "\n");
                }
            }

            this.vertice_aux_list.clear();

            this.txtLog.append("EMPILHAMENTO > " + this.empilhamento_dfs_stack + "\n");
            this.vertice_destino = null;
        }
    }

    /**
     * @author Luiz Henrique Bernardes
     */
    public void avanca_bfs() {
        if (this.vertice_aux == null) {
            this.vertice_aux = this.vertice_origem;
        } else {
            this.txtLog.append("\n");
        }

        if (this.vertice_remove != null) {
            int count = 0;
            for (Aresta a : this.vertice_remove.getArestaList()) {
                Vertice v_destino = (a.getVi() == this.vertice_remove ? a.getVj() : a.getVi());
                if (this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(v_destino))) {
                    count += 1;
                }
            }
            if (count == this.vertice_remove.getQtdeArestas()) {
                this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(this.vertice_remove));
                this.algoritmoDesenho.coresVertices.put(findVerticeVisual(this.vertice_remove), Color.black);
            } else {
                this.algoritmoDesenho.verticesMarcados.add(findVerticeVisual(this.vertice_remove));
                this.algoritmoDesenho.coresVertices.put(findVerticeVisual(this.vertice_remove), Color.gray);
            }
            this.vertice_remove = null;
        }

        if (!this.vertice_proc_stack.isEmpty()) {
            this.algoritmoDesenho.verticesMarcados.add(findVerticeVisual(this.vertice_aux));
            this.algoritmoDesenho.coresVertices.put(findVerticeVisual(this.vertice_aux), Color.gray);

            Vertice v_aux = this.vertice_proc_stack.pop();

            this.txtLog.append("VISITANDO VERTICE > " + v_aux.getDado() + "\n");

            //Percorre a lista de destinos para colorir
            if (!this.vertice_aux_list.contains(v_aux) && !this.vertice_black_list.contains(v_aux)) {
                //Colore de laranja
                this.algoritmoDesenho.verticesMarcados.add(findVerticeVisual(v_aux));
                this.algoritmoDesenho.coresVertices.put(findVerticeVisual(v_aux), Color.gray);

                if (v_aux.aresta_pai != null) {
                    this.algoritmoDesenho.arestasMarcadas.add(findArestaVisual(v_aux.aresta_pai));
                    this.algoritmoDesenho.coresArestas.put(findArestaVisual(v_aux.aresta_pai), Color.orange);
                    findArestaVisual(v_aux.aresta_pai).name = "Nível " + v_aux.getNivelArvore();
                }

                if (!this.fila_bfs_list.contains(v_aux.getDado())) {
                    this.fila_bfs_list.add((String) v_aux.getDado());
                }
                this.txtLog.append("FILA >> " + this.fila_bfs_list + "\n");

                this.vertice_aux_list.add(v_aux);
            }

            this.vertice_proc_list.clear();

            int count = 0;
            for (Aresta a : this.vertice_aux.getArestaList()) {
                Vertice v_destino = (a.getVi() == this.vertice_aux ? a.getVj() : a.getVi());
                if (this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(v_destino))) {
                    count += 1;
                }
            }
            if (count == this.vertice_aux.getQtdeArestas()) {
                this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(this.vertice_aux));
                this.algoritmoDesenho.coresVertices.put(findVerticeVisual(this.vertice_aux), Color.black);
                this.vertice_black_list.add(this.vertice_aux);
                this.txtLog.append("\nVERTICE >>> " + this.vertice_aux.getDado() + " JÁ FOI EXPLORADO!\n");
                this.txtLog.append("REMOVENDO VERTICE >>> " + this.vertice_aux.getDado() + "\n");
                this.fila_bfs_list.remove(this.vertice_aux.getDado());
                this.txtLog.append("FILA >> " + this.fila_bfs_list + "\n");
            }

            this.repaint();
            if (this.vertice_proc_stack.isEmpty() && !vertice_aux_list.isEmpty()) {
                //Busca o próximo vertice
                Vertice prox_vertice = this.vertice_aux_list.get(0);

                //Pega o próximo vertice
                this.vertice_aux = prox_vertice;

                //Remove o próximo vertice da lista de processamento
                this.vertice_aux_list.remove(prox_vertice);
            }

        } else if (!this.vertice_black_list.contains(this.vertice_aux)) {
            if (this.vertice_anterior != null) {
                this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(this.vertice_anterior));
                this.algoritmoDesenho.coresVertices.put(findVerticeVisual(this.vertice_anterior), Color.black);
                this.txtLog.append("\nVERTICE >>> " + this.vertice_anterior.getDado() + " JÁ FOI EXPLORADO!\n");
                this.txtLog.append("REMOVENDO VERTICE >>> " + this.vertice_anterior.getDado() + "\n");
                this.fila_bfs_list.remove(this.vertice_anterior.getDado());
                this.txtLog.append("FILA >> " + this.fila_bfs_list + "\n\n");
                this.vertice_anterior = null;
            }

            this.txtLog.append("PROCESSANDO VERTICE >>> " + this.vertice_aux.getDado() + "\n");
            if (!this.fila_bfs_list.contains(this.vertice_aux.getDado())) {
                this.fila_bfs_list.add((String) this.vertice_aux.getDado());
            }

            //Colore o vertice principal
            if (this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(this.vertice_aux))) {
                this.vertice_remove_stack.push(this.vertice_aux);
                this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(this.vertice_aux));
                this.algoritmoDesenho.coresVertices.put(findVerticeVisual(this.vertice_aux), Color.orange);
            } else {
                this.vertice_remove_stack.push(this.vertice_aux);
                this.algoritmoDesenho.verticesMarcados.add(findVerticeVisual(this.vertice_aux));
                this.algoritmoDesenho.coresVertices.put(findVerticeVisual(this.vertice_aux), Color.orange);
            }

            //Percorre as arestas do vertice principal
            for (Aresta a : this.vertice_aux.getArestaList()) {
                //Encontra o destino
                Vertice v_destino = (a.getVi() == this.vertice_aux ? a.getVj() : a.getVi());

                //Se o vertice destino não constar na lista de processamento
                if (!this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(v_destino))) {
                    //Adiciona o vertice a lista dos vertices processados
                    this.vertice_proc_stack.push(v_destino);
                    v_destino.setPai(this.vertice_aux, a);
                }
            }

            if (this.vertice_proc_stack.isEmpty()) {
                if (this.vertice_aux_list.isEmpty()) {
                    //this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(this.vertice_aux));
                    //this.algoritmoDesenho.coresVertices.put(findVerticeVisual(this.vertice_aux), Color.black);
                    //this.txtLog.append("REMOVENDO VERTICE 1 >>> " + this.vertice_aux.getDado() + "\n");
                    this.vertice_proc_stack.push(this.vertice_aux);
                } else {
                    this.vertice_anterior = this.vertice_aux;

                    Vertice prox_vertice = this.vertice_aux_list.get(0);

                    //Pega o próximo vertice
                    this.vertice_aux = prox_vertice;

                    //Remove o próximo vertice da lista de processamento
                    this.vertice_aux_list.remove(prox_vertice);
                }
            } else {
                this.txtLog.append("VERTICES VIZINHOS:\n");
                for (Vertice v : this.vertice_proc_stack) {
                    this.txtLog.append(">> " + v.getDado() + "\n");
                }
            }
        }
    }

    /**
     * @author Luiz Henrique Bernardes
     *
     */
    public void avanca_dijkstra() {
        if (this.vertice_aux == null) {
            this.vertice_aux = this.vertice_origem;
            this.algoritmoDesenho.verticesMarcados.add(findVerticeVisual(this.vertice_aux));
            this.algoritmoDesenho.coresVertices.put(findVerticeVisual(this.vertice_aux), Color.orange);
            this.txtLog.append("#######################\nProcessando vertice > " + this.vertice_aux + "\n");
        }

        if (!this.aresta_proc_list.isEmpty()) {
            Aresta aux = this.aresta_proc_list.get(0);
            this.algoritmoDesenho.arestasMarcadas.add(findArestaVisual(aux));
            this.algoritmoDesenho.coresArestas.put(findArestaVisual(aux), Color.orange);
            this.aresta_proc_list.remove(0);
            this.check_menor_caminho = true;

            Vertice v_destino = (aux.getVi().equals(this.vertice_aux) ? aux.getVj() : aux.getVi());
            this.txtLog.append("Processando a aresta > " + aux.getDado() + " para > " + v_destino.getDado() + "\n");
            this.txtLog.append("A distância de " + this.vertice_origem.getDado() + " para " + v_destino.getDado() + " é infinita!\n");
            this.txtLog.append("O novo pai de " + v_destino.getDado() + " é " + this.vertice_aux.getDado() + "\n");

        } else if (!this.vertice_cmp_pai_atual_list.isEmpty() && !this.aresta_cmp_pai_atual_list.isEmpty()) {
            String str_caminho_antigo = "";
            for (Vertice v : this.vertice_cmp_pai_atual_list) {
                this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(v));
                this.algoritmoDesenho.coresVertices.put(findVerticeVisual(v), Color.blue);
            }
            for (Aresta a : this.aresta_cmp_pai_atual_list) {
                this.algoritmoDesenho.coresArestas.remove(findArestaVisual(a));
                this.algoritmoDesenho.coresArestas.put(findArestaVisual(a), Color.blue);
            }
            this.repaint();

            String str_v = "";
            for (Vertice v : this.vertice_cmp_pai_atual_list) {
                String str_caminho = "";
                if (!this.vertice_origem.equals(v)) {
                    for (Vertice v_aux : v.get_caminho()) {
                        str_caminho += " > " + v_aux.getDado();
                    }
                }
                str_v = this.vertice_origem.getDado() + str_caminho;
                for (Aresta a : this.aresta_cmp_pai_atual_list) {
                    if (a.getVi().equals(v) || a.getVj().equals(v)) {
                        Vertice v_destino = (a.getVi().equals(v) ? a.getVj() : a.getVi());
                        str_caminho_antigo += str_v + " > " + v_destino.getDado() + "\n";
                    }
                }
            }

            this.txtLog.append("Comparando o caminho antigo:\n" + str_caminho_antigo + "\n");

            this.vertice_cmp_pai_atual_list.clear();
            this.aresta_cmp_pai_atual_list.clear();
        } else if (!this.vertice_cmp_pai_novo_list.isEmpty() && !this.aresta_cmp_pai_novo_list.isEmpty()) {
            String str_caminho_novo = "";
            for (Vertice v : this.vertice_cmp_pai_novo_list) {
                this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(v));
                this.algoritmoDesenho.coresVertices.put(findVerticeVisual(v), Color.cyan);
            }
            for (Aresta a : this.aresta_cmp_pai_novo_list) {
                this.algoritmoDesenho.coresArestas.remove(findArestaVisual(a));
                this.algoritmoDesenho.coresArestas.put(findArestaVisual(a), Color.cyan);
            }
            this.repaint();

            String str_v = "";
            for (Vertice v : this.vertice_cmp_pai_novo_list) {
                String str_caminho = "";
                if (!this.vertice_origem.equals(v)) {
                    for (Vertice v_aux : v.get_caminho()) {
                        str_caminho += " > " + v_aux.getDado();
                    }
                }
                str_v = this.vertice_origem.getDado() + str_caminho;
                for (Aresta a : this.aresta_cmp_pai_novo_list) {
                    if (a.getVi().equals(v) || a.getVj().equals(v)) {
                        Vertice v_destino = (a.getVi().equals(v) ? a.getVj() : a.getVi());
                        if (this.vertice_cmp_pai_novo_list.contains(v_destino)) {
                            str_caminho_novo += str_v + " > " + v_destino.getDado() + "\n";
                        }
                    }
                }
            }

            this.txtLog.append("Caminho novo:\n" + str_caminho_novo + "\n");

            this.vertice_cmp_pai_novo_list.clear();
            this.aresta_cmp_pai_novo_list.clear();
        } else if (this.check_menor_caminho) {
            String str_menor_caminho = "";
            this.txtLog.append("O menor caminho encontrado por vértice:\n");
            for (Vertice v : this.g().getVerticeList()) {
                if (this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(v))) {
                    this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(v));
                    this.algoritmoDesenho.coresVertices.put(findVerticeVisual(v), Color.green);

                    if (!v.equals(this.vertice_origem)) {
                        String str_caminho_novo = "";
                        for (Vertice v_aux : v.get_caminho()) {
                            str_caminho_novo += " > " + v_aux.getDado();
                        }
                        str_menor_caminho += "Caminho de " + this.vertice_origem.getDado() + " para " + v.getDado() + "" + str_caminho_novo + "\n";
                    }

                    for (Aresta a : v.getArestaList()) {
                        Vertice v_destino = (a.getVi().equals(v) ? a.getVj() : a.getVi());
                        if (this.algoritmoDesenho.arestasMarcadas.contains(findArestaVisual(a))) {
                            if (this.aresta_list.contains(a)) {
                                this.algoritmoDesenho.coresArestas.remove(findArestaVisual(a));
                                this.algoritmoDesenho.coresArestas.put(findArestaVisual(a), Color.green);
                            }
                        }
                        if (this.aresta_restricao_list.contains(a)) {
                            this.algoritmoDesenho.coresArestas.remove(findArestaVisual(a));
                            this.algoritmoDesenho.coresArestas.put(findArestaVisual(a), Color.red);
                        }
                    }
                }
            }
            this.txtLog.append(str_menor_caminho);
            this.check_menor_caminho = false;
        } else {

            this.check_menor_caminho = true;
            if (!this.vertice_ordem_list.isEmpty()) {
                this.vertice_aux = null;
                for (Vertice v : this.vertice_ordem_list) {
                    if (this.vertice_aux == null) {
                        this.vertice_aux = v;
                    } else if (v.get_custo() < this.vertice_aux.get_custo()) {
                        this.vertice_aux = v;
                    }
                }
                this.vertice_ordem_list.remove(this.vertice_aux);

                this.txtLog.append("#######################\nProcessando vertice > " + this.vertice_aux.getDado() + "\n");

                if (this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(this.vertice_aux))) {
                    this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(this.vertice_aux));
                    this.algoritmoDesenho.coresVertices.put(findVerticeVisual(this.vertice_aux), Color.orange);
                } else {
                    this.algoritmoDesenho.verticesMarcados.add(findVerticeVisual(this.vertice_aux));
                    this.algoritmoDesenho.coresVertices.put(findVerticeVisual(this.vertice_aux), Color.orange);
                }
            }
            for (Vertice v : this.g().getVerticeList()) {
                if (this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(v))) {
                    for (Aresta a : v.getArestaList()) {
                        if (this.algoritmoDesenho.arestasMarcadas.contains(findArestaVisual(a))) {
                            if (!this.aresta_list.contains(a) && this.vertice_aux.equals(v)) {
                                this.algoritmoDesenho.coresArestas.remove(findArestaVisual(a));
                                this.algoritmoDesenho.coresArestas.put(findArestaVisual(a), Color.red);
                                if (!this.aresta_restricao_list.contains(a)) {
                                    this.txtLog.append("Inutilizando aresta > " + a.getDado() + "\n");
                                    this.aresta_restricao_list.add(a);
                                }
                            }
                        }
                    }
                }
            }
            this.repaint();

            if (!this.vertice_aux.equals(this.vertice_origem)) {
                this.vertice_proc_stack.push(this.vertice_aux);
            }

            for (Aresta a : this.vertice_aux.getArestaList()) {
                if (!this.algoritmoDesenho.arestasMarcadas.contains(findArestaVisual(a))) {
                    this.aresta_proc_list.add(a);

                    Vertice v_destino = (a.getVi().equals(this.vertice_aux) ? a.getVj() : a.getVi());

                    if (!this.vertice_list.contains(v_destino)) {
                        this.vertice_list.add(v_destino);
                    }
                    this.vertice_proc_stack.push(v_destino);

                    //Se não for o vertice de origem
                    if (!v_destino.equals(this.vertice_origem)) {

                        //Se não é o vertice de origem e o pai é nulo
                        //então o vertice não tem pai
                        if (v_destino.vertice_pai == null) {

                            //this.txtLog.append("Vertice " + v_destino.getDado() + " não possui pai e a distância até ele é infinita!\n");

                            //Se não tem pai, adiciona o vertice atual como pai
                            v_destino.setPai(this.vertice_aux, a);

                            //Seta o custo como Custo da aresta + Custo do pai
                            v_destino.set_custo(a.getValor() + this.vertice_aux.get_custo());

                            //this.txtLog.append("Novo pai de " + v_destino.getDado() + " é " + this.vertice_aux.getDado() + " | Distância: " + v_destino.get_custo() + "\n");

                            //Percorre o caminho de vertices do pai novo até a origem
                            this.aresta_cmp_pai_novo_list.add(v_destino.aresta_pai);
                            if (!this.aresta_list.contains(v_destino.aresta_pai)) {
                                this.aresta_list.add(v_destino.aresta_pai);
                            }

                            //Percorre o caminho de vertices do pai novo até a origem
                            for (Vertice v : v_destino.get_caminho()) {
                                if (!this.vertice_cmp_pai_novo_list.contains(v)) {

                                    this.vertice_cmp_pai_novo_list.add(v);
                                    if (v.aresta_pai != null && !this.aresta_cmp_pai_novo_list.contains(v.aresta_pai) && this.algoritmoDesenho.arestasMarcadas.contains(findArestaVisual(v.aresta_pai))) {
                                        this.aresta_cmp_pai_novo_list.add(v.aresta_pai);
                                    }
                                }
                            }
                        } else {
                            //Se o pai não é nulo, ele tem pai
                            //Fazer comparação

                            //this.txtLog.append("O vertice " + v_destino.getDado() + " já tem um pai > " + v_destino.vertice_pai.getDado() + "\n");

                            Vertice pai_atual = v_destino.vertice_pai;

                            if (!this.aresta_cmp_pai_novo_list.contains(v_destino.aresta_pai)) {
                                this.aresta_cmp_pai_atual_list.add(v_destino.aresta_pai);
                            }

                            for (Vertice v : v_destino.get_caminho()) {
                                if (!this.vertice_cmp_pai_atual_list.contains(v) && !this.vertice_cmp_pai_novo_list.contains(v)) {
                                    this.vertice_cmp_pai_atual_list.add(v);
                                    if (v.aresta_pai != null && !this.aresta_cmp_pai_atual_list.contains(v.aresta_pai) && this.algoritmoDesenho.arestasMarcadas.contains(findArestaVisual(v.aresta_pai))) {
                                        this.aresta_cmp_pai_atual_list.add(v.aresta_pai);
                                    }
                                }
                            }

                            String str_caminho_novo = "";
                            for (Vertice v_aux : this.vertice_aux.get_caminho()) {
                                str_caminho_novo += " > " + v_aux.getDado();
                            }
                            String str_caminho_antigo = "";
                            for (Vertice v_aux : v_destino.get_caminho()) {
                                str_caminho_antigo += " > " + v_aux.getDado();
                            }
                            //this.txtLog.append("O caminho " + str_caminho_novo + " > " + v_destino.getDado() + " (Custo = " + (this.vertice_aux.get_custo() + a.getValor()) + ") é menor que o caminho " + str_caminho_antigo + " > " + v_destino.getDado() + " (Custo = " + v_destino.get_custo() + ")??\n");

                            if ((this.vertice_aux.get_custo() + a.getValor()) < v_destino.get_custo()) {
                                //this.txtLog.append("> Sim\n");

                                //Remove a aresta antiga pois encontrou uma aresta nova para o vertice

                                this.aresta_list.remove(v_destino.aresta_pai);
                                this.aresta_cmp_pai_novo_list.remove(v_destino.aresta_pai);

                                //Se não tem pai, adiciona o vertice atual como pai
                                v_destino.setPai(this.vertice_aux, a);

                                //Seta o custo como Custo da aresta + Custo do pai
                                v_destino.set_custo(a.getValor() + this.vertice_aux.get_custo());

                                //this.txtLog.append("Vertice " + this.vertice_aux.getDado() + " é o novo pai de " + v_destino.getDado() + "\n");

                                if (!this.aresta_list.contains(a)) {
                                    this.aresta_list.add(a);
                                }
                                if (!this.aresta_cmp_pai_novo_list.contains(a)) {
                                    this.aresta_cmp_pai_novo_list.add(a);
                                }

                                this.aresta_cmp_pai_novo_list.add(v_destino.aresta_pai);
                                //Percorre o caminho de vertices do pai novo até a origem

                                for (Vertice v : v_destino.get_caminho()) {
                                    if (!this.vertice_cmp_pai_novo_list.contains(v)) {
                                        this.vertice_cmp_pai_novo_list.add(v);
                                        if (v.aresta_pai != null && !this.aresta_cmp_pai_novo_list.contains(v.aresta_pai)) {
                                            this.aresta_cmp_pai_novo_list.add(v.aresta_pai);
                                        }
                                    }
                                }
                            } else {
                                //this.txtLog.append("> Não\n");

                                Vertice pai_vertice_antigo = v_destino.vertice_pai;
                                Aresta pai_aresta_antigo = v_destino.aresta_pai;
                                Vertice pai_aux = (a.getVi().equals(v_destino) ? a.getVj() : a.getVi());

                                //Se não tem pai, adiciona o vertice atual como pai
                                v_destino.setPai(pai_aux, a);

                                //Seta o custo como Custo da aresta + Custo do pai
                                v_destino.set_custo(a.getValor() + pai_aux.get_custo());

                                this.aresta_cmp_pai_novo_list.add(v_destino.aresta_pai);

                                //Percorre o caminho de vertices do pai novo até a origem
                                for (Vertice v : v_destino.get_caminho()) {
                                    if (!this.vertice_cmp_pai_novo_list.contains(v)) {
                                        this.vertice_cmp_pai_novo_list.add(v);
                                        if (v.aresta_pai != null && !this.aresta_cmp_pai_novo_list.contains(v.aresta_pai)) {
                                            this.aresta_cmp_pai_novo_list.add(v.aresta_pai);
                                        }
                                    }
                                }
                                this.aresta_list.remove(v_destino.aresta_pai);

                                //Se não tem pai, adiciona o vertice atual como pai
                                v_destino.setPai(pai_vertice_antigo, pai_aresta_antigo);

                                //Seta o custo como Custo da aresta + Custo do pai
                                v_destino.set_custo(pai_aresta_antigo.getValor() + pai_vertice_antigo.get_custo());
                            }
                        }
                    }
                    if (this.aresta_list.contains(a) && !this.vertice_ordem_list.contains(v_destino)) {
                        this.vertice_ordem_list.add(v_destino);
                    }
                }
            }
        }
    }

    /**
     * @author Luiz Henrique Bernardes
     *
     */
    public void avanca_dijkstra2() {

        if (this.vertice_aux == null) {
            this.vertice_aux = this.vertice_origem;
            this.algoritmoDesenho.verticesMarcados.add(findVerticeVisual(this.vertice_aux));
            this.algoritmoDesenho.coresVertices.put(findVerticeVisual(this.vertice_aux), Color.orange);
        }

        System.out.println(this.vertice_proc_stack);
        System.out.println(this.vertice_aux);
        System.out.println(this.vertice_proc_list);
        if (!this.vertice_proc_stack.isEmpty()) {
            this.vertice_aux = this.vertice_proc_stack.pop();
            System.out.println(this.vertice_aux);

            if (!this.vertice_aux.equals(this.vertice_origem)) {
                this.algoritmoDesenho.verticesMarcados.add(findVerticeVisual(this.vertice_aux));
                this.algoritmoDesenho.coresVertices.put(findVerticeVisual(this.vertice_aux), Color.orange);
                if (this.vertice_aux.aresta_pai != null) {
                    System.out.println("TESTE >> " + this.vertice_aux.aresta_pai);
                    this.algoritmoDesenho.arestasMarcadas.add(findArestaVisual(this.vertice_aux.aresta_pai));
                    this.algoritmoDesenho.coresArestas.put(findArestaVisual(this.vertice_aux.aresta_pai), Color.orange);
                }
            }
            if (this.vertice_aux.equals(this.vertice_destino)) {
                this.algoritmoDesenho.verticesMarcados.add(findVerticeVisual(this.vertice_aux));
                this.algoritmoDesenho.coresVertices.put(findVerticeVisual(this.vertice_aux), Color.green);
            }
            if (this.vertice_aux.vertice_pai != null) {
                System.out.println("PAI DO CARA >> " + this.vertice_aux.vertice_pai);
                for (Aresta aux : this.vertice_aux.vertice_pai.getArestaList()) {
                    if (!this.aresta_list.contains(aux)) {
                        Vertice v_destino_aux = (aux.getVi() == this.vertice_aux.vertice_pai ? aux.getVj() : aux.getVi());
                        if (this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(v_destino_aux))) {
                            this.algoritmoDesenho.coresArestas.remove(findArestaVisual(aux));
                            this.algoritmoDesenho.coresArestas.put(findArestaVisual(aux), Color.red);
                        }
                    }
                }
            }

            this.repaint();
        } else if (!this.vertice_cmp_pai_atual_list.isEmpty() && !this.aresta_cmp_pai_atual_list.isEmpty()) {
            //Percorre o caminho de vertices e arestas do pai atual até a origem

            for (Vertice v : this.vertice_cmp_pai_atual_list) {
                if (!this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(v))) {
                    this.algoritmoDesenho.verticesMarcados.add(findVerticeVisual(v));
                    this.vertice_aux_list.add(v);
                }
                this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(v));
                this.algoritmoDesenho.coresVertices.put(findVerticeVisual(v), Color.blue);
            }
            this.repaint();
            this.vertice_cmp_pai_atual_list.clear();

            for (Aresta a : this.aresta_cmp_pai_atual_list) {
                if (!this.algoritmoDesenho.arestasMarcadas.contains(findArestaVisual(a))) {
                    this.algoritmoDesenho.arestasMarcadas.add(findArestaVisual(a));
                    this.aresta_aux_list.add(a);
                }
                this.algoritmoDesenho.coresArestas.remove(findArestaVisual(a));
                this.algoritmoDesenho.coresArestas.put(findArestaVisual(a), Color.blue);
            }
            this.repaint();
            this.aresta_cmp_pai_atual_list.clear();

            for (Vertice v : this.vertice_aux_list) {
                this.algoritmoDesenho.verticesMarcados.remove(findVerticeVisual(v));
                this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(v));
            }
            for (Aresta a : this.aresta_aux_list) {
                this.algoritmoDesenho.arestasMarcadas.remove(findArestaVisual(a));
                this.algoritmoDesenho.coresArestas.remove(findArestaVisual(a));
            }
            this.repaint();
            this.vertice_aux_list.clear();
            this.aresta_aux_list.clear();

        } else if (!this.vertice_cmp_pai_novo_list.isEmpty() && !this.aresta_cmp_pai_novo_list.isEmpty()) {
            //Percorre o caminho de vertices e arestas do pai novo até a origem
            for (Vertice v : this.vertice_cmp_pai_novo_list) {
                if (!this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(v))) {
                    this.algoritmoDesenho.verticesMarcados.add(findVerticeVisual(v));
                    this.vertice_aux_list.add(v);
                }
                this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(v));
                this.algoritmoDesenho.coresVertices.put(findVerticeVisual(v), Color.cyan);
            }
            this.vertice_cmp_pai_novo_list.clear();
            for (Aresta a : this.aresta_cmp_pai_novo_list) {
                if (!this.algoritmoDesenho.arestasMarcadas.contains(findArestaVisual(a))) {
                    this.algoritmoDesenho.arestasMarcadas.add(findArestaVisual(a));
                    this.aresta_aux_list.add(a);
                }
                this.algoritmoDesenho.coresArestas.remove(findArestaVisual(a));
                this.algoritmoDesenho.coresArestas.put(findArestaVisual(a), Color.cyan);
            }

            this.aresta_cmp_pai_novo_list.clear();
            this.repaint();

            for (Vertice v : this.vertice_aux_list) {
                this.algoritmoDesenho.verticesMarcados.remove(findVerticeVisual(v));
                this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(v));
            }
            for (Aresta a : this.aresta_aux_list) {
                this.algoritmoDesenho.arestasMarcadas.remove(findArestaVisual(a));
                this.algoritmoDesenho.coresArestas.remove(findArestaVisual(a));
            }
            this.vertice_aux_list.clear();
            this.aresta_aux_list.clear();

        } else {
            System.out.println("Adicionando vertice: " + this.vertice_aux);
            if (!this.vertice_ordem_list.isEmpty()) {
                this.vertice_aux = null;
                for (Vertice v : this.vertice_ordem_list) {
                    if (this.vertice_aux == null) {
                        this.vertice_aux = v;
                    } else if (v.get_custo() < this.vertice_aux.get_custo()) {
                        this.vertice_aux = v;
                    }
                }
                this.vertice_ordem_list.remove(this.vertice_aux);
            }
            if (!this.vertice_aux.equals(this.vertice_origem)) {
                this.vertice_proc_stack.push(this.vertice_aux);
            }

            if (this.vertice_aux.equals(this.vertice_destino)) {
                for (Vertice v_aux : this.vertice_list) {
                    if (this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(v_aux))) {
                        this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(v_aux));
                        this.algoritmoDesenho.coresVertices.put(findVerticeVisual(v_aux), Color.green);
                    }
                }

                for (Aresta a_aux : this.aresta_list) {
                    if (this.algoritmoDesenho.arestasMarcadas.contains(findArestaVisual(a_aux))) {
                        this.algoritmoDesenho.coresArestas.remove(findArestaVisual(a_aux));
                        this.algoritmoDesenho.coresArestas.put(findArestaVisual(a_aux), Color.green);
                    }
                }

                for (Aresta aux : this.vertice_aux.getArestaList()) {
                    System.out.println("VAI REMOVER E PINTAR DE VERMELHO??!! >> " + aux);
                    if (!this.aresta_list.contains(aux)) {
                        Vertice v_destino_aux = (aux.getVi() == this.vertice_aux.vertice_pai ? aux.getVj() : aux.getVi());
                        if (this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(v_destino_aux))) {
                            this.algoritmoDesenho.coresArestas.remove(findArestaVisual(aux));
                            this.algoritmoDesenho.coresArestas.put(findArestaVisual(aux), Color.red);
                        }
                    }
                }

                this.repaint();
            }

            for (Aresta a : this.vertice_aux.getArestaList()) {
                if (!this.algoritmoDesenho.arestasMarcadas.contains(findArestaVisual(a))) {
                    this.aresta_proc_list.add(a);

                    Vertice v_destino = (a.getVi().equals(this.vertice_aux) ? a.getVj() : a.getVi());

                    if (!this.vertice_list.contains(v_destino)) {
                        //this.vertice_restricao_list.add(v_destino);
                        this.vertice_list.add(v_destino);
                    }
                    this.vertice_proc_stack.push(v_destino);

                    for (Vertice v_aux : this.vertice_restricao_list) {
                        int vertice_inatingivel = 0;
                        for (Aresta a_aux : v_aux.getArestaList()) {
                            if (this.algoritmoDesenho.arestasMarcadas.contains(findArestaVisual(a_aux))) {
                                vertice_inatingivel += 1;
                            }
                        }
                        if (vertice_inatingivel == v_aux.getArestaList().size()) {
                            this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(v_aux));
                            this.algoritmoDesenho.coresVertices.put(findVerticeVisual(v_aux), Color.black);
                            for (Aresta a_aux : v_aux.getArestaList()) {
                                this.algoritmoDesenho.coresArestas.remove(findArestaVisual(a_aux));
                                this.algoritmoDesenho.coresArestas.put(findArestaVisual(a_aux), Color.black);
                            }
                            this.vertice_list.remove(v_aux);
                        }
                    }
                    this.repaint();

                    if (!this.vertice_aux.equals(this.vertice_origem)) {
                        for (Vertice v_aux : this.vertice_list) {
                            if (this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(v_aux))) {
                                this.algoritmoDesenho.coresVertices.remove(findVerticeVisual(v_aux));
                                this.algoritmoDesenho.coresVertices.put(findVerticeVisual(v_aux), Color.green);
                            }
                        }

                        for (Aresta a_aux : this.aresta_list) {
                            if (this.algoritmoDesenho.arestasMarcadas.contains(findArestaVisual(a_aux))) {
                                this.algoritmoDesenho.coresArestas.remove(findArestaVisual(a_aux));
                                this.algoritmoDesenho.coresArestas.put(findArestaVisual(a_aux), Color.green);
                            }
                        }
                        this.repaint();

                        /*for (Aresta aux : this.vertice_aux.getArestaList()) {
                            System.out.println("TESTE >> " + aux);
                            if (!this.aresta_list.contains(aux) && this.algoritmoDesenho.arestasMarcadas.contains(findArestaVisual(aux))) {
                                this.algoritmoDesenho.coresArestas.remove(findArestaVisual(aux));
                                this.algoritmoDesenho.coresArestas.put(findArestaVisual(aux), Color.orange);
                            }
                        }*/

                        for (Vertice v_aux : this.vertice_restricao_list) {
                            for (Aresta a_aux : v_aux.getArestaList()) {
                                System.out.println("TESTE >> " + this.vertice_aux.aresta_pai);
                                if (!this.aresta_list.contains(a_aux) && this.algoritmoDesenho.arestasMarcadas.contains(findArestaVisual(a_aux))) {
                                    this.algoritmoDesenho.coresArestas.remove(findArestaVisual(a_aux));
                                    this.algoritmoDesenho.coresArestas.put(findArestaVisual(a_aux), Color.orange);
                                }
                            }
                        }

                        for (Aresta a2 : this.vertice_aux.vertice_pai.getArestaList()) {
                            Vertice v_destino2 = (a2.getVi().equals(this.vertice_aux.vertice_pai) ? a2.getVj() : a2.getVi());

                            if (v_destino2.vertice_pai == null && !v_destino2.equals(this.vertice_origem)) {
                                //Se não tem pai, adiciona o vertice atual como pai
                                v_destino2.setPai(this.vertice_aux.vertice_pai, a2);

                                //Seta o custo como Custo da aresta + Custo do pai
                                v_destino2.set_custo(a2.getValor() + this.vertice_aux.vertice_pai.get_custo());
                            }
                        }
                        this.repaint();
                    }

                    //Se não for o vertice de origem
                    if (!v_destino.equals(this.vertice_origem)) {

                        //Se não é o vertice de origem e o pai é nulo
                        //então o vertice não tem pai
                        if (v_destino.vertice_pai == null) {

                            //Se não tem pai, adiciona o vertice atual como pai
                            v_destino.setPai(this.vertice_aux, a);

                            //Seta o custo como Custo da aresta + Custo do pai
                            v_destino.set_custo(a.getValor() + this.vertice_aux.get_custo());

                            if (!this.vertice_restricao_list.contains(v_destino)) {

                                //Percorre o caminho de vertices do pai novo até a origem
                                this.aresta_cmp_pai_novo_list.add(v_destino.aresta_pai);
                                if (!this.aresta_list.contains(v_destino.aresta_pai)) {
                                    this.aresta_list.add(v_destino.aresta_pai);
                                }

                                //Percorre o caminho de vertices do pai novo até a origem
                                for (Vertice v : v_destino.get_caminho()) {
                                    if (!this.vertice_cmp_pai_novo_list.contains(v)) {

                                        this.vertice_cmp_pai_novo_list.add(v);
                                        if (v.aresta_pai != null && !this.aresta_cmp_pai_novo_list.contains(v.aresta_pai) && this.algoritmoDesenho.arestasMarcadas.contains(findArestaVisual(v.aresta_pai))) {
                                            this.aresta_cmp_pai_novo_list.add(v.aresta_pai);
                                        }
                                    }
                                }
                            }
                        } else {
                            //Se o pai não é nulo, ele tem pai
                            //Fazer comparação

                            Vertice pai_atual = v_destino.vertice_pai;
                            if (!this.vertice_restricao_list.contains(v_destino)) {

                                if (!this.aresta_cmp_pai_novo_list.contains(v_destino.aresta_pai)) {
                                    this.aresta_cmp_pai_atual_list.add(v_destino.aresta_pai);
                                }

                                for (Vertice v : v_destino.get_caminho()) {
                                    if (!this.vertice_cmp_pai_atual_list.contains(v) && !this.vertice_cmp_pai_novo_list.contains(v)) {

                                        this.vertice_cmp_pai_atual_list.add(v);
                                        if (v.aresta_pai != null && !this.aresta_cmp_pai_atual_list.contains(v.aresta_pai) && this.algoritmoDesenho.arestasMarcadas.contains(findArestaVisual(v.aresta_pai))) {
                                            this.aresta_cmp_pai_atual_list.add(v.aresta_pai);
                                        }
                                    }
                                }
                            }

                            if ((this.vertice_aux.get_custo() + a.getValor()) < v_destino.get_custo()) {
                                //Remove a aresta antiga pois encontrou uma aresta nova para o vertice
                                this.aresta_list.remove(v_destino.aresta_pai);
                                this.aresta_cmp_pai_novo_list.remove(v_destino.aresta_pai);

                                //Se não tem pai, adiciona o vertice atual como pai
                                v_destino.setPai(this.vertice_aux, a);

                                //Seta o custo como Custo da aresta + Custo do pai
                                v_destino.set_custo(a.getValor() + this.vertice_aux.get_custo());

                                if (!this.aresta_list.contains(a)) {
                                    this.aresta_list.add(a);
                                    this.aresta_cmp_pai_novo_list.add(a);
                                }
                            }

                            if (!this.vertice_restricao_list.contains(v_destino)) {

                                this.aresta_cmp_pai_novo_list.add(v_destino.aresta_pai);
                                //Percorre o caminho de vertices do pai novo até a origem

                                for (Vertice v : v_destino.get_caminho()) {
                                    if (!this.vertice_cmp_pai_novo_list.contains(v)) {
                                        this.vertice_cmp_pai_novo_list.add(v);
                                        if (v.aresta_pai != null && !this.aresta_cmp_pai_novo_list.contains(v.aresta_pai)) {
                                            this.aresta_cmp_pai_novo_list.add(v.aresta_pai);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (this.aresta_list.contains(a)) {
                        this.vertice_ordem_list.add(v_destino);
                    }
                }
            }
        }
    }

    public void avanca2() {
        this.debugLabel.setText("Atenção!! Avançou 1 passo do algoritmo >");

        int temp_index_a = 0;
        int temp_index_v = 0;

        if (this.index_vertice < this.vertice_list.size()) {
            //Passa para o próximo vertice
            this.index_vertice += 1;

            //Verifica o status do vertice anterior
            if (this.index_vertice > 1) {
                temp_index_v = this.vertice_list.size() - (this.index_vertice - 1);
            }

            if (this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(this.vertice_list.get(temp_index_v)))) {
                System.out.println("Entrou na verificação");
                //Atualiza o status do vertice
                this.algoritmoDesenho.coresVertices.put(findVerticeVisual(this.vertice_list.get(temp_index_v)), Color.green);

                //Verifica as demais arestas ligadas ao vertice
                Vertice temp_vertice = this.vertice_list.get(temp_index_v);

                //Percorre as arestas e colore de vermelho para identificar que não serão utilizadas
                for (Aresta temp_aresta : temp_vertice.getArestaList()) {
                    System.out.println(temp_aresta);
                    System.out.println("Arestas..");
                    System.out.println(findArestaVisual(temp_aresta).color);
                    System.out.println(Color.orange);
                    if (!this.aresta_list.contains(temp_aresta)) {
                        if (findArestaVisual(temp_aresta).color.equals(Color.orange)) {
                            System.out.println("Entrando");

                            try {
                                Thread.sleep(1000);
                                this.algoritmoDesenho.coresArestas.put(findArestaVisual(temp_aresta), Color.red);
                                this.repaint();
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }

            temp_index_v = this.vertice_list.size() - this.index_vertice;
            this.algoritmoDesenho.verticesMarcados.add(findVerticeVisual(this.vertice_list.get(temp_index_v)));
            this.algoritmoDesenho.coresVertices.put(findVerticeVisual(this.vertice_list.get(temp_index_v)), Color.orange);
        }

        if (this.index_aresta < this.aresta_list.size()) {
            //Passa para a próxima aresta
            this.index_aresta += 1;

            //Verifica o status da aresta anterior
            if (this.index_aresta > 1) {
                temp_index_a = this.aresta_list.size() - (this.index_aresta - 1);
            }

            if (this.algoritmoDesenho.arestasMarcadas.contains(findArestaVisual(this.aresta_list.get(temp_index_a)))) {
                if (findArestaVisual(this.aresta_list.get(temp_index_a)).color.equals(Color.orange)) {
                    //Atualiza o status da aresta anterior
                    this.algoritmoDesenho.coresArestas.put(findArestaVisual(this.aresta_list.get(temp_index_a)), Color.green);
                }
            }

            temp_index_a = this.aresta_list.size() - this.index_aresta;
            this.algoritmoDesenho.arestasMarcadas.add(findArestaVisual(this.aresta_list.get(temp_index_a)));
            this.algoritmoDesenho.coresArestas.put(findArestaVisual(this.aresta_list.get(temp_index_a)), Color.orange);
        }

        System.out.println(this.vertice_list.get(temp_index_v));
        if (!this.algoritmoDesenho.arestasMarcadas.contains(findVerticeVisual(this.vertice_list.get(temp_index_v)))) {
            System.out.println("Entrou");
            Vertice temp_vertice = this.vertice_list.get(temp_index_v);

            for (Aresta temp_aresta : temp_vertice.getArestaList()) {
                System.out.println(temp_aresta);
                if (!(temp_index_v == 0)) {
                    if (!this.algoritmoDesenho.arestasMarcadas.contains(findArestaVisual(temp_aresta))) {
                        //this.aux_aresta_list.add(temp_aresta);
                        this.algoritmoDesenho.arestasMarcadas.add(findArestaVisual(temp_aresta));
                        this.algoritmoDesenho.coresArestas.put(findArestaVisual(temp_aresta), Color.orange);
                    }
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    throw new RuntimeException(e);
                }
                this.repaint();
            }

            /*for (Aresta temp_aresta : this.aux_aresta_list) {
                if (this.algoritmoDesenho.arestasMarcadas.contains(findArestaVisual(temp_aresta))) {
                    if (this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(temp_aresta.getVi())) && this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(temp_aresta.getVj()))) {
                        this.algoritmoDesenho.coresArestas.put(findArestaVisual(temp_aresta), Color.gray);
                        this.repaint();
                    }
                }
            }*/
        }
    }

    public void avanca1() {
        this.debugLabel.setText("Atenção!! Avançou 1 passo do algoritmo >");

        int temp_index_a = 0;
        int temp_index_v = 0;

        if (this.index_vertice < this.vertice_list.size()) {
            this.index_vertice += 1;
            temp_index_v = this.vertice_list.size() - this.index_vertice;
            this.algoritmoDesenho.verticesMarcados.add(findVerticeVisual(this.vertice_list.get(temp_index_v)));
        }

        if (this.index_aresta < this.aresta_list.size()) {
            this.index_aresta += 1;
            temp_index_a = this.aresta_list.size() - this.index_aresta;
            this.algoritmoDesenho.arestasMarcadas.add(findArestaVisual(this.aresta_list.get(temp_index_a)));
        }

        System.out.println(this.vertice_list.get(temp_index_v));
        if (!this.algoritmoDesenho.arestasMarcadas.contains(findVerticeVisual(this.vertice_list.get(temp_index_v)))) {
            System.out.println("Entrou");
            Vertice temp_vertice = this.vertice_list.get(temp_index_v);

            for (Aresta temp_aresta : temp_vertice.getArestaList()) {
                System.out.println(temp_aresta);
                if (!(temp_index_v == 0)) {
                    if (!this.algoritmoDesenho.arestasMarcadas.contains(findArestaVisual(temp_aresta))) {
                        //this.aux_aresta_list.add(temp_aresta);
                        this.algoritmoDesenho.arestasMarcadas.add(findArestaVisual(temp_aresta));
                        this.algoritmoDesenho.coresArestas.put(findArestaVisual(temp_aresta), Color.red);
                    }
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    throw new RuntimeException(e);
                }
                this.repaint();
            }

            /*for (Aresta temp_aresta : this.aux_aresta_list) {
                if (this.algoritmoDesenho.arestasMarcadas.contains(findArestaVisual(temp_aresta))) {
                    if (this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(temp_aresta.getVi())) && this.algoritmoDesenho.verticesMarcados.contains(findVerticeVisual(temp_aresta.getVj()))) {
                        this.algoritmoDesenho.coresArestas.put(findArestaVisual(temp_aresta), Color.gray);
                        this.repaint();
                    }
                }
            }*/
        }
    }

    /**
     * @param menuBar
     */
    private void menuSobre(JMenuBar menuBar) {
        /*
         * Menu Propriedades
         */
        JMenu mnuAlgoritmos = new JMenu("Ajuda");

        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Sobre");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    //1. Create the frame.
                    JFrame frame = new JFrame("Sobre Furb Graphs");
                    frame.setSize(400, 300);

                    String texto =
                    /**/"FURB Graphs" +
                    /**/"\r\n" +
                    /**/"\r\n" +
                    /**/"Trabalho de Conclusão de Curso desenvolvido para obtenção do grau de " +
                    /**/"\r\nbacharelado do curso de Ciência da Computação do Centro de Ciências " +
                    /**/"\r\nExatas e Naturais da Universidade Regional de Blumenau." +
                    /**/"\r\n" +
                    /**/"\r\n" +
                    /**/"Trabalho inicialmente desenvolvido por Maicon Rafael Zatelli (FURB, 2010)." +
                    /**/"\r\nPosteriormente complementado por Anderson de Borba (FURB, 2014)." +
                    /**/"\r\nPosteriormente adaptado e complementado por Luiz Henrique Bernardes (FURB, 2016).";
                    JTextArea emptyLabel = new JTextArea(texto);
                    emptyLabel.setEditable(false);
                    //3. Create components and put them in the frame.
                    //...create emptyLabel...
                    frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);

                    //4. Size the frame.
                    frame.pack();
                    frame.setLocationRelativeTo(null);

                    //5. Show it.
                    frame.setVisible(true);
                }

            });
        }

        menuBar.add(mnuAlgoritmos);
    }

    private void menuAlgoritmos(JMenuBar menuBar) {
        /*
         * Menu Propriedades
         */
        JMenu mnuAlgoritmos = new JMenu("Algoritmos");

        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Limpar Execução");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    algoritmoDesenho.reset();
                    GraphViewer.this.repaint();
                    algoritmoExecutor = null;
                }

            });
        }
        {
            mnuAlgoritmos.add(new JSeparator());
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Bellman-Ford");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    algoritmoExecutor = new IAlgoritmoExecutor() {

                        boolean messageV1 = false;

                        VerticeVisual v1, v2;

                        @Override
                        public void nextStep(VerticeVisual... vertices) {
                            if (!messageV1) {
                                info("Selecione o vértice inicial.");
                                messageV1 = true;
                                return;
                            }

                            if (vertices != null && vertices.length == 1) {
                                if (v1 == null) {
                                    v1 = vertices[0];
                                    info("Selecione o vértice final.");

                                    algoritmoDesenho.coresVertices.put(v1, Color.MAGENTA);
                                    GraphViewer.this.repaint();

                                    VerticeVisual.selectNone(vertexs);
                                    GraphViewer.this.repaint();
                                } else {
                                    if (v1 == vertices[0]) {
                                        info("Vértice inicial e final devem ser diferentes");
                                        return;
                                    }
                                    v2 = vertices[0];

                                    Grafo g = g();

                                    Vertice _v1 = g.getVerticeById(v1.id);
                                    Vertice _v2 = g.getVerticeById(v2.id);

                                    AlgoritmoBellmanFord bellman = new AlgoritmoBellmanFord();
                                    bellman.executar(g, _v1);

                                    AlgoritmoBellmanFordResultado resultado = bellman.getResultado();

                                    List<Vertice> result_vertex = new ArrayList<>();

                                    Vertice predecessor = _v2;
                                    algoritmoDesenho.verticesMarcados.add(findVerticeVisual(_v1));
                                    algoritmoDesenho.verticesMarcados.add(findVerticeVisual(_v2));

                                    boolean existeCaminho = true;
                                    while (predecessor != _v1) {
                                        result_vertex.add(predecessor);

                                        algoritmoDesenho.verticesMarcados.add(findVerticeVisual(predecessor));
                                        Vertice predecessorTemp = resultado.getPredecessor(predecessor);

                                        Aresta aresta = g.getArestaByVertices(predecessor, predecessorTemp);
                                        if (aresta == null) {
                                            try {
                                                aresta = g.getArestaByVertices(predecessorTemp, predecessor);
                                            } catch (NullPointerException npe) {
                                                info("Não existe o caminho.");
                                                existeCaminho = false;
                                                break;
                                            }
                                        }

                                        algoritmoDesenho.arestasMarcadas.add(findArestaVisual(aresta));
                                        predecessor = predecessorTemp;
                                    }
                                    result_vertex.add(_v1);
                                    Collections.reverse(result_vertex);

                                    if (existeCaminho) {
                                        // frame
                                        String str = "Resultado Bellman-Ford:\n";
                                        for (Vertice vertice : result_vertex) {
                                            str += (vertice.getDado() == null ? "(não nomeado)" : vertice.getDado()) + "\n";
                                        }
                                        new ResultadoFrame("Resultado Bellman-Ford", str);
                                    }

                                    algoritmoExecutor = null;
                                    VerticeVisual.selectNone(vertexs);
                                    GraphViewer.this.repaint();
                                }
                            }
                        }

                    };

                    algoritmoDesenho.reset();
                    VerticeVisual.selectNone(vertexs);
                    GraphViewer.this.repaint();
                    algoritmoExecutor.nextStep();
                }

            });
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Busca Largura");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    algoritmoExecutor = new IAlgoritmoExecutor() {

                        boolean messageV1 = false;

                        VerticeVisual v1;

                        @Override
                        public void nextStep(VerticeVisual... vertices) {
                            if (!messageV1) {
                                info("Selecione o vértice inicial.");
                                messageV1 = true;
                                return;
                            }

                            if (vertices != null && vertices.length == 1) {
                                if (v1 == null) {
                                    GraphViewer.this.BFS = true;
                                    GraphViewer.this.DFS = false;
                                    GraphViewer.this.DIJKSTRA = false;
                                    GraphViewer.this.retorna();

                                    v1 = vertices[0];

                                    Grafo g = g();

                                    Vertice _v1 = g.getVerticeById(v1.id);
                                    GraphViewer.this.vertice_origem = _v1;

                                    AlgoritmoBuscaLargura bfs = new AlgoritmoBuscaLargura();
                                    bfs.executar(g, _v1);

                                    AlgoritmoBuscaLarguraResultado resultado = bfs.getResultado();

                                    // frame
                                    String str = "Resultado Busca Largura:\n";

                                    for (int i = 0; i < g.getTamanho(); i++) {
                                        Vertice vertice = g.getVertice(i);
                                        if (resultado.getVisitado(vertice)) {
                                            GraphViewer.this.vertice_list.add(vertice);

                                            //Melhoria, utilizando o metodo que retorna uma lista de arestas vinculadas ao vertice
                                            for (Aresta aresta : vertice.getArestaList()) {
                                                GraphViewer.this.aresta_list.add(aresta);
                                            }

                                            str += vertice.getDado() + //
                                            " Tempo abertura: " + resultado.getTempoAbertura(vertice) + //
                                            " Tempo fechamento: " + resultado.getTempoFechamento(vertice) + //
                                            "\n";
                                        }
                                    }

                                    if (GraphViewer.this.debugAtivo) {
                                        JOptionPane.showMessageDialog(null, "A opção de Debug está ativa!\nPara acompanhar a execução do algoritmo utilize os botões de controle da parte inferior da tela (<< < > >>)");

                                        GraphViewer.this.txtAlg.setText("");

                                        GraphViewer.this.algoritmoStr = "01 BUSCA-EM-LARGURA(G, s)\n";
                                        GraphViewer.this.algoritmoStr += "02 > INICIALIZAÇÃO\n";
                                        GraphViewer.this.algoritmoStr += "03 para cada u pertencente V[G] – {s} faça\n";
                                        GraphViewer.this.algoritmoStr += "04    cor[u] <- branco\n";
                                        GraphViewer.this.algoritmoStr += "05    d[u] <- infinito\n";
                                        GraphViewer.this.algoritmoStr += "06    py[u] <- NIL\n";
                                        GraphViewer.this.algoritmoStr += "07 cor[s] <- cinza\n";
                                        GraphViewer.this.algoritmoStr += "08 d[u] <- 0\n";
                                        GraphViewer.this.algoritmoStr += "09 py[u] <- NIL\n";
                                        GraphViewer.this.algoritmoStr += "10 Q <- 0\n\n";
                                        GraphViewer.this.algoritmoStr += "11 ENQUEUE(Q, s)\n";
                                        GraphViewer.this.algoritmoStr += "12    enquanto Q diferente 0 faça\n";
                                        GraphViewer.this.algoritmoStr += "13        u <- DEQUEUE(Q)\n";
                                        GraphViewer.this.algoritmoStr += "14        para cada v pertencente Adj[u] faça\n";
                                        GraphViewer.this.algoritmoStr += "15            se cor[v] == branco então\n";
                                        GraphViewer.this.algoritmoStr += "16                cor[v] <- cinza\n";
                                        GraphViewer.this.algoritmoStr += "17                d[v] <- d[u] + 1\n";
                                        GraphViewer.this.algoritmoStr += "18                py[v] <- u\n";
                                        GraphViewer.this.algoritmoStr += "19                ENQUEUE(Q, v)\n";
                                        GraphViewer.this.algoritmoStr += "20        cor[u] <- preto\n";

                                        GraphViewer.this.txtAlg.setText(GraphViewer.this.algoritmoStr);

                                    } else {
                                        for (Vertice vertice : GraphViewer.this.vertice_list) {
                                            algoritmoDesenho.verticesMarcados.add(findVerticeVisual(vertice));
                                        }
                                        for (Aresta aresta : GraphViewer.this.aresta_list) {
                                            algoritmoDesenho.arestasMarcadas.add(findArestaVisual(aresta));
                                        }
                                        GraphViewer.this.repaint();
                                    }
                                    new ResultadoFrame("Resultado Busca Largura", str);

                                    algoritmoExecutor = null;
                                    VerticeVisual.selectNone(vertexs);
                                    GraphViewer.this.repaint();
                                }
                            }
                        }

                    };

                    algoritmoDesenho.reset();
                    VerticeVisual.selectNone(vertexs);
                    GraphViewer.this.repaint();
                    algoritmoExecutor.nextStep();
                }

            });
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Busca Profundidade");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    algoritmoExecutor = new IAlgoritmoExecutor() {

                        boolean messageV1 = false;

                        VerticeVisual v1;

                        @Override
                        public void nextStep(VerticeVisual... vertices) {
                            if (!messageV1) {
                                info("Selecione o vértice inicial.");
                                messageV1 = true;
                                return;
                            }

                            if (vertices != null && vertices.length == 1) {
                                if (v1 == null) {
                                    GraphViewer.this.BFS = false;
                                    GraphViewer.this.DFS = true;
                                    GraphViewer.this.DIJKSTRA = false;
                                    GraphViewer.this.retorna();

                                    v1 = vertices[0];

                                    Grafo g = g();

                                    Vertice _v1 = g.getVerticeById(v1.id);
                                    GraphViewer.this.vertice_origem = _v1;

                                    AlgoritmoBuscaProfundidade dfs = new AlgoritmoBuscaProfundidade();
                                    dfs.executar(g, _v1);

                                    AlgoritmoBuscaProfundidadeResultado resultado = dfs.getResultado();

                                    // frame
                                    String str = "Resultado Busca Profundidade:\n";

                                    int tempo_abertura = 1;
                                    int tempo_fechamento = 0;

                                    //Enquanto os tamanhos das listas não forem os mesmos, ordena a lista
                                    //while (GraphViewer.this.vertice_list.size() != g.getQtdeArestas()) {
                                    //LUIZ HB
                                    //Aplicado melhoria para percorrer uma lista de vertices
                                    for (Vertice vertice : g.getVerticeList()) {
                                        if (resultado.getVisitado(vertice)) {

                                            GraphViewer.this.vertice_list.add(vertice);
                                            for (Aresta a : vertice.getArestaList()) {
                                                GraphViewer.this.aresta_list.add(a);
                                            }
                                            //LUIZ HB
                                            //Registrando o tempo de abertura e fechamento de cada vertice
                                            vertice.temp_abertura = resultado.getTempoAbertura(vertice);
                                            vertice.temp_fechamento = resultado.getTempoFechamento(vertice);

                                            str += vertice.getDado() + " Tempo abertura: " + vertice.temp_abertura + " Tempo fechamento: " + vertice.temp_fechamento + "\n";
                                        }
                                    }
                                    //}

                                    if (GraphViewer.this.debugAtivo) {
                                        JOptionPane.showMessageDialog(null, "A opção de Debug está ativa!\nPara acompanhar a execução do algoritmo utilize os botões de controle da parte inferior da tela (<< < > >>)");

                                        GraphViewer.this.txtAlg.setText("");

                                        GraphViewer.this.algoritmoStr = "01 dfs(G, u, cont)\n";
                                        GraphViewer.this.algoritmoStr += "02    u.visitado = True\n";
                                        GraphViewer.this.algoritmoStr += "03    u.d = cont\n";
                                        GraphViewer.this.algoritmoStr += "04    para v em adj(u) faça\n";
                                        GraphViewer.this.algoritmoStr += "05        se não v.visitado então\n";
                                        GraphViewer.this.algoritmoStr += "06            v.p = u\n";
                                        GraphViewer.this.algoritmoStr += "07            dfs(G, v, cont+1)\n";
                                        GraphViewer.this.algoritmoStr += "08 para u em V(G) faça\n";
                                        GraphViewer.this.algoritmoStr += "09    u.visitado = False\n";
                                        GraphViewer.this.algoritmoStr += "10    u.d = infinito\n";
                                        GraphViewer.this.algoritmoStr += "11    u.p = None\n";
                                        GraphViewer.this.algoritmoStr += "12 cont = 1\n";
                                        GraphViewer.this.algoritmoStr += "13 dfs(G, u, cont)\n";

                                        GraphViewer.this.txtAlg.setText(GraphViewer.this.algoritmoStr);

                                    } else {
                                        for (Vertice vertice : GraphViewer.this.vertice_list) {
                                            algoritmoDesenho.verticesMarcados.add(findVerticeVisual(vertice));
                                        }
                                        for (Aresta aresta : GraphViewer.this.aresta_list) {
                                            algoritmoDesenho.arestasMarcadas.add(findArestaVisual(aresta));
                                        }
                                        GraphViewer.this.repaint();
                                    }
                                    new ResultadoFrame("Resultado Busca Profundidade", str);

                                    algoritmoExecutor = null;
                                    VerticeVisual.selectNone(vertexs);
                                    GraphViewer.this.repaint();
                                }
                            }
                        }

                    };

                    algoritmoDesenho.reset();
                    VerticeVisual.selectNone(vertexs);
                    GraphViewer.this.repaint();
                    algoritmoExecutor.nextStep();
                }

            });
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Caminho Euleriano");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    AlgoritmoCaminhoEuleriano euleriano = new AlgoritmoCaminhoEuleriano();
                    AlgoritmoCaminhoEulerianoResultado resultado = euleriano.fleury(g());

                    algoritmoDesenho.reset();
                    if (resultado == null) {
                        JOptionPane.showMessageDialog(null, "Não encontrou caminho euleriano.");
                    } else {
                        for (Aresta aresta : resultado.getCaminho()) {
                            algoritmoDesenho.arestasMarcadas.add(findArestaVisual(aresta));
                            algoritmoDesenho.verticesMarcados.add(findVerticeVisual(aresta.getVi()));
                            algoritmoDesenho.verticesMarcados.add(findVerticeVisual(aresta.getVj()));
                        }
                        new ResultadoFrame("Resultado Caminho Euleriano", resultado.toString());
                    }
                    GraphViewer.this.repaint();
                }

            });
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Ciclo Hamiltoniano");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (hasDirection()) {
                        JOptionPane.showMessageDialog(null, "Algoritmo de ciclo hamiltoniano aplica-se somente para grafos não dirigidos.");
                    } else {
                        GrafoNaoDirigido g = (GrafoNaoDirigido) g();

                        AlgoritmoCicloHamiltoniano h = new AlgoritmoCicloHamiltoniano();
                        AlgoritmoCicloHamiltonianoResultado resultado = h.backtracking(g);

                        algoritmoDesenho.reset();
                        if (resultado == null) {
                            JOptionPane.showMessageDialog(null, "Ciclo hamiltoniano não encontrado");
                        } else {
                            List<Vertice> caminho = resultado.getCaminho();
                            for (int i = 0; i < caminho.size(); i++) {
                                Vertice vi = caminho.get(i);
                                algoritmoDesenho.verticesMarcados.add(findVerticeVisual(vi));

                                Vertice vj = (i > 0) ? caminho.get(i - 1) : caminho.get(caminho.size() - 1);
                                Aresta aresta = g.getArestaByVertices(vi, vj);
                                if (aresta == null) {
                                    System.out.println("não encontrou a aresta: " + vi + " " + vj);
                                } else {
                                    VerticeVisual v1 = findVerticeVisual(vi);
                                    VerticeVisual v2 = findVerticeVisual(vj);
                                    algoritmoDesenho.arestasMarcadas.add(getEdge(v1, v2));
                                }
                            }

                            new ResultadoFrame("Resultado Ciclo Hamiltoniano", resultado.toString());
                        }
                        GraphViewer.this.repaint();
                    }
                }

            });
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Dijkstra");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    algoritmoExecutor = new IAlgoritmoExecutor() {

                        boolean messageV1 = false;

                        VerticeVisual v1, v2;

                        @Override
                        public void nextStep(VerticeVisual... vertices) {
                            if (!messageV1) {
                                info("Selecione o vértice inicial.");
                                messageV1 = true;
                                return;
                            }

                            if (vertices != null && vertices.length == 1) {
                                if (v1 == null) {
                                    v1 = vertices[0];
                                    info("Selecione o vértice final.");
                                    algoritmoDesenho.coresVertices.put(v1, Color.MAGENTA);
                                    GraphViewer.this.repaint();

                                    VerticeVisual.selectNone(vertexs);
                                    GraphViewer.this.repaint();
                                } else {
                                    if (v1 == vertices[0]) {
                                        info("Vértice inicial e final devem ser diferentes");
                                        return;
                                    }
                                    GraphViewer.this.BFS = false;
                                    GraphViewer.this.DFS = false;
                                    GraphViewer.this.DIJKSTRA = true;
                                    GraphViewer.this.retorna();

                                    v2 = vertices[0];

                                    Grafo g = g();

                                    Vertice _v1 = g.getVerticeById(v1.id);
                                    Vertice _v2 = g.getVerticeById(v2.id);

                                    GraphViewer.this.vertice_origem = _v1;
                                    GraphViewer.this.vertice_destino = _v2;

                                    AlgoritmoDijkstra dijkstra = new AlgoritmoDijkstra();
                                    dijkstra.executar(g, _v1);

                                    AlgoritmoDijkstraResultado resultado = dijkstra.getResultado();

                                    List<Vertice> result_vertex = new ArrayList<>();

                                    Vertice predecessor = _v2;

                                    //algoritmoDesenho.verticesMarcados.add(findVerticeVisual(_v1));
                                    //algoritmoDesenho.verticesMarcados.add(findVerticeVisual(_v2));
                                    //result_vertex.add(_v2);
                                    List<Aresta> result_aresta = new ArrayList<>();

                                    boolean existeCaminho = true;
                                    while (predecessor != _v1) {
                                        result_vertex.add(predecessor);

                                        Vertice predecessorTemp = resultado.getPredecessor(predecessor);
                                        if (predecessorTemp == null) { // não encontrou resultado
                                            info("Não encontrou o caminho.");
                                            existeCaminho = false;
                                            break;
                                        }

                                        Aresta aresta = g.getArestaByVertices(predecessor, predecessorTemp);

                                        result_aresta.add(aresta);
                                        predecessor = predecessorTemp;

                                    }
                                    result_vertex.add(_v1);

                                    GraphViewer.this.vertice_list = result_vertex;
                                    GraphViewer.this.aresta_list = result_aresta;

                                    if (GraphViewer.this.debugAtivo) {
                                        JOptionPane.showMessageDialog(null, "A opção de Debug está ativa!\nPara acompanhar a execução do algoritmo utilize os botões de controle da parte inferior da tela (<< < > >>)");

                                        GraphViewer.this.txtAlg.setText("");

                                        GraphViewer.this.algoritmoStr = "01 Dijkstra(grafo, origem)\n";
                                        GraphViewer.this.algoritmoStr += "02  para cada vértice v em grafo\n";
                                        GraphViewer.this.algoritmoStr += "03      distancia[v] = infinito;\n";
                                        GraphViewer.this.algoritmoStr += "04      predecessor[v] = -1\n";
                                        GraphViewer.this.algoritmoStr += "05  fim\n";
                                        GraphViewer.this.algoritmoStr += "06  distancia[origem] = 0\n";
                                        GraphViewer.this.algoritmoStr += "07  Q = todos os vértices de grafo;\n";
                                        GraphViewer.this.algoritmoStr += "08  enquanto (Q não é vazio) faça\n";
                                        GraphViewer.this.algoritmoStr += "09      u = vértice é grafo com menor distancia\n";
                                        GraphViewer.this.algoritmoStr += "10         se (distancia u == infinito)\n";
                                        GraphViewer.this.algoritmoStr += "11             Salta o laço;\n";
                                        GraphViewer.this.algoritmoStr += "12         fim\n";
                                        GraphViewer.this.algoritmoStr += "13         remova u de Q;\n";
                                        GraphViewer.this.algoritmoStr += "14         para cada vizinho v de u\n";
                                        GraphViewer.this.algoritmoStr += "15             d = distancia[u] + distancia entre u e v;\n";
                                        GraphViewer.this.algoritmoStr += "16             se (d < distancia[v])\n";
                                        GraphViewer.this.algoritmoStr += "17                 distancia[v] = d;\n";
                                        GraphViewer.this.algoritmoStr += "18                 predecessor[v] = u;\n";
                                        GraphViewer.this.algoritmoStr += "19             fim\n";
                                        GraphViewer.this.algoritmoStr += "20         fim\n";
                                        GraphViewer.this.algoritmoStr += "21     fim\n";
                                        GraphViewer.this.algoritmoStr += "22 fim";

                                        GraphViewer.this.txtAlg.setText(GraphViewer.this.algoritmoStr);

                                    } else {
                                        for (Vertice vertice : result_vertex) {
                                            System.out.println(vertice.getDado());
                                            algoritmoDesenho.verticesMarcados.add(findVerticeVisual(vertice));
                                        }
                                        for (Aresta aresta : result_aresta) {
                                            algoritmoDesenho.arestasMarcadas.add(findArestaVisual(aresta));
                                        }
                                    }

                                    Collections.reverse(result_vertex);

                                    // frame
                                    if (existeCaminho) {
                                        String str = "Resultado Dijkstra:\n";
                                        for (Vertice vertice : result_vertex) {
                                            for (Aresta aresta : result_aresta) {
                                                if (aresta.getVi() == vertice) {
                                                    str += vertice.getDado() + " > " + aresta.getDado() + " > " + aresta.getVj().getDado() + "\n";
                                                    break;
                                                }
                                                if (aresta.getVj() == vertice) {
                                                    str += vertice.getDado() + "\n";
                                                    break;
                                                }
                                            }
                                        }
                                        new ResultadoFrame("Resultado Dijkstra", str);
                                    }

                                    algoritmoExecutor = null;
                                    VerticeVisual.selectNone(vertexs);
                                    GraphViewer.this.repaint();
                                }
                            }
                        }
                    };

                    algoritmoDesenho.reset();
                    VerticeVisual.selectNone(vertexs);
                    GraphViewer.this.repaint();
                    algoritmoExecutor.nextStep();
                }

            });
        }
        //        {
        //            JMenuItem mnuAlgoritmo = new JMenuItem("Emparelhamento Perfeito Máximo");
        //            mnuAlgoritmos.add(mnuAlgoritmo);
        //            mnuAlgoritmo.addActionListener(new ActionListener() {
        //
        //                @Override
        //                public void actionPerformed(ActionEvent e) {
        //                    JOptionPane.showMessageDialog(null, "Não implementado");
        //                }
        //
        //            });
        //        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Floyd-Warshall");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    algoritmoExecutor = new IAlgoritmoExecutor() {

                        boolean messageV1 = false;

                        VerticeVisual v1;

                        @Override
                        public void nextStep(VerticeVisual... vertices) {
                            if (!messageV1) {
                                info("Selecione o vértice inicial.");
                                messageV1 = true;
                                return;
                            }

                            if (vertices != null && vertices.length == 1) {
                                if (v1 == null) {
                                    v1 = vertices[0];

                                    Grafo g = g();

                                    Vertice _v1 = g.getVerticeById(v1.id);

                                    AlgoritmoFloydWarshall dfs = new AlgoritmoFloydWarshall();
                                    dfs.executar(g);

                                    AlgoritmoFloydWarshallResultado resultado = dfs.getResultado();

                                    // frame
                                    String str = "Resultado Floyd-Warshall:\n";

                                    for (int i = 0; i < g.getTamanho(); i++) {
                                        Vertice vertice = g.getVertice(i);
                                        if (vertice.getId() == _v1.getId()) {
                                            continue;
                                        }

                                        str += "Menor caminho para o vértice " + vertice.getDado() + " até o vértice " + _v1.getDado() + "\n";

                                        double total = 0.0;
                                        Vertice a = vertice;
                                        Vertice anterior = null;
                                        while (a.getId() != _v1.getId()) {
                                            str += " " + a.getDado();
                                            anterior = a;
                                            a = resultado.getPredecessor(_v1, a);

                                            Aresta aresta;
                                            try {
                                                aresta = g.getArestaByVertices(a, anterior);
                                            } catch (NullPointerException e) {
                                                aresta = g.getArestaByVertices(anterior, a);
                                            }
                                            str += " Valor: " + aresta.getValor() + "\n";

                                            total += aresta.getValor();
                                        }

                                        str += "Valor total: " + total + "\n\n";
                                    }

                                    new ResultadoFrame("Resultado Floyd-Warshall", str);

                                    algoritmoExecutor = null;
                                    VerticeVisual.selectNone(vertexs);
                                    GraphViewer.this.repaint();
                                }
                            }
                        }

                    };

                    algoritmoDesenho.reset();
                    VerticeVisual.selectNone(vertexs);
                    GraphViewer.this.repaint();
                    algoritmoExecutor.nextStep();
                }

            });
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Ford-Fulkerson");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    algoritmoExecutor = new IAlgoritmoExecutor() {

                        boolean messageV1 = false;

                        VerticeVisual v1, v2;

                        @Override
                        public void nextStep(VerticeVisual... vertices) {
                            if (!messageV1) {
                                info("Selecione o vértice inicial.");
                                messageV1 = true;
                                return;
                            }

                            if (vertices != null && vertices.length == 1) {
                                if (v1 == null) {
                                    v1 = vertices[0];
                                    info("Selecione o vértice final.");

                                    algoritmoDesenho.coresVertices.put(v1, Color.MAGENTA);
                                    GraphViewer.this.repaint();

                                    VerticeVisual.selectNone(vertexs);
                                    GraphViewer.this.repaint();
                                } else {
                                    if (v1 == vertices[0]) {
                                        info("Vértice inicial e final devem ser diferentes");
                                        return;
                                    }
                                    v2 = vertices[0];

                                    Grafo g = g();

                                    Vertice _v1 = g.getVerticeById(v1.id);
                                    Vertice _v2 = g.getVerticeById(v2.id);

                                    AlgoritmoFordFulkerson fordFulkerson = new AlgoritmoFordFulkerson();
                                    fordFulkerson.executar(g, _v1, _v2);

                                    AlgoritmoFordFulkersonResultado resultado = fordFulkerson.getResultado();

                                    // frame
                                    String str = "Resultado Ford-Fulkerson:\n";
                                    //        str += "Custo Fluxo: " + resultado.getCustoFluxo() + "\n";
                                    str += "Fluxo Máximo: " + resultado.getFluxoMaximo() + "\n\n";

                                    //        for (Entry<Pair, Double> entry : resultado.getFluxo().entrySet()) {
                                    //            Vertice vi = (Vertice) entry.getKey().getA();
                                    //            Vertice vj = (Vertice) entry.getKey().getB();
                                    //
                                    //            str += vi.getDado();
                                    //            str += " -> ";
                                    //            str += vj.getDado();
                                    //            Aresta aresta = g.getArestaByVertices(vi, vj);
                                    //            if (aresta == null) {
                                    //                aresta = g.getArestaByVertices(vj, vi);
                                    //            }
                                    //            str += ": " + aresta.getCapacidade() + " (";
                                    //            if (entry.getValue() >= 0) {
                                    //                str += entry.getValue();
                                    //            } else {
                                    //                str += entry.getValue();
                                    //            }
                                    //            str += ")\n";
                                    //        }

                                    //for (Vertice vertice : result_vertex) {
                                    //    str += vertice.getDado() + "\n";
                                    //}
                                    new ResultadoFrame("Resultado Ford-Fulkerson", str);

                                    algoritmoExecutor = null;
                                    VerticeVisual.selectNone(vertexs);
                                    GraphViewer.this.repaint();
                                }
                            }
                        }

                    };

                    algoritmoDesenho.reset();
                    VerticeVisual.selectNone(vertexs);
                    GraphViewer.this.repaint();
                    algoritmoExecutor.nextStep();
                }

            });
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Hopcroft-Tarjan");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    algoritmoDesenho.reset();
                    algoritmoExecutor = null;

                    Grafo g = g();

                    AlgoritmoHopcroftTarjan hopcroftTarjan = new AlgoritmoHopcroftTarjan();
                    hopcroftTarjan.executar(g);
                    AlgoritmoHopcroftTarjanResultado resultado = hopcroftTarjan.getResultado();
                    if (resultado.getQtdeComponentes() <= NumeroCromaticoCores.values().length) {

                        for (int i = 0; i < resultado.getQtdeComponentes(); i++) {
                            ArrayList<Vertice> componente = resultado.getComponente(i);
                            Color cor = NumeroCromaticoCores.values()[i].cor;

                            for (Vertice vertice : componente) {
                                algoritmoDesenho.verticesMarcados.add(findVerticeVisual(vertice));
                                algoritmoDesenho.coresVertices.put(findVerticeVisual(vertice), cor);

                                for (int j = 0; j < vertice.getQtdeArestas(); j++) {
                                    Aresta aresta = vertice.getAresta(j);

                                    algoritmoDesenho.arestasMarcadas.add(findArestaVisual(aresta));
                                    algoritmoDesenho.coresArestas.put(findArestaVisual(aresta), cor);
                                }
                            }
                        }
                    }

                    GraphViewer.this.repaint();
                }

            });
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Kruskal");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    algoritmoExecutor = null;
                    algoritmoDesenho.reset();

                    if (hasDirection()) {
                        info("Algoritmo de Kruskal executa somente sobre grafo não dirigidos.");
                        return;
                    }
                    GrafoNaoDirigido g = (GrafoNaoDirigido) g();

                    AlgoritmoKruskal kruskal = new AlgoritmoKruskal();
                    kruskal.executar(g);

                    AlgoritmoKruskalResultado resultado = kruskal.getResultado();

                    String str = "Resultado Kruskal\n";
                    str += "Custo total: " + resultado.getCustoTotal() + "\n";
                    str += "Arestas:\n";
                    for (Aresta aresta : resultado.getArestas()) {
                        Vertice vi = aresta.getVi();
                        Vertice vj = aresta.getVj();

                        str += aresta + "\n";
                        algoritmoDesenho.verticesMarcados.add(findVerticeVisual(vi));
                        algoritmoDesenho.verticesMarcados.add(findVerticeVisual(vj));
                        algoritmoDesenho.arestasMarcadas.add(findArestaVisual(aresta));
                    }

                    new ResultadoFrame("Resultado Kruskal", str);

                    GraphViewer.this.repaint();
                }

            });
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Ordenação Topológica");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    Grafo g = g();
                    if (!hasDirection()) {
                        info("Ordenação topológica executa somente sobre grafos dirigidos");
                        return;
                    }

                    AlgoritmoOrdenacaoTopologica topologica = new AlgoritmoOrdenacaoTopologica();
                    try {
                        topologica.executar((GrafoDirigido) g);
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        info(e1.getMessage());
                        return;
                    }

                    AlgoritmoOrdenacaoTopologicaResultado resultado = topologica.getResultado();

                    String str = "Ordenação topológica:\n";
                    for (Vertice v : resultado.getLista()) {
                        str += (v.getDado() == null ? "(vértice não nomeado)" : v.getDado().toString());
                        str += " Tempo de abertura: " + resultado.getTempoAbertura(v);
                        str += " Tempo de fechamento: " + resultado.getTempoFechamento(v);
                        str += "\n";
                    }

                    new ResultadoFrame("Resultado Ordenação Topológica", str);
                }

            });
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Pontes");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    algoritmoExecutor = null;
                    algoritmoDesenho.reset();

                    Grafo g = g();

                    AlgoritmoPontes pontes = new AlgoritmoPontes();
                    pontes.executar(g);

                    AlgoritmoPontesResultado resultado = pontes.getResultado();
                    if (resultado.getPontes().isEmpty()) {
                        info("Nenhuma ponte encontrada.");
                        return;
                    }

                    // frame
                    String str = "Resultado Pontes:\n";
                    for (Aresta aresta : resultado.getPontes()) {
                        str += aresta.getDado() + "\n";
                        algoritmoDesenho.arestasMarcadas.add(findArestaVisual(aresta));
                    }

                    new ResultadoFrame("Resultado Pontes", str);

                    VerticeVisual.selectNone(vertexs);
                    GraphViewer.this.repaint();
                }

            });
        }
        {
            JMenuItem mnuAlgoritmo = new JMenuItem("Prim");
            mnuAlgoritmos.add(mnuAlgoritmo);
            mnuAlgoritmo.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    algoritmoExecutor = null;
                    algoritmoDesenho.reset();

                    if (hasDirection()) {
                        info("Algoritmo de Prim executa somente sobre grafo não dirigidos.");
                        return;
                    }
                    GrafoNaoDirigido g = (GrafoNaoDirigido) g();

                    AlgoritmoPrim prim = new AlgoritmoPrim();
                    prim.executar(g);

                    AlgoritmoPrimResultado resultado = prim.getResultado();

                    String str = "Resultado Prim\n";
                    str += "Custo total: " + resultado.getCustoTotal() + "\n";
                    str += "Arestas:\n";
                    for (Aresta aresta : resultado.getArestas()) {
                        Vertice vi = aresta.getVi();
                        Vertice vj = aresta.getVj();

                        str += aresta + "\n";
                        algoritmoDesenho.verticesMarcados.add(findVerticeVisual(vi));
                        algoritmoDesenho.verticesMarcados.add(findVerticeVisual(vj));
                        algoritmoDesenho.arestasMarcadas.add(findArestaVisual(aresta));
                    }

                    new ResultadoFrame("Resultado Prim", str);

                    GraphViewer.this.repaint();
                }

            });
        }

        menuBar.add(mnuAlgoritmos);
    }

    /**
     * Metodo que monitora um algoritmo permitindo
     * que o usuário avance ou retroceda nos passos do algoritmo
     *
     * @param vertice_list
     * @param aresta_list
     */
    public void monitora_algoritmo(List<Vertice> vertice_list, List<Aresta> aresta_list) {
        int index_vertice, index_aresta = 0;
        int vertice_size = vertice_list.size();
        int aresta_size = aresta_list.size();

        String str_step = "";
        boolean voltou = false;

        vertice_size -= 1;
        algoritmoDesenho.verticesMarcados.add(findVerticeVisual(vertice_list.get(vertice_size)));
        GraphViewer.this.repaint();

        while (vertice_size > 0 && aresta_size > 0) {
            GraphViewer.this.repaint();
            voltou = false;
            index_vertice = vertice_size - 1;
            index_aresta = aresta_size - 1;

            if (aresta_list.size() != 0 && aresta_list.size() != index_aresta) {
                int opcao = 0;
                str_step = "Encontrado caminho para o vertice: " + vertice_list.get(index_vertice) + " através da aresta " + aresta_list.get(index_aresta);
                str_step += "\nDeseja avançar para o próximo passo?\n";
                opcao = JOptionPane.showConfirmDialog(null, str_step, "Execução do algoritmo..", JOptionPane.YES_NO_CANCEL_OPTION);
                if (opcao == JOptionPane.NO_OPTION) {
                    str_step = "Deseja retornar ao último passo executado?";
                    opcao = JOptionPane.showConfirmDialog(null, str_step, "Execução do algoritmo..", JOptionPane.YES_NO_OPTION);
                    if (opcao == JOptionPane.NO_OPTION) {
                        return;
                    }
                    voltou = true;

                    vertice_size += 1;
                    aresta_size += 1;
                    index_aresta += 1;
                    index_vertice += 1;
                    if (aresta_list.size() != index_aresta && vertice_list.size() != index_vertice) {
                        algoritmoDesenho.arestasMarcadas.remove(findArestaVisual(aresta_list.get(index_aresta)));
                        algoritmoDesenho.verticesMarcados.remove(findVerticeVisual(vertice_list.get(index_vertice)));
                        GraphViewer.this.repaint();
                    } else {
                        algoritmoDesenho.arestasMarcadas.clear();
                        algoritmoDesenho.verticesMarcados.clear();
                        GraphViewer.this.repaint();
                        JOptionPane.showMessageDialog(null, "Não existem mais passos para retornar!");
                        vertice_size = vertice_list.size();
                        algoritmoDesenho.verticesMarcados.add(findVerticeVisual(vertice_list.get(index_vertice)));
                        vertice_size -= 1;
                        aresta_size = aresta_list.size();
                    }
                } else if (opcao == JOptionPane.CANCEL_OPTION) {
                    return;
                }
                if (!voltou && aresta_size != 0) {
                    aresta_size -= 1;
                    algoritmoDesenho.arestasMarcadas.add(findArestaVisual(aresta_list.get(index_aresta)));
                    GraphViewer.this.repaint();
                }
            }
            if (!voltou && vertice_size != 0) {
                vertice_size -= 1;
                algoritmoDesenho.verticesMarcados.add(findVerticeVisual(vertice_list.get(index_vertice)));
                GraphViewer.this.repaint();
            }
        }
    }

    private void menuPropriedades(JMenuBar menuBar) {
        /*
         * Menu Propriedades
         */
        JMenu mnuPropriedades = new JMenu("Propriedades");

        JMenuItem mnuPropriedadeConexo = new JMenuItem("Conexo");
        mnuPropriedadeConexo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Conexo: " + g().ehConexo());
            }

        });
        mnuPropriedades.add(mnuPropriedadeConexo);

        JMenuItem mnuPropriedadeDesconexo = new JMenuItem("Desconexo");
        mnuPropriedadeDesconexo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Desconexo: " + g().ehDesconexo());
            }

        });
        mnuPropriedades.add(mnuPropriedadeDesconexo);

        JMenuItem mnuPropriedadeNulo = new JMenuItem("Nulo");
        mnuPropriedadeNulo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Nulo: " + g().ehNulo());
            }

        });
        mnuPropriedades.add(mnuPropriedadeNulo);

        JMenuItem mnuPropriedadeTrivial = new JMenuItem("Trivial");
        mnuPropriedadeTrivial.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Trivial: " + g().ehTrivial());
            }

        });
        mnuPropriedades.add(mnuPropriedadeTrivial);

        JMenuItem mnuPropriedadeRegular = new JMenuItem("Regular");
        mnuPropriedadeRegular.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Regular: " + g().ehRegular());
            }

        });
        mnuPropriedades.add(mnuPropriedadeRegular);

        JMenuItem mnuPropriedadeDenso = new JMenuItem("Denso");
        mnuPropriedadeDenso.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Denso: " + g().ehDenso());
            }

        });
        mnuPropriedades.add(mnuPropriedadeDenso);

        JMenuItem mnuPropriedadeEsparso = new JMenuItem("Esparso");
        mnuPropriedadeEsparso.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Esparso: " + g().ehEsparso());
            }

        });
        mnuPropriedades.add(mnuPropriedadeEsparso);

        JMenuItem mnuPropriedadeSimples = new JMenuItem("Simples");
        mnuPropriedadeSimples.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Simples: " + g().ehSimples());
            }

        });
        mnuPropriedades.add(mnuPropriedadeSimples);

        JMenuItem mnuPropriedadeMultigrafo = new JMenuItem("Multigrafo");
        mnuPropriedadeMultigrafo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Multigrafo: " + g().ehMultigrafo());
            }

        });
        mnuPropriedades.add(mnuPropriedadeMultigrafo);

        JMenuItem mnuPropriedadeCompleto = new JMenuItem("Completo");
        mnuPropriedadeCompleto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Completo: " + g().ehCompleto());
            }

        });
        mnuPropriedades.add(mnuPropriedadeCompleto);

        JMenuItem mnuPropriedadeBipartido = new JMenuItem("Bipartido");
        mnuPropriedadeBipartido.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Bipartido: " + g().ehBipartido());
            }

        });
        mnuPropriedades.add(mnuPropriedadeBipartido);

        JMenuItem mnuPropriedadeBipartidoCompleto = new JMenuItem("Bipartido completo");
        mnuPropriedadeBipartidoCompleto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Bipartido completo: " + g().ehBipartidoCompleto());
            }

        });
        mnuPropriedades.add(mnuPropriedadeBipartidoCompleto);

        JMenuItem mnuPropriedadeCiclo = new JMenuItem("Ciclo");
        mnuPropriedadeCiclo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Ciclo: " + g().ehCiclo());
            }

        });
        mnuPropriedades.add(mnuPropriedadeCiclo);

        JMenuItem mnuPropriedadeAciclico = new JMenuItem("Aciclico");
        mnuPropriedadeAciclico.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Aciclico: " + g().ehAciclico());
            }

        });
        mnuPropriedades.add(mnuPropriedadeAciclico);

        JMenuItem mnuPropriedadeArvore = new JMenuItem("Arvore/Arborescencia");
        mnuPropriedadeArvore.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (g().ehDirigido()) {
                    JOptionPane.showMessageDialog(null, "Arborescencia: " + ((GrafoDirigido) g()).ehArborescencia());
                } else {
                    JOptionPane.showMessageDialog(null, "Arvore: " + ((GrafoNaoDirigido) g()).ehArvore());
                }

            }

        });
        mnuPropriedades.add(mnuPropriedadeArvore);

        JMenuItem mnuPropriedadeFloresta = new JMenuItem("Floresta");
        mnuPropriedadeFloresta.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Floresta: " + g().ehFloresta());
            }

        });
        mnuPropriedades.add(mnuPropriedadeFloresta);

        // ====================================================================
        JMenuItem mnuPropriedadeNumeroCromatico = new JMenuItem("Número Cromático");
        mnuPropriedadeNumeroCromatico.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Grafo g = g();
                Map<Vertice, Integer> numeroCromatico = g.getNumeroCromatico();

                int max = 0;
                for (Integer i : numeroCromatico.values()) {
                    max = Math.max(max, i);
                }
                max++; // inicia em zero.
                if (g.getTamanho() == 0) {
                    max = 0;
                }

                algoritmoDesenho.reset();
                if (max <= NumeroCromaticoCores.values().length) {
                    for (Entry<Vertice, Integer> entry : numeroCromatico.entrySet()) {
                        algoritmoDesenho.coresVertices.put(findVerticeVisual(entry.getKey()), NumeroCromaticoCores.values()[entry.getValue()].cor);
                    }
                }
                GraphViewer.this.repaint();
                JOptionPane.showMessageDialog(null, "Número Cromático: " + max);
            }

        });
        mnuPropriedades.add(mnuPropriedadeNumeroCromatico);

        JMenuItem mnuPropriedadeHipercubo = new JMenuItem("Hipercubo");
        mnuPropriedadeHipercubo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Grafo g = g();
                if (g instanceof GrafoNaoDirigido) {
                    boolean ehHipercubo = ((GrafoNaoDirigido) g()).ehHipercubo();
                    JOptionPane.showMessageDialog(null, "O grafo " + (ehHipercubo ? "" : "não ") + "apresenta a propriedade hipercubo.");
                } else {
                    JOptionPane.showMessageDialog(null, "Hipercubos se aplicam somente para grafos não dirigidos.");
                }
            }

        });
        mnuPropriedades.add(mnuPropriedadeHipercubo);

        JMenuItem mnuPropriedadeIsomorfismo = new JMenuItem("Isomorfismo");
        mnuPropriedadeIsomorfismo.addActionListener(new ActionListener() {

            final JFileChooser chooser = new JFileChooser();

            @Override
            public void actionPerformed(ActionEvent e) {
                if (hasDirection()) {
                    JOptionPane.showMessageDialog(null, "Isomorfismo implementado aplica-se somente para grafos não dirigidos.");
                    return;
                }

                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setMultiSelectionEnabled(false);
                chooser.setDialogType(JFileChooser.OPEN_DIALOG);
                chooser.setDialogTitle("Carregar");

                //In response to a button click:
                int returnVal = chooser.showDialog(GraphViewer.this, "Carregar");
                if (returnVal != JFileChooser.APPROVE_OPTION) {
                    return;
                }

                System.out.println("You choose to open this file: " + chooser.getSelectedFile().getName());
                File file = chooser.getSelectedFile();

                DefinicaoGrafoVisual definicaoGrafoVisual = PersistenciaVisual.carregarGrafo(file);

                if (hasDirection(definicaoGrafoVisual.arestas)) {
                    JOptionPane.showMessageDialog(null, "Isomorfismo implementado aplica-se somente para grafos não dirigidos.");
                    return;
                }

                GrafoNaoDirigido g1 = (GrafoNaoDirigido) buildGraph(vertexs, edges);
                GrafoNaoDirigido g2 = (GrafoNaoDirigido) buildGraph(definicaoGrafoVisual.vertices, definicaoGrafoVisual.arestas);

                PropriedadeIsomorfismoResultado isomorfismo = g1.ehIsomorfico(g2);
                if (isomorfismo == null) {
                    JOptionPane.showMessageDialog(null, "Os grafos não são isomorfos.");
                } else {
                    new ResultadoFrame("Resultado Isomorfismo", isomorfismo.toString());
                }
            }

        });
        mnuPropriedades.add(mnuPropriedadeIsomorfismo);

        //        JMenuItem mnuPropriedadePlanar = new JMenuItem("Planar");
        //        mnuPropriedadePlanar.addActionListener(new ActionListener() {
        //
        //            @Override
        //            public void actionPerformed(ActionEvent e) {
        //                JOptionPane.showMessageDialog(null, "Não implementado");
        //            }
        //
        //        });
        //        mnuPropriedades.add(mnuPropriedadePlanar);

        menuBar.add(mnuPropriedades);
    }

    private void menuInfoDidatico(JMenuBar menuBar) {
        /*
         * Menu de informações didáticas
         */
        JMenu menuInfoDidatico = new JMenu("Info. Didáticas");

        JMenuItem infoDidaticoDijkstra = new JMenuItem("Dijkstra");
        infoDidaticoDijkstra.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Algoritmo de Dijkstra: ");
            }

        });
        menuInfoDidatico.add(infoDidaticoDijkstra);

        JMenuItem infoDidaticoBuscaLargura = new JMenuItem("Busca em Largura");
        infoDidaticoBuscaLargura.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Busca em Largura: ");
            }

        });
        menuInfoDidatico.add(infoDidaticoBuscaLargura);

        JMenuItem infoDidaticoBuscaProfundidade = new JMenuItem("Busca em Profundidade");
        infoDidaticoBuscaProfundidade.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Busca em Profundidade: ");
            }

        });
        menuInfoDidatico.add(infoDidaticoBuscaProfundidade);

        menuBar.add(menuInfoDidatico);
    }

    private Grafo g() {
        return buildGraph(vertexs, edges);
    }

    public GraphViewer() {
        setOpaque(true);
        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());
    }

    private void addKeyShortcut() {
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("GraphViewer.addKeyShortcut().new KeyAdapter() {...}.keyPressed()");
                // TODO Auto-generated method stub
                super.keyPressed(e);
            }

        });
        scrollPane.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("GraphViewer.addKeyShortcut().new KeyAdapter() {...}.keyPressed()");
                // TODO Auto-generated method stub
                super.keyPressed(e);
            }

            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    VerticeVisual.getSelected(vertexs, selected);

                    // FIXME: refatorar, mesmo código que o DeleteAction
                    ListIterator<VerticeVisual> iter = selected.listIterator();
                    while (iter.hasNext()) {
                        VerticeVisual n = iter.next();
                        if (n.isSelected()) {
                            deleteEdges(n);
                            iter.remove();
                        }
                    }

                    updateFooter();
                    repaint();
                }
            }

            // FIXME: código duplicado
            private void deleteEdges(VerticeVisual n) {
                ListIterator<ArestaVisual> iter = edges.listIterator();
                while (iter.hasNext()) {
                    ArestaVisual e = iter.next();
                    if (e.n1 == n || e.n2 == n) {
                        iter.remove();
                    }
                }
            }

        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HIGH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(0x00f0f0f0));
        g.fillRect(0, 0, getWidth(), getHeight());

        for (VerticeVisual n : vertexs) {
            n.drawAlgoritmo(g);
        }
        for (ArestaVisual e : edges) {
            e.draw(g);
        }
        for (VerticeVisual n : vertexs) {
            n.draw(g);
        }
        if (selecting) {
            g.setColor(Color.darkGray);
            g.drawRect(mouseRect.x, mouseRect.y, mouseRect.width, mouseRect.height);
        }
    }

    private ArestaVisual getEdge(VerticeVisual v1, VerticeVisual v2) {
        ArestaVisual edge = null;
        for (ArestaVisual e : edges) {
            if (e.n1 == v1 && e.n2 == v2) {
                edge = e;
                break;
            }
            if (e.n1 == v2 && e.n2 == v1) {
                edge = e;
                break;
            }
        }
        return edge;
    }

    /**
     * @author ande
     */
    private final class CarregarGrafoAction implements ActionListener {

        final JFileChooser chooser = new JFileChooser();

        @Override
        public void actionPerformed(ActionEvent e) {
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setMultiSelectionEnabled(false);
            chooser.setDialogType(JFileChooser.OPEN_DIALOG);
            chooser.setDialogTitle("Carregar");

            //In response to a button click:
            int returnVal = chooser.showDialog(GraphViewer.this, "Carregar");
            if (returnVal != JFileChooser.APPROVE_OPTION) {
                return;
            }

            System.out.println("You choose to open this file: " + chooser.getSelectedFile().getName());
            File file = chooser.getSelectedFile();

            DefinicaoGrafoVisual definicaoGrafoVisual = PersistenciaVisual.carregarGrafo(file);
            vertexs = definicaoGrafoVisual.vertices;
            edges = definicaoGrafoVisual.arestas;
            _id = definicaoGrafoVisual.ultimoIdVertice;

            GraphViewer.this.repaint();
            updateFooter();
        }
    }

    /**
     * @author ande
     */
    private final class SalvarGrafoAction implements ActionListener {

        final JFileChooser chooser = new JFileChooser();

        @Override
        public void actionPerformed(ActionEvent e) {
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setMultiSelectionEnabled(false);
            chooser.setDialogType(JFileChooser.SAVE_DIALOG);
            chooser.setDialogTitle("Salvar");

            //In response to a button click:
            int returnVal = chooser.showDialog(GraphViewer.this, "Salvar");
            if (returnVal != JFileChooser.APPROVE_OPTION) {
                return;
            }

            System.out.println("You choose to open this file: " + chooser.getSelectedFile().getName());

            DefinicaoGrafoVisual definicaoGrafoVisual = new DefinicaoGrafoVisual(vertexs, edges, _id);
            PersistenciaVisual.salvarGrafo(definicaoGrafoVisual, chooser.getSelectedFile());
        }

    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            selecting = false;
            mouseRect.setBounds(0, 0, 0, 0);

            // Verifica se houve seleção, se não tiver, cria o vértice.
            if (e.getPoint().equals(mousePoint)) {
                if (!VerticeVisual.hasNodeAtPosition(vertexs, mousePoint)) {
                    // só cria, caso não possui nenhum selecionado
                    if (VerticeVisual.getQuantitySelected(vertexs) >= 1) {
                        VerticeVisual.selectNone(vertexs);
                    } else {
                        // se o modo é algoritmo, então não cria
                        if (algoritmoExecutor == null) {
                            AddVertexAction action = new AddVertexAction(null);
                            action.actionPerformed(null);
                        }
                    }
                }
            }

            VerticeVisual.getSelected(vertexs, selected);
            validateEditFields();

            if (algoritmoExecutor != null) {
                algoritmoExecutor.nextStep(selected.toArray(new VerticeVisual[selected.size()]));
            }

            if (e.isPopupTrigger()) {
                showPopup(e);
            }
            // updateFooter();
            e.getComponent().repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mousePoint = e.getPoint();

            if (e.isShiftDown()) {
                VerticeVisual.selectToggle(vertexs, mousePoint);
            } else if (e.isPopupTrigger()) {
                VerticeVisual.selectOne(vertexs, mousePoint);
                showPopup(e);
            } else if (VerticeVisual.selectOne(vertexs, mousePoint)) {
                selecting = false;
            } else {
                selecting = true;
            }

            // updateFooter();
            e.getComponent().repaint();
        }

        private void showPopup(MouseEvent e) {
            control.popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    private class MouseMotionHandler extends MouseMotionAdapter {

        Point delta = new Point();

        @Override
        public void mouseDragged(MouseEvent e) {
            if (selecting) {
                mouseRect.setBounds(Math.min(mousePoint.x, e.getX()), Math.min(mousePoint.y, e.getY()), Math.abs(mousePoint.x - e.getX()), Math.abs(mousePoint.y - e.getY()));
                VerticeVisual.selectRect(vertexs, mouseRect);
            } else {
                delta.setLocation(e.getX() - mousePoint.x, e.getY() - mousePoint.y);
                VerticeVisual.updatePosition(vertexs, delta);
                mousePoint = e.getPoint();
            }
            // updateFooter();
            e.getComponent().repaint();
        }
    }

    public JToolBar getControlPanel() {
        return control;
    }

    private void updateFooter() {
        algoritmoDesenho.reset();

        statusLabel.setText(
        /**/"Quantidade de vértices: " + vertexs.size() + ". " +
        /**/"Quantidade de arestas: " + edges.size() + ". " +
        /**/"Tipo do grafo: " + getGraphType());
    }

    /**
     * @return
     */
    private String getGraphType() {
        return hasDirection() ? "Grafo Dirigido" : "Grafo Não Dirigido";
    }

    public void validateEditFields() {
        // algoritmoDesenho.reset();

        VerticeVisual.getSelected(vertexs, selected);

        if (selected.size() == 1) {
            vertexNameLabel.setEnabled(true);
            vertexNameField.setEnabled(true);
            vertexNameField.setText(selected.get(0).name);

            labelValorVertice.setEnabled(true);
            vertexValueField.setEnabled(true);
            vertexValueField.setText(selected.get(0).value);
        } else {
            vertexNameLabel.setEnabled(false);
            vertexNameField.setEnabled(false);
            vertexNameField.setText("");

            labelValorVertice.setEnabled(false);
            vertexValueField.setEnabled(false);
            vertexValueField.setText("");
        }

        btnConectar.setEnabled(selected.size() > 1);
        btnDeleteVertex.setEnabled(selected.size() > 0);

        boolean hasAnyEdge = false;
        a: for (VerticeVisual n1 : selected) {
            for (VerticeVisual n2 : selected) {
                if (n1 != n2) {
                    ArestaVisual edge = getEdge(n1, n2);
                    if (edge != null) {
                        hasAnyEdge = true;
                        break a;
                    }
                }
            }
        }
        btnDeleteEdge.setEnabled(hasAnyEdge);

        if (selected.size() == 2) {
            ArestaVisual edge = getEdge(selected.get(0), selected.get(1));
            if (edge != null) {
                labelDirecao.setEnabled(true);
                direcaoComboField.setEnabled(true);
                direcaoComboField.setSelectedIndex(edge.direction.ordinal());

                labelValorAresta.setEnabled(true);
                labelNameAresta.setEnabled(true);

                nameArestaField.setEnabled(true);
                nameArestaField.setText(edge.name);

                edgeValueField.setEnabled(true);
                edgeValueField.setText(edge.value);
            } else {
                labelDirecao.setEnabled(false);
                direcaoComboField.setEnabled(false);
                direcaoComboField.setSelectedIndex(0);

                labelValorAresta.setEnabled(false);
                labelNameAresta.setEnabled(false);

                nameArestaField.setEnabled(false);
                nameArestaField.setText("");

                edgeValueField.setEnabled(false);
                edgeValueField.setText("");
            }
        } else {
            labelDirecao.setEnabled(false);
            direcaoComboField.setEnabled(false);
            direcaoComboField.setSelectedIndex(0);

            labelValorAresta.setEnabled(false);
            labelNameAresta.setEnabled(false);

            nameArestaField.setEnabled(false);
            nameArestaField.setText("");

            edgeValueField.setEnabled(false);
            edgeValueField.setText("");
        }
    }

    private class ControlPanel extends JToolBar {

        private final Action clearAll = new ClearAction("Limpar");
        private final Action color = new ColorAction("Cor");
        private final Action debug = new DebugAction("Debug");
        private final Action connect = new ConnectAction("Conectar");
        private final Action deleteVertex = new DeleteVertexAction("Apagar vértice");
        private final Action deleteEdge = new DeleteEdgeAction("Apagar aresta");
        private final ColorIcon hueIcon = new ColorIcon(/* Color.blue */new Color(99, 190, 245));
        private final JPopupMenu popup = new JPopupMenu();

        ControlPanel() {
            setLayout(new FlowLayout(FlowLayout.LEFT));
            //            setBackground(Color.lightGray);
            setFloatable(false);

            this.add(new JButton(clearAll));
            this.add(new JButton(color));
            this.add(new JButton(debug));
            this.add(new JLabel(hueIcon));
            jspinner = new JSpinner();
            jspinner.setModel(new SpinnerNumberModel(RADIUS, 5, 100, 5));
            jspinner.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    JSpinner s = (JSpinner) e.getSource();
                    radius = (Integer) s.getValue();
                    VerticeVisual.updateRadius(vertexs, radius);
                    GraphViewer.this.repaint();
                }

            });
            this.add(new JLabel("Tamanho:"));
            this.add(jspinner);

            _addSeparator();

            btnConectar = new JButton(connect);
            btnConectar.setEnabled(false);

            this.add(btnConectar);

            _addSeparator();

            vertexNameLabel = new JLabel("Nome Vértice:");
            vertexNameLabel.setEnabled(false);
            this.add(vertexNameLabel);

            vertexNameField = new JTextField(7);
            vertexNameField.setEnabled(false);
            vertexNameField.getDocument().addDocumentListener(new DocumentListener() {

                @Override
                public void changedUpdate(DocumentEvent e) {
                    warn();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    warn();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    warn();
                }

                public void warn() {
                    if (selected.size() == 1) {
                        selected.get(0).setName(vertexNameField.getText());
                        GraphViewer.this.repaint();
                    }
                }

            });
            this.add(vertexNameField);

            labelValorVertice = new JLabel("Valor Vértice:");
            labelValorVertice.setEnabled(false);
            this.add(labelValorVertice);

            vertexValueField = new JTextField(7);
            vertexValueField.setEnabled(false);
            vertexValueField.getDocument().addDocumentListener(new DocumentListener() {

                @Override
                public void changedUpdate(DocumentEvent e) {
                    warn();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    warn();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    warn();
                }

                public void warn() {
                    if (selected.size() == 1) {
                        selected.get(0).setValue(vertexValueField.getText());
                        GraphViewer.this.repaint();
                    }
                }

            });
            this.add(vertexValueField);

            btnDeleteVertex = new JButton(deleteVertex);
            btnDeleteVertex.setEnabled(false);
            this.add(btnDeleteVertex);

            _addSeparator();

            labelNameAresta = new JLabel("Nome Aresta:");
            labelNameAresta.setEnabled(false);
            this.add(labelNameAresta);

            nameArestaField = new JTextField(7);
            nameArestaField.setEnabled(false);
            nameArestaField.getDocument().addDocumentListener(new DocumentListener() {

                @Override
                public void changedUpdate(DocumentEvent e) {
                    warn();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    warn();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    warn();
                }

                public void warn() {
                    if (selected.size() == 2) {
                        VerticeVisual v1 = selected.get(0);
                        VerticeVisual v2 = selected.get(1);

                        ArestaVisual edge = getEdge(v1, v2);
                        if (edge != null) {
                            edge.setName(nameArestaField.getText());
                            GraphViewer.this.repaint();
                        }
                    }
                }

            });
            this.add(nameArestaField);

            labelValorAresta = new JLabel("Valor Aresta:");
            labelValorAresta.setEnabled(false);

            this.add(labelValorAresta);
            edgeValueField = new JTextField(7);
            edgeValueField.setEnabled(false);
            edgeValueField.getDocument().addDocumentListener(new DocumentListener() {

                @Override
                public void changedUpdate(DocumentEvent e) {
                    warn();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    warn();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    warn();
                }

                public void warn() {
                    if (selected.size() == 2) {
                        VerticeVisual v1 = selected.get(0);
                        VerticeVisual v2 = selected.get(1);

                        ArestaVisual edge = getEdge(v1, v2);
                        if (edge != null) {
                            edge.setValue(edgeValueField.getText());
                            GraphViewer.this.repaint();
                        }
                    }
                }

            });
            this.add(edgeValueField);

            labelDirecao = new JLabel("Direção:");
            labelDirecao.setEnabled(false);
            this.add(labelDirecao);

            direcaoComboField = new JComboBox<String>();
            direcaoComboField.setEnabled(false);
            direcaoComboField.addItem("");
            direcaoComboField.addItem("->");
            direcaoComboField.addItem("<-");
            direcaoComboField.addItem("<->");
            direcaoComboField.addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent e) {
                    String item = (String) ((JComboBox<?>) e.getSource()).getSelectedItem();

                    ArestaVisual edge = getInternalEdge();
                    if (edge == null) {
                        return;
                    }

                    if (item.equals("->")) {
                        edge.direction = ArestaVisual.Direction.RIGHT;
                    } else if (item.equals("<-")) {
                        edge.direction = ArestaVisual.Direction.LEFT;
                    } else if (item.equals("<->")) {
                        edge.direction = ArestaVisual.Direction.BI;
                    } else {
                        edge.direction = ArestaVisual.Direction.NONE;
                    }

                    updateFooter();
                    GraphViewer.this.repaint();
                }

                ArestaVisual getInternalEdge() {
                    if (selected.size() == 2) {
                        VerticeVisual v1 = selected.get(0);
                        VerticeVisual v2 = selected.get(1);

                        ArestaVisual edge = getEdge(v1, v2);
                        return edge;
                    }
                    return null;
                }

            });

            this.add(direcaoComboField);

            btnDeleteEdge = new JButton(deleteEdge);
            btnDeleteEdge.setEnabled(false);

            this.add(btnDeleteEdge);

            //    this.add(new JButton(new AbstractAction("___") {
            //
            //        @Override
            //        public void actionPerformed(ActionEvent e) {
            //            buildGraph();
            //        }
            //
            //    }));

            popup.add(new JMenuItem(color));
            popup.add(new JMenuItem(connect));
            popup.add(new JMenuItem(deleteVertex));
        }

        private void _addSeparator() {
            addSeparator(new Dimension(4, 20));
        }
    }

    private class ClearAction extends AbstractAction {

        public ClearAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            vertexs.clear();
            edges.clear();

            updateFooter();
            repaint();
        }
    }

    private class ColorAction extends AbstractAction {

        public ColorAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Color color = control.hueIcon.getColor();
            color = JColorChooser.showDialog(GraphViewer.this, "Choose a color", color);
            if (color != null) {
                VerticeVisual.updateColor(vertexs, color);
                control.hueIcon.setColor(color);
                control.repaint();
                repaint();
            }
        }
    }

    private class DebugAction extends AbstractAction {

        public DebugAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int opcao = 0;
            if (debugAtivo) {
                opcao = JOptionPane.showConfirmDialog(null, "Opção de Debug já ativa!!\nDeseja desativar esta opção?", "Ativar opção de Debug", JOptionPane.YES_NO_OPTION);
                if (opcao == JOptionPane.YES_OPTION) {
                    debugAtivo = false;
                } else {
                    debugAtivo = true;
                }
            } else {
                opcao = JOptionPane.showConfirmDialog(null, "Deseja ativar a opção de Debug?\n*Esta função está habilitada para os algoritmos de Dijkstra, BFS e DFS", "Ativar opção de Debug", JOptionPane.YES_NO_OPTION);
                if (opcao == JOptionPane.YES_OPTION) {
                    debugAtivo = true;
                } else {
                    debugAtivo = false;
                }
            }
        }
    }

    private class ConnectAction extends AbstractAction {

        public ConnectAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            VerticeVisual.getSelected(vertexs, selected);

            if (selected.size() > 1) {
                for (VerticeVisual e1 : selected) {
                    for (VerticeVisual e2 : selected) {
                        if (e1 != e2) {
                            boolean has = false;
                            for (ArestaVisual edge : edges) {
                                if (edge.n1 == e1 && edge.n2 == e2 //
                                || edge.n1 == e2 && edge.n2 == e1) {
                                    has = true;
                                }
                            }
                            if (!has) {
                                edges.add(new ArestaVisual(e1, e2));
                            }
                        }
                    }
                }
            }

            validateEditFields();
            updateFooter();
            repaint();
        }
    }

    private class DeleteVertexAction extends AbstractAction {

        public DeleteVertexAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ListIterator<VerticeVisual> iter = vertexs.listIterator();
            while (iter.hasNext()) {
                VerticeVisual n = iter.next();
                if (n.isSelected()) {
                    deleteEdges(n);
                    iter.remove();
                }
            }

            validateEditFields();
            updateFooter();
            repaint();
        }

        private void deleteEdges(VerticeVisual n) {
            ListIterator<ArestaVisual> iter = edges.listIterator();
            while (iter.hasNext()) {
                ArestaVisual e = iter.next();
                if (e.n1 == n || e.n2 == n) {
                    iter.remove();
                }
            }
        }
    }

    private class DeleteEdgeAction extends AbstractAction {

        public DeleteEdgeAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            VerticeVisual.getSelected(vertexs, selected);

            for (VerticeVisual v1 : selected) {
                for (VerticeVisual v2 : selected) {
                    if (v1 != v2) {
                        deleteEdges(v1, v2);
                    }
                }
            }
            updateFooter();
            validateEditFields();
            repaint();
        }

        private void deleteEdges(VerticeVisual n1, VerticeVisual n2) {
            ListIterator<ArestaVisual> iter = edges.listIterator();
            while (iter.hasNext()) {
                ArestaVisual e = iter.next();
                if (e.n1 == n1 && e.n2 == n2) {
                    iter.remove();
                }
            }
        }
    }

    private class AddVertexAction extends AbstractAction {

        public AddVertexAction(String name) {
            super(name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            VerticeVisual.selectNone(vertexs);
            Point p = mousePoint.getLocation();
            Color color = control.hueIcon.getColor();
            VerticeVisual n = new VerticeVisual(p, radius, color);
            n.setSelected(true);
            vertexs.add(n);

            validateEditFields();
            updateFooter();
            repaint();
        }
    }

    /**
     * An Edge is a pair of Nodes.
     */
    public static class ArestaVisual {

        public enum Direction {

            NONE, RIGHT, LEFT, BI

        }

        public Direction direction = Direction.NONE;
        public final VerticeVisual n1;
        public final VerticeVisual n2;
        public String value;
        public String name;
        public Color color;

        public ArestaVisual(VerticeVisual n1, VerticeVisual n2) {
            this.n1 = n1;
            this.n2 = n2;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public static Path2D createArrowForLine(Point2D fromPoint, double rotationDeg, double length, double wingsAngleDeg) {
            double ax = fromPoint.getX();
            double ay = fromPoint.getY();

            double radB = Math.toRadians(-rotationDeg + wingsAngleDeg);
            double radC = Math.toRadians(-rotationDeg - wingsAngleDeg);

            Path2D resultPath = new Path2D.Double();
            resultPath.moveTo(length * Math.cos(radB) + ax, length * Math.sin(radB) + ay);
            resultPath.lineTo(ax, ay);
            resultPath.lineTo(length * Math.cos(radC) + ax, length * Math.sin(radC) + ay);
            return resultPath;
        }

        public void draw(Graphics g) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Point _p1 = n1.getLocation();
            Point _p2 = n2.getLocation();

            Point p1 = _p1; // calc(_p1, _p2, n1.r);
            Point p2 = _p2; // calc(_p2, _p1, n2.r);

            if (algoritmoDesenho.arestasMarcadas.contains(this)) {
                color = algoritmoDesenho.coresArestas.get(this);
                if (color == null) {
                    color = algoritmoDesenho.corArestasMarcadas;
                }

                g.setColor(color);

                int size = 2;
                for (int i = -size; i <= size; i++) {
                    g.drawLine(p1.x - i, p1.y - i, p2.x - i, p2.y - i);
                }
                for (int i = -size; i <= size; i++) {
                    g.drawLine(p1.x + i, p1.y + i, p2.x + i, p2.y + i);
                }
                for (int i = -size; i <= size; i++) {
                    g.drawLine(p1.x - i, p1.y + i, p2.x - i, p2.y + i);
                }
                for (int i = -size; i <= size; i++) {
                    g.drawLine(p1.x + i, p1.y - i, p2.x + i, p2.y - i);
                }

                drawArrows(g, p1, p2, color, 4);
            }

            if (value != null || name != null) {
                g.setColor(Color.BLACK);

                String _name = buildName(name, value);
                int str = g.getFontMetrics().stringWidth(_name);
                g.drawString(_name, ((p1.x + p2.x) / 2) //
                - str / 2, //
                (p1.y + p2.y) / 2 - 3);
            }

            g.setColor(Color.darkGray);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);

            drawArrows(g, p1, p2, Color.BLACK, 0);
        }

        private void drawArrows(Graphics g, Point p1, Point p2, Color color, int offset) {
            switch (direction) {
                case RIGHT:
                    drawArrowHead((Graphics2D) g, p2, p1, n2, color, offset);
                    break;
                case LEFT:
                    drawArrowHead((Graphics2D) g, p1, p2, n1, color, offset);
                    break;
                case BI:
                    drawArrowHead((Graphics2D) g, p1, p2, n1, color, offset);
                    drawArrowHead((Graphics2D) g, p2, p1, n2, color, offset);
                    break;
                default:
                    break;
            }
        }

        private Point calc(Point tip, Point tail, int raio) {
            double dy = tip.y - tail.y;
            double dx = tip.x - tail.x;
            double theta = Math.atan2(dy, dx);

            // --
            double n_y = tip.y - raio * Math.sin(theta);
            double n_x = tip.x - raio * Math.cos(theta);

            //System.out.println("theta = " + Math.toDegrees(theta));
            int x1 = (int) n_x;
            int y1 = (int) n_y;

            return new Point(x1, y1);
        }

        static double phi;
        static int barb;

        static {
            phi = Math.toRadians(40);
            barb = 10;
        }

        private void drawArrowHead(Graphics2D g2, Point tip, Point tail, VerticeVisual tipNode, Color color, int offset) {
            g2.setPaint(color);
            double dy = tip.y - tail.y;
            double dx = tip.x - tail.x;
            double theta = Math.atan2(dy, dx);

            // --
            double n_y = tip.y - (tipNode.r - offset) * Math.sin(theta);
            double n_x = tip.x - (tipNode.r - offset) * Math.cos(theta);

            //System.out.println("theta = " + Math.toDegrees(theta));
            int x1 = (int) n_x;
            int y1 = (int) n_y;

            double rho = theta + phi;

            double desloc = (barb + offset * 2);
            int x2 = (int) (x1 - desloc * Math.cos(rho));
            int y2 = (int) (y1 - desloc * Math.sin(rho));

            rho = theta - phi;
            int x3 = (int) (x1 - desloc * Math.cos(rho));
            int y3 = (int) (y1 - desloc * Math.sin(rho));

            triangle(g2, x1, y1, x2, y2, x3, y3);
        }

        void triangle(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3) {
            // Left both the lines and the fill so you could play with color or something.
            //            g.drawLine(x1, y1, x2, y2);
            //            g.drawLine(x2, y2, x3, y3);
            //            g.drawLine(x3, y3, x1, y1);
            g.fillPolygon(new int[] { x1, x2, x3 }, new int[] { y1, y2, y3 }, 3);
        }
    }

    private static Grafo buildGraph(List<VerticeVisual> _nodes, List<ArestaVisual> _edges) {
        // Se tiver alguma aresta dirigida, cria um GrafoDirigido, senão usa GrafoNaoDirigido.
        boolean hasDirection = hasDirection(_edges);

        // cria o grafo.
        Grafo grafo;
        if (hasDirection) {
            grafo = new GrafoDirigido();
        } else {
            grafo = new GrafoNaoDirigido();
        }

        // cria as arestas.
        Map<VerticeVisual, Vertice> mapVertex = new HashMap<VerticeVisual, Vertice>();
        for (VerticeVisual node : _nodes) {
            Vertice vertice = new Vertice(node.id);
            if (isPeso(node.value)) {
                vertice.setDado(node.value);
            }
            vertice.setDado(node.name);

            try {
                grafo.addVertice(vertice);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            mapVertex.put(node, vertice);
        }

        // popula de acordo com as arestas.
        int id_arestas = 1;
        for (ArestaVisual edge : _edges) {
            // ================================================================
            // Grafo Dirigido
            // ================================================================
            if (hasDirection) {
                ArestaDirigida aresta1 = null;
                ArestaDirigida aresta2 = null;

                switch (edge.direction) {
                    case LEFT:
                        aresta1 = new ArestaDirigida(id_arestas++, mapVertex.get(edge.n2), mapVertex.get(edge.n1));
                        if (edge.name != null && !edge.name.isEmpty()) {
                            aresta1.setDado(edge.name);
                        }
                        if (isPeso(edge.value)) {
                            aresta1.setValor(Double.parseDouble(edge.value));
                            aresta1.setCapacidade(Double.parseDouble(edge.value));
                        }
                        break;
                    case RIGHT:
                        aresta2 = new ArestaDirigida(id_arestas++, mapVertex.get(edge.n1), mapVertex.get(edge.n2));
                        if (edge.name != null && !edge.name.isEmpty()) {
                            aresta2.setDado(edge.name);
                        }
                        if (isPeso(edge.value)) {
                            aresta2.setValor(Double.parseDouble(edge.value));
                            aresta2.setCapacidade(Double.parseDouble(edge.value));
                        }
                        break;
                    case BI:
                    case NONE:
                        aresta1 = new ArestaDirigida(id_arestas++, mapVertex.get(edge.n2), mapVertex.get(edge.n1));
                        aresta2 = new ArestaDirigida(id_arestas++, mapVertex.get(edge.n1), mapVertex.get(edge.n2));
                        if (edge.name != null && !edge.name.isEmpty()) {
                            aresta1.setDado(edge.name);
                            aresta2.setDado(edge.name);
                        }

                        if (isPeso(edge.value)) {
                            aresta1.setValor(Double.parseDouble(edge.value));
                            aresta1.setCapacidade(Double.parseDouble(edge.value));
                            aresta2.setValor(Double.parseDouble(edge.value));
                            aresta2.setCapacidade(Double.parseDouble(edge.value));
                        }
                        break;
                }
                try {
                    if (aresta1 != null) {
                        ((GrafoDirigido) grafo).addAresta(aresta1);
                    }
                    if (aresta2 != null) {
                        ((GrafoDirigido) grafo).addAresta(aresta2);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                // ================================================================
                // Grafo Não-Dirigido
                // ================================================================
                ArestaNaoDirigida aresta = new ArestaNaoDirigida(id_arestas++, mapVertex.get(edge.n1), mapVertex.get(edge.n2));
                try {
                    if (edge.name != null && !edge.name.isEmpty()) {
                        aresta.setDado(edge.name);
                    }

                    if (isPeso(edge.value)) {
                        aresta.setValor(Double.parseDouble(edge.value));
                        aresta.setCapacidade(Double.parseDouble(edge.value));
                    }
                    ((GrafoNaoDirigido) grafo).addAresta(aresta);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return grafo;
    }

    private static boolean isPeso(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean hasDirection() {
        return hasDirection(edges);
    }

    private static boolean hasDirection(List<ArestaVisual> _edges) {
        boolean hasDirection = false;
        for (ArestaVisual edge : _edges) {
            if (edge.direction != ArestaVisual.Direction.NONE) {
                hasDirection = true;
                break;
            }
        }
        return hasDirection;
    }

    private VerticeVisual findVerticeVisual(Vertice v) {
        for (VerticeVisual visual : vertexs) {
            if (visual.id == v.getId()) {
                return visual;
            }
        }
        // TODO Auto-generated method stub
        System.out.println(" NOT FOUND!! " + v);
        return null;
    }

    private ArestaVisual findArestaVisual(Aresta a) {
        Vertice vi = a.getVi();
        Vertice vj = a.getVj();

        for (ArestaVisual visual : edges) {
            if (visual.n1.id == vi.getId() && visual.n2.id == vj.getId()) {
                return visual;
            }
            if (visual.n2.id == vi.getId() && visual.n1.id == vj.getId()) {
                return visual;
            }
        }
        System.out.println("NOT FOUND!! " + a);
        return null;
    }

    static int _id;

    static int nextId() {
        return ++_id;
    }

    private static String buildName(String name, String value) {
        boolean n = name == null || name.isEmpty();
        boolean v = value == null || value.isEmpty();

        if (n && v) {
            return "";
        }
        if (n) {
            return "(" + value + ")";
        }
        if (v) {
            return name;
        }
        return name + " (" + value + ")";
    }

    /**
     * A Node represents a node in a graph.
     */
    public static class VerticeVisual {

        private static final Color COLOR_MIDDLE_BLACK = new Color(51, 51, 51);

        public int id;

        private final Point p;
        private int r;
        private Color color;
        private transient boolean selected = false;
        private final Rectangle b = new Rectangle();
        private String name;
        private String value;

        /**
         * Construct a new node.
         */
        public VerticeVisual(Point p, int r, Color color) {
            this.p = p;
            this.r = r;
            this.color = color;
            setBoundary(b);
            id = nextId();
        }

        /**
         * @param text
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @param g
         */
        public void drawAlgoritmo(Graphics g) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (algoritmoDesenho.verticesMarcados.contains(this)) {
                g.setColor(algoritmoDesenho.corVerticesMarcados);

                g.fillOval(b.x - 3, b.y - 3, b.width + 6, b.height + 6);
            }
        }

        /**
         * @param value the value to set
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Calculate this node's rectangular boundary.
         */
        private void setBoundary(Rectangle b) {
            b.setBounds(p.x - r, p.y - r, 2 * r, 2 * r);
        }

        /**
         * Draw this node.
         */
        public void draw(Graphics g) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (name != null || value != null) {
                g.setColor(Color.BLACK);

                String _name = buildName(name, value);
                int str = g.getFontMetrics().stringWidth(_name);
                g.drawString(_name, b.x // início
                + b.width / 2 // centraliza o texto
                - str / 2, // divide metado do texto para cada lado
                b.y - 4);
            }

            g.setColor(COLOR_MIDDLE_BLACK);
            // if (this.kind == Kind.Circular) {
            g.fillOval(b.x, b.y, b.width, b.height);

            Color corVertice = algoritmoDesenho.coresVertices.containsKey(this) ? algoritmoDesenho.coresVertices.get(this) : color;
            g.setColor(corVertice);
            // if (this.kind == Kind.Circular) {
            g.fillOval(b.x + 1, b.y + 1, b.width - 2, b.height - 2);
            // } else if (this.kind == Kind.Rounded) {
            // g.fillRoundRect(b.x, b.y, b.width, b.height, r, r);
            // } else if (this.kind == Kind.Square) {
            // g.fillRect(b.x, b.y, b.width, b.height);
            // }
            if (selected) {
                g.setColor(Color.darkGray);
                g.drawRect(b.x, b.y, b.width, b.height);
            }
        }

        /**
         * Return this node's location.
         */
        public Point getLocation() {
            return p;
        }

        /**
         * Return true if this node contains p.
         */
        public boolean contains(Point p) {
            return b.contains(p);
        }

        /**
         * Return true if this node is selected.
         */
        public boolean isSelected() {
            return selected;
        }

        /**
         * Mark this node as selected.
         */
        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        /**
         * Collected all the selected nodes in list.
         */
        public static void getSelected(List<VerticeVisual> list, List<VerticeVisual> selected) {
            selected.clear();
            for (VerticeVisual n : list) {
                if (n.isSelected()) {
                    selected.add(n);
                }
            }
        }

        /**
         * Select no nodes.
         */
        public static void selectNone(List<VerticeVisual> list) {
            for (VerticeVisual n : list) {
                n.setSelected(false);
            }
        }

        /**
         * Select a single node; return true if not already selected.
         */
        public static boolean selectOne(List<VerticeVisual> list, Point p) {
            for (VerticeVisual n : list) {
                if (n.contains(p)) {
                    if (!n.isSelected()) {
                        VerticeVisual.selectNone(list);
                        n.setSelected(true);
                    }
                    return true;
                }
            }
            return false;
        }

        public static int getQuantitySelected(List<VerticeVisual> list) {
            int selected = 0;
            for (VerticeVisual n : list) {
                if (n.isSelected()) {
                    selected++;
                }
            }
            return selected;
        }

        public static boolean hasNodeAtPosition(List<VerticeVisual> list, Point p) {
            for (VerticeVisual n : list) {
                if (n.contains(p)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Select each node in r.
         */
        public static void selectRect(List<VerticeVisual> list, Rectangle r) {
            for (VerticeVisual n : list) {
                n.setSelected(r.contains(n.p));
            }
        }

        /**
         * Toggle selected state of each node containing p.
         */
        public static void selectToggle(List<VerticeVisual> list, Point p) {
            for (VerticeVisual n : list) {
                if (n.contains(p)) {
                    n.setSelected(!n.isSelected());
                }
            }
        }

        /**
         * Update each node's position by d (delta).
         */
        public static void updatePosition(List<VerticeVisual> list, Point d) {
            for (VerticeVisual n : list) {
                if (n.isSelected()) {
                    n.p.x += d.x;
                    n.p.y += d.y;
                    n.setBoundary(n.b);
                }
            }
        }

        /**
         * Update each node's radius r.
         */
        public static void updateRadius(List<VerticeVisual> list, int r) {
            for (VerticeVisual n : list) {
                if (n.isSelected()) {
                    n.r = r;
                    n.setBoundary(n.b);
                }
            }
        }

        /**
         * Update each node's color.
         */
        public static void updateColor(List<VerticeVisual> list, Color color) {
            for (VerticeVisual n : list) {
                if (n.isSelected()) {
                    n.color = color;
                }
            }
        }
    }

    private static class ColorIcon implements Icon {

        private static final int WIDE = 20;
        private static final int HIGH = 20;
        private Color color;

        public ColorIcon(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.setColor(color);

            // g.fillRect(x, y, WIDE, HIGH);

            g.fillRoundRect(x, y, WIDE, HIGH, 6, 6);

            // g.fillRect(x, y, WIDE, HIGH);
        }

        @Override
        public int getIconWidth() {
            return WIDE;
        }

        @Override
        public int getIconHeight() {
            return HIGH;
        }
    }

    enum NumeroCromaticoCores {

        _1(new Color(153, 255, 153)), // verde

        _2(new Color(153, 153, 255)), // azul

        _3(new Color(255, 153, 153)), // vermelho

        _4(new Color(255, 158, 95)), // laranja

        _5(new Color(255, 255, 153)), // amarelo

        _6(new Color(255, 102, 255)), // rosa

        _7(new Color(51, 51, 51)), // preto

        _8(Color.WHITE), // branco

        _9(Color.CYAN), // ciano

        _10(Color.LIGHT_GRAY), // cinza claro

        _11(Color.DARK_GRAY); // cinza escuro

        private Color cor;

        NumeroCromaticoCores(Color cor) {
            this.cor = cor;
        }

    }

    private interface IAlgoritmoExecutor {

        void nextStep(VerticeVisual... vertices);

    }

    private void info(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

}
