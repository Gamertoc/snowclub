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
import de.willuhn.jameica.gui.util.ScrolledContainer;
import eu.snoware.SnowClub.gui.action.DokumentationAction;
import eu.snoware.SnowClub.gui.control.EinstellungControl;

public class EinstellungenAbrechnungView extends AbstractView
{

  @Override
  public void bind() throws Exception
  {
    GUI.getView().setTitle("Einstellungen Abrechnung");

    final EinstellungControl control = new EinstellungControl(this);

    ScrolledContainer cont = new ScrolledContainer(getParent());

    cont.addLabelPair("Beitragsmodell", control.getBeitragsmodel());
    cont.addInput(control.getZahlungsrhytmus());
    cont.addInput(control.getZahlungsweg());
    cont.addInput(control.getSEPADatumOffset());
    cont.addInput(control.getDefaultSEPALand());
    cont.addLabelPair("Arbeitsstunden Modell",
        control.getArbeitsstundenmodel());
    cont.addLabelPair("Abrechnungslauf abschließen",
        control.getAbrlAbschliessen());
    cont.addSeparator();
    cont.addHeadline(
        "ACHTUNG! Nur ändern, wenn noch keine SEPA-Lastschriften durchgeführt wurden!");
    cont.addLabelPair("Quelle für SEPA-Mandatsreferenz (*)",
        control.getSepamandatidsourcemodel());

    ButtonArea buttons = new ButtonArea();
    buttons.addButton("Hilfe", new DokumentationAction(),
        DokumentationUtil.EINSTELLUNGEN, false, "question-circle.png");
    buttons.addButton("speichern", new Action()
    {

      @Override
      public void handleAction(Object context)
      {
        control.handleStoreAbrechnung();
      }
    }, null, true, "save.png");
    buttons.paint(this.getParent());
  }
}
