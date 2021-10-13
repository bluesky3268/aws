package aws.web;

import aws.service.posts.BoardService;
import aws.web.dto.BoardListResponseDto;
import aws.web.dto.BoardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final BoardService boardService;

    @GetMapping("/")
    public String index(Model model) {
        List<BoardListResponseDto> boards = boardService.findAllDesc();
        model.addAttribute("boards", boards);
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
