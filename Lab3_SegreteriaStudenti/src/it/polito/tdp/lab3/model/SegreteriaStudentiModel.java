package it.polito.tdp.lab3.model;


import it.polito.tdp.lab3.db.*;
import java.util.List;



public class SegreteriaStudentiModel {

	public List<String> popolaTendina() {
		// TODO Auto-generated method stub
		corsoDAO dao=new corsoDAO();
		List<String> lista=dao.popolaTendina();
		/* inserisco un campo vuoto da utilizzare quando non si vuole 
		   selezionare nessun corso:*/
		lista.add("");
		
		return lista ;
	}

	public Studente searchStudent(String matricola) {
		// TODO Auto-generated method stub
		StudenteDAO s=new StudenteDAO();			
		return s.searchStudent(matricola);
		
	}

	public List<Studente> iscrittiAlCorso(String nomecorso) {
		// TODO Auto-generated method stub
		corsoDAO dao=new corsoDAO();
		return dao.iscrittiAlCorso(nomecorso);
	}

	public List<Corso> corsiDelloStudente(Studente s) {
		// TODO Auto-generated method stub
		StudenteDAO dao=new StudenteDAO();
		return dao.corsiStudente(s);
	}
	public Corso searchCourse(String nomecorso) {
		// TODO Auto-generated method stub
		corsoDAO dao=new corsoDAO();			
		return dao.searchCourse(nomecorso);
		
	}

	public boolean matchStudentCourse(Studente s, Corso c) {
		// TODO Auto-generated method stub
		StudenteDAO dao=new StudenteDAO();
		return dao.matchStudentCourse(s, c);
	}

	public boolean iscrizione(Studente s, Corso c) {
		// TODO Auto-generated method stub
		corsoDAO dao=new corsoDAO();
		return dao.iscriviStudente(s, c);		
	}

}
