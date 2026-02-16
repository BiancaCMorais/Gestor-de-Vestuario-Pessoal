package Item;

import TiposItem.*;
import Usuario.PersistenciaDados;
import Usuario.TelaLogin;
import Usuario.Usuario;
import java.awt.*;
import javax.swing.*;

public class TelaCadastroItem extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;
    private final JComboBox<String> tipoBox;
    private final JTextField nomeField;
    private final JButton corBtn;
    private Color corSelecionada = Color.WHITE;
    private final JTextField tamanhoField;
    private final JTextField lojaField;
    private final JComboBox<String> conservacaoBox;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin frame = new TelaLogin();
            frame.setVisible(true);
        });
    }

    public TelaCadastroItem(Usuario usuario) {
        setTitle("Cadastrar Item");
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
            new TelaItens(usuario).setVisible(true);
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
        
        JLabel titulo = new JLabel("Cadastrar item");
        titulo.setFont(new Font("Serif", Font.BOLD, 40));
        titulo.setForeground(Color.WHITE);
        GridBagConstraints gbcTitulo = new GridBagConstraints();
        gbcTitulo.gridx = 0;
        gbcTitulo.gridy = 0;
        gbcTitulo.gridwidth = 2;
        gbcTitulo.insets = new Insets(10, 10, 20, 10);
        gbcTitulo.anchor = GridBagConstraints.CENTER;
        panel.add(titulo, gbcTitulo);
        
        tipoBox = new JComboBox<>(new String[]{"Selecione", "Calça", "Calcinha", "Camisa", "Cueca", "Saia","Tênis"});
        tipoBox.setPreferredSize(new Dimension(200, 25));

        nomeField = new JTextField(20);
        corBtn = new JButton("Selecionar cor");
        corBtn.setBackground(corSelecionada);
        corBtn.setForeground(Color.black);
        corBtn.setFocusPainted(false);
        corBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        corBtn.addActionListener(e -> {
            Color novaCor = JColorChooser.showDialog(this, "Escolha uma cor", corSelecionada);
            if (novaCor != null) {
                corSelecionada = novaCor;
                corBtn.setBackground(corSelecionada);
            }
        });
        
        tamanhoField = new JTextField(20);
        lojaField = new JTextField(20);
        
        conservacaoBox = new JComboBox<>(new String[]{"Selecione", "Excelente", "Boa", "Regular", "Ruim"});
        conservacaoBox.setPreferredSize(new Dimension(200, 25));

        String[] labels = {"Tipo:", "Nome:", "Cor:", "Tamanho:", "Loja de origem:", "Conservação:"};
        Component[] campos = {tipoBox, nomeField, corBtn, tamanhoField, lojaField, conservacaoBox};

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
        cadastrarBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
        cadastrarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cadastrarBtn.addActionListener(e -> {
        	boolean ok = cadastrarItem(usuario);
            if(ok){
                new TelaItens(usuario).setVisible(true);
                dispose();
            }
        });
        
        GridBagConstraints gbcBotao = new GridBagConstraints();
        gbcBotao.gridx = 0;
        gbcBotao.gridy = labels.length + 2;
        gbcBotao.gridwidth = 2;
        gbcBotao.insets = new Insets(20, 10, 10, 10);
        gbcBotao.anchor = GridBagConstraints.CENTER;
        panel.add(cadastrarBtn, gbcBotao);

        contentPane.add(panel, BorderLayout.CENTER);
    }

    private boolean cadastrarItem(Usuario usuario) {
        if (tipoBox.getSelectedIndex() == 0 || 
            nomeField.getText().trim().isEmpty() ||
            corSelecionada == null ||
            tamanhoField.getText().trim().isEmpty() ||
            conservacaoBox.getSelectedIndex() == 0) {
            
            JOptionPane.showMessageDialog(this, 
                "Preencha todos os campos obrigatórios!", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        for(Item item : usuario.verVestuario()){
            if(nomeField.getText().trim().equals(item.verNomeItem())){
                JOptionPane.showMessageDialog(this, 
                    "Já existe um item com esse nome!", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        try {
            Item novoItem = criarItem();
            usuario.adicionarItem(novoItem);
            PersistenciaDados.salvarDados(usuario);
            
            JOptionPane.showMessageDialog(this, 
                "Item cadastrado com sucesso!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao cadastrar item: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }

    private Item criarItem() {
        String tipo = (String) tipoBox.getSelectedItem();
        String nome = nomeField.getText().trim();
        String cor = "#" + Integer.toHexString(corSelecionada.getRGB()).substring(2).toUpperCase();
        String tamanho = tamanhoField.getText().trim();
        String loja = lojaField.getText().trim();
        String conservacao = (String) conservacaoBox.getSelectedItem();
        
        switch (tipo) {
            case "Camisa" -> {
                return new Camisa(nome, cor, tamanho, loja, conservacao);
            }
            case "Calça" -> {
                return new Calca(nome, cor, tamanho, loja, conservacao);
            }
            case "Calcinha" -> {
                return new Calcinha(nome, cor, tamanho, loja, conservacao);
            }
            case "Cueca" -> {
                return new Cueca(nome, cor, tamanho, loja, conservacao);
            }
            case "Saia" -> {
                return new Saia(nome, cor, tamanho, loja, conservacao);
            }
            case "Tênis" -> {
                return new Tenis(nome, cor, tamanho, loja, conservacao);
            }
        }
		return null;
    }

}