package restmodule.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import restmodule.models.IWServer;

@Repository
public interface IServerRepo extends JpaRepository<IWServer, String> {
    IWServer findById(int server_id);

    @Query("SELECT s FROM IWServer s WHERE s.code = :searchCode")
    IWServer getServerByCode(@Param("searchCode") String code);
}
