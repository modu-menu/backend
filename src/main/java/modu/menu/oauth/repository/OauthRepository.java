package modu.menu.oauth.repository;

import modu.menu.oauth.domain.Oauth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OauthRepository extends JpaRepository<Oauth, Long> {

    @Query("select o " +
            "from Oauth o " +
            "where o.oauthId = :oauthId")
    Optional<Oauth> findByOauthId(@Param("oauthId") Long oauthId);
}
