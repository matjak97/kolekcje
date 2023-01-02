package tb.soft;

import java.util.Arrays;

/**
 * Program: Aplikacja działająca w oknie konsoli, która umożliwia testowanie 
 *          operacji wykonywanych na obiektach klasy Person.
 *    Plik: PersonConsoleApp.java

 *   Autor: Paweł Rogaliński
 *    Data: październik 2018 r.
 */
public class PersonConsoleApp {

	private static final String GREETING_MESSAGE =
			"""
					Program Person - wersja konsolowa
					Autor: Paweł Rogaliński
					Data:  październik 2018 r.
					""";

	private static final String MENU =
			"""
					    M E N U   G Ł Ó W N E \s
					1 - Podaj dane nowej osoby\s
					2 - Usuń dane osoby       \s
					3 - Modyfikuj dane osoby  \s
					4 - Wczytaj dane z pliku  \s
					5 - Zapisz dane do pliku  \s
					6 - Wypisz listę osób     \s
					7 - Ponowne dodanie tej samej osoby \s
					8 - Przełącz typ klasy osoby\s
					0 - Zakończ program       \s
					""";
	
	private static final String CHANGE_MENU =
			"""
					   Co zmienić?    \s
					1 - Imię          \s
					2 - Nazwisko      \s
					3 - Rok urodzenia \s
					4 - Stanowisko    \s
					0 - Powrót do menu głównego
					""";

	
	/**
	 * ConsoleUserDialog to pomocnicza klasa zawierająca zestaw
	 * prostych metod do realizacji dialogu z użytkownikiem
	 * w oknie konsoli tekstowej.
	 */
	private static final ConsoleUserDialog UI = new JOptionUserDialog();
	
	
	public static void main(String[] args) {
		// Utworzenie obiektu aplikacji konsolowej
		// oraz uruchomienie głównej pętli aplikacji.
		PersonConsoleApp application = new PersonConsoleApp();
		application.runMainLoop();
	} 

	/*
	 *  Referencja do obiektu, który zawiera dane aktualnej osoby.
	 */
	private Person currentPerson = null;

