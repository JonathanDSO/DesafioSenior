package resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.ws.rs.Path;

import entity.Cidade;
import persistence.CidadeDao;

@Path("/cidades")
public class RestCidade {

	private static Scanner leitor;
	String caminhoArq = "WebContent/cidades.csv";
	CidadeDao cidadeDao = new CidadeDao();

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
				if(c != null){
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
	
	private Cidade buscarCidadePorId(Integer ibge_id){
		try {
			return cidadeDao.findById(ibge_id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void atualizarCidade(Cidade cidade){
		try {
			cidadeDao.edit(cidade);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new RestCidade().lerArquivoCSV();
		try {
			System.out.println(new CidadeDao().findAll());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
