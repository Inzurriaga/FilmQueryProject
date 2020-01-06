package com.skilldistillery.filmquery.database;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private static final String url = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	private static final String user = "student";
	private static final String pass = "student";

	public DatabaseAccessorObject() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {

		}
	}

	private PreparedStatement preparedAndBindStatementById(Connection conn, int id, String sql) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
		return stmt;
	}
	
	private PreparedStatement preparedAndBindStatementByKeyword(Connection conn, String word, String sql) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setString(1, "%" + word);
		stmt.setString(2, "%" + word);
		return stmt;
	}

	@Override
	public Film findFilmById(int filmId) {
		Film film = null;
		String sql = "Select film.id, film.title,  film.description, film.release_year, film.language_id, film.rental_duration, film.rental_rate, film.length, film.replacement_cost, film.rating, film.special_features, language.name from film join language on film.language_id = language.id where film.id = ?";
		try (Connection conn = DriverManager.getConnection(url, user, pass);
				PreparedStatement stmt = preparedAndBindStatementById(conn, filmId, sql);
				ResultSet rst = stmt.executeQuery();) {
			if (rst.next()) {
				film = new Film(rst.getInt("film.id"), rst.getString("film.title"), rst.getString("film.description"),
						rst.getInt("film.release_year"), rst.getInt("language_id"), rst.getInt("rental_duration"),
						rst.getDouble("rental_rate"), rst.getInt("length"), rst.getDouble("replacement_cost"),
						rst.getString("rating"), rst.getString("special_features"),rst.getString("language.name"), findActorsByFilmId(filmId));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return film;
	}

	// fix this fix this fix this fix this 
	@Override
	public Film findFilmByKeyword(String filmKeyword) {
		Film film = null;
		String sql = "Select film.id, film.title,  film.description, film.release_year, film.language_id, film.rental_duration, film.rental_rate, film.length, film.replacement_cost, film.rating, film.special_features, language.name from film join language on film.language_id = language.id where film.title like ? or film.description like ?";
		try (Connection conn = DriverManager.getConnection(url, user, pass);
				PreparedStatement stmt = preparedAndBindStatementByKeyword(conn, filmKeyword, sql);
				ResultSet rst = stmt.executeQuery();) {
			if (rst.next()) {
				film = new Film(rst.getInt("film.id"), rst.getString("film.title"), rst.getString("film.description"),
						rst.getInt("film.release_year"), rst.getInt("language_id"), rst.getInt("rental_duration"),
						rst.getDouble("rental_rate"), rst.getInt("length"), rst.getDouble("replacement_cost"),
						rst.getString("rating"), rst.getString("special_features"), rst.getString("language.name"), findActorsByFilmId(rst.getInt("film.id")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return film;
	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		String sql = "Select actor.id, actor.first_name, actor.last_name from actor where actor.id = ?";
		try (Connection conn = DriverManager.getConnection(url, user, pass);
				PreparedStatement stmt = preparedAndBindStatementById(conn, actorId, sql);
				ResultSet rst = stmt.executeQuery();) {
			if (rst.next()) {
				actor = new Actor(rst.getInt("actor.id"), rst.getString("actor.first_name"),
						rst.getString("actor.last_name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<Actor>();
		Actor actor = null;
		String sql = "Select actor.id, actor.first_name, actor.last_name from actor join film_actor on actor.id = film_actor.actor_id where film_actor.film_id = ?";
		try (Connection conn = DriverManager.getConnection(url, user, pass);
				PreparedStatement stmt = preparedAndBindStatementById(conn, filmId, sql);
				ResultSet rst = stmt.executeQuery();) {
			while (rst.next()) {
				actor = new Actor(rst.getInt("actor.id"), rst.getString("actor.first_name"),
						rst.getString("actor.last_name"));
				actors.add(actor);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return actors;
	}

}
