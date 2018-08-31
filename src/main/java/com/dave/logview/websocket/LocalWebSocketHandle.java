package com.dave.logview.websocket;

import com.dave.logview.Thread.TailLogThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.InputStream;
import java.util.List;

/**
 * wwj
 * 2018/8/24  16:54
 * 本地服务器: 日志查看
 */
@Component
@ServerEndpoint("/localLog")
public class LocalWebSocketHandle {

    private static Logger logger = LoggerFactory.getLogger(LocalWebSocketHandle.class);

    private Process process;
    private InputStream inputStream;

    /**
     * 新的WebSocket请求开启
     */
    @OnOpen
    public void onOpen(Session session) {
        try {
            // 执行tail -f命令
            ///home/root/project/nohup.out
            List<String> listMap = session.getRequestParameterMap().get("filePathName");
            if (null != listMap && listMap.size() > 0) {
                String filePathName = listMap.get(0);
                logger.info("查看日志文件："+filePathName);
                process = Runtime.getRuntime().exec("tail -f " + filePathName);
                session.getBasicRemote().sendText("文件已找到,等待载入......" + "<br>");
                logger.info("文件日志找到："+filePathName);
                inputStream = process.getInputStream();

                // 一定要启动新的线程，防止InputStream阻塞处理WebSocket的线程
                TailLogThread thread = new TailLogThread(inputStream, session);
                thread.start();
                logger.info("查看线程启动："+thread.getName());
            }else{
                session.getBasicRemote().sendText("文件名称没有找到" + "<br>");
            }
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
            if (inputStream != null) {
                inputStream.close();
            }
            if (process != null) {
                process.destroy();
            }
            logger.info("session:关闭成功!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @OnError
    public void onError(Throwable thr) {
        logger.error(thr.getMessage(), thr);
    }
}