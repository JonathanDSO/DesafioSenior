package resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.Path;

import entity.Cidade;

@Path("/cidades")
public class RestCidade {

	private static Scanner leitor;

	public static void main(String[] args) {
		try {
			List<Cidade> cidades = new ArrayList<Cidade>();

			String linhas = new String();

			File arquivoCSV = new File("WebContent/cidades.csv");
			leitor = new Scanner(arquivoCSV);

			linhas = leitor.nextLine();
			while (leitor.hasNext()) {
				Cidade cidade = new Cidade();
				linhas = leitor.nextLine();
				String[] valores = linhas.split(",");

				cidade.setIbge_id(new Integer(valores[0]));
				cidade.setUf(valores[1]);
				cidade.setName(valores[2]);
				cidade.setCapital(valores[3]);
				cidade.setLon(new Double(valores[4]));
				cidade.setLat(new Double(valores[5]));
				cidade.setNo_accents(valores[6]);
				cidade.setAlternative_names(valores[7]);
				cidade.setMicroregion(valores[8]);
				cidade.setMesoregion(valores[9]);

				cidades.add(cidade);

			}
			System.out.println(cidades);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
