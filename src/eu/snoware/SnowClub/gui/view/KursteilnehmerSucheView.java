/**********************************************************************
 * Copyright (c) by Heiner Jostkleigrewe
 * This program is free software: you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without 
 *  even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See 
 *  the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, 
 * see <http://www.gnu.org/licenses/>.
 * 
 * heiner@jverein.de
 * www.jverein.de
 **********************************************************************/
package eu.snoware.SnowClub.gui.view;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.willuhn.datasource.rmi.DBService;
import de.willuhn.datasource.rmi.ResultSetExtractor;
import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.parts.ButtonArea;
import de.willuhn.jameica.gui.util.LabelGroup;
import eu.snoware.SnowClub.Einstellungen;
import eu.snoware.SnowClub.gui.action.DokumentationAction;
import eu.snoware.SnowClub.gui.action.KursteilnehmerDetailAction;
import eu.snoware.SnowClub.gui.control.KursteilnehmerControl;

public class KursteilnehmerSucheView extends AbstractView
{

  @Override
  public void bind() throws Exception
  {
    GUI.getView().setTitle("Suche Kursteilnehmer");

    KursteilnehmerControl control = new KursteilnehmerControl(this);

    String sql = "select count(*) from kursteilnehmer";
    DBService service = Einstellungen.getDBService();
    ResultSetExtractor rs = new ResultSetExtractor()
    {

      @Override
      public Object extract(ResultSet rs) throws SQLException
      {
        rs.next();
        return Long.valueOf(rs.getLong(1));
      }
    };
    Long anzahl = (Long) service.execute(sql, new Object[] {}, rs);

    LabelGroup group = new LabelGroup(getParent(), "Filter");
    group.addLabelPair("Name", control.getSuchname());
    group.addLabelPair("Eingabedatum von", control.getEingabedatumvon());
    group.addLabelPair("Eingabedatum bis", control.getEingabedatumbis());

    if (anzahl.longValue() > 0)
    {
      control.getKursteilnehmerTable().paint(getParent());
    }
    ButtonArea buttons = new ButtonArea();
    buttons.addButton("Hilfe", new DokumentationAction(),
        DokumentationUtil.KURSTEILNEHMER, false, "question-circle.png");
    buttons.addButton("neu", new KursteilnehmerDetailAction(), null, false,
        "document-new.png");
    buttons.paint(this.getParent());
  }
}
