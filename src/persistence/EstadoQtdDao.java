package persistence;

import java.util.ArrayList;
import java.util.List;

import entity.EstadoQtd;

public class EstadoQtdDao extends Dao{

	public List<EstadoQtd> buscarEstadoEQuantidade() throws Exception {
		open();
		stmt = con.prepareStatement("select distinct(uf) as uf, count(uf) as qtd from cidade group by uf order by qtd");
		rs = stmt.executeQuery();
		List<EstadoQtd> estadoQtds = new ArrayList<EstadoQtd>();
		while (rs.next()) {
			estadoQtds.add(new EstadoQtd(rs.getString(1), rs.getInt(2)));
		}
		stmt.close();
		close();
		return estadoQtds;
	}
	
}
