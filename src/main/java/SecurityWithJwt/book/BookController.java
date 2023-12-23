package SecurityWithJwt.book;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/books.json")
    public ResponseEntity<List<Book>> getBooks(){
        return ResponseEntity.ok(bookService.findAll());
    }
}
