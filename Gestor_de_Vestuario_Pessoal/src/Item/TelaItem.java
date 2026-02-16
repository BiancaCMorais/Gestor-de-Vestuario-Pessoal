package Item;

import TiposItem.*;
import Usuario.PersistenciaDados;
import Usuario.TelaLogin;
import Usuario.Usuario;
import java.awt.*;
import javax.swing.*;

public class TelaItem extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel tipoBox;
    private JPanel corPanel;
    private final JLabel tamanhoField;
    private final JLabel lojaField;
    private final JLabel conservacaoBox;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin frame = new TelaLogin();
            frame.setVisible(true);
        });
    }

    public TelaItem(Usuario usuario, Item item,JFrame volta) {
        
        setTitle("Item");
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
        	volta.setVisible(true);
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
        
        tipoBox = new JLabel(item.getClass().getSimpleName());
        corPanel = new JPanel();
        try {
            corPanel.setBackground(Color.decode(item.verCor()));
        } catch (NumberFormatException e) {
            corPanel.setBackground(Color.GRAY);
        }

        tamanhoField = new JLabel(item.verTamanho());
        lojaField = new JLabel(item.verLojaOrigem());
        conservacaoBox = new JLabel(item.verConservacao());
        
        String[] labels = {"Tipo:", "Cor:", "Tamanho:", "Loja de origem:", "Conservação:"};
        Component[] campos = {tipoBox, corPanel, tamanhoField, lojaField, conservacaoBox};

        Font fonteLabel = new Font("Serif", Font.BOLD, 30);
        Font fonteCampo = new Font("Tahoma", Font.PLAIN, 24);
        Color corTexto = Color.WHITE;
        int larguraMaxima = 0;
        FontMetrics fm = panel.getFontMetrics(fonteCampo);

        for (Component c : campos) {
            String texto = "";
            if (c instanceof JLabel jLabel) {
                texto = jLabel.getText();
            } else if (c instanceof JPanel) {
                texto = "";
            }
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
            
            Component campo = campos[i];
            switch (campo) {
                case JLabel jLabel -> {
                    jLabel.setFont(fonteCampo);
                    jLabel.setForeground(Color.WHITE);
                    jLabel.setPreferredSize(new Dimension(larguraMaxima, 30));
                }
                case JPanel jPanel -> jPanel.setPreferredSize(new Dimension(larguraMaxima, 30));
                default -> {
                }
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
        editarBtn.setFont(fonteBotoes);
        editarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botoesPanel.add(editarBtn);
        editarBtn.addActionListener(e -> {
            new TelaEditarItem(usuario,item,volta).setVisible(true);
            dispose();
        });

        JButton usarBtn = new JButton("Uso");
        usarBtn.setFont(fonteBotoes);
        usarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        usarBtn.addActionListener(e -> {
        	new TelaUsoItem(usuario,item,volta).setVisible(true);
            dispose();
        });
        botoesPanel.add(usarBtn);
        
        if (item instanceof ILavavel) {
            JButton lavarBtn = new JButton("Lavagem");
            lavarBtn.setFont(fonteBotoes);
            lavarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            lavarBtn.addActionListener(e -> {
            	new TelaLavagemItem(usuario,item,volta).setVisible(true);
                dispose();
            });
            botoesPanel.add(lavarBtn);
        }

        if (item instanceof IEmprestavel) {
            JButton emprestarBtn = new JButton("Emprestimo");
            emprestarBtn.setFont(fonteBotoes);
            emprestarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            emprestarBtn.addActionListener(e -> {
            	new TelaEmprestimoItem(usuario,item,volta).setVisible(true);
                dispose();
            });
            botoesPanel.add(emprestarBtn);
        }
        
        JButton excluirBtn = new JButton("Excluir");
        excluirBtn.setFont(fonteBotoes);
        excluirBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botoesPanel.add(excluirBtn);
        excluirBtn.addActionListener(e -> {
        	int confirm = JOptionPane.showConfirmDialog(
            		this,
                	"Deseja realmente excluir esse item?",
                	"Confirmação de Exclusão",
                	JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                switch (item) {
                    case ParteSuperior parteSuperior -> usuario.removerItem(parteSuperior);
                    case ParteInferior parteInferior -> usuario.removerItem(parteInferior);
                    case RoupaIntimaInferior roupaIntimaInferior -> usuario.removerItem(roupaIntimaInferior);
                    case Pes pes -> usuario.removerItem(pes);
                    default -> {
                    }
                }
            	PersistenciaDados.salvarDados(usuario);
            	new TelaItens(usuario).setVisible(true);
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