	/*
	 *  Metoda runMainLoop wykonuje główną pętlę aplikacji.
	 *  UWAGA: Ta metoda zawiera nieskończoną pętlę,
	 *         w której program się zatrzymuje aż do zakończenia
	 *         działania za pomocą metody System.exit(0); 
	 */
	public void runMainLoop() {
		UI.printMessage(GREETING_MESSAGE);
		Kolekcje kolekcje = new Kolekcje();
		boolean isPersonExtended = false;

		while (true) {
			UI.clearConsole();
			showCurrentPerson();

			try {
				switch (UI.enterInt(MENU + "ExtendedPerson: " +  isPersonExtended + "\n==>> ")) {
					case 1 -> {
						// utworzenie nowej osoby
						currentPerson = createNewPerson();
						if(currentPerson != null)
							if(isPersonExtended)
								kolekcje.addElement(new Person2(currentPerson));
							else kolekcje.addElement(currentPerson);
					}
					case 2 -> {
						// usunięcie danych aktualnej osoby.
						currentPerson = null;
						UI.printInfoMessage("Dane aktualnej osoby zostały usunięte");
					}
					case 3 -> {
						// zmiana danych dla aktualnej osoby
						if (currentPerson == null) throw new PersonException("Żadna osoba nie została utworzona.");
						changePersonData(currentPerson);
					}
					case 4 -> {
						// odczyt danych z pliku tekstowego.
						String file_name = UI.enterString("Podaj nazwę pliku: ");
						currentPerson = Person.readFromFile(file_name);
						UI.printInfoMessage("Dane aktualnej osoby zostały wczytane z pliku " + file_name);
					}
					case 5 -> {
						// zapis danych aktualnej osoby do pliku tekstowego
						String file_name = UI.enterString("Podaj nazwę pliku: ");
						Person.printToFile(file_name, currentPerson);
						UI.printInfoMessage("Dane aktualnej osoby zostały zapisane do pliku " + file_name);
					}
					case 6 ->
						// wypisanie dodanych wcześniej osób
						UI.printInfoMessage(kolekcje.printContents());
					case 7 -> {
						// ponowne dodanie tej samej osoby
						if (isPersonExtended)
							kolekcje.addElement(new Person2(currentPerson));
						else kolekcje.addElement(currentPerson);
					}
					case 8 ->
						isPersonExtended = !isPersonExtended;
					case 0 -> {
						// zakończenie działania programu
						UI.printInfoMessage("\nProgram zakończył działanie!");
						System.exit(0);
					}
				} // koniec instrukcji switch
			} catch (PersonException e) { 
				// Tu są wychwytywane wyjątki zgłaszane przez metody klasy Person,
				// gdy nie są spełnione ograniczenia nałożone na dopuszczalne wartości
				// poszczególnych atrybutów.
				// Drukowanie komunikatu o błędzie zgłoszonym za pomocą wyjątku PersonException.
				UI.printErrorMessage(e.getMessage());
			}
		} // koniec pętli while
	}
	
	
	/*
	 *  Metoda wyświetla w oknie konsoli dane aktualnej osoby 
	 *  pamiętanej w zmiennej currentPerson.
	 */
	void showCurrentPerson() {
		showPerson(currentPerson);
	} 

	
	/* 
	 * Metoda wyświetla w oknie konsoli dane osoby reprezentowanej 
	 * przez obiekt klasy Person
	 */ 
	static void showPerson(Person person) {
		StringBuilder sb = new StringBuilder();
		
		if (person != null) {
			sb.append("Aktualna osoba: \n")
			  .append("      Imię: ").append(person.getFirstName()).append("\n")
			  .append("  Nazwisko: ").append(person.getLastName()).append("\n")
			  .append("   Rok ur.: ").append(person.getBirthYear()).append("\n")
			  .append("Stanowisko: ").append(person.getJob()).append("\n");
		} else
			sb.append( "Brak danych osoby\n" );
		UI.printMessage( sb.toString() );
	}

	
	/* 
	 * Metoda wczytuje w konsoli dane nowej osoby, tworzy nowy obiekt
	 * klasy Person i wypełnia atrybuty wczytanymi danymi.
	 * Walidacja poprawności danych odbywa się w konstruktorze i setterach
	 * klasy Person. Jeśli zostaną wykryte niepoprawne dane,
	 * to zostanie zgłoszony wyjątek, który zawiera komunikat o błędzie.
	 */
	static Person createNewPerson(){
		String first_name = UI.enterString("Podaj imię: ");
		String last_name = UI.enterString("Podaj nazwisko: ");
		String birth_year = UI.enterString("Podaj rok ur.: ");
		UI.printMessage("Dozwolone stanowiska:" + Arrays.deepToString(PersonJob.values()));
		String job_name = UI.enterString("Podaj stanowisko: ");
		Person person;
		try { 
			// Utworzenie nowego obiektu klasy Person oraz
			// ustawienie wartości wszystkich atrybutów.
			person = new Person(first_name, last_name);
			person.setBirthYear(birth_year);
			person.setJob(job_name);
		} catch (PersonException e) {    
			// Tu są wychwytywane wyjątki zgłaszane przez metody klasy Person,
			// gdy nie są spełnione ograniczenia nałożone na dopuszczalne wartości
			// poszczególnych atrybutów.
			// Drukowanie komunikatu o błędzie zgłoszonym za pomocą wyjątku PersonException.
			UI.printErrorMessage(e.getMessage());
			return null;
		}
		return person;
	}
	
	
	/* 
	 * Metoda pozwala wczytać nowe dane dla poszczególnych atrybutów 
	 * obiekty person i zmienia je poprzez wywołanie odpowiednich setterów z klasy Person.
	 * Walidacja poprawności wczytanych danych odbywa się w setterach
	 * klasy Person. Jeśli zostaną wykryte niepoprawne dane,
	 * to zostanie zgłoszony wyjątek, który zawiera komunikat o błędzie.
	 */
	static void changePersonData(Person person)
	{
		while (true) {
			UI.clearConsole();
			showPerson(person);

			try {		
				switch (UI.enterInt(CHANGE_MENU + "==>> ")) {
				case 1:
					person.setFirstName(UI.enterString("Podaj imię: "));
					break;
				case 2:
					person.setLastName(UI.enterString("Podaj nazwisko: "));
					break;
				case 3:
					person.setBirthYear(UI.enterString("Podaj rok ur.: "));
					break;
				case 4:
					UI.printMessage("Dozwolone stanowiska:" + Arrays.deepToString(PersonJob.values()));
					person.setJob(UI.enterString("Podaj stanowisko: "));
					break;
				case 0: return;
				}  // koniec instrukcji switch
			} catch (PersonException e) {     
				// Tu są wychwytywane wyjątki zgłaszane przez metody klasy Person,
				// gdy nie są spełnione ograniczenia nałożone na dopuszczalne wartości
				// poszczególnych atrybutów.
				// Drukowanie komunikatu o błędzie zgłoszonym za pomocą wyjątku PersonException.
				UI.printErrorMessage(e.getMessage());
			}
		}
	}
	
	
}  // koniec klasy PersonConsoleApp
