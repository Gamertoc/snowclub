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
import de.willuhn.jameica.gui.util.LabelGroup;
import eu.snoware.SnowClub.gui.action.DokumentationAction;
import eu.snoware.SnowClub.gui.action.MitgliedDetailAction;
import eu.snoware.SnowClub.gui.action.MitgliedskontoExportAction;
import eu.snoware.SnowClub.gui.action.MitgliedskontoExportAction.EXPORT_TYP;
import eu.snoware.SnowClub.gui.control.MitgliedskontoControl;
import eu.snoware.SnowClub.gui.menu.Mitgliedskonto2Menu;

public class MitgliedskontoListeView extends AbstractView
{

  @Override
  public void bind() throws Exception
  {
    GUI.getView().setTitle("Mitgliedskonten");

    final MitgliedskontoControl control = new MitgliedskontoControl(this);
    LabelGroup group = new LabelGroup(getParent(), "Filter");
    group.addInput(control.getSuchName());
    group.addLabelPair("von",
        control.getVondatum(MitgliedskontoControl.DATUM_MITGLIEDSKONTO));
    group.addLabelPair("bis",
        control.getBisdatum(MitgliedskontoControl.DATUM_MITGLIEDSKONTO));
    group.addLabelPair("Differenz", control.getDifferenz());

    control.getMitgliedskontoList(new MitgliedDetailAction(),
        new Mitgliedskonto2Menu()).paint(this.getParent());

    ButtonArea buttons = new ButtonArea();
    buttons.addButton("Hilfe", new DokumentationAction(),
        DokumentationUtil.MITGLIEDSKONTO_UEBERSICHT, false,
        "question-circle.png");
    buttons.addButton(new Button("Export",
        new MitgliedskontoExportAction(EXPORT_TYP.MITGLIEDSKONTO, null),
        control, false, "save.png"));
    buttons.paint(this.getParent());
  }
}
