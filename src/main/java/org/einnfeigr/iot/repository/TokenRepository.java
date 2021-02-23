package org.einnfeigr.iot.repository;

import org.einnfeigr.iot.pojo.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface TokenRepository extends JpaRepository<AccessToken, Long>{

	boolean existsByToken(String token);
	
}
