package com.dcj.securityDemo.web.async;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

//ContextRefreshedEvent系统启动时自动监听容器里面的（起全局监听的作用）

public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {
    //模拟消息队列
    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder resultHolder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //放到线程里面执行 否则系统会被阻塞
        new Thread(()->{
            while (true){
                if(StringUtils.isNotEmpty(mockQueue.getCompleteOrder())){
                    String orderNumber = mockQueue.getCompleteOrder();
                    System.out.println("返回订单处理结果："+orderNumber);
                    resultHolder.getMap().get(orderNumber).setResult("placeOrder success");
                    mockQueue.setCompleteOrder(null);
                }else{
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
