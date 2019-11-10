package net.engyne.backend.wrap;


/**
 * ExecRest 用于封装内部操作的返回结果
 *
 */
public class ExecResult {

    private boolean ok;
    private Object data;

    private ExecResult(boolean ok, Object data) {
        this.ok = ok;
        this.data = data;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ExecResult success() {
        return new ExecResult(true, null);
    }

    public static ExecResult success(Object data) {
        return new ExecResult(true, data);
    }

    public static ExecResult fail() {
        return new ExecResult(false, null);
    }

    public static ExecResult fail(Object data) {
        return new ExecResult(false, data);
    }

}
