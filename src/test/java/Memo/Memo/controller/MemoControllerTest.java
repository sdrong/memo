package Memo.Memo.controller;

import Memo.Memo.repository.MemoRepository;
import Memo.Memo.repository.MemoryMemoRepository;
import Memo.Memo.service.MemoService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import Memo.Memo.domain.Memo;

import java.util.List;
import java.util.Optional;

class MemoControllerTest {

    MemoryMemoRepository memoRepository;
    MemoService memoService;
    MemoController memoController;

    @BeforeEach
    public void beforeEach(){
        memoRepository = new MemoryMemoRepository();
        memoService = new MemoService(memoRepository);
        memoController = new MemoController(memoService);
    }

    @AfterEach
    public void afterEach(){
        memoRepository.clearStore();
    }

    @Test
    void create() {
        //given
        Memo m1 = new Memo();
        Memo m2 = new Memo();
        m1.setData("m1");
        m2.setData("m2");

        //when
        memoService.addMemo(m1);
        memoService.addMemo(m2);

        //then
        Optional<Memo> r = memoService.findMemo(m1.getId());
        if(r.isPresent())
        {
            Memo memo = r.get();
            Assertions.assertThat(memo).isEqualTo(m1);
        }
    }

    @Test
    void list() {
        //given
        Memo m1 = new Memo();
        Memo m2 = new Memo();
        m1.setData("m1");
        m2.setData("m2");

        //when
        memoService.addMemo(m1);
        memoService.addMemo(m2);
        List<Memo> memos = memoService.all_views();

        //then
       Assertions.assertThat(memos.size()).isEqualTo(2);
    }
}