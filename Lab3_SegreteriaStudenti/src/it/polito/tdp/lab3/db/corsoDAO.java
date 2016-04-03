package it.polito.tdp.lab3.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.lab3.model.Corso;
import it.polito.tdp.lab3.model.Studente;


public class corsoDAO {
	
	private String jdbcURL = "jdbc:mysql://localhost/iscritticorsi?user=root";	
	
	public List<String> popolaTendina(){
		List<String> lista=new ArrayList<String>();
		
		try {
			Connection conn = DriverManager.getConnection(jdbcURL);
			
			Statement st = conn.createStatement();
			
			String sql= "SELECT nome FROM corso";
			
			ResultSet res=st.executeQuery(sql);
			
			while(res.next()){				
				lista.add(res.getString("nome"));
			}
			res.close();
			conn.close();
			return lista;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return null;
	}

	public List<Studente> iscrittiAlCorso(String nomecorso) {
		List<Studente> lista=new ArrayList<Studente>();
		
		try {
			Connection conn = DriverManager.getConnection(jdbcURL);
			
			Statement st = conn.createStatement();
			
			String sql="SELECT DISTINCT S.matricola,S.cognome,S.nome,S.CDS FROM studente S,corso C,iscrizione I WHERE I.matricola=S.matricola AND C.codins=I.codins AND C.nome=\""+nomecorso+"\"";
			
			ResultSet res=st.executeQuery(sql);
			
			while(res.next()){				
				lista.add(new Studente(res.getString("matricola"),res.getString("nome"),res.getString("cognome"),res.getString("CDS")));
			}
			res.close();
			conn.close();
			/*la lista può essere vuota...in tal caso devo restituire null per
			 * forza perchè è lo stesso valore che restituisco dopo il try catch
			 */
			if(lista.isEmpty())
				lista=null;
			
			return lista;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return null;
	}

	public Corso searchCourse(String nomecorso) {
		// TODO Auto-generated method stub
		
		try {
			Connection conn = DriverManager.getConnection(jdbcURL);
			
			Statement st = conn.createStatement();
			
			String sql="SELECT codins,crediti,nome,pd FROM corso WHERE nome=\""+nomecorso+"\"";
									
			ResultSet res=st.executeQuery(sql);
			
			//uso IF perchè avrò al più una tupla
			if(res.next()){
				Corso c=new Corso(res.getString("codins"),res.getInt("crediti"),res.getString("nome"),res.getString("pd"));
				res.close();
				conn.close();
				return c;
			}
			else{
				return null;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}

	public boolean iscriviStudente(Studente s, Corso c) {
		// TODO Auto-generated method stub
		
		try {
			Connection conn = DriverManager.getConnection(jdbcURL);
			
			Statement st = conn.createStatement();
			
			String sql= String.format("INSERT INTO iscrizione VALUES('%s','%s')",s.getMatricola(),c.getCodCorso());
			
			if(st.executeUpdate(sql)==1){
				conn.close();
				return true;
			}
			
			conn.close();
			return false;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
	}

}
