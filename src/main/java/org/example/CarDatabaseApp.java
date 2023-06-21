package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

// Klasa reprezentująca samochód
class Car {
    private String brand;//tworzymy prywatne zmienne marka model i rok
    private String model;
    private int year;

    public Car(String brand, String model, int year) {//konstruktor do marki modelu i roku aby moc sie do nich potem odwolywac
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    public String getBrand() {//metoda na pozyskiwanie marki
        return brand;
    }

    public String getModel() {//metoda na pozyskiwanie modelu
        return model;
    }

    public int getYear() {//metoda na pozyskanie roku produkcji
        return year;
    }

    @Override
    public String toString() {//metoda odpowiedzialna za zwracanie tekstu @ovveride jest przesłonieciem z klasy nadrzednej metody o takiej samej nazwie
        return brand + " " + model + " (" + year + ")";
    }
}

// Klasa obsługująca bazę danych samochodów
class CarDatabase {
    private String filename; // tworzymy zmienna filename
    private List<Car> cars; // tworzymy liste w ktorej bedziemy przechowywac samochody


    public CarDatabase(String filename) {// konstruktor z dodawaniem samochodow do listy
        cars = new ArrayList<>();
        this.filename = filename;
    }

    // Dodaje samochód do bazy danych
    public void addCar(Car car) {
        cars.add(car);
    }

    // Zapisuje bazę danych do pliku
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {//Tworzymy obiekt PrintWriter wewnątrz bloku try-with-resources. PrintWriter umożliwia łatwe zapisywanie danych do pliku.
            //Tworzymy obiekt FileWriter z nazwą pliku, przekazaną jako parametr konstruktora. FileWriter służy do tworzenia plików tekstowych.

            for (Car car : cars) {//ta petla przeskakuje po samochodach z pliku
                writer.println(car.getBrand() + "," + car.getModel() + "," + car.getYear());//Uzyskane dane są zapisywane w pliku tekstowym, oddzielone przecinkami i zakończone znakiem nowej linii, za pomocą metody println() obiektu PrintWriter.
            }
            System.out.println("Baza danych została zapisana do pliku");
        } catch (IOException e) {//Jeśli wystąpi błąd podczas zapisywania do pliku (np. plik jest niedostępny lub nie można go utworzyć), wyjątek IOException zostanie przechwycony w bloku catch, a komunikat błędu zostanie wyświetlony na konsoli.
            System.out.println("Błąd zapisu do pliku: " + e.getMessage());
        }
    }

    // Wczytuje bazę danych z pliku
    public void loadFromFile() {
        cars.clear();//Metoda rozpoczyna się od wyczyszczenia listy samochodów (cars.clear()) w celu usunięcia ewentualnych wcześniej wczytanych danych.
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {//Następnie tworzymy obiekt BufferedReader wewnątrz bloku try-with-resources. BufferedReader służy do czytania danych z pliku tekstowego.
            //Tworzymy obiekt FileReader z nazwą pliku, przekazaną jako parametr konstruktora. FileReader służy do odczytywania plików tekstowych.

            String line;
            while ((line = reader.readLine()) != null) {//W pętli while, odczytywane są kolejne linie z pliku za pomocą metody readLine() obiektu BufferedReader. Odczytana linia jest przypisywana do zmiennej line.Jeśli odczytana linia nie jest równa null, następuje jej przetwarzanie.
                String[] parts = line.split(",");//Odczytana linia jest podzielona na części za pomocą metody split(","), co oznacza podział linii na elementy rozdzielone przecinkami. Wynikiem jest tablica napisów parts.
                if (parts.length == 3) {//Jeśli tablica parts ma długość równą 3, oznacza to, że odczytana linia zawiera poprawne informacje o samochodzie (marka, model, rok).
                    String brand = parts[0];//Dane są pobierane z tablicy parts: marka znajduje się pod indeksem 0, model pod indeksem 1, a rok jako napis pod indeksem 2.
                    String model = parts[1];
                    int year = Integer.parseInt(parts[2]);//Rok jest konwertowany na liczbę całkowitą za pomocą metody parseInt().
                    Car car = new Car(brand, model, year);
                    //Na podstawie odczytanych informacji tworzony jest nowy obiekt Car i dodawany do listy samochodów (cars.add(car)).
                    cars.add(car);
                }
            }
        } catch (IOException e) {//Jeśli wystąpi błąd podczas odczytu pliku (np. plik nie istnieje lub nie może być odczytany), wyjątek IOException zostanie przechwycony w bloku catch, a komunikat błędu zostanie wyświetlony na konsoli.
            System.out.println("Błąd odczytu z pliku: " + e.getMessage());
        }
    }

