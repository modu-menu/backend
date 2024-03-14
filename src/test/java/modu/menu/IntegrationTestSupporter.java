package modu.menu;

import modu.menu.core.auth.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@SpringBootTest
public abstract class IntegrationTestSupporter {

    @Autowired
    protected JwtProvider jwtProvider;
}
