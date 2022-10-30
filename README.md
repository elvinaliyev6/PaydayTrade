<h1>Technologies used for this project</h1>
<ul>
  <li>Spring Data JPA</li>
  <li>Spring Web</li>
  <li>Spring Security</li>
  <li>Swagger</li>
  <li>Lombok</li>
  <li>H2 Database</li>
</ul>

<h1>Requirement for running</h1>
<ul>
<li>Postman - for testing project</1li>
<li>Intellij Idea - for opening project and checking code</li>
</ul>

<h1>How to run locally</h1>
<ol>
  <li>Make sure that, you have installed java to your pc and added it to system path</li>
  <li>Make sure that, you have IntelliJ IDEA (preffered) or anther IDE installed</li>
  <li>Clone project to your pc</li>
  <li>Open it with your favorite editor</li>
  <li>Run it</li>
  <li>Test endpoints you are interested in</li>
</ol>
 
 <h1>Endpoint</h1>
 
    Main url-<code>http://localhost:8000/bookstore</code>
    
    POST <code>http://localhost:8082/paydaytrade/user/signUp</code> for register users.
    ![register](https://user-images.githubusercontent.com/60142132/198893704-b2b44a30-6064-4601-b4ec-1dfeab435f80.png)

  <li>POST <code>http://localhost:8000/bookstore/user/signUp</code> for registration</li>
  
  <li>GET <code>http://localhost:8000/bookstore/books/bookList</code> for get all books</li>
  
  <li>GET <code>http://localhost:8000/bookstore/books/book/{id}</code> for get book by id</li>
  
  <li>POST <code>http://localhost:8000/bookstore/books/addBook</code> for insert a book</li>
  
  <li>PUT <code>http://localhost:8000/bookstore/books/book/{id}</code> for update a book givem id</li>
  
  <li>GET <code>http://localhost:8000/bookstore/books/publisher</code> for get books by publisher</li>
  
  <li>GET <code>http://localhost:8000/bookstore/books/name{name}</code> for get books by name. Also pagination is used.</li>
  
  <li>Swagger url: <code>http://localhost:8000/bookstore/swagger-ui/index.html</code></li>  
