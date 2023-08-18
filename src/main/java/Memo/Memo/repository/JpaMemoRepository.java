package Memo.Memo.repository;

import Memo.Memo.domain.Memo;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemoRepository implements MemoRepository{

    private final EntityManager em;

    public JpaMemoRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Memo save(Memo memo) {
        em.persist(memo); //새로운 엔티티 추가
        return memo;
    }

    @Override
    public Optional<Memo> findById(Long id) {
        Memo memo = em.find(Memo.class, id);
        return Optional.ofNullable(memo);
    }

    @Override
    public void delete(Long num) {
        Memo memo = em.find(Memo.class, num);
        if (memo != null) {
            em.remove(memo);
        }
    }

    @Override
    public List<Memo> all_view() {
        return em.createQuery("select m from Memo m", Memo.class)
                .getResultList();
    }
}