    // Wyszukuje samochody po wybranej kategorii
    public List<Car> searchByCategory(String category, String value) {//Metoda przyjmuje dwa parametry: category, który określa wybraną kategorię (marka, model lub rocznik), oraz value, który zawiera wartość, według której będzie dokonywane wyszukiwanie.
        List<Car> results = new ArrayList<>();//Tworzona jest pusta lista results, która będzie przechowywać wyniki wyszukiwania.
        for (Car car : cars) {//Pętla for-each idzie po samochodach w bazie danych (cars).
            if (category.equalsIgnoreCase("marka") && car.getBrand().equalsIgnoreCase(value)) {//Dla każdego samochodu, sprawdzane jest, czy wartość kategorii (value) odpowiada określonej kategorii (category), przy uwzględnieniu nierejestrowania się na wielkość liter.
                results.add(car);//Jeśli kategoria jest "marka" i marka samochodu (car.getBrand()) jest równa wartości, dodawany jest samochód do listy wyników (results.add(car)).
            } else if (category.equalsIgnoreCase("model") && car.getModel().equalsIgnoreCase(value)) {
                results.add(car);//Jeśli kategoria jest "model" i model samochodu (car.getModel()) jest równy wartości, dodawany jest samochód do listy wyników.
            } else if (category.equalsIgnoreCase("rocznik") && String.valueOf(car.getYear()).equalsIgnoreCase(value)) {
                results.add(car);//Jeśli kategoria jest "rocznik" i rok samochodu (car.getYear()) jest równy wartości (po zamianie na napis), dodawany jest samochód do listy wyników.
            }
        }
        return results;//Proces sprawdzania i dodawania samochodów do listy wyników powtarza się dla każdego samochodu w bazie danych.
        // Na koniec metoda zwraca listę wyników wyszukiwania (results).
    }
}

// Klasa główna aplikacji z interfejsem użytkownika
public class CarDatabaseApp extends JFrame {//Klasa CarDatabaseApp dziedziczy po klasie JFrame, co oznacza, że reprezentuje główne okno aplikacji.
    private CarDatabase database;
    private JTextArea outputArea;
    private JTextField valueField;
    private JComboBox<String> categoryComboBox;
    private JButton searchButton;/*W klasie zdefiniowane są prywatne pola, takie jak database (obiekt klasy CarDatabase reprezentujący bazę danych samochodów),
     outputArea (obiekt klasy JTextArea do wyświetlania wyników), valueField (pole tekstowe do wprowadzania wartości do wyszukiwania),
     categoryComboBox (rozwijana lista do wyboru kategorii), searchButton (przycisk do rozpoczęcia wyszukiwania).*/

