package Memo.Memo.repository;

import Memo.Memo.domain.Memo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaMemoRepository implements MemoRepository{

    private final EntityManager em; //EntityManager은 자바 애플리케이션과 데이터 베이스 사이의 영속성을 관리하는 역할

    public JpaMemoRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Memo save(Memo memo) {
        em.persist(memo); //새로운 엔티티 추가 데이터베이스에 영속화
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

    @Override
    public Memo redata(Long num, String rewrite) {
        Memo memo = em.find(Memo.class, num);
        if (memo != null){
            memo.setData(rewrite);
            em.merge(memo);
        }
        return memo;
    }
}
