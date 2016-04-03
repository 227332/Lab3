package it.polito.tdp.lab3.model;

public class Studente {
	/* è più corretto vedere matricola come Stringa piuttosto che come numero,
	 * in quanto la somma o differenza di due matricole non ha senso
	 * esempi di matricola sono 146101, 148072...
	 */
	private String matricola;
	private String nome;
	private String cognome;
	private String cDs;
	
	public Studente(String matricola, String nome, String cognome, String cDs) {
		super();
		this.matricola = matricola;
		this.nome = nome;
		this.cognome = cognome;
		this.cDs = cDs;
	}

	public String getMatricola() {
		return matricola;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getcDs() {
		return cDs;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return matricola+"   "+cognome+"   "+nome+"   "+cDs;
	}
	
	

}
