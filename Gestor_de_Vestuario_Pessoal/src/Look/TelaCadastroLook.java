package Look;

import Usuario.PersistenciaDados;
import Usuario.TelaLogin;
import Usuario.Usuario;
import java.awt.*;
import javax.swing.*;

public class TelaCadastroLook extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;
    private final JTextField nomeField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin frame = new TelaLogin();
            frame.setVisible(true);
        });
    }

    public TelaCadastroLook(Usuario usuario) {
        setTitle("Cadastrar Look");
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
            new TelaLooks(usuario).setVisible(true);
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
        
        JLabel titulo = new JLabel("Cadastrar look");
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

        Font fonteLabel = new Font("Serif", Font.BOLD, 30);
        Color corTexto = Color.WHITE;
								       
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridx = 0;
        gbcLabel.gridy = 1;
        gbcLabel.anchor = GridBagConstraints.LINE_START;
        gbcLabel.insets = new Insets(5, 10, 5, 10);
								            
        JLabel label = new JLabel("Nome:");
		label.setFont(fonteLabel);
		label.setForeground(corTexto);
		panel.add(label, gbcLabel);
								
		GridBagConstraints gbcCampo = new GridBagConstraints();
		gbcCampo.gridx = 1;
		gbcCampo.gridy = 1;
		gbcCampo.fill = GridBagConstraints.HORIZONTAL;
		gbcCampo.insets = new Insets(5, 10, 5, 10);
		panel.add(nomeField, gbcCampo);
        
        JButton cadastrarBtn = new JButton("Cadastrar");
        cadastrarBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
        cadastrarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cadastrarBtn.addActionListener(e -> {
        	boolean ok = cadastrarLook(usuario);
            if(ok){
                new TelaLooks(usuario).setVisible(true);
                dispose();
            }
        });
        
        GridBagConstraints gbcBotao = new GridBagConstraints();
        gbcBotao.gridx = 0;
        gbcBotao.gridy = 2;
        gbcBotao.gridwidth = 2;
        gbcBotao.insets = new Insets(20, 10, 10, 10);
        gbcBotao.anchor = GridBagConstraints.CENTER;
        panel.add(cadastrarBtn, gbcBotao);

        contentPane.add(panel, BorderLayout.CENTER);
    }

    private boolean cadastrarLook(Usuario usuario) {
        if (nomeField.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, 
                "Preencha o nome!", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        for(Look look : usuario.verLooks()){
            if(nomeField.getText().trim().equals(look.verNomeLook())){
                JOptionPane.showMessageDialog(this, 
                    "Já existe um look com esse nome!", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        try {
            String novoLook = nomeField.getText();
            usuario.criarLook(novoLook);
            PersistenciaDados.salvarDados(usuario);
            
            JOptionPane.showMessageDialog(this, 
                "Look cadastrado com sucesso!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao cadastrar look: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }
}
