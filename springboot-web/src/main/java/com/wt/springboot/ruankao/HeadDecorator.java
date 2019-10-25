package com.wt.springboot.ruankao;

/**
 * @author wutong
 * @date 2019/5/12 21:03
 * PROJECT_NAME springboot-parent
 */
public class HeadDecorator extends Decorator {

    public HeadDecorator(Invoice ticket) {
        super(ticket);
    }

    @Override
    public void printInvoice() {
        System.out.println("header");
        super.printInvoice();
    }
}
