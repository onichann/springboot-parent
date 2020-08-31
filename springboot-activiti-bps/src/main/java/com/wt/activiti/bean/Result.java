package com.wt.activiti.bean;

import java.io.Serializable;

/**
 * @author Administrator
 * @date 2020-03-17 下午 1:59
 * description
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -2280474194173929542L;
    private T result;
    private String message;
    private boolean success;

    Result(final T result, final String message, final boolean success) {
        this.result = result;
        this.message = message;
        this.success = success;
    }

    Result(){

    }

    public static <T> ResultBuilder<T> builder() {
        return new ResultBuilder<T>();
    }

    public T getResult() {
        return this.result;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setResult(final T result) {
        this.result = result;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public String toString() {
        return "Result(result=" + this.getResult() + ", message=" + this.getMessage() + ", success=" + this.isSuccess() + ")";
    }

    public static class ResultBuilder<T> {
        private T result;
        private String message;
        private boolean success;

        ResultBuilder() {
        }

        public ResultBuilder<T> result(final T result) {
            this.result = result;
            return this;
        }

        public ResultBuilder<T> message(final String message) {
            this.message = message;
            return this;
        }

        public ResultBuilder<T> success(final boolean success) {
            this.success = success;
            return this;
        }

        public Result<T> build() {
            return new Result<T>(this.result, this.message, this.success);
        }

        public String toString() {
            return "Result.ResultBuilder(result=" + this.result + ", message=" + this.message + ", success=" + this.success + ")";
        }
    }
}
