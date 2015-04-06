package com.bensler.bubbles.server.jetty;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

class RequestParser {

  private final static Pattern INT_PATTERN = Pattern.compile("(\\d+)");
  
  private final List<String> path_;
  
  RequestParser() {
    super();
    path_ = new ArrayList<>();
  }

  synchronized MethodCall parse(HttpServletRequest request) {
    return new MethodCall(parsePath(request.getPathInfo()));
  }

  private List<String> parsePath(String path) {
    final String[] pathParts;

    path_.clear();
    pathParts = path.split("/");
    for (int i = 0; i < pathParts.length; i++) {
      final String part = pathParts[i];
      
      if (!part.isEmpty()) {
        path_.add(part);
      }
    }
    return path_;
  }
  
  static class MethodCall extends Object {

    private static String getPathPart(List<String> path, int index) {
      return ((path.size() > index) ? path.get(index).toLowerCase() : null);
    }
    
    private final String module_;
    private final String method_;
    private final String arg_;
    
    MethodCall(List<String> path) {
      module_ = getPathPart(path, 0);
      method_ = getPathPart(path, 1);
      arg_ = getPathPart(path, 2);
    }

    boolean isValid() {
      return ((module_ != null) && (method_ != null));
    }
    
    boolean hasArgument() {
      return (arg_ != null);
    }
    
    String getModule() {
      return module_;
    }

    String getMethod() {
      return method_;
    }

    String getArg() {
      return arg_;
    }

    Integer getIntArg() {
      if (hasArgument()) {
        final Matcher matcher = INT_PATTERN.matcher(arg_);
        
        if (matcher.find()) {
          return Integer.parseInt(matcher.group());
        }
      }
      return null;
    }
    
  }
  
}
