package Usuario;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class TelaEditarUsuario extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;
    private final JTextField nomeField;
    private final JTextField senhaField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin frame = new TelaLogin();
            frame.setVisible(true);
        });
    }

    public TelaEditarUsuario(Usuario usuario) {
        setTitle("Editar Usuário");
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
            new TelaUsuario(usuario).setVisible(true);
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
        
        JLabel titulo = new JLabel("Editar usuário");
        titulo.setFont(new Font("Serif", Font.BOLD, 40));
        titulo.setForeground(Color.WHITE);
        GridBagConstraints gbcTitulo = new GridBagConstraints();
        gbcTitulo.gridx = 0;
        gbcTitulo.gridy = 0;
        gbcTitulo.gridwidth = 2;
        gbcTitulo.insets = new Insets(10, 10, 20, 10);
        gbcTitulo.anchor = GridBagConstraints.CENTER;
        panel.add(titulo, gbcTitulo);

        nomeField = new JTextField(usuario.verNomeUsuario());
        senhaField = new JTextField(usuario.verSenha());

        String[] labels = {"Nome:", "Senha:"};
        Component[] campos = {nomeField, senhaField};

        Font fonteLabel = new Font("Serif", Font.BOLD, 30);
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

            GridBagConstraints gbcCampo = new GridBagConstraints();
            gbcCampo.gridx = 1;
            gbcCampo.gridy = i + 1;
            gbcCampo.fill = GridBagConstraints.HORIZONTAL;
            gbcCampo.insets = new Insets(5, 10, 5, 10);
            panel.add(campos[i], gbcCampo);
        }
        
        JButton editarBtn = new JButton("Editar");
        editarBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
        editarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editarBtn.addActionListener(e -> {
        	Usuario usuario2 = editarUsuario(usuario);
            if(usuario2!=null){
                new TelaUsuario(usuario2).setVisible(true);
                dispose();
            }
        });
        
        GridBagConstraints gbcBotao = new GridBagConstraints();
        gbcBotao.gridx = 0;
        gbcBotao.gridy = labels.length + 2;
        gbcBotao.gridwidth = 2;
        gbcBotao.insets = new Insets(20, 10, 10, 10);
        gbcBotao.anchor = GridBagConstraints.CENTER;
        panel.add(editarBtn, gbcBotao);

        contentPane.add(panel, BorderLayout.CENTER);
    }

    private Usuario editarUsuario(Usuario usuario) {
        if(!usuario.verNomeUsuario().equals(nomeField.getText().trim()) && PersistenciaDados.verificarUsuarioExistente(nomeField.getText().trim())){
            JOptionPane.showMessageDialog(this, 
                "Já existe um usuário com esse nome!", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            return PersistenciaDados.atualizarRegistro(usuario.verNomeUsuario(),nomeField.getText().trim(),usuario.verSenha(),senhaField.getText().trim());
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao editar usuário: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
