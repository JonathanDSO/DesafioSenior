package persistence;

import java.util.ArrayList;
import java.util.List;

import entity.Cidade;

public class CidadeDao extends Dao {

	public void create(Cidade c) throws Exception {
		open();
		stmt = con.prepareStatement("insert into cidade values (?,?,?,?,?,?,?,?,?,?)");
		stmt.setInt(1, c.getIbge_id());
		stmt.setString(2, c.getUf());
		stmt.setString(3, c.getName());
		stmt.setBoolean(4, c.getCapital());
		stmt.setDouble(5, c.getLon());
		stmt.setDouble(6, c.getLat());
		stmt.setString(7, c.getNo_accents());
		stmt.setString(8, c.getAlternative_names());
		stmt.setString(9, c.getMicroregion());
		stmt.setString(10, c.getMesoregion());
		stmt.execute();
		stmt.close();
		close();
	}

	public List<Cidade> findAll() throws Exception {
		open();
		List<Cidade> lista = new ArrayList<Cidade>();
		stmt = con.prepareStatement("select * from cidade");
		rs = stmt.executeQuery();
		while (rs.next()) {
			Cidade cidade = new Cidade(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getDouble(5),
					rs.getDouble(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));

			lista.add(cidade);
		}
		stmt.close();
		close();
		return lista;
	}
	
	public Cidade findById(Integer ibge_id) throws Exception {
		open();
		stmt = con.prepareStatement("select * from cidade where ibge_id=?");
		stmt.setInt(1, ibge_id);
		rs = stmt.executeQuery();
		Cidade cidade = null;
		if (rs.next()) {
			cidade = new Cidade(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getDouble(5),
					rs.getDouble(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
		}
		stmt.close();
		close();
		return cidade;
	}

	public void edit(Cidade c) throws Exception {
		open();
		stmt = con.prepareStatement(
				"update cidade set uf=?, name=?, capital=?, lon=?, lat=?, no_accents=?, alternative_names=?, microregion=?, mesoregion=? where ibge_id=?");
		stmt.setString(1, c.getUf());
		stmt.setString(2, c.getName());
		stmt.setBoolean(3, c.getCapital());
		stmt.setDouble(4, c.getLon());
		stmt.setDouble(5, c.getLat());
		stmt.setString(6, c.getNo_accents());
		stmt.setString(7, c.getAlternative_names());
		stmt.setString(8, c.getMicroregion());
		stmt.setString(9, c.getMesoregion());
		stmt.setInt(10, c.getIbge_id());
		stmt.executeUpdate();
		stmt.close();
		close();
	}

	public void delete(Integer ibge_id) throws Exception {
		open();
		stmt = con.prepareStatement("delete from cidade where ibge_id=?");
		stmt.setInt(1, ibge_id);
		stmt.execute();
		stmt.close();
		close();
	}
	
	public List<Cidade> consultaPorQuery(String query) throws Exception {
		open();
		stmt = con.prepareStatement(query);
		rs = stmt.executeQuery();
		List<Cidade> cidades = new ArrayList<Cidade>();
		while (rs.next()) {
			cidades.add(new Cidade(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getDouble(5),
					rs.getDouble(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10)));
		}
		stmt.close();
		close();
		return cidades;
	}

}
