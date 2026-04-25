package jp.kwebs.pcshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import jp.kwebs.pcshop.entiry.Pc;

public interface PcRepository extends JpaRepository<Pc, Long> {

}
