package output;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.GsonBuilder;

import entity.EstadoQtd;

@XmlRootElement
public class SaidaEstadoQtds extends Saida {

	private List<EstadoQtd> estadosQtds;

	@Override
	public String toString() {
		return new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}
	
	public SaidaEstadoQtds() {
	}

	public SaidaEstadoQtds(String status, String mensagem) {
		setStatus(status);
		setMensagem(mensagem);
	}

	public SaidaEstadoQtds(List<EstadoQtd> estadosQtds, String status, String mensagem) {
		this.estadosQtds = estadosQtds;
		setStatus(status);
		setMensagem(mensagem);
	}

	public List<EstadoQtd> getEstadosQtds() {
		return estadosQtds;
	}

	public void setEstadosQtds(List<EstadoQtd> estadosQtds) {
		this.estadosQtds = estadosQtds;
	}

}
