package Memo.Memo.controller;

import Memo.Memo.domain.Memo;
import Memo.Memo.service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

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

    @GetMapping(value = "/onememo")
    public String one_view(Model model, @RequestParam("id") Long id){
        Optional<Memo> memo = memoService.findMemo(id);

        if(memo.isPresent()){
            model.addAttribute("onememo", memo.get());
            return "/oneview";
        }
        else{
            return "redirect:/memos"; // 메모가 없는 경우 목록 페이지로 리다이렉트합니다.
        }
    }

    @PostMapping(value = "/delete")
    public String deleteMemo(@RequestParam("id") Long id){
        memoService.deleteMemo(id);
        return "redirect:/memos";
    }

    @GetMapping("/re")
    public String editForm(Model model, @RequestParam("id") Long id) {
        Optional<Memo> memo = memoService.findMemo(id);

        if (memo.isPresent()) {
            model.addAttribute("memo", memo.get());
            return "/redataMemo"; // 수정 폼 페이지로 이동
        } else {
            return "redirect:/memos"; // 메모가 없는 경우 목록 페이지로 리다이렉트
        }
    }

    @PostMapping("/re")
    public String edit(@RequestParam("id") Long id, @RequestParam("data") String newData) {
        Optional<Memo> memo = memoService.findMemo(id);

        if (memo.isPresent()) {
            Memo existingMemo = memo.get();
            existingMemo.setData(newData);
            memoService.addMemo(existingMemo);
        }

        return "redirect:/memos"; // 수정 후 목록 페이지로 리다이렉트
    }
}
