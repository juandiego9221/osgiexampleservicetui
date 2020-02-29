package pe.com.jdmm21.felix.bookshelf.service.tui.activator;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import pe.com.jdmm21.felix.bookshelf.service.tui.BookshelfServiceProxy;
import pe.com.jdmm21.felix.bookshelf.service.tui.BookshelfServiceProxyImpl;

public class BookshelfTuiActivator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		Hashtable props = new Hashtable();
		props.put("osgi.command.scope", BookshelfServiceProxy.SCOPE);
		props.put("osgi.command.function", BookshelfServiceProxy.FUNCTIONS);
		context.registerService(BookshelfServiceProxy.class, new BookshelfServiceProxyImpl(context), props);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
	}

}
