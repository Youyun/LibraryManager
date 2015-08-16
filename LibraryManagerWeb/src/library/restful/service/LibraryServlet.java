package library.restful.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import library.entitybeans.jpa.Books;
import library.sessionbeans.ejb.LibrarySessionBeansLocal;

@Path("LibraryServlet")
public class LibraryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	@Context
	private UriInfo context;

	@Context
	private HttpServletResponse response;

	@EJB
	private LibrarySessionBeansLocal libManager;

	/**
	 * Default constructor.
	 */
	public LibraryServlet() {
		// TODO Auto-generated constructor stub
	}

	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain")
	public void createText(
			@FormParam("title") String title,
			@FormParam("author") String author,
			@FormParam("pages") String pages,
			@FormParam("isbn") String isbn) {

		System.out.println(title + " " + author + " " + pages + " " + isbn);

		libManager.addBook(title, author, Integer.parseInt(pages), isbn);

		response.setContentType("text/html");

		PrintWriter out;
		try {
			out = response.getWriter();
			out.println("<html><head><meta http-equiv=\"refresh\" content=\"1;url=../index.html\"><title>Result</title></head>" + "<body>");
			out.println("<h1> You Have Secussfully Added A Book </h1>");
			out.println("<input type=\"button\" onclick=\"location.href('../index.html');\" value=\"Back To Index\"></body></html>");

			out.close();

		} catch (Exception e) {
			try {
				out = response.getWriter();
				out.println("<title>Result</title>" + "<body>");
				out.println("<h1> Failed To Add A Book, Please check the book details </h1>");
				out.println("<input type=\"button\" onclick=\"location.href('../index.html');\" value=\"Back To Index\">");

				out.close();
			} catch (Exception ex) {

			}
		}

	}
	
	@Path("/{isbn}")
	@POST
	@Produces("text/plain")
	public String deleteText(@PathParam("isbn") @DefaultValue("") String isbn) {

		System.out.println(isbn);

		Boolean result = libManager.removeABook(isbn);

		//response.setContentType("text/html");

		//PrintWriter out;
		try {
			//out = response.getWriter();

			if (result) {
				return "Successfully delete the book";
				//out.println("<title>Result</title>" + "<body>");
				//out.println("<h1> You Have Secussfully Deleted A Book </h1>");
				//out.println("<input type=\"button\" onclick=\"location.href('../../index.html');\" value=\"Back To Index\">");
			} else {
				//out.println("<title>Result</title>" + "<body>");
				//out.println("<h1> Failed To Delete A Book, Please check the book details </h1>");
				//out.println("<input type=\"button\" onclick=\"location.href('../../index.html');\" value=\"Back To Index\">");
				return "Cannot delete the book since the book does not exist!";
			}
			//out.close();

		} catch (Exception e) {

		}
		
		return "finished";
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Books> getAllEmployees () {
		return libManager.getBookList();
	}

	@GET
	@Path("/{isbn}")
	@Produces(MediaType.APPLICATION_JSON)
	public Books getText(@PathParam("isbn") String isbn) {
		
		Books book = libManager.getABook(isbn);
		
//		response.setContentType(MediaType.APPLICATION_JSON);

//		PrintWriter out;
		try {
//			out = response.getWriter();
			if (book != null) {
//				out.println("<title>Result</title>" + "<body>");
//				out.println("<h1> You Have Secussfully Found A Book </h1>");
//				out.println("Book Details:");
//				out.println(book.toString());
//				out.println("<br>");
//				out.println("<input type=\"button\" onclick=\"location.href('../../index.html');\" value=\"Back To Index\">");
				
			} else {
//				out.println("<title>Result</title>" + "<body>");
//				out.println("<h1> Failed To locate A Book, Please check the book details </h1>");
//				out.println("<input type=\"button\" onclick=\"location.href('../../index.html');\" value=\"Back To Index\">");

			}
//			out.println(book.toString());
//			out.close();

		} catch (Exception e) {

		}
		
		
		return book;
		
	}
	

	@POST
	@Path("/{isbn}/{title}/{author}/{pages}")
	@Produces("text/plain")
	public String updateText(@PathParam("isbn") @DefaultValue("") String isbn, 
			@PathParam("title") @DefaultValue("") String title,
			@PathParam("author") @DefaultValue("") String author,
			@PathParam("pages") @DefaultValue("") String pages) throws IOException {

		libManager.modifyABook(isbn, title, author, Integer.parseInt(pages));
		return "finished";
	}

}