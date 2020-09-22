package com.dcj.securityDemo.web.async;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;
@RestController
//spring多线程优势 将主线程空出 提高吞吐量 ，副线程处理业务不堵塞接口
//缺点必须是主线程调用副线程  在主线程里 在消息队列模型中 线程一发消息 存放到消息队列  线程二监听消息队列 被隔离
public class AsyncController {
    @Autowired
    private DeferredResultHolder resultHolder;
    @Autowired
    private  MockQueue mockQueue;

    Logger logger = LoggerFactory.getLogger(AsyncController.class);
    @GetMapping("/asyncorder")
    public Callable<String> asyncorder(){
        logger.info("主线程开始");
        Callable<String> result = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Long start = System.currentTimeMillis();
                logger.info("副线程开始"  );
                Thread.sleep(1000);
                logger.info("副线程结束{}",System.currentTimeMillis()-start);
                return "success";
            }
        };
        logger.info("主线程结束");
        return  result;
    }
    @GetMapping("/order")
    public DeferredResult order() throws InterruptedException {
        Long start = System.currentTimeMillis();
        logger.info("订单主线程开始");
        String randromOrder = RandomStringUtils.random(5);
        mockQueue.setPlaceOrder(randromOrder);
        DeferredResult<String>  result = new DeferredResult<>();
        resultHolder.getMap().put(randromOrder,result);
        logger.info("订单主线程结束{}",System.currentTimeMillis()-start);
        return  result;
    }
}
