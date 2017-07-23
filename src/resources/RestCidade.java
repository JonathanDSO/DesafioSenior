package resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.Path;

import com.google.gson.GsonBuilder;

import entity.Cidade;
import entity.EstadoQtd;
import persistence.CidadeDao;
import persistence.EstadoQtdDao;

@Path("/cidades")
public class RestCidade {

	private static Scanner leitor;
	String caminhoArq = "WebContent/cidades.csv";
	CidadeDao cidadeDao = new CidadeDao();
	EstadoQtdDao estadoQtdDao = new EstadoQtdDao();

	private void lerArquivoCSV() {
		try {
			String linhas = new String();
			File arquivoCSV = new File(caminhoArq);
			leitor = new Scanner(arquivoCSV);

			linhas = leitor.nextLine();
			while (leitor.hasNext()) {
				linhas = leitor.nextLine();
				String[] valores = linhas.split(",");
				Cidade cidade = new Cidade(new Integer(valores[0]), valores[1], valores[2], new Boolean(valores[3]),
						new Double(valores[4]), new Double(valores[5]), valores[6], valores[7], valores[8], valores[9]);

				Cidade c = buscarCidadePorId(cidade.getIbge_id());
				if (c != null) {
					atualizarCidade(cidade);
				} else {
					gravarCidade(cidade);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void gravarCidade(Cidade cidade) {
		try {
			cidadeDao.create(cidade);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deletarCidade(Integer ibge_id) {
		try {
			cidadeDao.delete(ibge_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Cidade buscarCidadePorId(Integer ibge_id) {
		try {
			return cidadeDao.findById(ibge_id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void atualizarCidade(Cidade cidade) {
		try {
			cidadeDao.edit(cidade);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<Cidade> buscarCapitaisOrdenadas() {
		try {
			String query = "select * from cidade where capital = true order by name";
			return cidadeDao.consultaPorQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private List<EstadoQtd> buscarEstadosComMaiorEMenorQtdCidades() {
		try {
			List<EstadoQtd> lista = estadoQtdDao.buscarEstadoEQuantidade();
			List<EstadoQtd> estados = new ArrayList<EstadoQtd>();
			estados.add(lista.get(0));
			estados.add(lista.get(lista.size() - 1));
			return estados;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private List<EstadoQtd> buscarEstadosQtdCidades() {
		try {
			List<EstadoQtd> lista = estadoQtdDao.buscarEstadoEQuantidade();
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String buscarCidadesPorEstado(String estado) {
		try {
			String query = "select * from cidade where uf = '" + estado + "'";
			List<Cidade> cidades = cidadeDao.consultaPorQuery(query);
			List<String> nomes = new ArrayList<String>();
			for (Cidade cidade : cidades) {
				nomes.add(cidade.getName());
			}
			return new GsonBuilder().setPrettyPrinting().create().toJson(nomes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private List<Cidade> filtrarCidadesPorColuna(String coluna, String filtro) {
		try {
			String query = "select * from cidade where " + coluna + " like '%" + filtro + "%'";
			return cidadeDao.consultaPorQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Integer consultarQuantidadeDistintaPorColuna(String coluna) {
		try {
			return cidadeDao.consultarQuantidadeDistintaPorColuna(coluna);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Integer consultarQuantidadeRegistros() {
		try {
			return cidadeDao.consultarQuantidadeRegistros();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private List<Cidade> buscarAsDuasCidadesMaisDistanteUmaDaOutra() {
		List<Cidade> cidadesMaisDistantes = new ArrayList<Cidade>();
		Cidade cidade1 = null;
		Cidade cidade2 = null;
		Double distancia = 0.0;
		
		try {
			List<Cidade> cidades = cidadeDao.findAll();
			for(Cidade c1 : cidades){
				String query = "	SELECT *,	" +
						"	(6371 * acos(	" +
						"	 cos( radians("+c1.getLat()+") )	" +
						"	 * cos( radians( lat ) )	" +
						"	 * cos( radians( lon ) - radians("+c1.getLon()+") )	" +
						"	 + sin( radians("+c1.getLat()+") )	" +
						"	 * sin( radians( lat ) ) ) ) as distancia	" +
						"	FROM cidade	" +
						"	ORDER BY distancia DESC	" +
						"	LIMIT 1	";
				Cidade c2 = cidadeDao.consultaCidadeMaisDistante(query);
				if(c2.getDistancia() > distancia){
					distancia = c2.getDistancia();
					cidade1 = c1;
					cidade2 = c2;
				}
			}
			cidadesMaisDistantes.add(cidade1);
			cidadesMaisDistantes.add(cidade2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cidadesMaisDistantes;
	}
	
	

	public static void main(String[] args) {
		try {
//			new RestCidade().lerArquivoCSV();
			System.out.println(new RestCidade().buscarAsDuasCidadesMaisDistanteUmaDaOutra());
			// new RestCidade().deletarCidade(3300100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
