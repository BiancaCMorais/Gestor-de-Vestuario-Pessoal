package Estatistica;

import Usuario.TelaLogin;
import Usuario.TelaPrincipal;
import Usuario.Usuario;
import java.awt.*;
import javax.swing.*;

public class TelaEstatisticas extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin frame = new TelaLogin();
            frame.setVisible(true);
        });
    }

    public TelaEstatisticas(Usuario usuario) {
    	setTitle("Estatísticas");
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
        	new TelaPrincipal(usuario).setVisible(true);
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
        JLabel titulo = new JLabel("Estatísticas ("+usuario.verNomeUsuario()+")");
        GridBagConstraints gbcTitulo = new GridBagConstraints();
        titulo.setFont(fonteTitulo);
        titulo.setForeground(Color.WHITE);
        gbcTitulo.gridx = 0;
        gbcTitulo.gridy = 0;
        gbcTitulo.gridwidth = 2;
        gbcTitulo.anchor = GridBagConstraints.CENTER;
        panel.add(titulo, gbcTitulo);
        
        String[] labels = {"Itens mais usados", "Itens menos usados", "Looks mais usados", "Looks menos usados", "Itens mais lavados", "Itens menos lavados", "Itens em emprestimo"};
        JFrame[] campos = {new TelaMaisUsadosItens(usuario), 
        		new TelaMenosUsadosItens(usuario), 
        		new TelaMaisUsadosLooks(usuario), 
        		new TelaMenosUsadosLooks(usuario), 
        		new TelaMaisLavados(usuario), 
        		new TelaMenosLavados(usuario),
        		new TelaEmEmprestimo(usuario)};
        
        for (int i = 0; i < labels.length; i++) {
        	final int index = i;
        	JButton Btn = new JButton(labels[index]);
            Btn.setPreferredSize(new Dimension(350, 50));
            Btn.setFont(new Font("Tahoma", Font.PLAIN, 30));
            Btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            GridBagConstraints gbcBtn = new GridBagConstraints();
            gbcBtn.gridx = 0;
            gbcBtn.gridy = i + 1;
            gbcBtn.gridwidth = 4;
            gbcBtn.insets = new Insets(20, 10, 10, 10);
            gbcBtn.anchor = GridBagConstraints.CENTER;
            
            panel.add(Btn, gbcBtn);
            Btn.addActionListener(e -> {
            	campos[index].setVisible(true);
                dispose();
            });
        }
        
        contentPane.add(panel, BorderLayout.CENTER);
    }
    
}
