package TiposItem;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public abstract class Item implements Serializable{
	private static final long serialVersionUID = 1L;
	protected String nomeItem;
	protected String cor;
	protected String tamanho;
	protected String lojaOrigem;
	protected String conservacao;
	protected int vezesUsadoItem;
	protected Map<LocalDateTime, String> usosItem;
	public Item(String nome, String cor, String tamanho, String lojaOrigem, String conservacao) {
		this.nomeItem=nome;
        this.cor = cor;
        this.tamanho = tamanho;
        this.lojaOrigem = lojaOrigem;
        this.conservacao = conservacao;
		this.vezesUsadoItem=0;
		this.usosItem=new HashMap<>();
	}
	
	public void mudarNomeItem(String nome){
		if(!nomeItem.equals("")) {
			this.nomeItem=nome;
		}
	}
	
	public String verNomeItem() {
		return nomeItem;
	}
	
	public void mudarCor(String cor) {
		if(!cor.equals("")) {
			this.cor=cor;
		}
	}
	
	public String verCor() {
		return cor;
	}
	
	public void mudarTamanho(String tamanho) {
		if(!tamanho.equals("")) {
			this.tamanho=tamanho;
		}
	}
	
	public String verTamanho() {
		return tamanho;
	}
	
	public void mudarLojaOrigem(String lojaOrigem) {
		if(!lojaOrigem.equals("")) {
			this.lojaOrigem=lojaOrigem;
		}
	}
	
	public String verLojaOrigem() {
		return lojaOrigem;
	}
	
	public void mudarConservacao(String conservacao) {
		if(!conservacao.equals("")) {
			this.conservacao=conservacao;
		}
	}
	
	public String verConservacao() {
		return conservacao;
	}
	
	public void usarItem(LocalDateTime dataUso, String eventoUso) {
		usosItem.put(dataUso, eventoUso);
		vezesUsadoItem++;
	}
	
	public Map<LocalDateTime, String> verUsosItem() {
		return usosItem;
	}
	
	public int verVezesUsadoItem() {
		return vezesUsadoItem;
	}
	
}
