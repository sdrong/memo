package Memo.Memo.repository;

import Memo.Memo.domain.Memo;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository //다음과 같은 애노테이션이 있어야 외부에서 이 객체를 찾아 사용 가능하다.
public class MemoryMemoRepository implements MemoRepository{
    private static Map<Long, Memo> store = new HashMap<>();
    private static long id = 0L;
    @Override
    public Memo save(Memo memo) {
        memo.setId(++id);
        store.put(memo.getId(), memo);
        return memo;
    }

    @Override
    public Optional<Memo> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void delete(Long num) {
        if(store.containsKey(num))
            store.remove(num);
    }

    @Override
    public List<Memo> all_view() {
        return new ArrayList<>(store.values()); //map형태를 배열 형태로 만들어 주기 위함
    }

    @Override
    public Memo redata(Long num, String rewrite) {
        return null;
    }

    public void clearStore(){
        store.clear();
    }
}
