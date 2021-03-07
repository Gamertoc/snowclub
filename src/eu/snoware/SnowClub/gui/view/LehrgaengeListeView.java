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
import de.willuhn.jameica.gui.util.LabelGroup;
import eu.snoware.SnowClub.gui.action.DokumentationAction;
import eu.snoware.SnowClub.gui.control.LehrgangControl;

public class LehrgaengeListeView extends AbstractView
{

  @Override
  public void bind() throws Exception
  {
    GUI.getView().setTitle("Lehrg�nge");

    LehrgangControl control = new LehrgangControl(this);

    LabelGroup group = new LabelGroup(getParent(), "Filter");
    group.addLabelPair("Lehrgangsart", control.getSuchLehrgangsart());
    group.addLabelPair("Datum von", control.getDatumvon());
    group.addLabelPair("Datum bis", control.getDatumbis());

    control.getLehrgaengeList().paint(this.getParent());
    ButtonArea buttons = new ButtonArea();
    buttons.addButton("Hilfe", new DokumentationAction(),
        DokumentationUtil.LEHRGANG, false, "question-circle.png");
    buttons.paint(this.getParent());
  }
}
