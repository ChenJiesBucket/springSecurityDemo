package com.dcj.securityDemo.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
@RestController
//spring多线程优势 将主线程空出 提高吞吐量 ，副线程处理业务不堵塞接口
//缺点必须是主线程调用副线程  在主线程里 在消息队列模型中 线程一发消息 存放到消息队列  线程二监听消息队列 被隔离
public class AsyncController {
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
}
