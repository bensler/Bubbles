package com.bensler.bubbles.client;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.i18n.client.NumberFormat;

public class CssColorWithAlpha {
  
  private final static NumberFormat ONE_DOT_ONE = NumberFormat.getFormat("0.0");

  private final StringBuilder builder;
  private final int r_;
  private final int g_;
  private final int b_;
   
  CssColorWithAlpha(String htmlColor) {
    r_ = Integer.parseInt(htmlColor.substring(1, 3), 16);
    g_ = Integer.parseInt(htmlColor.substring(3, 5), 16);
    b_ = Integer.parseInt(htmlColor.substring(5, 7), 16);
    builder = new StringBuilder(30);
  }

  public CssColor createCssColor(double opacity) {
    builder.setLength(0);
    builder.append("rgba(");
    builder.append(r_).append(",").append(g_).append(",").append(b_).append(",");
    builder.append(ONE_DOT_ONE.format(opacity)).append(")");
    return CssColor.make(builder.toString());
  }

}
