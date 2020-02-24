package pe.com.jdmm21.felix.bookshelf.service.tui;

import java.util.Set;

import pe.com.jdmm21.felix.bookshelf.inventory.api.Book;
import pe.com.jdmm21.felix.bookshelf.inventory.api.BookAlreadyExistsException;
import pe.com.jdmm21.felix.bookshelf.inventory.api.InvalidBookException;
import pe.com.jdmm21.felix.bookshelf.service.api.InvalidCredentialsException;

public interface BookshelfServiceProxy {
	String SCOPE = "book";
	String[] FUNCTIONS = new String[] { "add", "search" };

	String add(String username, String password, String isbn, String title, String author, String category, int rating)
			throws InvalidCredentialsException, BookAlreadyExistsException, InvalidBookException;

	Set<Book> search(String username, String password, String attribute, String filter)
			throws InvalidCredentialsException;

	Set<Book> search(String username, String password, String attribute, int lower, int upper)
			throws InvalidCredentialsException;

}
