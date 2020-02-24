package pe.com.jdmm21.felix.bookshelf.service.tui;

import java.util.Set;

import pe.com.jdmm21.felix.bookshelf.inventory.api.Book;
import pe.com.jdmm21.felix.bookshelf.inventory.api.BookAlreadyExistsException;
import pe.com.jdmm21.felix.bookshelf.inventory.api.InvalidBookException;
import pe.com.jdmm21.felix.bookshelf.service.api.InvalidCredentialsException;

public class BookshelfServiceProxyImpl implements BookshelfServiceProxy {

	@Override
	public String add(String username, String password, String isbn, String title, String author, String category,
			int rating) throws InvalidCredentialsException, BookAlreadyExistsException, InvalidBookException {
		return null;
	}

	@Override
	public Set<Book> search(String username, String password, String attribute, String filter)
			throws InvalidCredentialsException {
		return null;
	}

	@Override
	public Set<Book> search(String username, String password, String attribute, int lower, int upper)
			throws InvalidCredentialsException {
		return null;
	}

}
