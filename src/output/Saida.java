package output;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.GsonBuilder;

@XmlRootElement
public class Saida {

	private String status;
	private String mensagem;

	@Override
	public String toString() {
		return new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}
	
	public Saida() {
	}

	public Saida(String status, String mensagem) {
		this.status = status;
		this.mensagem = mensagem;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
