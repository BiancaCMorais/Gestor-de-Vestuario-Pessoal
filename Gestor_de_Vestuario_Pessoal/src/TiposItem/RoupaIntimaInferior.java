package TiposItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RoupaIntimaInferior extends Item implements ILavavel {
	private static final long serialVersionUID = 1L;
	private int vezesLavado;
	private final List<LocalDateTime> datasLavagem;
	public RoupaIntimaInferior(String nome, String cor, String tamanho, String lojaOrigem, String conservacao) {
		super(nome, cor, tamanho, lojaOrigem, conservacao);
		vezesLavado = 0;
		datasLavagem = new ArrayList<>();
	}
	
	public String toString() {
        return this.verNomeItem();
    }
	
	public void registrarLavagem(LocalDateTime data) {
        vezesLavado++;
        datasLavagem.add(data);
    }
	
	public int verVezesLavado() {
        return vezesLavado;
    }
	
	public List<LocalDateTime> verDatasLavagem() {
		return datasLavagem;
	}
}
