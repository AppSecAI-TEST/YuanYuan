package xyz.zimuju.common.rx;

import xyz.zimuju.common.entity.BasalError;

public class RxResponseException extends Exception {

    private BasalError basicError = new BasalError();

    public RxResponseException(int code) {
        basicError.setCode(code);
        basicError.setMessage(getErrorMessage(code));
    }

    public BasalError getRxError() {
        return basicError;
    }


    private String getErrorMessage(int code) {
        String message = "";

        if (code == 999) {
            message = "无数据";
        } else if (code == 1007) {
            message = "账号或密码错误，请重新填写";
        } else if (code == 1006) {
            message = "账号不存在";
        } else if (code == 1000) {
            message = "鉴权失败";
        } else if (code == 1001) {
            message = "数据库异常";
        } else if (code == 1002) {
            message = "客户端数据异常";
        } else if (code == 1007) {
            message = "密码错误";
        } else if (code == 8000) {
            message = "后台维护";
        } else if (code == 9000) {
            message = "其他错误";
        }

        return message;
    }

}
