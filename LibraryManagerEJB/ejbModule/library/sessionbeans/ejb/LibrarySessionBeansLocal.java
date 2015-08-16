package library.sessionbeans.ejb;

import java.util.Collection;

import javax.ejb.Local;

import library.entitybeans.jpa.Books;

@Local
public interface LibrarySessionBeansLocal {
	//add a new new book
		public void addBook(String title, String author, Integer pages, String isbn);
		
		//list books
		public Collection<Books> getBookList();
		
		//select a book
		public Books getABook(String isbn);
		
		//modify a book
		public void modifyABook(String isbn, String title, String author, int pages);
		
		//remove a book
		public Boolean removeABook(String isbn);
}
