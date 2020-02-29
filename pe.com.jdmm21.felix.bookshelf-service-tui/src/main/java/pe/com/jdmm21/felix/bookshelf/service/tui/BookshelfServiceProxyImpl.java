package pe.com.jdmm21.felix.bookshelf.service.tui;

import java.util.HashSet;
import java.util.Set;

import org.apache.felix.service.command.Descriptor;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pe.com.jdmm21.felix.bookshelf.inventory.api.Book;
import pe.com.jdmm21.felix.bookshelf.inventory.api.BookAlreadyExistsException;
import pe.com.jdmm21.felix.bookshelf.inventory.api.InvalidBookException;
import pe.com.jdmm21.felix.bookshelf.service.api.BookshelfService;
import pe.com.jdmm21.felix.bookshelf.service.api.InvalidCredentialsException;

public class BookshelfServiceProxyImpl implements BookshelfServiceProxy {

	private BundleContext context;

	public BookshelfServiceProxyImpl(BundleContext context) {
		this.context = context;
	}

	@Override
	public String add(@Descriptor("username") String username, @Descriptor("password") String password,
			@Descriptor("ISBN") String isbn, @Descriptor("title") String title, @Descriptor("author") String author,
			@Descriptor("category") String category, @Descriptor("rating (0..10)") int rating)
			throws InvalidCredentialsException, BookAlreadyExistsException, InvalidBookException {
		BookshelfService service = lookupService();
		String sessionId = service.login(username, password.toCharArray());
		service.addBook(sessionId, isbn, title, author, category, rating);
		return isbn;
	}

	@Override
	@Descriptor("Search books by author,title, or category")
	public Set<Book> search(@Descriptor("username") String username, @Descriptor("password") String password,
			@Descriptor("search by attribute: author, title, or category") String attribute,
			@Descriptor("match line (use % at the beggining or the end of the like)") String filter)
			throws InvalidCredentialsException {
		BookshelfService service = lookupService();
		String sessionId = service.login(username, password.toCharArray());
		Set<String> results;
		if ("title".equals(attribute)) {
			results = service.searchBooksByTitle(sessionId, filter);
		} else if ("author".equals(attribute)) {
			results = service.searchBooksByAuthor(sessionId, filter);
		} else if ("category".equals(attribute)) {
			results = service.searchBooksByCategory(sessionId, filter);
		} else {
			throw new RuntimeException("Invalid attribute, expecting one of this attributes: title,author,category");
		}
		return getBooks(sessionId, service, results);
	}

	@Override
	@Descriptor("search by rating")
	public Set<Book> search(@Descriptor("username") String username, @Descriptor("password") String password,
			@Descriptor("attribute: rating") String attribute, @Descriptor("lower rating") int lower,
			@Descriptor("upper rating") int upper) throws InvalidCredentialsException {
		if (!"rating".equals(attribute)) {
			throw new RuntimeException("invalid attribute, expecting 'rating'");
		}
		BookshelfService service = lookupService();
		String sessionId = service.login(username, password.toCharArray());
		Set<String> results = service.searchBooksByRating(sessionId, lower, upper);
		return getBooks(sessionId, service, results);
	}

	protected BookshelfService lookupService() {
		ServiceReference reference = context.getServiceReference(BookshelfService.class.getName());
		if (reference == null) {
			throw new RuntimeException("Bookshelfservice not registered, cannot invoke");
		}
		BookshelfService service = (BookshelfService) this.context.getService(reference);
		if (service == null) {
			throw new RuntimeException("Bookshelfservice not registered, cannot invoke");
		}
		return service;
	}

	private Set<Book> getBooks(String sessionId, BookshelfService service, Set<String> results) {
		Set<Book> books = new HashSet<Book>();
		for (String isbn : results) {
			Book book;
			try {
				book = service.getBook(sessionId, isbn);
				books.add(book);
			} catch (Exception e) {
				throw new RuntimeException("ISBN " + isbn + " referenced but not found");
			}
		}
		return books;
	}

}
