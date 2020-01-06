package com.skilldistillery.filmquery.database;

import com.skilldistillery.filmquery.entities.Film;
import com.skilldistillery.filmquery.entities.Actor;
import java.util.List;

public interface DatabaseAccessor {
  public Film findFilmById(int filmId);
  public Film findFilmByKeyword(String filmKeyword);
  public Actor findActorById(int actorId);
  public List<Actor> findActorsByFilmId(int filmId);
}
