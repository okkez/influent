package sample;

import influent.forward.ForwardCallback;
import influent.forward.ForwardServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

public class TLSPrint {
  private static final Logger logger = LoggerFactory.getLogger(Print.class);

  public static void main(final String[] args) throws Exception {
    final long durationSeconds = Long.valueOf(args[0]);

    final ForwardCallback callback = ForwardCallback.ofSyncConsumer(
        stream -> logger.info(stream.toString()),
        Executors.newWorkStealingPool()
    );

    final ForwardServer server =
        new ForwardServer.Builder(callback)
            .protocol(ForwardServer.Protocol.TLS)
            .tlsVersion(ForwardServer.TlsVersion.TLSv1_2)
            .build();
    server.start();

    // ForwardServer#start returns immediately
    Thread.sleep(durationSeconds * 1000);

    server.shutdown().get();
  }
}