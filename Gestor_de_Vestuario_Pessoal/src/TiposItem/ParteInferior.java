package TiposItem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ParteInferior extends Item implements IEmprestavel, ILavavel{
	private static final long serialVersionUID = 1L;
	private boolean emprestado;
	private LocalDate dataEmprestimo;
	private String destinatarioEmprestimo = "";
	private int vezesLavado;
	private final List<LocalDateTime> datasLavagem;
	public ParteInferior(String nome, String cor, String tamanho, String lojaOrigem, String conservacao) {
		super(nome, cor, tamanho, lojaOrigem, conservacao);
		emprestado=false;
		vezesLavado = 0;
		datasLavagem = new ArrayList<>();
	}
	
	public String toString() {
        return this.verNomeItem();
    }
	
	public void registrarEmprestimo(String destinatarioEmprestimo) {
		emprestado = true;
		this.destinatarioEmprestimo = destinatarioEmprestimo;
		this.dataEmprestimo = LocalDate.now();
	}
	
	public boolean estaEmprestado() {
		return emprestado;
	}
	
	public String comQuemEsta() {
		return destinatarioEmprestimo;
	}
	
	public LocalDate verDataEmprestimo() {
		return dataEmprestimo;
	}
	
	public long quantidadeDeDiasDesdeOEmprestimo() {
		LocalDate hoje = LocalDate.now();
		return ChronoUnit.DAYS.between(dataEmprestimo, hoje);
	}
	
	public void registrarDevolucao() {
		emprestado = false;
		destinatarioEmprestimo = "";
		
	}
	
	public void registrarLavagem(LocalDateTime data) {
		if(!emprestado) {
			vezesLavado++;
			datasLavagem.add(data);
		}
    }
	
	public int verVezesLavado() {
        return vezesLavado;
    }
	
	public List<LocalDateTime> verDatasLavagem() {
		return datasLavagem;
	}
}