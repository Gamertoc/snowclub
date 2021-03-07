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
import eu.snoware.SnowClub.Einstellungen;
import eu.snoware.SnowClub.gui.action.DokumentationAction;
import eu.snoware.SnowClub.gui.control.AbrechnungSEPAControl;
import eu.snoware.SnowClub.keys.Beitragsmodel;

public class AbrechnungSEPAView extends AbstractView
{

  @Override
  public void bind() throws Exception
  {
    GUI.getView().setTitle("Abrechnung");

    final AbrechnungSEPAControl control = new AbrechnungSEPAControl(this);

    LabelGroup group = new LabelGroup(getParent(), "Parameter");
    group.addLabelPair("Modus", control.getAbbuchungsmodus());
    group.addLabelPair("F�lligkeit SEPA (Erst-/Einzel-Lastschrift)",
        control.getFaelligkeit1());
    group.addLabelPair("F�lligkeit SEPA (Folge-/Letzte-Lastschrift)",
        control.getFaelligkeit2());
    if (Einstellungen.getEinstellung()
        .getBeitragsmodel() == Beitragsmodel.FLEXIBEL)
    {
      group.addLabelPair("Abrechnungsmonat", control.getAbrechnungsmonat());
    }
    group.addLabelPair("Stichtag", control.getStichtag());
    group.addLabelPair("Von Eingabedatum", control.getVondatum());
    group.addLabelPair("Bis Austrittsdatum", control.getBisdatum());
    group.addLabelPair("Zahlungsgrund f�r Beitr�ge",
        control.getZahlungsgrund());
    group.addLabelPair("Zusatzbetr�ge", control.getZusatzbetrag());
    if (!Einstellungen.getEinstellung().getZusatzbetrag())
    {
      control.getZusatzbetrag().setEnabled(false);
    }
    group.addLabelPair("Kursteilnehmer", control.getKursteilnehmer());
    group.addLabelPair("Kompakte Abbuchung", control.getKompakteAbbuchung());
    group.addLabelPair("SEPA-Datei drucken", control.getSEPAPrint());

    if (!Einstellungen.getEinstellung().getKursteilnehmer())
    {
      control.getKursteilnehmer().setEnabled(false);
    }
    group.addLabelPair("Abbuchungsausgabe", control.getAbbuchungsausgabe());
    group.addSeparator();
    group.addText(
        "*) f�r die Berechnung, ob ein Mitglied bereits eingetreten oder ausgetreten ist. ",
        true);

    ButtonArea buttons = new ButtonArea();
    buttons.addButton("Hilfe", new DokumentationAction(),
        DokumentationUtil.ABRECHNUNG, false, "question-circle.png");
    buttons.addButton("Fehler/Warnungen/Hinweise", new Action()
    {

      @Override
      public void handleAction(Object context)
      {
        GUI.startView(SEPABugsView.class.getName(), null);
      }
    }, null, false, "bug.png");
    buttons.addButton(control.getStartButton());
    buttons.paint(this.getParent());
  }
}
