package it.polito.tdp.lab3.model;

public class Corso {
	private String codCorso;
	private int crediti;
	private String nomeCorso;
	private String pD;
	
	public Corso(String codCorso, int crediti, String nomeCorso, String pD) {
		super();
		this.codCorso = codCorso;
		this.crediti = crediti;
		this.nomeCorso = nomeCorso;
		this.pD = pD;
	}

	public String getCodCorso() {
		return codCorso;
	}

	public int getCrediti() {
		return crediti;
	}

	public String getNomeCorso() {
		return nomeCorso;
	}

	public String getpD() {
		return pD;
	}
/*
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.nomeCorso;
	}*/
	
	public String toString(){
		return codCorso+"  "+crediti+"  "+nomeCorso+"  "+pD;
	}
	
	

}
