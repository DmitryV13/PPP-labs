import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        library.addBook(new Book("The Hobbit", "J.R.R. Tolkien"));
        library.addBook(new Book("1984", "George Orwell"));
        library.addBook(new Book("Pride and Prejudice", "Jane Austen"));
        library.addBook(new Book("To Kill a Mockingbird", "Harper Lee"));
        library.addBook(new Book("Lord of the Flies", "William Golding"));
        library.addBook(new Book("The Catcher in the Rye", "J.D. Salinger"));
        library.addBook(new Book("Brave New World", "Aldous Huxley"));
        library.addBook(new Book("War and Peace", "Leo Tolstoy"));

        BookIterator forwardIterator = library.createIterator();
        System.out.println("Library books (forward order):");
        while (forwardIterator.hasNext()) {
            Book book = forwardIterator.next();
            System.out.println(book);
        }

        BookIterator reverseIterator = library.createReverseIterator();
        System.out.println("\nLibrary books (reverse order):");
        while (reverseIterator.hasNext()) {
            Book book = reverseIterator.next();
            System.out.println(book);
        }

        // Подсчет книг с помощью прямого итератора
        forwardIterator = library.createIterator();
        int bookCount = 0;
        while (forwardIterator.hasNext()) {
            forwardIterator.next();
            bookCount++;
        }
        System.out.println("\nTotal number of books: " + bookCount);
    }
}

// ITERATOR INTERFACE
interface BookIterator {
    boolean hasNext();
    Book next();
}

// COLLECTION INTERFACE
interface BookCollection {
    BookIterator createIterator();
    BookIterator createReverseIterator();
    int size();
    Book getBook(int index);
}

// ELEMENT OF OUR COLLECTION
class Book {
    private String title;
    private String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }

    @Override
    public String toString() {
        return "Book {title='" + title + "', author='" + author + "'}";
    }
}

// CONCRETE IMPLEMENTATION OF COLLECTION
class Library implements BookCollection {
    private List<Book> books;

    public Library() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public int size() {
        return books.size();
    }

    @Override
    public Book getBook(int index) {
        if (index >= 0 && index < books.size()) {
            return books.get(index);
        }
        return null;
    }

    @Override
    public BookIterator createIterator() {
        return new ForwardLibraryIterator(this);
    }

    @Override
    public BookIterator createReverseIterator() {
        return new BackwardLibraryIterator(this);
    }
}

// FORWARD ITERATOR
class ForwardLibraryIterator implements BookIterator {
    private Library library;
    private int position = 0;

    public ForwardLibraryIterator(Library library) {
        this.library = library;
    }

    @Override
    public boolean hasNext() {
        return position < library.size();
    }

    @Override
    public Book next() {
        if (hasNext()) {
            return library.getBook(position++);
        }
        return null;
    }
}

// BACKWARD ITERATOR
class BackwardLibraryIterator implements BookIterator {
    private Library library;
    private int position;

    public BackwardLibraryIterator(Library library) {
        this.library = library;
        this.position = library.size() - 1;
    }

    @Override
    public boolean hasNext() {
        return position >= 0;
    }

    @Override
    public Book next() {
        if (hasNext()) {
            return library.getBook(position--);
        }
        return null;
    }
}