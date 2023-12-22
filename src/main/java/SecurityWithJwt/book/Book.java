package SecurityWithJwt.book;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Book {
    @Id
    @GeneratedValue
    private Integer id;
    private String author;

    @Column(nullable = false,updatable = false)
    @CreatedDate
    private LocalDateTime createDate;

    @Column(insertable = false)
    private LocalDateTime lastModified;

    @Column(nullable = false,updatable = false)
    @CreatedBy()
    private Integer createdBy;

    @Column(insertable = false)
    @LastModifiedBy
    private Integer lastModifiedBy;

}
