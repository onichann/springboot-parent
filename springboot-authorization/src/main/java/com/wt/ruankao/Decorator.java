package com.wt.ruankao;

/**
 * @author wutong
 * @date 2019/5/12 21:01
 * PROJECT_NAME springboot-parent
 */
public class Decorator extends  Invoice {

    protected Invoice ticket;

    public Decorator(Invoice ticket) {
        this.ticket = ticket;
    }

    @Override
    public void printInvoice() {
        if(ticket!=null){
            ticket.printInvoice();
        }
    }
}
