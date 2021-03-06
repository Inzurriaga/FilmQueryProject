package com.skilldistillery.filmquery.app;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//    app.test();
		app.launch();
	}

	private void test() {
		Film film = db.findFilmById(1);
		System.out.println(film);
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		int userInput = 0;
		do {
			try {
				menu();
				userInput = input.nextInt();
				switch (userInput) {
					case 1:
						searchFilmById(input);
						break;
					case 2:
						searchFilmByKeyword(input);
						break;
					case 3:
						break;
					default:
						System.err.println("Please pick an option from the menu.");
				}
			} catch (InputMismatchException e) {
				System.err.println("invalid input please pick an option from the menu. \n");
				input.next();
			}
		} while (userInput != 3);
		System.out.println("Thank you have a good day!!!");

	}

	private void menu() {
		System.out.print(
				"1) Look up a film by its id \n2) Look up a film by a search keyword.\n3) Exit the application.\nEnter input: ");
	}

	private void searchFilmById(Scanner input) {
		int filmId = 0;
		do {
			try {
				System.out.print("Enter the film's id: ");
				filmId = input.nextInt();
				Film film = db.findFilmById(filmId);
				if (film == null) {
					System.out.println("Film not found");
				} else {
					displayFilm(film);
				}
			} catch (InputMismatchException e) {
				System.err.println("invalid input please pick an option from the menu. \n");
				input.next();
			}
		} while (filmId == 0);

	}

	private void searchFilmByKeyword(Scanner input) {
		String keyword = null;
		do {
			System.out.print("Enter Keywords to find film: ");
			keyword = input.next();
			List<Film> films = db.findFilmByKeyword(keyword);
			if (films.size() == 0) {
				System.out.println("Film not found");
			} else {
				for(Film film : films) {
					displayFilm(film);
				}
			}
		} while (keyword == null);

	}

	private void displayFilm(Film film) {
		System.out.println("\nFilm title: " + film.getTitle() + "\nRelease date: " + film.getReleaseYear() + "\nRating: " + film.getRating() + "\nLanguage: " + film.getFilmLanguage()  +  "\nDescription: " + film.getDescription() + "\nActors: " + film.getActors() + "\n");
	}

}
