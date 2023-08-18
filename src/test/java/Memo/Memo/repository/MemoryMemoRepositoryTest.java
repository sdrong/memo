package Memo.Memo.repository;

import Memo.Memo.domain.Memo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemoryMemoRepositoryTest {
    MemoryMemoRepository repository = new MemoryMemoRepository();
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }
    @Test
    void save() {
        //given
        Memo memo = new Memo();
        memo.setData("memo1");
        //when
        repository.save(memo);
        //then
        Memo copy = repository.findById(memo.getId()).get();
        assertThat(memo).isEqualTo(copy);
    }

    @Test
    void delete() {
        Memo memo = new Memo();
        memo.setData("memo1");
        //when
        repository.save(memo);
        repository.delete(memo.getId());
        //then
        Optional<Memo> copy = repository.findById(memo.getId());
        assertThat(copy).isEmpty();

    }

    @Test
    void all_view() {
        //given
        Memo m1 = new Memo();
        Memo m2 = new Memo();
        Memo m3 = new Memo();
        m1.setData("m1_data");
        m2.setData("m2_data");
        m3.setData("m3_data");
        repository.save(m1);
        repository.save(m2);
        repository.save(m3);

        //when
        List<Memo> result = repository.all_view();

        //then
        Assertions.assertThat(result.size()).isEqualTo(3);
    }
}