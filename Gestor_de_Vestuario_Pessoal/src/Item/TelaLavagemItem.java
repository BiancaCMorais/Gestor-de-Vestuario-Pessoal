package Item;

import TiposItem.*;
import Usuario.PersistenciaDados;
import Usuario.TelaLogin;
import Usuario.Usuario;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import javax.swing.*;

public class TelaLavagemItem extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin frame = new TelaLogin();
            frame.setVisible(true);
        });
    }

    public TelaLavagemItem(Usuario usuario, Item item, JFrame volta) {
    	setTitle("Lavagem item");
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
        	new TelaItem(usuario,item,volta).setVisible(true);
            dispose();
        });
        voltarBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
        voltarBtn.setFocusPainted(false);
        voltarBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        voltarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel voltarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        voltarPanel.setBackground(Color.BLACK);
        voltarPanel.add(voltarBtn);

        JButton lavarBtn = new JButton("Lavar item");
        lavarBtn.addActionListener(e -> {
        	if(item instanceof IEmprestavel iEmprestavel) {
        		if(!iEmprestavel.estaEmprestado()){
        			LocalDateTime hojeLocal = LocalDateTime.now();
        			usuario.registrarLavagem(((ILavavel)item), hojeLocal);
                    PersistenciaDados.salvarDados(usuario);
                    new TelaLavagemItem(usuario, item,volta).setVisible(true);
                    dispose();
        		}else {
        			JOptionPane.showMessageDialog(null,"O item está emprestado e não pode ser lavado.","Atenção",JOptionPane.WARNING_MESSAGE);
        		}
        	}else {
        		LocalDateTime hojeLocal = LocalDateTime.now();
        		usuario.registrarLavagem(((ILavavel)item), hojeLocal);
                PersistenciaDados.salvarDados(usuario);
                new TelaLavagemItem(usuario, item,volta).setVisible(true);
                dispose();
        	}
        });
        lavarBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lavarBtn.setFocusPainted(false);
        lavarBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        lavarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel botoesBar = new JPanel(new BorderLayout());
        botoesBar.setBackground(Color.BLACK);
        botoesBar.setPreferredSize(new Dimension(getWidth(), 40));
        botoesBar.add(voltarPanel, BorderLayout.WEST);
        botoesBar.add(lavarBtn, BorderLayout.EAST);

        JPanel topArea = new JPanel(new BorderLayout());
        topArea.setBackground(Color.BLACK);
        topArea.add(topBar, BorderLayout.NORTH);
        topArea.add(botoesBar, BorderLayout.SOUTH);
        contentPane.add(topArea, BorderLayout.NORTH);

        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        
        JLabel titulo = new JLabel(Objects.requireNonNull(item,"Item é nulo!").verNomeItem());
        titulo.setFont(new Font("Serif", Font.BOLD, 40));
        titulo.setForeground(Color.WHITE);
        GridBagConstraints gbcTitulo = new GridBagConstraints();
        gbcTitulo.gridx = 0;
        gbcTitulo.gridy = 0;
        gbcTitulo.gridwidth = 2;
        gbcTitulo.insets = new Insets(10, 10, 20, 10);
        gbcTitulo.anchor = GridBagConstraints.CENTER;
        panel.add(titulo, gbcTitulo);
        
        
        JLabel label = new JLabel("Lavagens:");

        Font fonteLabel = new Font("Serif", Font.BOLD, 30);
        Font fonteCampo = new Font("Tahoma", Font.PLAIN, 24);
        Color corTexto = Color.WHITE;
        Dimension campoSize = new Dimension(300, 35);
        
        GridBagConstraints gbcLabel = new GridBagConstraints();
     
        gbcLabel.gridx = 0;
        gbcLabel.gridy = 1;
        gbcLabel.anchor = GridBagConstraints.LINE_START;
        gbcLabel.insets = new Insets(5, 10, 5, 10);
        label.setFont(fonteLabel);
        label.setForeground(corTexto);
        panel.add(label, gbcLabel);
        
        int linhaAtual = 2;
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (LocalDateTime data : ((ILavavel)item).verDatasLavagem()) {
            String dataFormatada = data.format(formatador);
            JLabel lavagem = new JLabel("Lavado em " + dataFormatada);

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
            
    	    campoComBorda.setPreferredSize(new Dimension((int)(screenSize.width * 0.6), 35));
    	    gbcCampo.insets = new Insets(5, 0, 5, 0);
    	    gbcCampo.gridx = 0;
    	    gbcCampo.gridy = linhaAtual;
    	    gbcCampo.gridwidth = 1;
    	    gbcCampo.anchor = GridBagConstraints.CENTER;
    	    gbcCampo.fill = GridBagConstraints.NONE;

    	    linhaAtual++;
            
            panel.add(campoComBorda, gbcCampo);
        }
        
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null); 
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.getViewport().setBackground(Color.BLACK); 

        contentPane.add(scrollPane, BorderLayout.CENTER);
    }
}