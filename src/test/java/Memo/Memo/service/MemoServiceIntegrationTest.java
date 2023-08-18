package Memo.Memo.service;

import Memo.Memo.domain.Memo;
import Memo.Memo.repository.MemoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class MemoServiceIntegrationTest {

    @Autowired
    MemoService memoService;
    @Autowired
    MemoRepository memoRepository;

    @Test
    public void 메모추가() throws Exception {
        //given
        Memo memo = new Memo();
        memo.setData("m1");

        //when
        memoService.addMemo(memo);

        //then
        Memo result = memoRepository.findById(memo.getId()).get();
        assertEquals(memo.getData(), result.getData());
    }
}
