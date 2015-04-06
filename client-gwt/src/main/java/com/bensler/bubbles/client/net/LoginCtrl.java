package com.bensler.bubbles.client.net;

import static com.bensler.bubbles.client.net.JsonProperty.makeObject;

class LoginCtrl extends Object {
	  
	  private final ServerConnection srv_;
	  private final LoginPopup popup_;

	  private String lastUsername_;
	  private String lastPassword_;
	  private User currentUser_;

	  LoginCtrl(ServerConnection app) {
	    super();
	    srv_ = app;
      popup_ = new LoginPopup(this);  
	    setLastCredentials("", "");
	  }
	  
	  void logout() {
	    srv_.callServer("logout", null, null, true, null);
	    srv_.setLoggedInUser(null);
	    setLastCredentials("", "");
	  }
	  
	  private void setLastCredentials(String username, String password) {
	    lastUsername_ = username;
	    lastPassword_ = password;
	  }
	  
	  private void setCurrentUser(User user) {
	    currentUser_ = user;
	    srv_.setLoggedInUser(currentUser_);
	  }
	  
	  void login(final String password, final String username) {
	    srv_.callServer("login", null, makeObject(
	      new JsonProperty("User", makeObject(
	        new JsonProperty("username", username),
	        new JsonProperty("password", password)
	      ))
	    ), true, new ServerCall.Success<User>() {
	      @Override
	      public void serverCallDone(User user) {
	        setCurrentUser(user);
	        if (user != null) {
	          setLastCredentials(username, password);
	          popup_.hide(true);
	        } else {
	          popup_.retry();
	        }
	      }
	    });
	  }

    void login() {
      if (lastUsername_.isEmpty()) {
        popup_.show();
      } else {
        login(lastUsername_, lastPassword_);
      }
    }

	}