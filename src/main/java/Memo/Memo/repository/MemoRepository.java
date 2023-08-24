package Memo.Memo.repository;

import Memo.Memo.domain.Memo;

import java.util.List;
import java.util.Optional;

public interface MemoRepository {
    Memo save(Memo memo);

    Optional<Memo> findById(Long id);
    void delete(Long num);
    List<Memo> all_view();



}
