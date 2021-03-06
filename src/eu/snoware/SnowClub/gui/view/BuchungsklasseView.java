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

import de.willuhn.jameica.gui.AbstractView;
import de.willuhn.jameica.gui.Action;
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.parts.ButtonArea;
import de.willuhn.jameica.gui.util.LabelGroup;
import eu.snoware.SnowClub.gui.action.DokumentationAction;
import eu.snoware.SnowClub.gui.control.BuchungsklasseControl;

public class BuchungsklasseView extends AbstractView
{

  @Override
  public void bind() throws Exception
  {
    GUI.getView().setTitle("Buchungsklasse");

    final BuchungsklasseControl control = new BuchungsklasseControl(this);

    LabelGroup group = new LabelGroup(getParent(), "Buchungsklasse");
    group.addLabelPair("Nummer", control.getNummer(true));
    group.addLabelPair("Bezeichnung", control.getBezeichnung());

    ButtonArea buttons = new ButtonArea();
    buttons.addButton("Hilfe", new DokumentationAction(),
        DokumentationUtil.BUCHUNGSKLASSEN, false, "question-circle.png");

    buttons.addButton("speichern", new Action()
    {
      @Override
      public void handleAction(Object context)
      {
        control.handleStore();
      }
    }, null, true, "save.png");
    buttons.paint(this.getParent());
  }
}
