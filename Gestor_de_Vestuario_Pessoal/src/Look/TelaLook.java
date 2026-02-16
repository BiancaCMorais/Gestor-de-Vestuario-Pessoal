package Look;

import Item.TelaItem;
import TiposItem.*;
import Usuario.PersistenciaDados;
import Usuario.TelaLogin;
import Usuario.Usuario;
import java.awt.*;
import javax.swing.*;

public class TelaLook extends JFrame{
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;
    private final JLabel parteSuperior;
    private final JLabel parteInferior;
    private final JLabel roupaIntimaInferior;
    private final JLabel pes;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin frame = new TelaLogin();
            frame.setVisible(true);
        });
    }

    public TelaLook(Usuario usuario, Look look) {
        
        setTitle("Look");
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
        
        JLabel titulo = new JLabel(look.verNomeLook());
        titulo.setFont(new Font("Serif", Font.BOLD, 40));
        titulo.setForeground(Color.WHITE);
        GridBagConstraints gbcTitulo = new GridBagConstraints();
        gbcTitulo.gridx = 0;
        gbcTitulo.gridy = 0;
        gbcTitulo.gridwidth = 2;
        gbcTitulo.insets = new Insets(10, 10, 20, 10);
        gbcTitulo.anchor = GridBagConstraints.CENTER;
        panel.add(titulo, gbcTitulo);
        if(look.verParteSuperior()!=null) {
        	parteSuperior = new JLabel(look.verParteSuperior().verNomeItem());
        }else {
        	parteSuperior= new JLabel("");
        }
        if(look.verParteInferior()!=null) {
        	parteInferior = new JLabel(look.verParteInferior().verNomeItem());
        }else {
        	parteInferior=new JLabel("");
        }
        if(look.verRoupaIntimaInferior()!=null) {
        	roupaIntimaInferior = new JLabel(look.verRoupaIntimaInferior().verNomeItem());
        }else {
        	roupaIntimaInferior=new JLabel("");
        }
        if(look.verPes()!=null) {
        	pes = new JLabel(look.verPes().verNomeItem());
        }else {
        	pes=new JLabel("");
        }
        
        String[] labels = {"Parte Superior:", "Parte Inferior:", "Roupa Intima Inferior:","Pés:"};
        Component[] campos = {parteSuperior, parteInferior, roupaIntimaInferior,pes};
        
        Font fonteLabel = new Font("Serif", Font.BOLD, 30);
        Font fonteCampo = new Font("Tahoma", Font.PLAIN, 24);
        Color corTexto = Color.WHITE;
        
        int larguraMaxima = 0;
        FontMetrics fm = panel.getFontMetrics(fonteCampo);

        for (Component c : campos) {
            String texto = ((JLabel) c).getText();
            int larguraTexto = fm.stringWidth(texto);
            larguraMaxima = Math.max(larguraMaxima, larguraTexto);
        }
        larguraMaxima += 40;
        if(larguraMaxima<300) {
        	larguraMaxima=300;
        }

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
            
            JLabel campo = (JLabel) campos[i];
            campo.setFont(fonteCampo);
            campo.setForeground(Color.WHITE);
            campo.setPreferredSize(new Dimension(larguraMaxima, 30));
            campo.setHorizontalAlignment(SwingConstants.LEFT);
            
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
            
            if(!campo.getText().equals("")) {
                JButton campoBtn = new JButton("Ver item");
                campoBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
                campoBtn.setFocusPainted(false);
                campoBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                campoBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                switch (i) {
                    case 0 -> campoBtn.addActionListener(e -> {
                            new TelaItem(usuario,look.verParteSuperior(),new TelaLook(usuario,look)).setVisible(true);
                            dispose();
                        });
                    case 1 -> campoBtn.addActionListener(e -> {
                            new TelaItem(usuario,look.verParteInferior(),new TelaLook(usuario,look)).setVisible(true);
                            dispose();
                        });
                    case 2 -> campoBtn.addActionListener(e -> {
                            new TelaItem(usuario,look.verRoupaIntimaInferior(),new TelaLook(usuario,look)).setVisible(true);
                            dispose();
                        });
                    case 3 -> campoBtn.addActionListener(e -> {
                            new TelaItem(usuario,look.verPes(),new TelaLook(usuario,look)).setVisible(true);
                            dispose();
                        });
                    default -> {
                    }
                }
            	GridBagConstraints gbcCampoBtn = new GridBagConstraints();
	            gbcCampoBtn.gridx = 2;
	            gbcCampoBtn.gridy = i + 1;
	            gbcCampoBtn.insets = new Insets(5, 10, 5, 10);
	            gbcCampoBtn.anchor = GridBagConstraints.LINE_START;
	
	            panel.add(campoBtn, gbcCampoBtn);
            }
            
            JButton excluirItemBtn = new JButton("Excluir item do look");
            excluirItemBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
            excluirItemBtn.setFocusPainted(false);
            excluirItemBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            excluirItemBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            
    		
            if(!campo.getText().equals("")) {
            	int index = i; 
	            excluirItemBtn.addActionListener(e -> {
	            	int confirm = JOptionPane.showConfirmDialog(
	            		this,
	                	"Deseja realmente excluir esse item?",
	                	"Confirmação de Exclusão",
	                	JOptionPane.YES_NO_OPTION,
	                    JOptionPane.QUESTION_MESSAGE
	               	);

	               	if (confirm == JOptionPane.YES_OPTION) {
                        switch (index) {
                            case 0 -> look.remover((ParteSuperior)look.verParteSuperior());
                            case 1 -> look.remover((ParteInferior)look.verParteInferior());
                            case 2 -> look.remover((RoupaIntimaInferior)look.verRoupaIntimaInferior());
                            case 3 -> look.remover((Pes)look.verPes());
                            default -> {
                            }
                        }
			
			            PersistenciaDados.salvarDados(usuario);
			
			            new TelaLook(usuario, look).setVisible(true);
			            dispose();
	               	}
	                
	            });
	            
	            GridBagConstraints gbcExcluir = new GridBagConstraints();
	            gbcExcluir.gridx = 3;
	            gbcExcluir.gridy = i + 1;
	            gbcExcluir.insets = new Insets(5, 10, 5, 10);
	            gbcExcluir.anchor = GridBagConstraints.LINE_START;
	
	            panel.add(excluirItemBtn, gbcExcluir);
        		
            }


            
        }
        
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        botoesPanel.setBackground(Color.BLACK);

        Font fonteBotoes = new Font("Tahoma", Font.PLAIN, 20);

        JButton editarBtn = new JButton("Editar");
        editarBtn.setFont(fonteBotoes);
        editarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botoesPanel.add(editarBtn);
        editarBtn.addActionListener(e -> {
            new TelaEditarLook(usuario,look).setVisible(true);
            dispose();
        });
        
        JButton usarBtn = new JButton("Uso");
        usarBtn.setFont(fonteBotoes);
        usarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        usarBtn.addActionListener(e -> {
        	new TelaUsoLook(usuario,look).setVisible(true);
            dispose();
        });
        botoesPanel.add(usarBtn);
        
        
        JButton excluirBtn = new JButton("Excluir look");
        excluirBtn.setFont(fonteBotoes);
        excluirBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botoesPanel.add(excluirBtn);
        excluirBtn.addActionListener(e -> {
        	int confirm = JOptionPane.showConfirmDialog(
            	    this,
            	    "Deseja realmente excluir esse look?",
            	    "Confirmação de Exclusão",
            	    JOptionPane.YES_NO_OPTION,
            	    JOptionPane.QUESTION_MESSAGE
            	);

            	if (confirm == JOptionPane.YES_OPTION) {
            	    usuario.removerLook(look);
		        	PersistenciaDados.salvarDados(usuario);
		        	new TelaLooks(usuario).setVisible(true);
		            dispose();
            	}
        	
        });
        
        
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