    // Dodaje samochód ręcznie na podstawie wprowadzonych danych
    private void addCarManually() {
        String brand = JOptionPane.showInputDialog("Podaj markę samochodu:");
        String model = JOptionPane.showInputDialog("Podaj model samochodu:");
        String yearStr = JOptionPane.showInputDialog("Podaj rok produkcji samochodu:");
        int year = Integer.parseInt(yearStr);

        Car car = new Car(brand, model, year);
        database.addCar(car);
        database.saveToFile();
    }/*Metoda addCarManually() odpowiada za ręczne dodawanie samochodów na podstawie wprowadzonych danych.
    Użytkownik zostaje poproszony o podanie marki, modelu i roku produkcji samochodu za pomocą okien dialogowych JOptionPane.
    Dane są zapisywane jako obiekt Car i dodawane do bazy danych. Następnie baza danych jest zapisywana do pliku.
*/
    public CarDatabaseApp() {
        /*Konstruktor CarDatabaseApp() inicjalizuje aplikację.
        Tworzy interfejs użytkownika, który składa się z pola tekstowego (outputArea) do wyświetlania wyników,
        etykietki (categoryLabel) i rozwijanej listy (categoryComboBox) do wyboru kategorii,
        etykietki (valueLabel) i pola tekstowego (valueField) do wprowadzania wartości do wyszukiwania oraz przycisku (searchButton) do rozpoczęcia wyszukiwania.
         Dodaje również przycisk (addButton) do ręcznego dodawania samochodów. Przyciski addButton i searchButton posiadają obsługę zdarzeń.
         */
        super("Baza Danych Samochodów");
        database = new CarDatabase("C:\\Users\\Student\\Desktop\\_asdasd\\untitled2\\src\\database.txt");/*warto pamietac o tym iż ten program nie bedzie dzialac jezeli zmienimy ścieżke pliku,
         trzeba ją bedzie zmieniać odpowiednio od zapisania pliku database.txt*/
        database.loadFromFile();

        // Utworzenie interfejsu użytkownika
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JLabel categoryLabel = new JLabel("Kategoria:");
        String[] categories = {"Marka", "Model", "Rocznik"};
        categoryComboBox = new JComboBox<>(categories);
        JLabel valueLabel = new JLabel("Wartość:");
        valueField = new JTextField(10);
        searchButton = new JButton("Szukaj");

        JPanel inputPanel = new JPanel();
        inputPanel.add(categoryLabel);
        inputPanel.add(categoryComboBox);
        inputPanel.add(valueLabel);
        inputPanel.add(valueField);
        inputPanel.add(searchButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        JButton addButton = new JButton("Dodaj");

        // Dodanie obsługi zdarzenia przycisku "Dodaj"
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCarManually();
            }
        });
        inputPanel.add(addButton);

        // Obsługa zdarzenia przycisku "Szukaj"
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String category = (String) categoryComboBox.getSelectedItem();//Pobiera wybraną kategorię z rozwijanej listy categoryComboBox i przypisuje ją do zmiennej category.
                String value = valueField.getText().trim();//Pobiera wartość wprowadzoną przez użytkownika z pola tekstowego valueField i usuwa ewentualne spacje z początku i końca napisu. Wynik przypisuje do zmiennej value.
                if (!value.isEmpty()) {//Sprawdza, czy wartość value nie jest pusta (czy użytkownik wprowadził jakąś wartość).
                    List<Car> searchResults = database.searchByCategory(category, value);/*Jeśli wartość nie jest pusta,
                    wywołuje metodę searchByCategory(category, value) na obiekcie database (instancji klasy CarDatabase).
                     Metoda ta zwraca listę samochodów pasujących do określonej kategorii i wartości.*/
                    displaySearchResults(searchResults);/*Wynik wyszukiwania zapisywany jest do zmiennej searchResults.
                    Wywołuje metodę displaySearchResults(searchResults), która wyświetla wyniki wyszukiwania w polu tekstowym outputArea.*/
                }
            }
        });

        // Ustawienia okna aplikacji
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Wczytanie bazy danych z pliku
        database.loadFromFile();
    }

    // Wyświetlanie wyników wyszukiwania w polu tekstowym
    private void displaySearchResults(List<Car> results) {
        /* Metoda displaySearchResults(List<Car> results) odpowiada za wyświetlanie wyników wyszukiwania w polu tekstowym (outputArea).
        Jeśli lista wyników nie jest pusta, dla każdego samochodu w liście wyników wyświetlane jest jego reprezentacja tekstowa (car.toString()) wraz z nową linią.
        Jeśli lista wyników jest pusta, wyświetlany jest odpowiedni komunikat. */
        outputArea.setText("");
        if (!results.isEmpty()) {
            for (Car car : results) {
                outputArea.append(car.toString() + "\n");
            }
        } else {
            outputArea.append("Brak samochodów spełniających kryteria wyszukiwania.\n");
        }
        database.saveToFile();
    }

    public static void main(String[] args) {
        /*Metoda main(String[] args) jest metodą główną aplikacji.
         Uruchamia aplikację, tworząc obiekt CarDatabaseApp w wątku zdarzeń interfejsu użytkownika (SwingUtilities.invokeLater()). */
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CarDatabaseApp();
            }
        });
    }
}
