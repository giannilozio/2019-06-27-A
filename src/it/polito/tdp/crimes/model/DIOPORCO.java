package it.polito.tdp.crimes.model;

public class DIOPORCO {
	
	private String v1;
	private String v2;
	private double peso;

	public DIOPORCO(String string, String string2, double double1) {
		
		this.v1=string;
		this.v2=string2;
		this.peso=double1;
		
	}

	public String getV1() {
		return v1;
	}

	public void setV1(String v1) {
		this.v1 = v1;
	}

	public String getV2() {
		return v2;
	}

	public void setV2(String v2) {
		this.v2 = v2;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return String.format("Vertice 1 =%s, Vertice 2 =%s, peso = %s "+"\n", v1, v2, peso);
	}
	
	
	

}
