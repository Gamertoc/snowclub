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
import de.willuhn.jameica.gui.GUI;
import de.willuhn.jameica.gui.parts.ButtonArea;
import eu.snoware.SnowClub.gui.action.BuchungsklasseAction;
import eu.snoware.SnowClub.gui.action.DokumentationAction;
import eu.snoware.SnowClub.gui.control.BuchungsklasseControl;

public class BuchungsklasseListView extends AbstractView
{

  @Override
  public void bind() throws Exception
  {
    GUI.getView().setTitle("Buchungsklassen");

    BuchungsklasseControl control = new BuchungsklasseControl(this);

    control.getBuchungsklasseList().paint(this.getParent());

    ButtonArea buttons = new ButtonArea();
    buttons.addButton("Hilfe", new DokumentationAction(),
        DokumentationUtil.BUCHUNGSKLASSEN, false, "question-circle.png");
    buttons.addButton("neu", new BuchungsklasseAction(), null, false,
        "file.png");
    buttons.paint(this.getParent());
  }
}
