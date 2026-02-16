package Look;

import TiposItem.*;
import Usuario.PersistenciaDados;
import Usuario.TelaLogin;
import Usuario.Usuario;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class TelaEditarLook extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;
    private final JComboBox<ParteSuperior> parteSuperior;
    private final JComboBox<ParteInferior> parteInferior;
    private final JComboBox<RoupaIntimaInferior> roupaIntimaInferior;
    private final JComboBox<Pes> pes;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin frame = new TelaLogin();
            frame.setVisible(true);
        });
    }

    public TelaEditarLook(Usuario usuario, Look look) {
        setTitle("Editar look");
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
            new TelaLook(usuario,look).setVisible(true);
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
        
        JLabel titulo = new JLabel("Editar ("+look.verNomeLook()+")");
        titulo.setFont(new Font("Serif", Font.BOLD, 40));
        titulo.setForeground(Color.WHITE);
        GridBagConstraints gbcTitulo = new GridBagConstraints();
        gbcTitulo.gridx = 0;
        gbcTitulo.gridy = 0;
        gbcTitulo.gridwidth = 2;
        gbcTitulo.insets = new Insets(10, 10, 20, 10);
        gbcTitulo.anchor = GridBagConstraints.CENTER;
        panel.add(titulo, gbcTitulo);

        List<ParteSuperior> ps= new ArrayList<>();
        List<ParteInferior> pi= new ArrayList<>();
        List<RoupaIntimaInferior> rii= new ArrayList<>();
        List<Pes> p= new ArrayList<>();
        
        for(Item item:usuario.verVestuario()) {
            switch (item) {
                case ParteSuperior parteSuperior1 -> ps.add(parteSuperior1);
                case ParteInferior parteInferior1 -> pi.add(parteInferior1);
                case RoupaIntimaInferior roupaIntimaInferior1 -> rii.add(roupaIntimaInferior1);
                case Pes pes1 -> p.add(pes1);
                default -> {
                }
            }
        }
        
        DefaultComboBoxModel<ParteSuperior> modeloPS = new DefaultComboBoxModel<>();
        modeloPS.addElement((ParteSuperior)look.verParteSuperior());
        for (ParteSuperior opcao : ps) {
        	if (!opcao.equals(look.verParteSuperior())) {
        		modeloPS.addElement(opcao);
        	}
        }
        
        parteSuperior = new JComboBox<>(modeloPS);
        parteSuperior.setPreferredSize(new Dimension(200, 25));
        
        DefaultComboBoxModel<ParteInferior> modeloPI = new DefaultComboBoxModel<>();
        modeloPI.addElement((ParteInferior)look.verParteInferior());
        for (ParteInferior opcao : pi) {
        	if (!opcao.equals(look.verParteInferior())) {
        		modeloPI.addElement(opcao);
        	}
        }
        
        parteInferior = new JComboBox<>(modeloPI);
        parteInferior.setPreferredSize(new Dimension(200, 25));
        
        DefaultComboBoxModel<RoupaIntimaInferior> modeloRII = new DefaultComboBoxModel<>();
        modeloRII.addElement((RoupaIntimaInferior)look.verRoupaIntimaInferior());
        for (RoupaIntimaInferior opcao : rii) {
        	if (!opcao.equals(look.verRoupaIntimaInferior())) {
        		modeloRII.addElement(opcao);
        	}
        }
        
        roupaIntimaInferior = new JComboBox<>(modeloRII);
        roupaIntimaInferior.setPreferredSize(new Dimension(200, 25));

        DefaultComboBoxModel<Pes> modeloP = new DefaultComboBoxModel<>();
        modeloP.addElement((Pes)look.verPes());
        for (Pes opcao : p) {
        	if (!opcao.equals(look.verPes())) {
        		modeloP.addElement(opcao);
        	}
        }
        
        pes = new JComboBox<>(modeloP);
        pes.setPreferredSize(new Dimension(200, 25));

        String[] labels = {"Parte Superior:", "Parte Inferior:", "Roupa Intima Inferior:", "Pés:"};
        Component[] campos = {parteSuperior,parteInferior,roupaIntimaInferior,pes};

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
        	editarLook(usuario,look);
        	new TelaLook(usuario,look).setVisible(true);
            dispose();
        });
        
        GridBagConstraints gbcBtn = new GridBagConstraints();
        gbcBtn.gridx = 0;
        gbcBtn.gridy = labels.length + 2;
        gbcBtn.gridwidth = 2;
        gbcBtn.insets = new Insets(20, 10, 10, 10);
        gbcBtn.anchor = GridBagConstraints.CENTER;
        panel.add(editarBtn, gbcBtn);

        contentPane.add(panel, BorderLayout.CENTER);
    }

    private void editarLook(Usuario usuario,Look look) {
        
        try {
            edicaoLook(look);
            PersistenciaDados.salvarDados(usuario);
            JOptionPane.showMessageDialog(this, 
                "Look editado com sucesso!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao editar look: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void edicaoLook(Look look) {
    	Object psSelecionado = parteSuperior.getSelectedItem();
        Object piSelecionado = parteInferior.getSelectedItem();
        Object riiSelecionado = roupaIntimaInferior.getSelectedItem();
        Object pSelecionado = pes.getSelectedItem();
        look.mudar((ParteSuperior) psSelecionado);
        look.mudar((ParteInferior) piSelecionado);
        look.mudar((RoupaIntimaInferior) riiSelecionado);
        look.mudar((Pes) pSelecionado);
    }
}
