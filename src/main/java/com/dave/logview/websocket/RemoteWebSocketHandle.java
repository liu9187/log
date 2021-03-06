package com.dave.logview.websocket;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.dave.logview.config.LogViewConfig;
import com.dave.logview.thread.TailLogThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import java.io.InputStream;
import java.util.List;


/**
 * wwj
 * 2018/8/24  17:56
 * 远程SSH
 */
@Component
@ServerEndpoint("/remoteLog")
public class RemoteWebSocketHandle {

    private static Logger logger = LoggerFactory.getLogger(RemoteWebSocketHandle.class);

    private Session ssh = null;

    private Connection conn = null;

    private final int port = 22;

    /**
     * 新的WebSocket请求开启
     */
    @OnOpen
    public void onOpen(javax.websocket.Session session) {
        try {
            List<String> listMap = session.getRequestParameterMap().get("filePathName");
            String filePathName = listMap.get(0);

            /** 服务器参数 */
            String hostname = LogViewConfig.remoteIp;
            String username = LogViewConfig.remoteUserName;
            String password = LogViewConfig.remotePassWord;

            conn = new Connection(hostname, port);
            //连接到主机
            conn.connect();
            //使用用户名和密码校验
            boolean isconn = conn.authenticateWithPassword(username, password);
            if (!isconn) {
                session.getBasicRemote().sendText("用户名称或者是密码不正确" + "<br>");
            } else {
                session.getBasicRemote().sendText(hostname + "：连接成功!" + "<br>");
                ssh = conn.openSession();
                ssh.execCommand("tail -f " + filePathName);
                InputStream inputStream = new StreamGobbler(ssh.getStdout());
                TailLogThread thread = new TailLogThread(inputStream, session);
                thread.start();
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


    @OnClose
    public void onClose() {
        try {
            if (ssh != null) {
                ssh.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @OnError
    public void onError(Throwable thr) {
        logger.error(thr.getMessage(), thr);
    }
}
