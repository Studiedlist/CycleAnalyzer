package org.einnfeigr.iot.repository;

import java.util.Optional;

import org.einnfeigr.iot.pojo.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface SessionRepository extends JpaRepository<Session, Integer> {

	@Override
	@Query(value="SELECT * FROM sessions\n"
			+ "	JOIN records \n"
			+ "		ON records.session_id = sessions.id\n"
			+ "	JOIN gyros \n"
			+ "		ON gyros.record_id = records.id\n"
			+ "	WHERE sessions.id = ?1",
			nativeQuery = true)
	Optional<Session> findById(Integer id);
	
}
