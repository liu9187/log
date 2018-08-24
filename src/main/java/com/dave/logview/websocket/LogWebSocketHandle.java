package com.dave.logview.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;

/**
 * wwj
 * 2018/8/24  16:54
 */
@Component
@ServerEndpoint("/log")
public class LogWebSocketHandle {

    private static Logger logger = LoggerFactory.getLogger(LogWebSocketHandle.class);

    private Process process;
    private InputStream inputStream;

    /**
     * 新的WebSocket请求开启
     */
    @OnOpen
    public void onOpen(Session session) {
        try {
            // 执行tail -f命令
            process = Runtime.getRuntime().exec("tail -f /home/dave/es/nohup.out");
            inputStream = process.getInputStream();

            // 一定要启动新的线程，防止InputStream阻塞处理WebSocket的线程
            TailLogThread thread = new TailLogThread(inputStream, session);
            thread.start();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            try {
                session.getBasicRemote().sendText(e.getMessage() + "<br>");
            } catch (Exception e1) {
                logger.error(e1.getMessage(), e1);
            }
        }
    }

    /**
     * WebSocket请求关闭
     */
    @OnClose
    public void onClose() {
        try {
            if (inputStream != null){
                inputStream.close();
            }
            if (process != null) {
                process.destroy();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    @OnError
    public void onError(Throwable thr) {
         logger.error(thr.getMessage(),thr);
    }
}