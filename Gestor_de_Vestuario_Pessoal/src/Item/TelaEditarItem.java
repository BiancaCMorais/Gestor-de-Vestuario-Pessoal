package Item;

import TiposItem.*;
import Usuario.PersistenciaDados;
import Usuario.TelaLogin;
import Usuario.Usuario;
import java.awt.*;
import javax.swing.*;

public class TelaEditarItem extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;
    private final JTextField nomeField;
    private JButton corBtn;
    private Color corSelecionada;
    private final JTextField tamanhoField;
    private final JTextField lojaField;
    private final JComboBox<String> conservacaoBox;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin frame = new TelaLogin();
            frame.setVisible(true);
        });
    }

    public TelaEditarItem(Usuario usuario, Item item,JFrame volta) {
        setTitle("Editar Item");
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
        
        JLabel titulo = new JLabel("Editar ("+item.getClass().getSimpleName()+")");
        titulo.setFont(new Font("Serif", Font.BOLD, 40));
        titulo.setForeground(Color.WHITE);
        GridBagConstraints gbcTitulo = new GridBagConstraints();
        gbcTitulo.gridx = 0;
        gbcTitulo.gridy = 0;
        gbcTitulo.gridwidth = 2;
        gbcTitulo.insets = new Insets(10, 10, 20, 10);
        gbcTitulo.anchor = GridBagConstraints.CENTER;
        panel.add(titulo, gbcTitulo);

        nomeField = new JTextField(item.verNomeItem());
        corSelecionada = Color.decode(item.verCor());
        corBtn = new JButton("Selecionar cor");
        corBtn.setBackground(corSelecionada);
        corBtn.setForeground(Color.WHITE);
        corBtn.setFocusPainted(false);
        corBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        corBtn.addActionListener(e -> {
            Color novaCor = JColorChooser.showDialog(this, "Escolha uma cor", corSelecionada);
            if (novaCor != null) {
                corSelecionada = novaCor;
                corBtn.setBackground(corSelecionada);
            }
        });
        tamanhoField = new JTextField(item.verTamanho());
        lojaField = new JTextField(item.verLojaOrigem());
        
        String[] opcoes = {"Excelente", "Boa", "Regular", "Ruim"};
        DefaultComboBoxModel<String> modeloConservacao = new DefaultComboBoxModel<>();
        modeloConservacao.addElement(item.verConservacao());
        for (String opcao : opcoes) {
        	if (!opcao.equals(item.verConservacao())) {
        		modeloConservacao.addElement(opcao);
        	}
        }
        conservacaoBox = new JComboBox<>(modeloConservacao);
        conservacaoBox.setPreferredSize(new Dimension(200, 25));

        String[] labels = {"Nome:", "Cor:", "Tamanho:", "Loja de origem:", "Conservação:"};
        Component[] campos = {nomeField, corBtn, tamanhoField, lojaField, conservacaoBox};

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
        	boolean ok = editarItem(usuario,item);
            if(ok){
                new TelaItem(usuario,item,volta).setVisible(true);
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

    private boolean editarItem(Usuario usuario,Item item) {
        if(!item.verNomeItem().equals(nomeField.getText().trim())){
            for(Item item1 : usuario.verVestuario()){
                if(nomeField.getText().trim().equals(item1.verNomeItem())){
                    JOptionPane.showMessageDialog(this, 
                        "Já existe um item com esse nome!", 
                        "Erro", 
                        JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        

        try {
            edicaoItem(item);
            
            PersistenciaDados.salvarDados(usuario);
            
            JOptionPane.showMessageDialog(this, 
                "Item editado com sucesso!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao editar item: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }

    private void edicaoItem(Item item) {
        item.mudarNomeItem(nomeField.getText().trim());
        String corHex = "#" + Integer.toHexString(corSelecionada.getRGB()).substring(2).toUpperCase();
        item.mudarCor(corHex);
        item.mudarTamanho(tamanhoField.getText().trim());
        item.mudarLojaOrigem(lojaField.getText().trim());
        item.mudarConservacao((String) conservacaoBox.getSelectedItem());
        
    }
}