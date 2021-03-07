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
import eu.snoware.SnowClub.gui.control.MitgliedskontoControl;
import eu.snoware.SnowClub.gui.control.MitgliedskontoNode;

public class MitgliedskontoDetailView extends AbstractView
{

  private int typ;

  public MitgliedskontoDetailView(int typ)
  {
    this.typ = typ;
  }

  @Override
  public void bind() throws Exception
  {
    GUI.getView().setTitle("Mitgliedskonto-Buchung");

    final MitgliedskontoControl control = new MitgliedskontoControl(this);
    LabelGroup grBuchung = new LabelGroup(getParent(),
        (typ == MitgliedskontoNode.SOLL ? "Soll" : "Ist") + "-Buchung");
    grBuchung.addLabelPair("Datum", control.getDatum());
    grBuchung.addLabelPair("Verwendungszweck 1", control.getZweck1());
    grBuchung.addLabelPair("Zahlungsweg", control.getZahlungsweg());
    control.getBetrag().setMandatory(true);
    grBuchung.addLabelPair("Betrag", control.getBetrag());
    grBuchung.addLabelPair("Buchungsart", control.getBuchungsart());

    ButtonArea buttons = new ButtonArea();
    buttons.addButton("Hilfe", new DokumentationAction(),
        DokumentationUtil.MITGLIEDSKONTO_UEBERSICHT, false,
        "question-circle.png");
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
