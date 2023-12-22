package SecurityWithJwt.Token;

import SecurityWithJwt.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Token {
    @Id
    @GeneratedValue
    private  Integer id;

    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private Boolean revoked;
    private Boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user ;

}
