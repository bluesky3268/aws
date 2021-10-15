package aws.web;

import aws.config.auth.LoginUser;
import aws.config.auth.dto.SessionUser;
import aws.service.posts.BoardService;
import aws.web.dto.BoardListResponseDto;
import aws.web.dto.BoardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.mail.Session;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final BoardService boardService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        List<BoardListResponseDto> boards = boardService.findAllDesc();
        model.addAttribute("boards", boards);

//        SessionUser user = (SessionUser) httpSession.getAttribute("user"); // 어노테이션을 사용하여 이 코드는 없어도 됨
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/board/save")
    public String boardSave() {
        return "board-save";
    }

    @GetMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable Long id, Model model) {
        BoardResponseDto find = boardService.findById(id);
        model.addAttribute("board", find);
        return "board-update";
    }


}
