package resources;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import entity.Cidade;
import entity.EstadoQtd;
import output.Saida;
import output.SaidaCidade;
import output.SaidaCidades;
import output.SaidaEstadoQtds;
import output.SaidaQuantidadeRegistro;
import persistence.CidadeDao;
import persistence.EstadoQtdDao;

@Path("/cidades")
public class RestCidade {

	private static Scanner leitor;
	private	String caminhoArq = "WebContent/cidades.csv";
	private CidadeDao cidadeDao = new CidadeDao();
	private EstadoQtdDao estadoQtdDao = new EstadoQtdDao();

	@GET
	@Path("/teste")
	@Produces(MediaType.APPLICATION_JSON)
	public Saida teste() {
		return new Saida("Teste", "testado");
	}
	
	@GET
	@Path("/lerArquivo")
	@Produces(MediaType.APPLICATION_JSON)
	public Saida lerArquivoCSV() {
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

				Cidade c = buscarCidadePorIdMethod(cidade.getIbge_id());
				if (c != null) {
					atualizarCidade(cidade);
				} else {
					gravarCidade(cidade);
				}
			}
			return new Saida("Sucesso", "Arquivo lido e persistido no banco de dados");
		} catch (Exception e) {
			e.printStackTrace();
			return new Saida("Erro", e.getMessage());
		}
	}
	
	@POST
	@Path("/gravarCidade")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Saida gravarCidade(Cidade cidade) {
		try {
			cidadeDao.create(cidade);
			return new Saida("Sucesso", "Cidade salva no banco de dados");
		} catch (Exception e) {
			e.printStackTrace();
			return new Saida("Erro", e.getMessage());
		}
	}

	@DELETE
	@Path("/deletarCidade/{ibge_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Saida deletarCidade(@PathParam("ibge_id") Integer ibge_id) {
		try {
			cidadeDao.delete(ibge_id);
			return new Saida("Sucesso", "Cidade deletada no banco de dados");
		} catch (Exception e) {
			e.printStackTrace();
			return new Saida("Erro", e.getMessage());
		}
	}

	@GET
	@Path("/buscarCidade/{ibge_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public SaidaCidade buscarCidadePorId(@PathParam("ibge_id") Integer ibge_id) {
		try {
			Cidade cidade = buscarCidadePorIdMethod(ibge_id);
			if(cidade != null){
				return new SaidaCidade(cidade, "Sucesso", "Cidade retornada");
			}
			return new SaidaCidade("Alerta", "Nenhuma cidade encontrada com o 'ibge_id' informado");
		} catch (Exception e) {
			e.printStackTrace();
			return new SaidaCidade("Erro", e.getMessage());
		}
	}
	
	private Cidade buscarCidadePorIdMethod(@PathParam("ibge_id") Integer ibge_id) throws Exception {
		return cidadeDao.findById(ibge_id);
	}

	@PUT
	@Path("/atualizarCidade")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Saida atualizarCidade(Cidade cidade) {
		try {
			cidadeDao.edit(cidade);
			return new Saida("Sucesso", "Cidade atualizada");
		} catch (Exception e) {
			e.printStackTrace();
			return new Saida("Erro", e.getMessage());
		}
	}

	@GET
	@Path("/buscarCapitaisOrdenadas")
	@Produces(MediaType.APPLICATION_JSON)
	public SaidaCidades buscarCapitaisOrdenadas() {
		try {
			String query = "select * from cidade where capital = true order by name";
			List<Cidade> cidades = cidadeDao.consultaPorQuery(query);
			return new SaidaCidades(cidades, "Sucesso", "Capitais retornadas");
		} catch (Exception e) {
			e.printStackTrace();
			return new SaidaCidades("Erro", e.getMessage());
		}
	}

	@GET
	@Path("/buscarEstadosComMaiorEMenorQtdCidades")
	@Produces(MediaType.APPLICATION_JSON)
	public SaidaEstadoQtds buscarEstadosComMaiorEMenorQtdCidades() {
		try {
			List<EstadoQtd> lista = estadoQtdDao.buscarEstadoEQuantidade();
			List<EstadoQtd> estados = new ArrayList<EstadoQtd>();
			estados.add(lista.get(0));
			estados.add(lista.get(lista.size() - 1));
			return new SaidaEstadoQtds(estados, "Sucesso", "Estados com maior e menor quantidades cidades, e quantidade de cidades retornadas");
		} catch (Exception e) {
			e.printStackTrace();
			return new SaidaEstadoQtds("Erro", e.getMessage());
		}
	}

	@GET
	@Path("/buscarEstadosQtdCidades")
	@Produces(MediaType.APPLICATION_JSON)
	public SaidaEstadoQtds buscarEstadosQtdCidades() {
		try {
			List<EstadoQtd> lista = estadoQtdDao.buscarEstadoEQuantidade();
			return new SaidaEstadoQtds(lista, "Sucesso", "Estados e quantidades de cidades retornadas");
		} catch (Exception e) {
			e.printStackTrace();
			return new SaidaEstadoQtds("Erro", e.getMessage());
		}
	}

	@GET
	@Path("/buscarCidadesPorEstado/{estado}")
	@Produces(MediaType.APPLICATION_JSON)
	public SaidaCidades buscarCidadesPorEstado(@PathParam("estado") String estado) {
		try {
			String query = "select * from cidade where uf = '" + estado + "'";
			List<Cidade> cidades = cidadeDao.consultaPorQuery(query);
			List<Cidade> lista = new ArrayList<Cidade>();
			for (Cidade cidade : cidades) {
				Cidade c = new Cidade();
				c.setName(cidade.getName());
				lista.add(c);
			}
			return new SaidaCidades(cidades, "Sucesso", "Cidades de "+estado+" retornadas");
		} catch (Exception e) {
			e.printStackTrace();
			return new SaidaCidades("Erro", e.getMessage());
		}
	}

	@GET
	@Path("/filtrarCidadesPorColuna/{coluna}/{filtro}")
	@Produces(MediaType.APPLICATION_JSON)
	public SaidaCidades filtrarCidadesPorColuna(@PathParam("coluna") String coluna, @PathParam("filtro") String filtro) {
		try {
			String query = "select * from cidade where " + coluna + " like '%" + filtro + "%'";
			List<Cidade> cidades = cidadeDao.consultaPorQuery(query);
			return new SaidaCidades(cidades, "Sucesso", "Cidades retornadas");
		} catch (Exception e) {
			e.printStackTrace();
			return new SaidaCidades("Erro", e.getMessage());
		}
	}

	@GET
	@Path("/consultarQuantidadeDistintaPorColuna/{coluna}")
	@Produces(MediaType.APPLICATION_JSON)
	public SaidaQuantidadeRegistro consultarQuantidadeDistintaPorColuna(String coluna) {
		try {
			Integer quantidade = cidadeDao.consultarQuantidadeDistintaPorColuna(coluna);
			return new SaidaQuantidadeRegistro(quantidade, "Sucesso", "Retornado a quantidade de registros únicos da coluna '"+coluna+"'");
		} catch (Exception e) {
			e.printStackTrace();
			return new SaidaQuantidadeRegistro("Erro", e.getMessage());
		}
	}
	
	@GET
	@Path("/consultarQuantidadeRegistros")
	@Produces(MediaType.APPLICATION_JSON)
	public SaidaQuantidadeRegistro consultarQuantidadeRegistros() {
		try {
			Integer quantidade = cidadeDao.consultarQuantidadeRegistros();
			return new SaidaQuantidadeRegistro(quantidade, "Sucesso", "Retornado a quantidade total de registros");
		} catch (Exception e) {
			e.printStackTrace();
			return new SaidaQuantidadeRegistro("Erro", e.getMessage());
		}
	}
	
	@GET
	@Path("/buscarAsDuasCidadesMaisDistanteUmaDaOutra")
	@Produces(MediaType.APPLICATION_JSON)
	public SaidaCidades buscarAsDuasCidadesMaisDistanteUmaDaOutra() {
		List<Cidade> cidadesMaisDistantes = new ArrayList<Cidade>();
		Cidade cidade1 = null;
		Cidade cidade2 = null;
		Double distancia = 0.0;
		try {
			List<Cidade> cidades = cidadeDao.findAll();
			for(Cidade c1 : cidades){
				Cidade c2 = cidadeDao.consultaCidadeMaisDistante(c1.getLat(), c1.getLon());
				if(c2.getDistancia() > distancia){
					distancia = c2.getDistancia();
					cidade1 = c1;
					cidade2 = c2;
				}
			}
			cidadesMaisDistantes.add(cidade1);
			cidadesMaisDistantes.add(cidade2);
			return new SaidaCidades(cidadesMaisDistantes, "Sucesso", "Retornada as duas cidades que são mais distantes uma da outra");
		} catch (Exception e) {
			e.printStackTrace();
			return new SaidaCidades("Erro", e.getMessage());
		}
		
	}
	
}
