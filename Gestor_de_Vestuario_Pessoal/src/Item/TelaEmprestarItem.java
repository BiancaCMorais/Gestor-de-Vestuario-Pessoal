package Item;

import TiposItem.*;
import Usuario.PersistenciaDados;
import Usuario.TelaLogin;
import Usuario.Usuario;
import java.awt.*;
import javax.swing.*;

public class TelaEmprestarItem extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;
    private JTextField nomeField;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin frame = new TelaLogin();
            frame.setVisible(true);
        });
    }

    public TelaEmprestarItem(Usuario usuario,Item item,JFrame volta) {
        setTitle("Emprestar Item");
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
            new TelaEmprestimoItem(usuario,item,volta).setVisible(true);
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
        
        JLabel titulo = new JLabel("Emprestar ("+item.verNomeItem()+")");
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

        String labels = "Destinatário emprestimo:";
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
        
        JButton emprestarBtn = new JButton("Emprestar");
        emprestarBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
        emprestarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        emprestarBtn.addActionListener(e -> {
            ((IEmprestavel)item).registrarEmprestimo(nomeField.getText());
            PersistenciaDados.salvarDados(usuario);
            new TelaEmprestimoItem(usuario, item,volta).setVisible(true);
            dispose();
        });

        GridBagConstraints gbcBtn = new GridBagConstraints();
        gbcBtn.gridx = 0;
        gbcBtn.gridy = 3; 
        gbcBtn.gridwidth = 2;
        gbcBtn.insets = new Insets(20, 10, 10, 10);
        gbcBtn.anchor = GridBagConstraints.CENTER;

        panel.add(emprestarBtn, gbcBtn);

        contentPane.add(panel, BorderLayout.CENTER);
    }
}
