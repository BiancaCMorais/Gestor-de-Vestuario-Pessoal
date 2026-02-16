package Estatistica;

import Usuario.TelaLogin;
import Usuario.Usuario;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class TelaMaisUsadosItens extends JFrame{
	private static final long serialVersionUID = 1L;
    private final JPanel contentPane;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin frame = new TelaLogin();
            frame.setVisible(true);
        });
    }

    public TelaMaisUsadosItens(Usuario usuario) {
    	setTitle("Itens mais usados");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Color.BLACK);
        setContentPane(contentPane);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(54,54,54)); 
        topBar.setPreferredSize(new Dimension(getWidth(), 80));
        
        JLabel tituloTopo = new JLabel("Gestor de Vestuário Pessoal", SwingConstants.CENTER);
        tituloTopo.setForeground(Color.WHITE);
        tituloTopo.setFont(new Font("Stencil", Font.BOLD, 50));
        topBar.add(tituloTopo, BorderLayout.CENTER);
        
        JButton voltarBtn = new JButton("Voltar");
        voltarBtn.addActionListener(e -> {
        	new TelaEstatisticas(usuario).setVisible(true);
            dispose();
        });
        voltarBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
        voltarBtn.setFocusPainted(false);
        voltarBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        voltarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel voltarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        voltarPanel.setBackground(Color.BLACK);
        voltarPanel.add(voltarBtn);

        JPanel topArea = new JPanel(new BorderLayout());
        topArea.setBackground(Color.BLACK);
        topArea.add(topBar, BorderLayout.NORTH);
        topArea.add(voltarPanel, BorderLayout.SOUTH);
        contentPane.add(topArea, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);

        Font fonteTitulo = new Font("Serif", Font.BOLD, 40);
        JLabel titulo = new JLabel("Itens mais usados ("+usuario.verNomeUsuario()+")");
        GridBagConstraints gbcTitulo = new GridBagConstraints();
        titulo.setFont(fonteTitulo);
        titulo.setForeground(Color.WHITE);
        gbcTitulo.gridx = 0;
        gbcTitulo.gridy = 0;
        gbcTitulo.gridwidth = 2;
        gbcTitulo.anchor = GridBagConstraints.CENTER;
        panel.add(titulo, gbcTitulo);
        
        Font fonteCampo = new Font("Tahoma", Font.PLAIN, 24);
        Dimension campoSize = new Dimension(300, 35);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        Estatisticas estatisticas = new Estatisticas();
        Map<String,Integer> crescenteUsoItem = estatisticas.ordenacaoUsadoItem(usuario);
        Map<String,Integer> decrescenteItem = new LinkedHashMap<>();

        if (crescenteUsoItem != null) {
            decrescenteItem = mostrarEstatisticasDecrescente(crescenteUsoItem);
        }
        int linhaAtual=1;
        String texto;
        if(!usuario.verVestuario().isEmpty()) {
        	for (Map.Entry<String,Integer> entry : decrescenteItem.entrySet()) {
                JLabel lavagem = new JLabel(entry.getValue()+" " + entry.getKey());

                lavagem.setFont(fonteCampo);
                lavagem.setForeground(Color.WHITE);

                JPanel campoComBorda = new JPanel(new BorderLayout());
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                campoComBorda.setFont(new Font("Tahoma", Font.PLAIN, 20));
                campoComBorda.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                campoComBorda.setBackground(Color.BLACK);
                campoComBorda.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
                campoComBorda.add(lavagem, BorderLayout.LINE_START);
                campoComBorda.setPreferredSize(campoSize);

                GridBagConstraints gbcCampo = new GridBagConstraints();
                
                int botaoLargura = (int)(screenSize.width * 0.6);
        	    int botaoAltura = 35;
        	    campoComBorda.setPreferredSize(new Dimension(botaoLargura, botaoAltura));
        	    gbcCampo.insets = new Insets(5, 0, 5, 0);
        	    gbcCampo.gridx = 0;
        	    gbcCampo.gridy = linhaAtual;
        	    gbcCampo.gridwidth = 1;
        	    gbcCampo.anchor = GridBagConstraints.CENTER;
        	    gbcCampo.fill = GridBagConstraints.NONE;

        	    linhaAtual++;
                
                panel.add(campoComBorda, gbcCampo);
        	}
        }else {
        	texto = "Nenhum item cadastrado";
        	JLabel label = new JLabel(texto);
            label.setFont(fonteTitulo);
            label.setForeground(Color.WHITE);
            GridBagConstraints gbc1 = new GridBagConstraints();
            gbc1.gridx = 1;
            gbc1.gridy = 6;
            panel.add(label, gbc1);
        }
        
        
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null); 
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.getViewport().setBackground(Color.BLACK); 

        contentPane.add(scrollPane, BorderLayout.CENTER);
    }
    private Map<String,Integer> mostrarEstatisticasDecrescente(Map<String, Integer> mapa) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(mapa.entrySet());
        Map<String, Integer> entriesReverse = new LinkedHashMap<>();
        for (int i = entries.size() - 1; i >= 0; i--) {
        	Map.Entry<String, Integer> entry = entries.get(i);
            entriesReverse.put(entry.getKey(), entry.getValue());
        }
        return entriesReverse;
    }
}
