package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private List<Event> vertici;
	private List<DIOPORCO> archi;
	private EventsDao dao;
	private Map<Long,Event> idMap;
	private List <DIOPORCO> best;
	private Graph<String,DefaultWeightedEdge> grafo;
	private double cont ;
	
	public Model() {
		this.vertici = new ArrayList<>();
		this.dao = new EventsDao();
		this.best = new ArrayList<>();
		this.idMap = new HashMap<>();
		this.archi = new ArrayList<>();
		this.grafo = new SimpleWeightedGraph<String,DefaultWeightedEdge>(DefaultWeightedEdge.class);
	}

	public List<String> getCategory() {
	return	dao.getCategory();
		
	}

	public List<Integer> getAnni() {
		return dao.getAnni();
	}

	public void creaGrafo(String categoria, Integer anno) {
		vertici = dao.getVertici(categoria,anno,idMap);
		for(Event e : vertici) {
			grafo.addVertex(e.getOffense_type_id());
		}
		//Graphs.addAllVertices(grafo, vertici);
			archi = dao.getArchi(categoria,anno,idMap);
		for(DIOPORCO p :archi) {
			String p1 = p.getV1();
			String p2 = p.getV2();
			double peso = p.getPeso();
			
			Graphs.addEdge(grafo, p1, p2, peso);
		}
	}

	public Graph<String, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public void setGrafo(Graph<String, DefaultWeightedEdge> grafo) {
		this.grafo = grafo;
	}

	public List<DIOPORCO> getBest() {
		 cont =0;
		for(DIOPORCO e : archi) {
			
			if(e.getPeso()>cont) {
				best = new ArrayList<>();
				best.add(e);
				cont = e.getPeso();
			}if(e.getPeso() == cont)
				best.add(e);
		}
		return best;
		
		
	}

	public double getCont() {
		return cont;
	}
	
}
