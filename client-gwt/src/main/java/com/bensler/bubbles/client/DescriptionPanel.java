package com.bensler.bubbles.client;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.layout.VLayout;

public class DescriptionPanel extends Object {

  private final VLayout panel_;
  
  private final DynamicForm form_; 
  private final StaticTextItem nameItem_; 
  
  public DescriptionPanel() {
    super();
    panel_ = new VLayout();
    panel_.setWidth100();
    panel_.setHeight100();
    form_ = new DynamicForm();
    nameItem_ = new StaticTextItem("name", "Name");
    form_.setItems(nameItem_);
    form_.setWidth100();
    panel_.addMember(form_);
    panel_.setMargin(10);
  }

  public VLayout getPanel() {
    return panel_;
  }

  public void showEntity(Entity entity) {
    nameItem_.setValue(entity.getName());
  }

}
