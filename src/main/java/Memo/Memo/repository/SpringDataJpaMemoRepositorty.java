package Memo.Memo.repository;

import Memo.Memo.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemoRepositorty extends JpaRepository<Memo, Long> {
    Optional<Memo> findById(Long id);
}
