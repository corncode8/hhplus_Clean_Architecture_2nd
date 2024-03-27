package hhplus.demo.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BaseResponseStatus {

    SUCCESS(true, HttpStatus.OK.value(), "요청에 성공하였습니다."),

    NOT_FIND_USER(false, HttpStatus.NOT_FOUND.value(),"일치하는 유저가 없습니다."),
    ALREADY_REGIST_USER(false, HttpStatus.NOT_FOUND.value(),"이미 신청한 유저입니다."),
    NOT_FIND_LECTURE(false, HttpStatus.NOT_FOUND.value(),"일치하는 강의가 없습니다."),
    EMPTY_QUANTITY_LECTURE(false, HttpStatus.NOT_FOUND.value(),"강의가 품절입니다."),

    FAIL_RESERVATION(false, HttpStatus.NOT_FOUND.value(),"강의 신청에 실패하였습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
