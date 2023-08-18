package Memo.Memo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApiController {
    @GetMapping("api_style")
    @ResponseBody
    public Memo apiString(@RequestParam("data") String data){ //이건 뷰파일 필요없이 http://localhost:8080/api_style?data=spring사용
        Memo memo = new Memo();
        memo.setData(data);
        return memo;
    }

    static class Memo{
        private String data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
