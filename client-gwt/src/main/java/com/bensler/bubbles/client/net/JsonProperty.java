package com.bensler.bubbles.client.net;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class JsonProperty extends Object {
  
  private final String name_;
  private final JSONValue value_;
  
  public JsonProperty(String name, JSONValue value) {
    name_ = name;
    value_ = value;
  }
  
  public JsonProperty(String name, String value) {
    super();
    name_ = name;
    value_ = new JSONString((value == null) ? "" : value);
  }
  
  public static JSONObject makeObject(JsonProperty... values) {
    final JSONObject value = new JSONObject();
    
    for (JsonProperty jsonProperty : values) {
      value.put(jsonProperty.name_, jsonProperty.value_);
    }
    return value;
  }

  public static JSONArray makeArray(JSONObject... objects) {
    final JSONArray array = new JSONArray();
    
    for (int i = 0; i < objects.length; i++) {
      array.set(i, objects[i]);
    }
    return array;
  }
  
}