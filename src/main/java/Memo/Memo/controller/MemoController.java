package Memo.Memo.controller;

import Memo.Memo.domain.Memo;
import Memo.Memo.service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemoController {

    private final MemoService memoService;

    @Autowired //스프링이 연관된 객체를 스프링 컨테이너에서 찾아서 넣어줌. 이렇게 객체 의존관계를 외부에서 넣어주는걸 DI 의존성 주입이라 함
    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @GetMapping(value = "/memos/new")// "/memos/new" 경로로 GET 요청이 들어왔을 때, "createMemo" 뷰를 보여주는 것
    public String createForm(){
        return "/createMemo";
    }

    @PostMapping(value = "/memos/new")
    public String create(MemoForm memoForm){
        Memo memos = new Memo();
        memos.setData(memoForm.getData());

        memoService.addMemo(memos);

        return "redirect:/";
    }

    @GetMapping(value = "/memos")
    public String list(Model model){//model은 데이터를 뷰로 전달하기 위한 객체
        List<Memo> memos = memoService.all_views();
        model.addAttribute("memos", memos);// "memos"라는 이름으로 모델에 추가합니다. 이렇게 함으로써 뷰에서 해당 이름을 통해 메모 목록에 접근할 수 있습니다.
        return "/memoList";
    }
}