package com.fenixcommunity.centralspace.app.service.remoteserver;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component @Lazy
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RemoteServerConnector {
    //TODO KeyPair -> kpair.writePrivateKey(...) and kpair.writePublicKey(...)
    public static final String SFTP_CHANNEL_TYPE = "sftp";
    private final String remoteHost;
    private final String knownHosts;
    private final String username;
    private final String password;
    private final String remoteFileDirection;

    @Autowired
    public RemoteServerConnector(@Value("${remoteserver.host}") final String remoteHost,
                                 @Value("${remoteserver.knownHosts}") final String knownHosts,
                                 @Value("${remoteserver.username}") final String username,
                                 @Value("${remoteserver.password}") final String password,
                                 @Value("${remoteserver.remoteFileDirection}") final String remoteFileDirection) {
        this.remoteHost = remoteHost;
        this.knownHosts = knownHosts;
        this.username = username;
        this.password = password;
        this.remoteFileDirection = remoteFileDirection;
    }

    public boolean updateFile(final String localFileDirection, final String fileName) {
        final ChannelSftp channelSftp;
        try {
            channelSftp = setupJsch();
            channelSftp.connect();

            channelSftp.put(localFileDirection + fileName, remoteFileDirection + fileName);

            channelSftp.exit();
        } catch (JSchException | SftpException e) {
            return false;
        }
        return true;
    }

    public boolean downloadFile(final String localFileDirection, final String fileName) {
        final ChannelSftp channelSftp;
        try {
            channelSftp = setupJsch();
            channelSftp.connect();

            channelSftp.get(fileName, localFileDirection + fileName);

            channelSftp.exit();
        } catch (JSchException | SftpException e) {
            return false;
        }
        return true;
    }

    private ChannelSftp setupJsch() throws JSchException {
        final JSch jsch = new JSch();
        jsch.setKnownHosts(knownHosts);
        final Session jschSession = jsch.getSession(username, remoteHost);
        jschSession.setPassword(password);
        jschSession.setConfig("StrictHostKeyChecking", "no");
        jschSession.connect();
        return (ChannelSftp) jschSession.openChannel(SFTP_CHANNEL_TYPE);
    }
}
