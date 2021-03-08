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
import de.willuhn.jameica.gui.util.SimpleContainer;
import eu.snoware.SnowClub.gui.action.DokumentationAction;
import eu.snoware.SnowClub.gui.action.MitgliedskontoExportAction;
import eu.snoware.SnowClub.gui.action.MitgliedskontoExportAction.EXPORT_TYP;
import eu.snoware.SnowClub.gui.control.MitgliedskontoControl;
import eu.snoware.SnowClub.keys.FormularArt;

public class MitgliedskontoRechnungView extends AbstractView
{

  @Override
  public void bind() throws Exception
  {
    GUI.getView().setTitle("Rechnung");

    final MitgliedskontoControl control = new MitgliedskontoControl(this);

    SimpleContainer cont = new SimpleContainer(getParent(), true);
    cont.addHeadline("Parameter");
    if (this.getCurrentObject() == null)
    {
      cont.addLabelPair("von Datum",
          control.getVondatum(MitgliedskontoControl.TYP.RECHNUNG.name()));
      cont.addLabelPair("bis Datum",
          control.getBisdatum(MitgliedskontoControl.TYP.RECHNUNG.name()));
      cont.addLabelPair("ohne Abbucher", control.getOhneAbbucher());
    }
    cont.addLabelPair("Formular", control.getFormular(FormularArt.RECHNUNG));
    cont.addInput(control.getAusgabeart());
    cont.addInput(control.getAusgabesortierung());

    cont.addHeadline("Mail");
    cont.addInput(
        control.getBetreff(MitgliedskontoControl.TYP.RECHNUNG.name()));
    cont.addLabelPair("Text",
        control.getTxt(MitgliedskontoControl.TYP.RECHNUNG.name()));

    ButtonArea buttons = new ButtonArea();
    buttons.addButton("Hilfe", new DokumentationAction(),
        DokumentationUtil.RECHNUNG, false, "question-circle.png");
    buttons.addButton(new Button("Export",
        new MitgliedskontoExportAction(EXPORT_TYP.RECHNUNGEN,
            getCurrentObject()),
        control, false, "save.png"));
    buttons.addButton(control.getStartRechnungButton(this.getCurrentObject()));
    buttons.paint(this.getParent());
  }
}