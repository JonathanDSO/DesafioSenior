package output;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.GsonBuilder;

@XmlRootElement
public class SaidaQuantidadeRegistro extends Saida {

	private Integer quantidade;

	@Override
	public String toString() {
		return new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}

	public SaidaQuantidadeRegistro() {
	}

	public SaidaQuantidadeRegistro(String status, String mensagem) {
		setStatus(status);
		setMensagem(mensagem);
	}

	public SaidaQuantidadeRegistro(Integer quantidade, String status, String mensagem) {
		this.quantidade = quantidade;
		setStatus(status);
		setMensagem(mensagem);
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

}
