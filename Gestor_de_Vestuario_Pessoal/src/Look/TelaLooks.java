package Look;

import Usuario.TelaLogin;
import Usuario.TelaPrincipal;
import Usuario.Usuario;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class TelaLooks extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin frame = new TelaLogin();
            frame.setVisible(true);
        });
    }

    public TelaLooks(Usuario usuario) {
    	setTitle("Looks");
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

        JButton cadastrarBtn = new JButton("Cadastrar Novo Look");
        cadastrarBtn.addActionListener(e -> {
        	new TelaCadastroLook(usuario).setVisible(true);
			dispose(); 
        });
        cadastrarBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
        cadastrarBtn.setFocusPainted(false);
        cadastrarBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        cadastrarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel botoesBar = new JPanel(new BorderLayout());
        botoesBar.setBackground(Color.BLACK);
        botoesBar.setPreferredSize(new Dimension(getWidth(), 40));
        botoesBar.add(voltarPanel, BorderLayout.WEST);
        botoesBar.add(cadastrarBtn, BorderLayout.EAST);

        JPanel topArea = new JPanel(new BorderLayout());
        topArea.setBackground(Color.BLACK);
        topArea.add(topBar, BorderLayout.NORTH);
        topArea.add(botoesBar, BorderLayout.SOUTH);
        contentPane.add(topArea, BorderLayout.NORTH);


        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fonteTitulo = new Font("Serif", Font.BOLD, 40);
        JLabel titulo = new JLabel("Looks ("+usuario.verNomeUsuario()+")");
        GridBagConstraints gbcTitulo = new GridBagConstraints();
        titulo.setFont(fonteTitulo);
        titulo.setForeground(Color.WHITE);
        gbcTitulo.gridx = 0;
        gbcTitulo.gridy = 0;
        gbcTitulo.gridwidth = 2;
        gbcTitulo.anchor = GridBagConstraints.CENTER;
        panel.add(titulo, gbcTitulo);
        
        List<Look> looks = usuario.verLooks();
        
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.insets = new Insets(10, 10, 10, 10);
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        
        int linhaAtual=1;
        String texto;
        if(!looks.isEmpty()) {
        	for (Look look : looks) {
        		JButton itemButton = new JButton(look.verNomeLook());
        		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        		itemButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        	    itemButton.setFocusPainted(false);
        	    itemButton.setBackground(new Color(70, 70, 70));
        	    itemButton.setForeground(Color.WHITE);
        	    itemButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        	    itemButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        	    itemButton.setHorizontalAlignment(SwingConstants.LEFT);
        	    itemButton.setHorizontalTextPosition(SwingConstants.LEFT);
        		itemButton.addActionListener(e -> {
        			new TelaLook(usuario, look).setVisible(true);
        			dispose(); 
        		});
        		int botaoLargura = (int)(screenSize.width * 0.6);
        	    int botaoAltura = 35;
        	    itemButton.setPreferredSize(new Dimension(botaoLargura, botaoAltura));

        	    gbc.gridx = 0;
        	    gbc.gridy = linhaAtual;
        	    gbc.gridwidth = 1;
        	    gbc.anchor = GridBagConstraints.CENTER;
        	    gbc.fill = GridBagConstraints.NONE;

        	    panel.add(itemButton, gbc);

        	    panel.add(itemButton, gbc);
        	    linhaAtual++;
        	}
        }else {
        	texto = "Nenhum look cadastrado";
        	JLabel label = new JLabel(texto);
            label.setFont(fonteTitulo);
            label.setForeground(Color.WHITE);
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
}
