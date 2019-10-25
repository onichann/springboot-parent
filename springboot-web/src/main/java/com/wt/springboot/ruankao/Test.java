package com.wt.springboot.ruankao;

/**
 * @author wutong
 * @date 2019/5/12 21:04
 * PROJECT_NAME springboot-parent
 */
public class Test {
    public static void main(String[] args) {
        Invoice t = new Invoice();
        Invoice ticket;
        ticket = new FootDecorator(new HeadDecorator(t));
        ticket.printInvoice();
        System.out.println("-----------");
        ticket = new FootDecorator(new HeadDecorator(null));
        ticket.printInvoice();

        System.out.println("-----------");

        System.out.println("book:");
        ticket = new HeadDecorator(new FootDecorator(t));
        ticket.printInvoice();
        System.out.println("-----------");
        ticket = new HeadDecorator(new FootDecorator(null));
        ticket.printInvoice();
    }
}
