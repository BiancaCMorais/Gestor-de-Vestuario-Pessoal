package Look;

import TiposItem.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class Look implements Serializable{
	private static final long serialVersionUID = 1L;
	private final String nomeLook;
	private final Map<String, Item> look;
	private final Map<LocalDateTime, String> usosLook;
	private int vezesUsadoLook;
	
	public Look(String nomeLook) {
        this.nomeLook = nomeLook;
        this.look = new HashMap<>();
        this.usosLook = new HashMap<>();
    }
	
	public String verNomeLook() {
		return nomeLook;
	}
	
	public Map<String,Item> verPecasLook(){
		return look;
	}
	
	public Item verParteSuperior() {
		return look.get("Parte Superior");
	}
	
	public Item verParteInferior() {
		return look.get("Parte Inferior");
	}
	
	public Item verRoupaIntimaInferior() {
		return look.get("Roupa Intima Inferior");
	}

	public Item verPes() {
		return look.get("Pes");
	}
	
	public void mudar(ParteSuperior novaParteSuperior) {
		look.put("Parte Superior",novaParteSuperior);
	}
	
	public void mudar(ParteInferior novaParteInferior) {
		look.put("Parte Inferior",novaParteInferior);
	}
	
	public void mudar(RoupaIntimaInferior novaRoupaIntima) {
		look.put("Roupa Intima Inferior",novaRoupaIntima);
	}

	public void mudar(Pes pes) {
		look.put("Pes",pes);
	}
	
	public void remover(ParteSuperior parteSuperior) {
		look.remove("Parte Superior");
	}
	
	public void remover(ParteInferior parteInferior) {
		look.remove("Parte Inferior");
	}
	
	public void remover(RoupaIntimaInferior roupaIntima) {
		look.remove("Roupa Intima Inferior");
	}

	public void remover(Pes pes) {
		look.remove("Pes");
	}
	
	public void usarLook(LocalDateTime dataUso, String eventoUso) {
		usosLook.put(dataUso, eventoUso);
		int i =0;
		for (Item item : look.values()) {
			if(item instanceof IEmprestavel iEmprestavel) {
    			if(iEmprestavel.estaEmprestado()){
    				i=1;
    				break;
    			}
    		}
        }
		if(i==0) {
			for(Item item : look.values()) {
				if(item!=null) {
					item.usarItem(dataUso, eventoUso);
				}
			}
			vezesUsadoLook++;
		}else {
			JOptionPane.showMessageDialog(null,"O look contém item(ns) emprestado(s) e não pode ser usado.","Atenção",JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public Map<LocalDateTime, String> verUsosLook() {
		return usosLook;
	}
	
	public int verVezesUsadoLook() {
		return vezesUsadoLook;
	}
	
}
