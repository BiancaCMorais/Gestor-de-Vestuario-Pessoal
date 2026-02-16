package TiposItem;

import java.time.LocalDateTime;
import java.util.List;

public interface ILavavel {
	public void registrarLavagem(LocalDateTime data);
    int verVezesLavado();
    public List<LocalDateTime> verDatasLavagem();
}