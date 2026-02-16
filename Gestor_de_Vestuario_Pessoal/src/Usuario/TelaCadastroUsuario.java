package Usuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class TelaCadastroUsuario extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;
    private final JTextField nomeField;
    private final JPasswordField senhaField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin frame = new TelaLogin();
            frame.setVisible(true);
        });
    }

    public TelaCadastroUsuario() {
        setTitle("Cadastrar Usuário");
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
        	new TelaLogin().setVisible(true);
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
        JLabel titulo = new JLabel("Cadastrar usuário");
        titulo.setFont(fonteTitulo);
        titulo.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titulo, gbc);
        
        nomeField = new JTextField(20);
        senhaField = new JPasswordField(20);
        
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

        JButton cadastrarBtn = new JButton("Cadastrar");
        cadastrarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cadastrarBtn.addActionListener((ActionEvent e) -> {
            cadastrarUsuario();
        });
        cadastrarBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
        GridBagConstraints gbcBtn = new GridBagConstraints();
        gbcBtn.gridx = 0;
        gbcBtn.gridy = 3;
        gbcBtn.gridwidth = 2;
        gbcBtn.insets = new Insets(20, 10, 10, 10);
        gbcBtn.anchor = GridBagConstraints.CENTER;
        panel.add(cadastrarBtn, gbcBtn);

        contentPane.add(panel, BorderLayout.CENTER);
    }

    private void cadastrarUsuario() {
        if(PersistenciaDados.verificarUsuarioExistente(nomeField.getText().trim())){
            JOptionPane.showMessageDialog(this, 
                "Já existe um usuário com esse nome!", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nome = nomeField.getText().trim();
        String senha = new String(senhaField.getPassword()).trim();

        if (nome.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Preencha todos os campos!", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            
            Usuario usuario = new Usuario(nome, senha);
            
            PersistenciaDados.salvarDados(usuario);
            
            JOptionPane.showMessageDialog(this, 
                "Usuário cadastrado com sucesso!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            new TelaLogin().setVisible(true);
            this.dispose();
            
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao cadastrar usuário: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
