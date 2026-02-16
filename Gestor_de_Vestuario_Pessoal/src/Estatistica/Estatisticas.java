package Estatistica;


import Look.Look;
import TiposItem.IEmprestavel;
import TiposItem.ILavavel;
import TiposItem.Item;
import Usuario.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Estatisticas {
	private final Map<String,Integer> vezesUsadoItem;
	private final Map<String,Integer> vezesLavado;
	private final Map<String,Integer> vezesUsadoLook;
	private final List<String> emprestados;
	public Estatisticas() {
		this.vezesUsadoItem=new HashMap<>();
		this.vezesLavado = new HashMap<>();
		this.vezesUsadoLook = new HashMap<>();
		this.emprestados = new ArrayList<>();
	}
	
	public Map<String,Integer> ordenacaoUsadoItem(Usuario usuario) {
		if(!usuario.verVestuario().isEmpty()) {
			for(Item item : usuario.verVestuario()) {
				vezesUsadoItem.put(item.verNomeItem(), item.verVezesUsadoItem());
			}
			return ordenarPorValor(vezesUsadoItem);
		}
		return null;
	}
	
	public Map<String,Integer> ordenacaoLavado(Usuario usuario) {
		if(!usuario.verVestuario().isEmpty()) {
			for(Item item : usuario.verVestuario()) {
				if(item instanceof ILavavel lavavel) {
					vezesLavado.put(item.verNomeItem(), lavavel.verVezesLavado());
				}
			}
			return ordenarPorValor(vezesLavado);
		}
		return null;
	}
	
	public Map<String,Integer> ordenacaoUsadoLook(Usuario usuario) {
		if(!usuario.verLooks().isEmpty()) {
			for(Look look : usuario.verLooks()) {
				vezesUsadoLook.put(look.verNomeLook(), look.verVezesUsadoLook());
			}
			return ordenarPorValor(vezesUsadoLook);
		}
		return null;
	}
	
	public List<String> emprestados(Usuario usuario){
		if(!usuario.verVestuario().isEmpty()) {
			for(Item item : usuario.verVestuario()) {
				if(item instanceof IEmprestavel emprestavel) {
					if(emprestavel.estaEmprestado()) {
						emprestados.add(item.verNomeItem());
					}
				}
			}
			return emprestados;
		}
		return null;
	}
	
	private Map<String, Integer> ordenarPorValor(Map<String, Integer> mapa) {
        return mapa.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
    }
}
