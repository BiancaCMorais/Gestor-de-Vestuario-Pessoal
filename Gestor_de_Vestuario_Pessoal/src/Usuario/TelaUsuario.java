package Usuario;

import java.awt.*;
import javax.swing.*;

public class TelaUsuario extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;
    private final JLabel nomeField;
    private final JLabel senhaField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin frame = new TelaLogin();
            frame.setVisible(true);
        });
    }

    public TelaUsuario(Usuario usuario) {
        setTitle("Usuário");
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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fonteTitulo = new Font("Serif", Font.BOLD, 40);
        JLabel titulo = new JLabel("Usuário");
        titulo.setFont(fonteTitulo);
        titulo.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titulo, gbc);
        
        nomeField = new JLabel(usuario.verNomeUsuario());
        senhaField = new JLabel(usuario.verSenha());
        
        String[] labels = {"Nome:", "Senha:"};
        Component[] campos = {nomeField, senhaField};

        Font fonteLabel = new Font("Serif", Font.BOLD, 30);
        Font fonteCampo = new Font("Tahoma", Font.PLAIN, 24);
        Color corTexto = Color.WHITE;
        
        for (int i = 0; i < labels.length; i++) {
        	GridBagConstraints gbcLabel = new GridBagConstraints();
            gbcLabel.gridx = 0;
            gbcLabel.gridy = i + 1;
            gbcLabel.anchor = GridBagConstraints.LINE_START;
            gbcLabel.insets = new Insets(5, 10, 5, 10);
            JLabel label = new JLabel(labels[i]);
            label.setFont(fonteLabel);
            label.setForeground(corTexto);
            panel.add(label, gbcLabel);
            
            Component campo = campos[i];
            if (campo instanceof JLabel jLabel) {
                jLabel.setFont(fonteCampo);
                jLabel.setForeground(Color.WHITE);
                jLabel.setPreferredSize(new Dimension(300, 30));
            }
            
            JPanel campoComBorda = new JPanel(new BorderLayout());
            campoComBorda.setBackground(Color.BLACK);
            campoComBorda.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            campoComBorda.add(campo, BorderLayout.CENTER);
        	campoComBorda.setAlignmentX(Component.CENTER_ALIGNMENT);

            GridBagConstraints gbcCampo = new GridBagConstraints();
            gbcCampo.gridx = 1;
            gbcCampo.gridy = i + 1;
            gbcCampo.weightx = 0.0;
            gbcCampo.anchor = GridBagConstraints.LINE_START;
            gbcCampo.insets = new Insets(5, 10, 5, 10);
            panel.add(campoComBorda,gbcCampo);
            
        }
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        botoesPanel.setBackground(Color.BLACK);

        Font fonteBotoes = new Font("Tahoma", Font.PLAIN, 20);

        JButton editarBtn = new JButton("Editar");
        editarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editarBtn.addActionListener(e -> {
            new TelaEditarUsuario(usuario).setVisible(true);
            dispose();
        });
        editarBtn.setFont(fonteBotoes);
        botoesPanel.add(editarBtn);
        
        JButton excluirBtn = new JButton("Excluir");
        excluirBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        excluirBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
            	this,
                "Deseja realmente excluir esse usuário?",
                "Confirmação de Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
            	PersistenciaDados.removerUsuario(usuario.verNomeUsuario(),usuario.verSenha());
               	new TelaLogin().setVisible(true);
               	dispose();
            }
        });
        excluirBtn.setFont(fonteBotoes);
        botoesPanel.add(excluirBtn);
        
        GridBagConstraints gbcBotoes = new GridBagConstraints();
        gbcBotoes.gridx = 0;
        gbcBotoes.gridy = labels.length + 2;
        gbcBotoes.gridwidth = 2;
        gbcBotoes.insets = new Insets(30, 10, 10, 10);
        gbcBotoes.anchor = GridBagConstraints.CENTER;
        panel.add(botoesPanel, gbcBotoes);

        contentPane.add(panel, BorderLayout.CENTER);
    }
}
