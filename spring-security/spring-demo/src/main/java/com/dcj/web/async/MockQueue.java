package com.dcj.web.async;

import org.springframework.stereotype.Component;

@Component
public class MockQueue {
    private String placeOrder;
    private String completeOrder;

    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder){
        //处理事务应该放到线程里执行
        new Thread(()->{
            //虚拟队列
            Long start = System.currentTimeMillis();
            System.out.println("接到订单");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.completeOrder = placeOrder;
            System.out.println("完成订单"+(System.currentTimeMillis()-start));
        }).start();

    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}
