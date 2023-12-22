package SecurityWithJwt.user;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static SecurityWithJwt.user.Permission.*;

@RequiredArgsConstructor
@Getter
public enum Role {

    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )
    ),
    MANAGER(
            Set.of(
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )
    );
   private final Set<Permission> permissions;

   public List<SimpleGrantedAuthority> getAuthorities(){
       return getPermissions()
               .stream()
               .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
               .toList();
   }
}