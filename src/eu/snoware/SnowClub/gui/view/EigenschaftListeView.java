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
import eu.snoware.SnowClub.gui.action.DokumentationAction;
import eu.snoware.SnowClub.gui.action.EigenschaftDeleteAction;
import eu.snoware.SnowClub.gui.action.EigenschaftDetailAction;
import eu.snoware.SnowClub.gui.control.EigenschaftControl;

public class EigenschaftListeView extends AbstractView
{

  @Override
  public void bind() throws Exception
  {
    GUI.getView().setTitle("Eigenschaften");

    EigenschaftControl control = new EigenschaftControl(this);

    control.getEigenschaftList().paint(this.getParent());

    ButtonArea buttons = new ButtonArea();
    buttons.addButton("Hilfe", new DokumentationAction(),
        DokumentationUtil.EIGENSCHAFT, false, "question-circle.png");
    buttons.addButton("l�schen", new EigenschaftDeleteAction(),
        control.getEigenschaftList(), false, "trash-alt.png");
    buttons.addButton("neu", new EigenschaftDetailAction(true), null, false,
        "document-new.png");
    buttons.paint(this.getParent());
  }
}
