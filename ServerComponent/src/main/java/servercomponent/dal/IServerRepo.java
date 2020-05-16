package servercomponent.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import servercomponent.models.IWServer;

@Repository
public interface IServerRepo extends JpaRepository<IWServer, String> {

    @Modifying
    @Query("UPDATE IWServer u SET u = :newServer WHERE u = :newServer")
    void update(@Param("newServer") IWServer server);

    IWServer findById(int server_id);

    IWServer getServerByCode(String code);
}
