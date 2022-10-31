package com.kw.kwdn.global.error;

public enum ErrorComment {
    JSON_PARSE_EXCEPTION("Json 객체를 변환하는 것에 실패하였습니다.");

    private final String comment;

    public String getComment() {
        return comment;
    }

    ErrorComment(String comment) {
        this.comment = comment;
    }
}
