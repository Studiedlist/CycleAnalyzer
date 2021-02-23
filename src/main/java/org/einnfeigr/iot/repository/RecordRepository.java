package org.einnfeigr.iot.repository;

import org.einnfeigr.iot.pojo.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {

}
