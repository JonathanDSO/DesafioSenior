package output;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.GsonBuilder;

import entity.Cidade;

@XmlRootElement
public class SaidaCidades extends Saida {

	private List<Cidade> cidades;

	@Override
	public String toString() {
		return new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}
	
	public SaidaCidades() {
	}

	public SaidaCidades(String status, String mensagem) {
		setStatus(status);
		setMensagem(mensagem);
	}

	public SaidaCidades(List<Cidade> cidades, String status, String mensagem) {
		this.cidades = cidades;
		setStatus(status);
		setMensagem(mensagem);
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

}
