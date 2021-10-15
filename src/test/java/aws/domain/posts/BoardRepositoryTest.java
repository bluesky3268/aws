package aws.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @AfterEach
    public void cleanUp() {
        boardRepository.deleteAll();
    }

    @Test
    public void 게시글_저장_불러오기() throws Exception{
        // given
        String title = "테스트 제목";
        String content = "테스트 내용";

        boardRepository.save(Board.builder()
                .title(title)
                .content(content)
                .author("hyunbin")
                .build());

        // when
        List<Board> boardList = boardRepository.findAll();

        // then
        Board board = boardList.get(0);
        assertThat(board.getTitle()).isEqualTo(title);
        assertThat(board.getContent()).isEqualTo(content);

    }

    @Test
    public void BaseTimeEntity등록() {
        //given
        LocalDateTime now = LocalDateTime.now();
        boardRepository.save(Board.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        // when
        List<Board> result = boardRepository.findAll();

        // then
        Board post = result.get(0);
        System.out.println(">>> createDate : " + post.getCreatedDate() + ", lastModifiedDate : " + post.getModifiedDate());

        assertThat(post.getCreatedDate()).isAfter(now);
        assertThat(post.getModifiedDate()).isAfter(now);

    }

}