package com.bensler.bubbles.server.jetty;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.bensler.bubbles.entity.DisplayMap;
import com.bensler.bubbles.entity.Entity;
import com.bensler.bubbles.entity.RelativePosition;

public class EntityController {

  private final SessionFactory hibernate_;

  public EntityController(SessionFactory hibernate) {
    hibernate_ = hibernate;
  }

  DisplayMap getInitialDisplayMap() throws Exception {
    return getDisplayMap(1);
  }
  
  DisplayMap getDisplayMap(Integer entityId) throws Exception {
    final Session session = hibernate_.openSession();
    final Entity entity = (Entity) session.createCriteria(Entity.class).add(Restrictions.eq("id_", Long.valueOf(entityId))).list().get(0);
    final DisplayMap displayMap = new DisplayMap(entity);
    final List<?> positions = session.createCriteria(RelativePosition.class).add(
      Restrictions.sqlRestriction("center_entity_id=" + entityId)
    ).list();
    
    for (int i = 0; i < positions.size(); i++) {
      displayMap.putPosition((RelativePosition)positions.get(i));
    }
    return displayMap;
  }

  public void saveNodePosition(
    Integer sourceEntityId, Integer targetEntityId, int dx, int dy
  ) {
    final Session session = hibernate_.openSession();
    // TODO Auto-generated method stub
    
  }

}
