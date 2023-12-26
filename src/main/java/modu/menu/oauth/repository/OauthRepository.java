package modu.menu.oauth.repository;

import modu.menu.oauth.domain.Oauth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthRepository extends JpaRepository<Oauth, Long> {
}
