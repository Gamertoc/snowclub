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
import de.willuhn.jameica.gui.parts.Button;
import de.willuhn.jameica.gui.parts.ButtonArea;
import eu.snoware.SnowClub.gui.action.BuchungNeuAction;
import eu.snoware.SnowClub.gui.action.DokumentationAction;
import eu.snoware.SnowClub.gui.control.BuchungsControl;
import eu.snoware.SnowClub.gui.parts.BuchungPart;

public class BuchungView extends AbstractView
{

  @Override
  public void bind() throws Exception
  {
    final BuchungsControl control = new BuchungsControl(this);
    GUI.getView().setTitle(control.getTitleBuchungsView());

    final boolean buchungabgeschlossen = control.isBuchungAbgeschlossen();

    BuchungPart part = new BuchungPart(control, this, buchungabgeschlossen);
    part.paint(this.getParent());

    ButtonArea buttons = new ButtonArea();
    buttons.addButton("Hilfe", new DokumentationAction(),
        DokumentationUtil.BUCHUNGEN, false, "question-circle.png");
    if (control.getBuchung().getSpeicherung())
    {
      buttons.addButton("neu", new BuchungNeuAction(), null, false, "file.png");
    }
    Button savButton = new Button("speichern",
        control.getBuchungSpeichernAction(), null, true, "save.png");
    savButton.setEnabled(!buchungabgeschlossen);
    buttons.addButton(savButton);
    buttons.paint(getParent());
  }
}
