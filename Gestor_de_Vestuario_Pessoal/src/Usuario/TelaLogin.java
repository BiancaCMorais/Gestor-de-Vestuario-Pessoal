package Usuario;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class TelaLogin extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;
    private JTextField nomeField;
    private JPasswordField senhaField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin frame = new TelaLogin();
            frame.setVisible(true);
        });
    }

    public TelaLogin() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Color.BLACK);
        setContentPane(contentPane);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(54, 54, 54));
        topBar.setPreferredSize(new Dimension(getWidth(), 80));

        JLabel tituloTopo = new JLabel("Gestor de Vestuário Pessoal", SwingConstants.CENTER);
        tituloTopo.setForeground(Color.WHITE);
        tituloTopo.setFont(new Font("Stencil", Font.BOLD, 50));
        topBar.add(tituloTopo, BorderLayout.CENTER);

        JPanel topArea = new JPanel(new BorderLayout());
        topArea.setBackground(Color.BLACK);
        topArea.add(topBar, BorderLayout.NORTH);
        contentPane.add(topArea, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fonteTitulo = new Font("Serif", Font.BOLD, 40);
        JLabel titulo = new JLabel("Login");
        GridBagConstraints gbcTitulo = new GridBagConstraints();
        titulo.setFont(fonteTitulo);
        titulo.setForeground(Color.WHITE);
        gbcTitulo.gridx = 0;
        gbcTitulo.gridy = 0;
        gbcTitulo.gridwidth = 2;
        gbcTitulo.anchor = GridBagConstraints.CENTER;
        panel.add(titulo, gbcTitulo);
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
        
        
        GridBagConstraints gbcLoginBtn = new GridBagConstraints();
        gbcLoginBtn.gridx = 0;
        gbcLoginBtn.gridy = 3;
        gbcLoginBtn.gridwidth = 2;
        gbcLoginBtn.insets = new Insets(20, 10, 10, 10);
        gbcLoginBtn.anchor = GridBagConstraints.CENTER;
        
        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.addActionListener(e -> {
            String nome = nomeField.getText();
            String senha = new String(senhaField.getPassword());
            
            if (PersistenciaDados.verificarRegistro(nome, senha)) {
                Usuario usuario = PersistenciaDados.carregarUsuario(nome);
                if (usuario != null) {
                    new TelaPrincipal(usuario).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao carregar dados do usuário", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(loginBtn, gbcLoginBtn);
        
        JButton novoUsuarioBtn = new JButton("Cadastrar novo usuário");
        novoUsuarioBtn.setFont(new Font("Tahoma", Font.PLAIN, 18));
        novoUsuarioBtn.setContentAreaFilled(false);
        novoUsuarioBtn.setForeground(Color.WHITE);
        novoUsuarioBtn.setFocusPainted(false);
        novoUsuarioBtn.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
        novoUsuarioBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        novoUsuarioBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                novoUsuarioBtn.setForeground(Color.gray); 
                novoUsuarioBtn.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.gray));
            }
            @Override
            public void mouseExited(MouseEvent e) {
            	novoUsuarioBtn.setForeground(Color.white); 
                novoUsuarioBtn.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.white));
            }
        });
        
        GridBagConstraints gbcNovoUsuarioBtn = new GridBagConstraints();
        gbcNovoUsuarioBtn.gridx = 0;
        gbcNovoUsuarioBtn.gridy = 4;
        gbcNovoUsuarioBtn.gridwidth = 4;
        gbcNovoUsuarioBtn.insets = new Insets(20, 10, 10, 10);
        gbcNovoUsuarioBtn.anchor = GridBagConstraints.CENTER;
        
        panel.add(novoUsuarioBtn, gbcNovoUsuarioBtn);
        novoUsuarioBtn.addActionListener(e -> {
        	new TelaCadastroUsuario().setVisible(true);
            dispose();
        });

        gbc.gridy = 4;
        panel.add(novoUsuarioBtn, gbcNovoUsuarioBtn);

        contentPane.add(panel, BorderLayout.CENTER);
    }
    
}
