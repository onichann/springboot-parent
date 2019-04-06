package com.wt.springboot.excel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public  abstract  class ValidHandler {

    private ValidHandler nextValidHandler;

    protected abstract  Class<? extends Annotation> getAnnotation();

    private boolean checkFieldWithAnnotation(Field field){

        return null != field.getAnnotation(this.getAnnotation());
    }

    protected abstract ValidResponse validValue(ValidRequest request);

    /**
     * 验证列有对应注解则检查，没有则进行下个验证，若检查通过，还有验证处理器则继续调用
     * @param request
     * @return
     */
    public final ValidResponse doValid(ValidRequest request){
        ValidResponse response=null;
        if(this.checkFieldWithAnnotation(request.getField())){
            response= this.validValue(request);
            if(!response.isSuccess()){
                return  response;
            }else{
                response=this.doNextValid(request);
            }
        }else{
            response=this.doNextValid(request);
        }
        return response;
    }

    private ValidResponse doNextValid(ValidRequest request){

        ValidResponse response=null;
        if(this.nextValidHandler!=null){
            response=this.nextValidHandler.doValid(request);
        }else {
            response = new ValidResponse().setSuccess(true).setErrorMessage("所有验证均已经通过,没有找到接下来的验证处理器.");
        }
        return response;
    }

    protected ValidHandler getNextValidHandler() {
        return nextValidHandler;
    }

    protected void setNextValidHandler(ValidHandler nextValidHandler) {
        this.nextValidHandler = nextValidHandler;
    }
}
