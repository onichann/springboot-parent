package com.wt.springboot.ruankao;

/**
 * @author wutong
 * @date 2019/5/12 21:04
 * PROJECT_NAME springboot-parent
 */
public class FootDecorator extends  Decorator {
    public FootDecorator(Invoice ticket) {
        super(ticket);
    }

    @Override
    public void printInvoice() {
        super.printInvoice();
        System.out.println("foot");
    }
}
