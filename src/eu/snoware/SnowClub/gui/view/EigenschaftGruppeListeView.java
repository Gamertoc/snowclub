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
import eu.snoware.SnowClub.gui.action.EigenschaftGruppeDeleteAction;
import eu.snoware.SnowClub.gui.action.EigenschaftGruppeDetailAction;
import eu.snoware.SnowClub.gui.control.EigenschaftGruppeControl;

public class EigenschaftGruppeListeView extends AbstractView
{

  @Override
  public void bind() throws Exception
  {
    GUI.getView().setTitle("Eigenschaften Gruppen");

    EigenschaftGruppeControl control = new EigenschaftGruppeControl(this);

    control.getEigenschaftGruppeList().paint(this.getParent());

    ButtonArea buttons = new ButtonArea();
    buttons.addButton("Hilfe", new DokumentationAction(),
        DokumentationUtil.EIGENSCHAFTGRUPPE, false, "question-circle.png");
    buttons.addButton("l�schen", new EigenschaftGruppeDeleteAction(),
        control.getEigenschaftGruppeList(), false, "trash-alt.png");
    buttons.addButton("neu", new EigenschaftGruppeDetailAction(true), null,
        false, "document-new.png");
    buttons.paint(this.getParent());
  }
}
