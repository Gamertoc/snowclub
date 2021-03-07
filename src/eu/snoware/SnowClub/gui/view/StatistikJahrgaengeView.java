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
import de.willuhn.jameica.messaging.StatusBarMessage;
import de.willuhn.jameica.system.Application;
import eu.snoware.SnowClub.Einstellungen;
import eu.snoware.SnowClub.gui.action.DokumentationAction;
import eu.snoware.SnowClub.gui.action.StatistikJahrgaengeExportAction;
import eu.snoware.SnowClub.gui.control.MitgliedControl;

public class StatistikJahrgaengeView extends AbstractView
{

  @Override
  public void bind() throws Exception
  {
    GUI.getView().setTitle("Statistik Jahrg�nge");

    final MitgliedControl control = new MitgliedControl(this);

    LabelGroup group = new LabelGroup(getParent(), "Parameter");
    group.addLabelPair("Jahr", control.getJubeljahr());

    ButtonArea buttons = new ButtonArea();
    buttons.addButton("Hilfe", new DokumentationAction(),
        DokumentationUtil.JUBILAEEN, false, "question-circle.png");
    Button btnStart = new Button("Start", new StatistikJahrgaengeExportAction(),
        control.getJubeljahr(), true, "walking.png");
    if (!Einstellungen.getEinstellung().getGeburtsdatumPflicht())
    {
      btnStart.setEnabled(false);
      Application.getMessagingFactory().sendMessage(new StatusBarMessage(
          "Einstellungen->Anzeige->Geburtsdatum: keine Pflicht. Die Statistik kann nicht erstellt werden.",
          StatusBarMessage.TYPE_ERROR));

    }
    buttons.addButton(btnStart);

    buttons.paint(getParent());
  }
}
