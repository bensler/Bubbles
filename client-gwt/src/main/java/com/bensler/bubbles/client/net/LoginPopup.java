package com.bensler.bubbles.client.net;

import com.google.gwt.storage.client.Storage;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.KeyNames;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.events.ItemKeyPressEvent;
import com.smartgwt.client.widgets.form.events.ItemKeyPressHandler;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

class LoginPopup extends Object implements ClickHandler, ItemKeyPressHandler {
	  
	  public final static String LAST_USERNAME_KEY = "lastUserName";
	  
    private final LoginCtrl parent_;
	  private final TextItem usernameTf_;
	  private final PasswordItem passwordTf_;
	  private final ButtonItem loginButton_;
	  private final Storage storage_;
	  
	  private Window popup_;

	  LoginPopup(LoginCtrl parent) {
	    super();
	    parent_ = parent;
	    passwordTf_ = new PasswordItem("password", "Kennwort");
	    usernameTf_ = new TextItem("username", "Benutzer");
      loginButton_ = new ButtonItem("login", "Anmelden");
      loginButton_.setColSpan(2);
      loginButton_.setAlign(Alignment.RIGHT);
      loginButton_.addClickHandler(this);
	    storage_ = Storage.getLocalStorageIfSupported();
	  }

    void show() {
      createPopup();
      popup_.show();  
      if (storage_ != null) {
        final String userName = storage_.getItem(LAST_USERNAME_KEY);
        
        if ((userName != null) && (userName.length() > 0)) {
          usernameTf_.setValue(userName);
          passwordTf_.focusInItem();
        } else {
          usernameTf_.focusInItem();
        }
      }
    }
    
    private void createPopup() {
      if (popup_ == null) {
        popup_ = new Window();
        popup_.setTitle("Login");
        popup_.setShowMinimizeButton(false);
        popup_.setShowCloseButton(false);
        popup_.setIsModal(true);
        popup_.setShowModalMask(true);
        final DynamicForm form = new DynamicForm();
        form.setHeight100();
        form.setWidth100();
        form.setMargin(10);
        form.setLayoutAlign(VerticalAlignment.BOTTOM);
        form.setFields(usernameTf_, passwordTf_, loginButton_);
        form.addItemKeyPressHandler(this);
        popup_.addItem(form);
        popup_.setAutoSize(true);
        popup_.centerInPage();
      }
    }

    void hide(boolean storeUsername) {
      if (popup_ != null) {
        if (storeUsername && (storage_ != null)) {
          storage_.setItem(LAST_USERNAME_KEY, getText(usernameTf_));
        }
        popup_.hide();
      }
    }

    void retry() {
      passwordTf_.focusInItem();
    }
    
    private static String getText(TextItem textItem) {
      final String value = textItem.getValueAsString();

      return ((value == null) ? "" : value);
    }
    
	  @Override
	  public void onClick(ClickEvent event) {
      okClicked();
	  }

    private void okClicked() {
      parent_.login(getText(usernameTf_), getText(passwordTf_));
      passwordTf_.setValue("");
    }

    @Override
    public final void onItemKeyPress(final ItemKeyPressEvent event) {
        if (KeyNames.ENTER.equals(event.getKeyName())) {
            okClicked();
        }

        if (KeyNames.ESC.equals(event.getKeyName())) {
            popup_.hide();
        }
    }

	}