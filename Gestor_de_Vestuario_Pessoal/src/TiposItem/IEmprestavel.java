package TiposItem;

import java.time.LocalDate;

public interface IEmprestavel {
	public boolean estaEmprestado();
	public String comQuemEsta();
	public void registrarEmprestimo(String destinatarioEmperstumo);
	public long quantidadeDeDiasDesdeOEmprestimo();
	public void registrarDevolucao();
	public LocalDate verDataEmprestimo();
}
