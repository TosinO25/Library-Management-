package edu.mu.book;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryManagment {

    private List<Book> personalLibrary;

    public LibraryManagment() {
        this.personalLibrary = new ArrayList<>();
    }

    public void addBookToLibrary(Book book) {
        personalLibrary.add(book);
    }

    public void removeBookFromLibrary(Book book) {
        personalLibrary.remove(book);
    }

    public List<Book> getBooksByStatus(ReadingStatus status) {
        return personalLibrary.stream()
                .filter(book -> book.getReadingStatus() == status)
                .collect(Collectors.toList());
    }

    public void saveLibraryToCSV(String filename) {
        List<Book> existingBooks = loadLibrary(filename); // Load existing books from CSV
        PrintWriter writer = null;

        try {
            writer = new PrintWriter(new FileWriter(filename));
            for (Book book : personalLibrary) {
                // Check if the book already exists in the CSV file
                boolean alreadyExists = existingBooks.stream().anyMatch(existingBook -> existingBook.equals(book));
                if (!alreadyExists) {
                    writer.println(book.toCsvString());
                    System.out.println("Book added to library: " + book.getTitle());
                }
            }
            System.out.println("Library data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving library data: " + e.getMessage());
        } finally {
            if (writer != null) {
                writer.close(); // Close the PrintWriter
            }
        }
    }


    public List<Book> getPersonalLibrary() {
        return new ArrayList<>(personalLibrary);
    }

    public List<Book> findBooksByTitleAndAuthor(String title, String author) {
        return personalLibrary.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title) && book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public void addBooksToLibrary(List<Book> books) {
        personalLibrary.addAll(books);
    }

    public static List<Book> loadLibrary(String filename) {
        List<Book> library = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    String title = parts[0].trim();
                    String author = parts[1].trim();
                    int publicationYear = Integer.parseInt(parts[2].trim());
                    Genre genre = Genre.valueOf(parts[3].trim());
                    int pageCount = Integer.parseInt(parts[4].trim());
                    ReadingStatus readingStatus = ReadingStatus.valueOf(parts[5].trim());
                    char favoritedStatus = parts[6].trim().charAt(0);
                    Book book = new Book(title, author, publicationYear, genre, pageCount, readingStatus, favoritedStatus);
                    library.add(book);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading library data: " + e.getMessage());
        }
        return library;
    }
    
    public List<Book> showFavoritedBooks()
    {
    	List<Book> favorites = new ArrayList<>();
    	char test;
    	for (Book book : personalLibrary) 
    	{
            test = book.getFavoritedStatus();
    		if (test == 'y' || test == 'Y') {
                favorites.add(book);
            }
        }
        return favorites;
    }
    
    public void printBooks(List<Book> list)
    {
    	
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-4s | %-30s | %-30s | %-6s | %-12s | %-6s | %-12s | %-1s%n", 
                          "No.", "Title", "Author", "Year", "Genre", "Pages", "Status", "Favorited");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
        List<Book> booklist = list;
        for (int i = 0; i < booklist.size(); i++) {
            Book book = booklist.get(i);
            System.out.printf("%-4d | %-30s | %-30s | %-6d | %-12s | %-6d | %-12s | %-1s%n", 
                              i + 1, book.getTitle(), book.getAuthor(), book.getPublicationYear(), 
                              book.getGenre(), book.getPageCount(), book.getReadingStatus(), book.getFavoritedStatus());
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
    }
    
 
    public List<Book> sortBooksByAuthor() {
    	
    	 List<Book> sortedBooks = new ArrayList<>(); 
    	 sortedBooks = personalLibrary;
    	
        Collections.sort(sortedBooks, new Comparator<Book>() {
           
            public int compare(Book b1, Book b2) {
                return b1.getAuthor().compareTo(b2.getAuthor());
            }
        });
        return sortedBooks; 
    }
    	
    
    
    
    
    
    
    
    
    
}
