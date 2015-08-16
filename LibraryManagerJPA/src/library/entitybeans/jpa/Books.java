package library.entitybeans.jpa;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Books
 *
 */
@Entity
@Table(schema="Library", name="Books")
public class Books implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	
	private String title;
	
	private String authors;
	
	private Integer pages;
	
	private String isbn;
	
	private static final long serialVersionUID = 1L;

	public Books() {
		super();
	} 
	public Books(String title, String author, Integer pages, String isbn){
		this.title = title;
		this.authors = author;
		this.pages = pages;
		this.isbn = isbn;
	}
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}   
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}   
	public String getAuthors() {
		return this.authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}   
	public Integer getPages() {
		return this.pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}   
	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	@Override
	public String toString(){
		return this.title + " " + this.authors + " " + this.pages + " "  + this.isbn;
	}
   
}
