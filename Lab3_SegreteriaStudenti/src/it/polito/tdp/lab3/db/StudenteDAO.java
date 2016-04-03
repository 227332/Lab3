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

public class StudenteDAO {
	
	private String jdbcURL = "jdbc:mysql://localhost/iscritticorsi?user=root";
	
	public Studente searchStudent(String matricola) {
		
			try {
				Connection conn = DriverManager.getConnection(jdbcURL);
				
				Statement st = conn.createStatement();
				
				String sql="SELECT matricola,cognome,nome,CDS FROM studente WHERE matricola=\""+matricola+"\"";
				/*andava bene anche:
				 * String sql= String.format("SELECT matricola,cognome,nome,CDS FROM studente WHERE matricola='%s'", matricola);
				 */
								
				ResultSet res=st.executeQuery(sql);
				
				//uso IF perchè avrò al più una tupla
				if(res.next()){
					Studente s=new Studente(res.getString("matricola"),res.getString("nome"),res.getString("cognome"),res.getString("CDS"));
					res.close();
					conn.close();
					return s;
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

	public List<Corso> corsiStudente(Studente s) {
		// TODO Auto-generated method stub
		List<Corso> lista=new ArrayList<Corso>();
		try {
			Connection conn = DriverManager.getConnection(jdbcURL);
			
			Statement st = conn.createStatement();
			
			String sql="SELECT C.codins,C.crediti,C.nome,C.pd FROM studente S,iscrizione I, corso C WHERE C.codins=I.codins AND S.matricola=I.matricola AND S.matricola=\""+s.getMatricola()+"\"";
			
			ResultSet res=st.executeQuery(sql);
			
			while(res.next()){
				lista.add(new Corso(res.getString("codins"),res.getInt("crediti"),res.getString("nome"),res.getString("pd")));
			}
						
			res.close();
			conn.close();
			//restituisco una lista che può essere anche empty
			return lista;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}

	public boolean matchStudentCourse(Studente s, Corso c) {
		// TODO Auto-generated method stub
		
		try {
			Connection conn = DriverManager.getConnection(jdbcURL);
			
			Statement st = conn.createStatement();
			
			String sql= String.format("SELECT matricola FROM iscrizione WHERE matricola= '%s' AND codins='%s'", s.getMatricola(),c.getCodCorso());
			
			
			ResultSet res=st.executeQuery(sql);
			
			//uso IF perchè il risultato della query avrà al più una tupla
			if(res.next()){
				res.close();
				conn.close();
				return true;
			}			
			res.close();
			conn.close();
			return false;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
		return false;
	}

}

