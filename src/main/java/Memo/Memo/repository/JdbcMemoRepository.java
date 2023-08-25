package Memo.Memo.repository;

import Memo.Memo.domain.Memo;
import org.mariadb.jdbc.Statement;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemoRepository implements MemoRepository{

    private final DataSource dataSource;

    public JdbcMemoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public int getCountOfExistingData() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection(); // 데이터베이스 연결 설정

            String sql = "SELECT COUNT(*) FROM memo"; // 특정 테이블의 레코드 개수 조회 쿼리
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1); // 첫 번째 컬럼의 값을 가져옴
            } else {
                throw new SQLException("No result found.");
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs); // 연결 및 리소스 닫기
        }
    }
    @Override
    public Memo save(Memo memo) {

        //db에 전송할 쿼리문
        String sql = "INSERT INTO memo(data) values(?)";
        //db와의 연결과 관련된 정보를 담을 변수들을 선언
        Connection conn = null; //인터페이스와 데이터 베이스의 연결성
        PreparedStatement pstmt = null; //sql전달
        //db통신과의 결과를 담을 변수
        ResultSet rs = null; //쿼리 실행시 데이터베이스에서 반환하는 데이터를 읽어들이기 위함

        //핵심 로직
        try {
            //db와의 커넥션을 불러와 할당
            conn = getConnection();

            //우선 저장된 sql저장
            //RETURN_GENERATED_KEYS는 dv에 insert후 리턴받을 번호를 가져오는 옵션
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //첫번쨰 ?와 memo의 data를 매핑해서 치환
            //데이터 바인딩
            pstmt.setString(1, memo.getData());//데이터 세팅
            //db에 실제 쿼리가 발송
            pstmt.executeUpdate();//insert,update,delete를 실행하고 결과를 int타입으로 변환
            //RETURN_GENERATED_KEYS 로 얻은 값을 불러와 rs에 저장
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {//데이터를 순차적으로 읽기 위함 값이 있으면 불러옴
                memo.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return memo;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Memo> findById(Long id) {
        String sql = "select * from memo where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Memo memo = new Memo();
                memo.setId(rs.getLong("id"));
                memo.setData(rs.getString("data"));
                return Optional.of(memo);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from memo where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("삭제된 행이 없습니다");
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, null); // 삭제 작업에는 ResultSet을 닫을 필요가 없습니다
        }
    }

    @Override
    public List<Memo> all_view() {
        String sql = "select * from memo";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Memo> memos = new ArrayList<>();
            while(rs.next()) {
                Memo memo = new Memo();
                memo.setId(rs.getLong("id"));
                memo.setData(rs.getString("data"));
                memos.add(memo);
            }
            return memos;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Memo redata(Memo memo) {
        return null;
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        //역순으로 db와의 연결을 종료
        try {
            if (rs != null) {
                    rs.close();
                }
            } catch(SQLException e){
                e.printStackTrace();
            }
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    close(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    private void close(Connection conn) throws SQLException{
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
