package entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Cidade implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer ibge_id;
	private String uf;
	private String name;
	private Boolean capital;
	private Double lon;
	private Double lat;
	private String no_accents;
	private String alternative_names;
	private String microregion;
	private String mesoregion;
	private Double distancia;

	public Cidade() {
	}

	public Cidade(Integer ibge_id, String uf, String name, Boolean capital, Double lon, Double lat, String no_accents,
			String alternative_names, String microregion, String mesoregion) {
		super();
		this.ibge_id = ibge_id;
		this.uf = uf;
		this.name = name;
		this.capital = capital;
		this.lon = lon;
		this.lat = lat;
		this.no_accents = no_accents;
		this.alternative_names = alternative_names;
		this.microregion = microregion;
		this.mesoregion = mesoregion;
	}

	public Cidade(Integer ibge_id, String uf, String name, Boolean capital, Double lon, Double lat, String no_accents,
			String alternative_names, String microregion, String mesoregion, Double distancia) {
		super();
		this.ibge_id = ibge_id;
		this.uf = uf;
		this.name = name;
		this.capital = capital;
		this.lon = lon;
		this.lat = lat;
		this.no_accents = no_accents;
		this.alternative_names = alternative_names;
		this.microregion = microregion;
		this.mesoregion = mesoregion;
		this.distancia = distancia;
	}

	public Integer getIbge_id() {
		return ibge_id;
	}

	public void setIbge_id(Integer ibge_id) {
		this.ibge_id = ibge_id;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getCapital() {
		return capital;
	}

	public void setCapital(Boolean capital) {
		this.capital = capital;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public String getNo_accents() {
		return no_accents;
	}

	public void setNo_accents(String no_accents) {
		this.no_accents = no_accents;
	}

	public String getAlternative_names() {
		return alternative_names;
	}

	public void setAlternative_names(String alternative_names) {
		this.alternative_names = alternative_names;
	}

	public String getMicroregion() {
		return microregion;
	}

	public void setMicroregion(String microregion) {
		this.microregion = microregion;
	}

	public String getMesoregion() {
		return mesoregion;
	}

	public void setMesoregion(String mesoregion) {
		this.mesoregion = mesoregion;
	}

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}

}
