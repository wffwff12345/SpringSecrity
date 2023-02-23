package com.example.project.dto;
import com.example.project.enums.AppHttpCodeEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseResult<T> implements Serializable {
    private Integer code;
    private String errorMessage;
    private T data;
    public ResponseResult(){
        this.code= AppHttpCodeEnum.SUCCESS.getCode();
    }
    public ResponseResult(Integer code, T data){
        this.code=code;
        this.data=data;
    }
    public ResponseResult(Integer code, String errorMessage){
        this.code=code;
        this.errorMessage=errorMessage;
    }
    public static  ResponseResult okResult(){
        return new ResponseResult();
//        return okResult(null);
    }
    public static<T> ResponseResult okResult(T data){
        return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(),data);

    }
    public static ResponseResult errorResult(int code,String msg){
        return new ResponseResult(code,msg);

    }
    public static ResponseResult errorResult(AppHttpCodeEnum enums) {
        return new ResponseResult(enums.getCode(), enums.getErrorMessage());
    }

    public static ResponseResult errorResult(AppHttpCodeEnum enums, String errorMessage) {
        return new ResponseResult(enums.getCode(), errorMessage);
    }

    public static void main(String[] args) {
        ResponseResult responseResult = new ResponseResult(0,"6");
         /*responseResult.setData("6");*/
        System.out.println(responseResult);

    }
}
