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
import eu.snoware.SnowClub.gui.control.SpendenbescheinigungAutoNeuControl;

public class SpendenbescheinigungAutoNeuView extends AbstractView
{

  @Override
  public void bind() throws Exception
  {
    GUI.getView().setTitle("Spendenbescheinigungen automatisch neu erzeugen");

    SpendenbescheinigungAutoNeuControl control = new SpendenbescheinigungAutoNeuControl(
        this);

    LabelGroup group = new LabelGroup(getParent(), "Jahr");
    group.addLabelPair("Jahr", control.getJahr());
    // TODO Unterscheidung Einzel/Sammel-Best�tigung: zwei Felder
    group.addLabelPair("Formular-Einzelbest.", control.getFormular());
    group.addLabelPair("Formular-Sammelbest.",
        control.getFormularSammelbestaetigung());

    control.getSpendenbescheinigungTree().paint(this.getParent());

    ButtonArea buttons = new ButtonArea();
    buttons.addButton(control.getSpendenbescheinigungErstellenButton());
    buttons.addButton("Hilfe", new DokumentationAction(),
        DokumentationUtil.SPENDENBESCHEINIGUNG, false, "question-circle.png");
    buttons.paint(getParent());
  }
}
