package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.crimes.model.DIOPORCO;
import it.polito.tdp.crimes.model.Event;


public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<String> getCategory() {
		String sql = "SELECT DISTINCT offense_category_id " +
					 "FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<String> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getString("offense_category_id"));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
		
	}

	public List<Integer> getAnni() {
		String sql = "SELECT DISTINCT Year(reported_date) anno " + 
					 "FROM EVENTS" ;
	try {
		Connection conn = DBConnect.getConnection() ;

		PreparedStatement st = conn.prepareStatement(sql) ;
		
		List<Integer> list = new ArrayList<>() ;
		
		ResultSet res = st.executeQuery() ;
		
		while(res.next()) {
			try {
				list.add(res.getInt("anno"));
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		conn.close();
		return list ;

	} catch (SQLException e) {
		e.printStackTrace();
		return null ;
	}
	}

	public List<Event> getVertici(String categoria, Integer anno, Map<Long, Event> idMap) {
		String sql = "SELECT * " + 
					 "FROM EVENTS e " + 
					 "WHERE YEAR(reported_date) = ? AND offense_category_id = ?" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			st.setInt(1, anno);
			st.setString(2, categoria);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					Event e = new Event(res.getLong("incident_id"),res.getInt("offense_code"),res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), res.getString("offense_category_id"), res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"), res.getDouble("geo_lon"), res.getDouble("geo_lat"),
							res.getInt("district_id"), res.getInt("precinct_id"), res.getString("neighborhood_id"),
							res.getInt("is_crime"), res.getInt("is_traffic"));
					list.add(e);
					idMap.put(res.getLong("incident_id"), e);
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}

	public List<DIOPORCO> getArchi(String categoria, Integer anno, Map<Long, Event> idMap) {
		String sql = "SELECT distinct e1.offense_type_id, e2.offense_type_id, COUNT(DISTINCT e1.district_id) as peso " + 
					 "FROM EVENTS e1, EVENTS e2 " + 
					 "WHERE e1.offense_type_id <> e2.offense_type_id " + 
					 "AND e1.district_id = e2.district_id " + 
					 "AND e1.offense_category_id =? " + 
					 "AND e1.incident_id <> e2.incident_id " + 
					 "AND e2.offense_category_id=e1.offense_category_id " + 
					 "AND YEAR(e1.reported_date)=? " + 
					 "AND YEAR(e2.reported_date)=YEAR(e1.reported_date) " + 
					 "GROUP BY  e1.offense_type_id, e2.offense_type_id";
	try {
		Connection conn = DBConnect.getConnection() ;

		PreparedStatement st = conn.prepareStatement(sql) ;
		
		List<DIOPORCO> list = new ArrayList<>() ;
		st.setString(1, categoria);
		st.setInt(2, anno);
		ResultSet res = st.executeQuery() ;
		
		while(res.next()) {
			try {
				DIOPORCO e = new DIOPORCO (res.getString("e1.offense_type_id"), res.getString("e2.offense_type_id"),res.getDouble("peso")); ;
				list.add(e);
	
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		
		conn.close();
		return list ;

	} catch (SQLException e) {
		e.printStackTrace();
		return null ;
	}

		
	}

}
