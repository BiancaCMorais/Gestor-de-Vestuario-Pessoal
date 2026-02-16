package Item;

import TiposItem.*;
import Usuario.PersistenciaDados;
import Usuario.TelaLogin;
import Usuario.Usuario;
import java.awt.*;
import java.time.LocalDateTime;
import javax.swing.*;

public class TelaUsarItem extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;
    private JTextField nomeField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin frame = new TelaLogin();
            frame.setVisible(true);
        });
    }

    public TelaUsarItem(Usuario usuario,Item item, JFrame volta) {
        setTitle("Usar Item");
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
            new TelaUsoItem(usuario,item,volta).setVisible(true);
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
        
        JLabel titulo = new JLabel("Usar ("+item.verNomeItem()+")");
        titulo.setFont(new Font("Serif", Font.BOLD, 40));
        titulo.setForeground(Color.WHITE);
        GridBagConstraints gbcTitulo = new GridBagConstraints();
        gbcTitulo.gridx = 0;
        gbcTitulo.gridy = 0;
        gbcTitulo.gridwidth = 2;
        gbcTitulo.insets = new Insets(10, 10, 20, 10);
        gbcTitulo.anchor = GridBagConstraints.CENTER;
        panel.add(titulo, gbcTitulo);
        
        nomeField = new JTextField(20);
        nomeField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        nomeField.setPreferredSize(new Dimension(200, 30));

        String labels = "Evento:";
        Component campos = nomeField;

        Font fonteLabel = new Font("Serif", Font.BOLD, 30);
        Color corTexto = Color.WHITE;

        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridx = 0;
        gbcLabel.gridy = 1;
        gbcLabel.anchor = GridBagConstraints.LINE_START;
        gbcLabel.insets = new Insets(5, 10, 5, 10);
        JLabel label = new JLabel(labels);
        label.setFont(fonteLabel);
        label.setForeground(corTexto);
        panel.add(label, gbcLabel);

        GridBagConstraints gbcCampo = new GridBagConstraints();
        gbcCampo.gridx = 1;
        gbcCampo.gridy = 1;
        gbcCampo.fill = GridBagConstraints.HORIZONTAL;
        gbcCampo.insets = new Insets(5, 10, 5, 10);
        panel.add(campos, gbcCampo);
        
        JButton usarBtn = new JButton("Usar");
        usarBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
        usarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        usarBtn.addActionListener(e -> {
        	LocalDateTime agora = LocalDateTime.now();
            String nome = nomeField.getText();
            item.usarItem(agora, nome);
            PersistenciaDados.salvarDados(usuario);
            new TelaUsoItem(usuario, item,volta).setVisible(true);
            dispose();
        });

        GridBagConstraints gbcBotao = new GridBagConstraints();
        gbcBotao.gridx = 0;
        gbcBotao.gridy = 4; 
        gbcBotao.gridwidth = 2;
        gbcBotao.insets = new Insets(20, 10, 10, 10);
        gbcBotao.anchor = GridBagConstraints.CENTER;

        panel.add(usarBtn, gbcBotao);

        contentPane.add(panel, BorderLayout.CENTER);
    }
}