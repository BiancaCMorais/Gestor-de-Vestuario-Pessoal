package Item;

import TiposItem.*;
import Usuario.PersistenciaDados;
import Usuario.TelaLogin;
import Usuario.Usuario;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class TelaEmprestimoItem extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;
    private final JLabel estaEmprestado;
    private final JLabel comQuemEsta;
    private final JLabel dataEmprestimo;
    private final JLabel QtdDiasDesdeEmprestimo;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin frame = new TelaLogin();
            frame.setVisible(true);
        });
    }

    public TelaEmprestimoItem(Usuario usuario, Item item, JFrame volta) {
    	setTitle("Emprestimo item");
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

        JPanel botoesBar = new JPanel(new BorderLayout());
        botoesBar.setBackground(Color.BLACK);
        botoesBar.setPreferredSize(new Dimension(getWidth(), 40));
        botoesBar.add(voltarPanel, BorderLayout.WEST);

        JPanel topArea = new JPanel(new BorderLayout());
        topArea.setBackground(Color.BLACK);
        topArea.add(topBar, BorderLayout.NORTH);
        topArea.add(botoesBar, BorderLayout.SOUTH);
        contentPane.add(topArea, BorderLayout.NORTH);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        
        JLabel titulo = new JLabel(item.verNomeItem());
        titulo.setFont(new Font("Serif", Font.BOLD, 40));
        titulo.setForeground(Color.WHITE);
        GridBagConstraints gbcTitulo = new GridBagConstraints();
        gbcTitulo.gridx = 0;
        gbcTitulo.gridy = 0;
        gbcTitulo.gridwidth = 2;
        gbcTitulo.insets = new Insets(10, 10, 20, 10);
        gbcTitulo.anchor = GridBagConstraints.CENTER;
        panel.add(titulo, gbcTitulo);

        Font fonteLabel = new Font("Serif", Font.BOLD, 30);
        Font fonteCampo = new Font("Tahoma", Font.PLAIN, 24);
        Color corTexto = Color.WHITE;
        Dimension campoSize = new Dimension(300, 35);
        
        if(((IEmprestavel)item).estaEmprestado()) {
        	estaEmprestado = new JLabel("Sim");
        	comQuemEsta = new JLabel(((IEmprestavel)item).comQuemEsta());
        	DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = ((IEmprestavel)item).verDataEmprestimo().format(formatador);
        	dataEmprestimo = new JLabel(dataFormatada);
        	QtdDiasDesdeEmprestimo = new JLabel(String.valueOf(((IEmprestavel)item).quantidadeDeDiasDesdeOEmprestimo()));
        }else {
        	estaEmprestado = new JLabel("Não");
        	comQuemEsta = new JLabel("");
        	dataEmprestimo = new JLabel("");
        	QtdDiasDesdeEmprestimo = new JLabel("");
        }
        
        int linhaAtual=2;
        String[] labels = {"Esta emprestado:", "Com quem está:", "Data emprestimo:", "Quantidade de dias desde o emprestimo:"};
        Component[] campos = {estaEmprestado, comQuemEsta, dataEmprestimo, QtdDiasDesdeEmprestimo};

        for (int i = 0; i < labels.length; i++) {
            GridBagConstraints gbcLabel = new GridBagConstraints();
            gbcLabel.gridx = 0;
            gbcLabel.gridy = linhaAtual;
            gbcLabel.anchor = GridBagConstraints.LINE_START;
            gbcLabel.insets = new Insets(5, 10, 5, 10);
            JLabel label = new JLabel(labels[i]);
            label.setFont(fonteLabel);
            label.setForeground(corTexto);
            panel.add(label, gbcLabel);
            
            Component campo = campos[i];
            ((JLabel) campo).setFont(fonteCampo);
            ((JLabel) campo).setForeground(Color.WHITE);
            
            GridBagConstraints gbcCampo = new GridBagConstraints();
            gbcCampo.gridx = 1;
            gbcCampo.gridy = linhaAtual;
            gbcCampo.fill = GridBagConstraints.HORIZONTAL;
            gbcCampo.insets = new Insets(5, 10, 5, 10);
            JPanel campoComBorda = new JPanel(new BorderLayout());
            campoComBorda.setBackground(Color.BLACK);
            campoComBorda.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            campoComBorda.add(campos[i], BorderLayout.CENTER);
            campoComBorda.setPreferredSize(campoSize);
            linhaAtual++;
            panel.add(campoComBorda, gbcCampo);
        }
        JButton devolverBtn;
        if(((IEmprestavel)item).estaEmprestado()) {
        	devolverBtn = new JButton("Devolver");
        	devolverBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
        	devolverBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        	devolverBtn.addActionListener(e -> {
        		((IEmprestavel)item).registrarDevolucao();
                PersistenciaDados.salvarDados(usuario);
        		new TelaEmprestimoItem(usuario, item,volta).setVisible(true);
        		dispose();
        	});
        }else {
        	devolverBtn = new JButton("Emprestar");
        	devolverBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
        	devolverBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        	devolverBtn.addActionListener(e -> {
        		new TelaEmprestarItem(usuario, item,volta).setVisible(true);
        		dispose();
        	});
        }
        
        GridBagConstraints gbcBtn = new GridBagConstraints();
        gbcBtn.gridx = 0;
        gbcBtn.gridy = linhaAtual; 
        gbcBtn.gridwidth = 2;
        gbcBtn.insets = new Insets(20, 10, 10, 10);
        gbcBtn.anchor = GridBagConstraints.CENTER;

        panel.add(devolverBtn, gbcBtn);
        
        contentPane.add(panel, BorderLayout.CENTER);
    }
}
