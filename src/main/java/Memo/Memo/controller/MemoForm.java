package Memo.Memo.controller;

public class MemoForm { //이것의 존재 이유는 웹 등록 화면에서 전달 받을 폼 객체이다. 데이터 전송과 유용성 검사를 위해 사용되어 진다.
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
