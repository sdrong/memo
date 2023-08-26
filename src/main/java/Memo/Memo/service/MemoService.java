package Memo.Memo.service;

import Memo.Memo.domain.Memo;
import Memo.Memo.repository.MemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service 이것이 있어야 외부에서 이 객체를 사용 가능하다.하지만 우리는 config로 할것이다
@Transactional//jpa를 통한 모든 데이터 변경은 트랜잭션 안에서 실행
public class MemoService {
    private final MemoRepository memoRepository;

    //@Autowired
    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }


    public void addMemo(Memo memo){
        memoRepository.save(memo);
    }

    public List<Memo> all_views(){
        return memoRepository.all_view();
    }

    public Optional<Memo> findMemo(Long memoId){ //없을 수 도 있으므로
        return memoRepository.findById(memoId);
    }

    public void deleteMemo(Long id){memoRepository.delete(id);}

    public void reMemo(Long id, String newData) {
        Optional<Memo> memoOptional = memoRepository.findById(id);

        if (memoOptional.isPresent()) {
            Memo existingMemo = memoOptional.get();

            // 기존 데이터를 수정
            existingMemo.setData(newData);

            // 엔티티 저장
            memoRepository.save(existingMemo);
        }
    }
}
