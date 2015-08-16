package library.sessionbeans.ejb;

import java.util.Collection;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import library.entitybeans.jpa.Books;

/**
 * Session Bean implementation class librarySessionBeans
 */
@Stateless
@LocalBean
public class LibrarySessionBeans implements LibrarySessionBeansLocal {

	@PersistenceContext(name="LibraryJPA")
    private EntityManager em;
	
    /**
     * Default constructor. 
     */
    public LibrarySessionBeans() {
    }

	@Override
	public void addBook(String title, String author, Integer pages, String isbn) {
		
//		Books book = new Books();
//		book.setTitle(title);
//		book.setAuthors(author);
//		book.setPages(pages);
//		book.setIsbn(isbn);
//		System.out.println("get book object");
		System.out.println(title + " " + author + " " + pages + " " + isbn);
		Books book = new Books(title, author, pages, isbn);
		em.persist(book);
	}

	@Override
	public Collection<Books> getBookList() {

		TypedQuery<Books> query = em.createQuery("SELECT b FROM Books AS b ORDER BY b.title", Books.class);
		
		List<Books> bookList = query.getResultList();
		
		return bookList;
	}

	@Override
	public Books getABook(String isbn) {
		
		System.out.println("isbn is : " + isbn);
		
		TypedQuery<Books> query = em.createQuery("SELECT b FROM Books AS b WHERE b.isbn = :isbn", Books.class);
        
		query.setParameter("isbn", isbn);
		
		Books book = null;
        try {
            book = query.getSingleResult();

        } catch (EntityNotFoundException ex) {
            System.out.println("Book not found: " + book);

        } catch (NonUniqueResultException ex) {
            System.out.println("More than one team named: " + book);

        }
        doDiagnostics("Got book in getBookByName()", book);
        return book;
		
	}

	@Override
	public void modifyABook(String isbn, String title, String author, int pages) {
		// TODO Auto-generated method stub
		Books book = getABook(isbn);
		if(book != null){
			book.setTitle(title);
			book.setAuthors(author);
			book.setPages(pages);
			doDiagnostics("New information have been added to the book", book);
		}
		else{
			System.out.println("The Book does not exist");
		}
	}

	@Override
	public Boolean removeABook(String isbn) {
		// TODO Auto-generated method stub
		if( getABook(isbn) != null){
			System.out.println("isbn is : "+isbn);
			TypedQuery<Books> query = em.createQuery("DELETE FROM Books b WHERE b.isbn = :isbn", Books.class);
		    query.setParameter("isbn", isbn);
		    query.executeUpdate();
		    
		    return true;
			
		}
		else{
			System.out.println("The Book does not exist");
			
			return false;
		}
		
		
	}
	
    private void doDiagnostics(String message, Books book) {
        System.out.println(message);
        if (book == null) {
            System.out.print("Book is null");
        } else {
            System.out.println("Got " + book.getTitle());
        }
    }

}
