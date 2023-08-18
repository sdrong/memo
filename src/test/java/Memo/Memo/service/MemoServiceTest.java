package Memo.Memo.service;

import Memo.Memo.domain.Memo;
import Memo.Memo.repository.MemoryMemoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class MemoServiceTest {

    MemoService memoService;
    MemoryMemoRepository memoRepository;

    @BeforeEach
    public void beforeEach(){
        memoRepository = new MemoryMemoRepository();
        memoService = new MemoService(memoRepository);
    }

    @AfterEach
    public void afterEach(){
        memoRepository.clearStore();
    }
    @Test
    void addMemo() {
        //given
        Memo memo = new Memo();
        memo.setData("m1");

        //when
        memoService.addMemo(memo);

        //then
        Memo result = memoRepository.findById(memo.getId()).get();
        Assertions.assertThat(result).isEqualTo(memo);
    }

    @Test
    void all_views() {
        //given
        Memo m1 = new Memo();
        m1.setData("m1");
        Memo m2 = new Memo();
        m2.setData("m2");
        Memo m3 = new Memo();
        m3.setData("m3");

        //when
        memoService.addMemo(m1);
        memoService.addMemo(m2);
        memoService.addMemo(m3);
        List<Memo> memoList = memoService.all_views();

        //then
        Assertions.assertThat(memoList.size()).isEqualTo(3);
    }

    @Test
    void findMemo() {
        //given
        Memo memo = new Memo();
        memo.setData("m1");

        //when
        memoService.addMemo(memo);

        //then
        Memo result = memoService.findMemo(memo.getId()).get();
        Assertions.assertThat(result).isEqualTo(memo);
    }
}