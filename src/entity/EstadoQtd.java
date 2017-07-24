package entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EstadoQtd {

	private String uf;
	private Integer qtd;

	public EstadoQtd() {
	}

	public EstadoQtd(String uf, Integer qtd) {
		super();
		this.uf = uf;
		this.qtd = qtd;
	}
	
	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public Integer getQtd() {
		return qtd;
	}

	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}

}
