package Memo.Memo;

import Memo.Memo.repository.JdbcMemoRepository;
import Memo.Memo.repository.JpaMemoRepository;
import Memo.Memo.repository.MemoRepository;
import Memo.Memo.service.MemoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;
    private final EntityManager em;

    public SpringConfig(DataSource dataSource, EntityManager em) {
        this.dataSource = dataSource;
        this.em = em;
    }

    @Bean
    public MemoService memoService(){
        return new MemoService(memoRepository());
    }

    @Bean
    public MemoRepository memoRepository(){
        //return new MemoRepository();
        //return new JdbcMemoRepository(dataSource);
        return new JpaMemoRepository(em);
    }
}
