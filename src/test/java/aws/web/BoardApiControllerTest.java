package aws.web;

import aws.domain.posts.Board;
import aws.domain.posts.BoardRepository;
import aws.web.dto.BoardSaveDto;
import aws.web.dto.BoardUpdateDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoardApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BoardRepository boardRepository;

    @After
    public void tearDown() throws Exception{
        boardRepository.deleteAll();
    }

    @Test
    public void Posts_등록() throws Exception{
        // given
        String title = "title";
        String content = "content";

        BoardSaveDto requestDto = BoardSaveDto.builder()
                .title(title)
                .content(content)
                .author("ABC")
                .build();

        String url = "http://localhost:" + port + "/api/v1/board";

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Board> result = boardRepository.findAll();
        assertThat(result.get(0).getTitle()).isEqualTo(title);
        assertThat(result.get(0).getContent()).isEqualTo(content);
    }


    @Test
    public void 수정() throws Exception{
        // given
        Board savedPost = boardRepository.save(Board.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPost.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        BoardUpdateDto updateDto = BoardUpdateDto.builder().title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/board/" + updateId;

        HttpEntity<BoardUpdateDto> requestEntity = new HttpEntity<>(updateDto);

        // when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Board> result = boardRepository.findAll();
        assertThat(result.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(result.get(0).getContent()).isEqualTo(expectedContent);

    }
}