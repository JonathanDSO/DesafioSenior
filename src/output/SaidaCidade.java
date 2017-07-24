package output;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.GsonBuilder;

import entity.Cidade;

@XmlRootElement
public class SaidaCidade extends Saida {

	private Cidade cidade;

	@Override
	public String toString() {
		return new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}
	
	public SaidaCidade() {
	}

	public SaidaCidade(String status, String mensagem) {
		setStatus(status);
		setMensagem(mensagem);
	}

	public SaidaCidade(Cidade cidade, String status, String mensagem) {
		this.cidade = cidade;
		setStatus(status);
		setMensagem(mensagem);
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

}
