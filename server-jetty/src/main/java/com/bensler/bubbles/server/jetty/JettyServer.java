package com.bensler.bubbles.server.jetty;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.bensler.bubbles.server.jetty.RequestParser.MethodCall;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JettyServer extends Object {

  private static final Logger LOG = Log.getLogger(JettyServer.class);

  public static final int  PORT = 4711;

  public final static String METHOD_SAVE_NODE_POSITION = "saveNodePosition".toLowerCase();
  public final static String METHOD_GET_INITIAL_DISPLAY_MAP = "getInitialDisplayMap".toLowerCase();
  public final static String METHOD_GET_DISPLAY_MAP = "getDisplayMap".toLowerCase();
  
  public static class Main {

    public static void main(String[] args) throws Exception {
      new JettyServer();
    }

  }

  private final SessionFactory hibernate_;
  private final RequestParser requestParser_;
  private final EntityController entityController_;
  private final Server jettyServer_;

  JettyServer() throws Exception {
    hibernate_ = bootHibernate();
    requestParser_ = new RequestParser();
    entityController_ = new EntityController(hibernate_);
    (jettyServer_ = createJettyServer()).start();
  }

  private SessionFactory bootHibernate() {
    final Configuration cfg = new Configuration().configure();
    final ServiceRegistry registry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();

    return cfg.buildSessionFactory(registry);
  }

  private void shutDown() throws Exception {
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          Thread.sleep(1000);
          hibernate_.close();
          jettyServer_.stop();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  private Server createJettyServer() throws UnknownHostException {
    final Server server;

    LOG.info("listening on localhost:{}", PORT);
    // new Server(new InetSocketAddress(InetAddress.getLoopbackAddress(), PORT)); ... reachable locally only
    // new Server(PORT); ... reachable from local network
    server = new Server(new InetSocketAddress(InetAddress.getLoopbackAddress(), PORT));
    server.setHandler(new AbstractHandler() {
      @Override
      public void handle(
        String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response
      ) throws IOException, ServletException {
        final JsonFactory factory = new JsonFactory();
        final JsonGenerator jsonGenerator = factory.createGenerator(response.getOutputStream(), JsonEncoding.UTF8);
        final MethodCall call = requestParser_.parse(request);
        
        try {
          if (!call.isValid()) {
            throw new IllegalArgumentException("invalid method call");
          }
          final String method = call.getMethod();
          Object result = null;

          if (METHOD_GET_INITIAL_DISPLAY_MAP.equals(method)) {
            result = entityController_.getInitialDisplayMap();
          }
          if (METHOD_GET_DISPLAY_MAP.equals(method)) {
            result = entityController_.getDisplayMap(call.getIntArg());
          }
          if (METHOD_SAVE_NODE_POSITION.equals(method)) {
            final ObjectMapper mapper = new ObjectMapper();
            final Map<String, Object> args = mapper.readValue(
              request.getInputStream(), new TypeReference<Map<String, Object>>() {}
            );
            final Map<?, ?> relativePosition = (Map<?, ?>)args.get("RelativePosition");
            
            entityController_.saveNodePosition(
              call.getIntArg(), (Integer)args.get("targetEntity"), 
              (Integer)relativePosition.get("dx"), (Integer)relativePosition.get("dy")
            );
          }
          
          response.setContentType("text/html;charset=utf-8");
          jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
          jsonGenerator.setCodec(new ObjectMapper());
          response.setStatus(HttpServletResponse.SC_OK);
          baseRequest.setHandled(true);
          jsonGenerator.writeObject(new Result(result));
          jsonGenerator.flush();
        } catch (Exception e) {
          response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
          e.printStackTrace(response.getWriter());
        }
      }

    });
    return server;
  }

}
