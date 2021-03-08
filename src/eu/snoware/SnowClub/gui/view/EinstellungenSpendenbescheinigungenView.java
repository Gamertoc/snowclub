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

public class EinstellungenSpendenbescheinigungenView extends AbstractView
{

  @Override
  public void bind() throws Exception
  {
    GUI.getView().setTitle("Einstellungen Spendenbescheinigungen");

    final EinstellungControl control = new EinstellungControl(this);

    ScrolledContainer cont = new ScrolledContainer(getParent());

    cont.addLabelPair("Finanzamt", control.getFinanzamt());
    cont.addLabelPair("Steuernummer", control.getSteuernummer());
    cont.addLabelPair("Bescheiddatum", control.getBescheiddatum());
    cont.addLabelPair("Bescheid nach � 60a AO", control.getVorlaeufig());
    // cont.addLabelPair("Vorl�ufig ab", control.getVorlaeufigab());
    cont.addLabelPair("Veranlagung von", control.getVeranlagungVon());
    cont.addLabelPair("Veranlagung bis", control.getVeranlagungBis());
    cont.addLabelPair("beg�nstigter Zweck", control.getBeguenstigterzweck());
    cont.addLabelPair("Mitgliedsbeitr�ge d�rfen bescheinigt werden",
        control.getMitgliedsbetraege());
    cont.addLabelPair("Mindestbetrag",
        control.getSpendenbescheinigungminbetrag());
    cont.addLabelPair("Verzeichnis",
        control.getSpendenbescheinigungverzeichnis());
    cont.addLabelPair("Buchungsart drucken",
        control.getSpendenbescheinigungPrintBuchungsart());

    ButtonArea buttons = new ButtonArea();
    buttons.addButton("Hilfe", new DokumentationAction(),
        DokumentationUtil.EINSTELLUNGEN, false, "question-circle.png");
    buttons.addButton("speichern", new Action()
    {

      @Override
      public void handleAction(Object context)
      {
        control.handleStoreSpendenbescheinigungen();
      }
    }, null, true, "save.png");
    buttons.paint(this.getParent());
  }
}