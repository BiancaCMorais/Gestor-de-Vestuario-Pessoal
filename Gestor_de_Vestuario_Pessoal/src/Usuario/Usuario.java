package Usuario;

import Look.Look;
import TiposItem.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable{
	private static final long serialVersionUID = 1L;
    private String nome;
    private String senha;
    private List<Item> vestuario;
    private List<Look> looks;
    
    public Usuario(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
        this.vestuario = new ArrayList<>();
        this.looks = new ArrayList<>();
    }
    
    public void mudarNomeUsuario(String nome) {
    	this.nome = nome;
    }
    
    public String verSenha() {
        return senha;
    }
    
    public void mudarSenha(String senha) { 
    	this.senha = senha; 
    }
    
    public boolean verificarSenha(String tentativa) {
    	return senha != null && senha.equals(tentativa);
    }
    
    public String verNomeUsuario() {
    	return nome;
    }
    
    
    public void adicionarItem(Item item) {
        vestuario.add(item);
    }
    
    public void removerItem(ParteSuperior parteSuperior) {
        vestuario.remove(parteSuperior);
        looks.forEach(look -> look.remover(parteSuperior));
    }
    
    public void removerItem(ParteInferior parteInferior) {
        vestuario.remove(parteInferior);
        looks.forEach(look -> look.remover(parteInferior));
    }
    
    public void removerItem(RoupaIntimaInferior roupaIntima) {
        vestuario.remove(roupaIntima);
        looks.forEach(look -> look.remover(roupaIntima));
    }

    public void removerItem(Pes pes) {
        vestuario.remove(pes);
        looks.forEach(look -> look.remover(pes));
    }
    
    public void criarLook(String nome) {
        Look look = new Look(nome);
        looks.add(look);
    }
    
    public void removerLook(Look look) {
        looks.remove(look);
    }
    
    public void registrarLavagem(ILavavel item, LocalDateTime data) {
        item.registrarLavagem(data);
    }
    
    public List<Item> verVestuario() {
        return vestuario;
    }
    
    public List<Look> verLooks() {
        return looks;
    }
    
    public void lerVestuario(List<Item> vestuario) {
        this.vestuario = vestuario;
    }
    
    public void lerLooks(List<Look> looks) {
        this.looks = looks;
    }
}
